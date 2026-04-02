package network;

import java.util.HashMap;
import java.util.Map;

public class DnsServer {
    private Map<String, String> dnsRecords;

    public DnsServer() {
        dnsRecords = new HashMap<>();
        // Pre-populating DNS Simulation records
        dnsRecords.put("www.musicstream.com", "104.21.34.12");
        dnsRecords.put("api.musicstream.com", "104.21.34.45");
        dnsRecords.put("www.movietickets.com", "192.168.10.5");
    }

    public String resolve(String hostname) {
        System.out.println("    [DNS LOOKUP] Resolving hostname: " + hostname);
        try { Thread.sleep(300); } catch (InterruptedException e) {} // Simulate latency
        
        String ipAddress = dnsRecords.get(hostname);
        if (ipAddress != null) {
            System.out.println("    [DNS SUCCESS] " + hostname + " resolved to IP: " + ipAddress);
            return ipAddress;
        } else {
            System.out.println("    [DNS ERROR] NXDOMAIN - Domain not found.");
            return null;
        }
    }
}
