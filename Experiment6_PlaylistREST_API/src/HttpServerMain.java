import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.PlaylistController;
import model.ApiResponse;
import service.RateLimiter;
import strategy.CollaborativeFilteringStrategy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpServerMain {
    public static void main(String[] args) throws IOException {
        final int port = (args.length > 0) ? Integer.parseInt(args[0]) : 3000;

        // Initialize our PlaylistController exactly like Main.java
        RateLimiter rateLimiter = new RateLimiter(5000); // Set high limit for load testing!
        PlaylistController api = new PlaylistController(rateLimiter, new CollaborativeFilteringStrategy());

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serve GET and PUT /api/v1/playlists/{id}
        server.createContext("/api/v1/playlists/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");
                String playlistId = parts[parts.length - 1]; // naive extract last part
                String method = exchange.getRequestMethod();

                // Add CORS headers for our Web UI!
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, PUT, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                if ("OPTIONS".equalsIgnoreCase(method)) {
                    exchange.sendResponseHeaders(204, -1);
                    exchange.close();
                    return;
                }

                ApiResponse<String> responsePayload = null;

                if ("GET".equalsIgnoreCase(method)) {
                    responsePayload = api.getPlaylist("127.0.0.1", playlistId);
                } else if ("PUT".equalsIgnoreCase(method)) {
                    responsePayload = api.updatePlaylist("127.0.0.1", playlistId, "{\"id\":\"" + playlistId + "\", \"name\":\"Updated\", \"songs\":42}");
                } else {
                    responsePayload = new ApiResponse<>(405, "Method Not Allowed", null);
                }

                // Prepend our unique Server Port so we can visually see Nginx load balancing!
                String responseStr = "[SERVED BY PORT " + port + "] " + responsePayload.toString();
                
                System.out.println("Handled " + method + " request for playlist " + playlistId + " on PORT " + port);

                exchange.sendResponseHeaders(responsePayload.getStatusCode(), responseStr.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseStr.getBytes());
                os.close();
            }
        });

        // Serve GET /api/v1/users/{id}/recommendations
        server.createContext("/api/v1/users/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");
                // Expected path: /api/v1/users/{userId}/recommendations
                String userId = parts.length > 4 ? parts[4] : "U00"; 
                
                // You can add logic to switch algorithms based on a query param if needed
                ApiResponse<String> responsePayload = api.getRecommendations("127.0.0.1", userId);

                String responseStr = "[SERVED BY PORT " + port + "] " + responsePayload.toString();
                
                System.out.println("Handled GET request for recommendations for user " + userId + " on PORT " + port);

                exchange.sendResponseHeaders(responsePayload.getStatusCode(), responseStr.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseStr.getBytes());
                os.close();
            }
        });

        server.setExecutor(null); 
        server.start();
        System.out.println("Playlist REST API Backend is listening on port " + port);
    }
}
