package service;

import java.util.HashMap;
import java.util.Map;

// Simulates API Rate Limiter using Token Bucket / Fixed Window concept
public class RateLimiter {
    private int maxRequests;
    private Map<String, Integer> requestCounts;

    public RateLimiter(int maxRequests) {
        this.maxRequests = maxRequests;
        this.requestCounts = new HashMap<>();
    }

    public boolean isAllowed(String clientIp) {
        int currentCount = requestCounts.getOrDefault(clientIp, 0);
        if (currentCount >= maxRequests) {
            System.out.println("    [RATE LIMITER] BLOCKING Request from " + clientIp + " (Limit Exceeded)");
            return false;
        }
        requestCounts.put(clientIp, currentCount + 1);
        return true;
    }

    public void resetWindow() {
        requestCounts.clear();
        System.out.println("    [RATE LIMITER] Window reset.");
    }
}
