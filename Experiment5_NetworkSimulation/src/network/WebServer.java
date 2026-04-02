package network;

import model.HttpRequest;
import model.HttpResponse;

public class WebServer {
    private String serverId;

    public WebServer(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() { return serverId; }

    public HttpResponse handleRequest(HttpRequest request) {
        System.out.println("    [WEB SERVER] " + serverId + " received request -> " + request.getMethod() + " " + request.getUrl());
        try { Thread.sleep(400); } catch (InterruptedException e) {} // Simulate processing
        
        String routingPath = request.getUrl().replace("https://" + request.getHost(), "");
        
        if (routingPath.equals("/") || routingPath.isEmpty()) {
            return new HttpResponse(200, "NGINX/" + serverId, "<html><body><h1>Welcome to " + request.getHost() + "</h1></body></html>");
        } else if (routingPath.startsWith("/api")) {
            return new HttpResponse(200, "Node.js/" + serverId, "{\"status\":\"success\", \"message\":\"API hit registered.\"}");
        } else {
            return new HttpResponse(404, "NGINX/" + serverId, "<html><body><h1>404 Not Found</h1></body></html>");
        }
    }
}
