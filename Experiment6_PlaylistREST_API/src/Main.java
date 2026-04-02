import controller.PlaylistController;
import service.RateLimiter;
import strategy.CollaborativeFilteringStrategy;
import strategy.ContentBasedStrategy;

/**
 * Experiment 6: REST APIs for Playlist & Recommendation Service
 * 
 * Includes:
 *   1. HTTP Response Modeling (ApiResponse wrapper)
 *   2. Rate Limiting (Token Bucket / Fixed Window logic)
 *   3. Cache-Aside Strategy (Mocked Redis and SQL)
 *   4. Strategy Pattern (Collaborative vs Content-Based recommendations)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║      EXPERIMENT 6: PLAYLIST & RECOMMENDATION REST APIs               ║");
        System.out.println("║      Features: Redis Cache-Aside, Rate Limiting, Strategy Pattern    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

        // Allow max 3 requests per IP window
        RateLimiter rateLimiter = new RateLimiter(3);
        
        // Start with Collaborative filtering
        PlaylistController api = new PlaylistController(rateLimiter, new CollaborativeFilteringStrategy());

        String clientIp = "192.168.1.100";

        // ─────────────────────────────────────────────
        // 1. CACHE-ASIDE PATTERN: FETCH PLAYLIST
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 1: CACHE-ASIDE PATTERN (Read-Through)");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        System.out.println("\n--- Request 1: GET /api/v1/playlists/PL100 (First Call) ---");
        System.out.println(api.getPlaylist(clientIp, "PL100"));

        System.out.println("\n--- Request 2: GET /api/v1/playlists/PL100 (Subsequent Call) ---");
        System.out.println(api.getPlaylist(clientIp, "PL100"));

        // ─────────────────────────────────────────────
        // 2. RATE LIMITING
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 2: API RATE LIMITING (Max 3 req/window)");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        System.out.println("\n--- Request 3: GET /api/v1/playlists/PL101 ---");
        System.out.println(api.getPlaylist(clientIp, "PL101"));

        System.out.println("\n--- Request 4: GET /api/v1/playlists/PL101 (Should be BLOCKED) ---");
        System.out.println(api.getPlaylist(clientIp, "PL101"));

        // Reset rate limit window
        rateLimiter.resetWindow();

        // ─────────────────────────────────────────────
        // 3. CACHE INVALIDATION ON WRITE (PUT Request)
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 3: CACHE INVALIDATION UPDATES (Write)");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        System.out.println("\n--- Request 5: PUT /api/v1/playlists/PL100 ---");
        System.out.println(api.updatePlaylist(clientIp, "PL100", "{\"id\":\"PL100\", \"name\":\"Chill Vibes Vol 2\", \"songs\":46}"));

        System.out.println("\n--- Request 6: GET /api/v1/playlists/PL100 (Post Update, Cache Miss Expected) ---");
        System.out.println(api.getPlaylist(clientIp, "PL100"));

        // ─────────────────────────────────────────────
        // 4. STRATEGY PATTERN: RECOMMENDATIONS
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 4: STRATEGY PATTERN (Dynamic Recommendation Algorithms)");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        System.out.println("\n--- Request 7: GET /api/v1/users/U99/recommendations (Collaborative) ---");
        System.out.println(api.getRecommendations(clientIp, "U99"));
        
        // Switch strategy dynamically without changing controller code
        System.out.println("\n--- Switching Strategy to Content-Based ---");
        api.setRecommendationStrategy(new ContentBasedStrategy());

        System.out.println("\n--- Request 8: GET /api/v1/users/U99/recommendations (Content-Based) ---");
        System.out.println(api.getRecommendations(clientIp, "U99"));

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 6 COMPLETED SUCCESSFULLY");
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
