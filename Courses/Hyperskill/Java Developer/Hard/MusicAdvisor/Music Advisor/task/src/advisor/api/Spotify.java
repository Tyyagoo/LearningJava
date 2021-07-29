package advisor.api;

import advisor.exceptions.UnauthorizedException;
import advisor.utils.Console;
import com.google.gson.*;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Spotify {
    private static final int TEST_PORT = new Random().nextInt(20000) + 9000;
    private static final int LOCAL_PORT = 8080;

    private static final Gson gson = new Gson();
    private static final String clientID = "0d7cdbd67fde4af792a1745e5c10605f";
    private static final String clientSecret = System.getenv("CLIENT_SECRET");
    private static final int port = TEST_PORT;
    private static final String redirectURI = String.format("http://localhost:%s", port);

    private final HttpServer server;
    private final HttpClient client;

    private final String accessUrl;
    private final String apiUrl;
    private volatile String code = null;
    private volatile boolean serverIsClosed = true;
    private boolean authenticated = false;

    private Map<String, String> tokenUtils = new HashMap<>();
    private final Timer TOKEN_TIMER = new Timer();

    public Spotify(String url, String api) throws IOException {
        this.accessUrl = url;
        this.apiUrl = api;
        this.server = HttpServer.create(new InetSocketAddress(port), 10);
        this.client = HttpClient.newBuilder().build();
    }

    public void auth() {
        if (authenticated) return;
        initializeSever();
        String authUrl = getOAuthUrl();
        Console.print("use this link to request the access code:\n%s\nwaiting for code...", authUrl);
        while (!serverIsClosed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (code != null) {
            getToken();
        }
    }

    public void getToken() {
        Console.print("code received\nmaking http request for access_token...");

        String body = String.format("grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s", code, redirectURI, clientID, clientSecret);
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(String.format("%s/api/token", accessUrl)))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jsonObject.has("error")) {
                Console.println(jsonObject.get("error").getAsJsonObject().get("message").getAsString());
                return;
            }
            setToken(jsonObject);
            tokenUtils.put("refresh_token", jsonObject.get("refresh_token").getAsString());
            authenticated = true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setToken(JsonObject jsonObject) {
        tokenUtils.put("access_token", jsonObject.get("access_token").getAsString());
        tokenUtils.put("token_type", jsonObject.get("token_type").getAsString());
        tokenUtils.put("expires_in", jsonObject.get("expires_in").getAsString());
        TimerTask tokenExpirationTimer = new TimerTask() {
            @Override
            public void run() {
                refreshToken();
            }
        };
        TOKEN_TIMER.schedule(tokenExpirationTimer, TimeUnit.SECONDS.toMillis(Long.parseLong(tokenUtils.get("expires_in"))));
    }

    public void refreshToken() {
        String body = String.format("grant_type=refresh_token&refresh_token=%s", tokenUtils.get("refresh_token"));
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization: Basic", String.format("%s:%s", clientID, clientSecret))
                .uri(URI.create(String.format("%s/api/token", accessUrl)))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jsonObject.has("error")) {
                Console.println(jsonObject.get("error").getAsJsonObject().get("message").getAsString());
                return;
            }
            setToken(jsonObject);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void initializeSever() {
        server.createContext("/", httpExchange -> {
            // http:localhost:8080?code=123
            String query = httpExchange.getRequestURI().getQuery();
            if (query == null) query ="void";
            if (query.contains("code=")) {
                String[] tokens = query.split("=");
                if (tokens.length == 2) {
                    code = tokens[1];
                }
            }

            String response = (code != null) ? "Got the code. Return back to your program." :
                    "Authorization code not found. Try again.";
            httpExchange.sendResponseHeaders(200, response.length());
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
            if (code != null) {
                Console.println("Closing the server.");
                serverIsClosed = true;
                server.stop(10);
            }
        });
        serverIsClosed = false;
        server.start();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getOAuthUrl() {
        return String.format("%s/authorize?client_id=%s&response_type=code&redirect_uri=%s", accessUrl, clientID, redirectURI);
    }

    public JsonObject GET(String req) throws UnauthorizedException {
        if (!isAuthenticated()) throw new UnauthorizedException();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", String.format("%s %s", tokenUtils.get("token_type"), tokenUtils.get("access_token")))
                .uri(URI.create(String.format("%s/v1/browse/%s", apiUrl, req)))
                .build();
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void POST() throws UnauthorizedException {
        if (!isAuthenticated()) throw new UnauthorizedException();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
