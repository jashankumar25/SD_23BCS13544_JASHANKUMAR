import model.HttpRequest;
import model.HttpResponse;
import network.DnsServer;
import network.ReverseProxy;

/**
 * Experiment 5: URL-to-Website Request Flow Module
 * 
 * Simulates:
 *   1. DNS Lookup (Resolving Hostname -> IP)
 *   2. Reverse Proxy & API Gateway Behavior
 *   3. Load Balancing to backend Web Servers
 *   4. HTTP Request-Response modeling
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║      EXPERIMENT 5: URL-TO-WEBSITE REQUEST FLOW                       ║");
        System.out.println("║      Simulates: DNS, Proxy, Load Balancing, HTTP Models              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

        // Module Initialization
        DnsServer dns = new DnsServer();
        ReverseProxy proxy = new ReverseProxy();

        System.out.println("\n--- Initiating Request to https://www.musicstream.com/ ---");
        
        // 1. App layer generates a raw request outline
        HttpRequest req1 = new HttpRequest("https://www.musicstream.com/", "GET");
        
        // 2. DNS LOOKUP Phase
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  PHASE 1: DNS Resolution");
        System.out.println("══════════════════════════════════════════════════════════════");
        String resolvedIp = dns.resolve(req1.getHost());
        
        if (resolvedIp == null) {
            System.err.println("Fatal: DNS lookup failed.");
            return;
        }

        // 3. Attach standard headers & IP (Connection establishment simulated)
        req1.setIpAddress(resolvedIp);
        req1.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64)");

        // 4. Reverse Proxy / Gateway Phase
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  PHASE 2: Reverse Proxy & Load Balancing");
        System.out.println("══════════════════════════════════════════════════════════════");
        HttpResponse res1 = proxy.forwardRequest(req1);

        // 5. Response Received
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  PHASE 3: HTTP Response Delivered to Client");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println(res1.toString());


        // ------ SECOND SIMULATION ------
        System.out.println("\n\n--- Initiating API Request to https://api.musicstream.com/api/v1/users ---");
        HttpRequest req2 = new HttpRequest("https://api.musicstream.com/api/v1/users", "POST");
        
        String ip2 = dns.resolve(req2.getHost());
        if(ip2 != null) {
            req2.setIpAddress(ip2);
            HttpResponse res2 = proxy.forwardRequest(req2);
            System.out.println("\n" + res2.toString());
        }

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 5 COMPLETED SUCCESSFULLY");
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
