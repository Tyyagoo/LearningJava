package advisor.api;

import advisor.exceptions.UnauthorizedException;
import advisor.utils.Console;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Spotify {
    private static final String clientID = "0d7cdbd67fde4af792a1745e5c10605f";
    private static final String clientSecret = System.getenv("CLIENT_SECRET");
    private static final int port = new Random().nextInt(20000) + 9000;
    private static final String redirectURI = String.format("http://localhost:%d", port);

    private final HttpServer server;
    private final HttpClient client;

    private final String accessUrl;
    private volatile String code = null;
    private volatile boolean serverIsClosed = true;
    private boolean authenticated = false;

    public Spotify(String url) throws IOException {
        this.accessUrl = url;
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
            Console.print("code received\nmaking http request for access_token...");

            String body = String.format("grant_type=authorization_code&code=%s&redirect_uri=%s&client_id=%s&client_secret=%s", code, redirectURI, clientID, clientSecret);
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create(String.format("%s/api/token", accessUrl)))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Console.println(response.body());
                authenticated = true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
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

    public void GET() throws UnauthorizedException {
        if (!isAuthenticated()) throw new UnauthorizedException();
    }

    public void POST() throws UnauthorizedException {
        if (!isAuthenticated()) throw new UnauthorizedException();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
