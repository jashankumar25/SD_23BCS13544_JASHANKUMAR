package network;

import model.HttpRequest;
import model.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReverseProxy {
    
    // Simulate multiple backend app servers
    private List<WebServer> backendServers;
    private Random lbRandomizer;

    public ReverseProxy() {
        backendServers = Arrays.asList(
            new WebServer("Backend-Node-1"),
            new WebServer("Backend-Node-2"),
            new WebServer("Backend-Node-3")
        );
        lbRandomizer = new Random();
    }

    public HttpResponse forwardRequest(HttpRequest request) {
        System.out.println("    [REVERSE PROXY] Terminating SSL/TLS for " + request.getIpAddress());
        System.out.println("    [REVERSE PROXY] Checking WAF rules & Rate limits...");

        try { Thread.sleep(200); } catch (InterruptedException e) {}

        // Simple Load Balancing logic (Round Robin / Random)
        WebServer selectedServer = backendServers.get(lbRandomizer.nextInt(backendServers.size()));
        
        System.out.println("    [LOAD BALANCER] Forwarding request to: " + selectedServer.getServerId());
        
        return selectedServer.handleRequest(request);
    }
}
