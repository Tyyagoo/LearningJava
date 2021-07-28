package advisor.api;

import advisor.exceptions.UnauthorizedException;

public class Spotify {
    private static final String clientID = "0d7cdbd67fde4af792a1745e5c10605f";
    private static final String redirectUrl = "http://localhost:8080";
    private static final String OAuthUrl = "https://accounts.spotify.com/authorize?client_id=%s&redirect_uri=%s&response_type=code";

    private static boolean authenticated = false;

    public static String doAuth() {
        authenticated = true;
        return String.format(OAuthUrl, clientID, redirectUrl);
    }

    public static void POST() throws UnauthorizedException {
        if (!authenticated) throw new UnauthorizedException();
    }

    public static void GET() throws UnauthorizedException {
        if (!authenticated) throw new UnauthorizedException();
    }

    public static boolean isAuthenticated() {
        return isAuthenticated();
    }
}
