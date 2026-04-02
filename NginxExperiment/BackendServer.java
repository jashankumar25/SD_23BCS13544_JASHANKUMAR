import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class BackendServer {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please provide a port number. Usage: java BackendServer <port>");
            return;
        }
        
        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Handle all paths starting with "/"
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "Response from Backend Server on port " + port;
                
                // Print to console so we can see which server is receiving traffic
                System.out.println("Handled request on port: " + port);
                
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        server.setExecutor(null);
        server.start();
        System.out.println("Backend Server is listening on port " + port);
    }
}
