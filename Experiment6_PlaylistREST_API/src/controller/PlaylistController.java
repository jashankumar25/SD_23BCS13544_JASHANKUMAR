package controller;

import model.ApiResponse;
import service.DatabaseMock;
import service.RateLimiter;
import service.RedisCache;
import strategy.RecommendationStrategy;

public class PlaylistController {

    private RateLimiter rateLimiter;
    private RedisCache cache;
    private RecommendationStrategy recStrategy;

    public PlaylistController(RateLimiter rateLimiter, RecommendationStrategy recStrategy) {
        this.rateLimiter = rateLimiter;
        this.cache = RedisCache.getInstance();
        this.recStrategy = recStrategy;
    }

    public void setRecommendationStrategy(RecommendationStrategy strategy) {
        this.recStrategy = strategy;
    }

    // GET /api/v1/playlists/{id}
    public ApiResponse<String> getPlaylist(String clientIp, String playlistId) {
        if (!rateLimiter.isAllowed(clientIp)) {
            return new ApiResponse<>(429, "Too Many Requests", null);
        }

        // Cache-Aside Pattern: 1. Check Cache
        String cacheKey = "playlist:" + playlistId;
        String data = cache.get(cacheKey);

        if (data == null) {
            // Cache-Aside Pattern: 2. Fetch from DB on Cache Miss
            data = DatabaseMock.getPlaylist(playlistId);
            if (data == null) {
                return new ApiResponse<>(404, "Not Found", null);
            }
            // Cache-Aside Pattern: 3. Populate Cache
            cache.set(cacheKey, data);
        }

        return new ApiResponse<>(200, "OK", data);
    }

    // PUT /api/v1/playlists/{id}
    public ApiResponse<String> updatePlaylist(String clientIp, String playlistId, String payload) {
        if (!rateLimiter.isAllowed(clientIp)) {
            return new ApiResponse<>(429, "Too Many Requests", null);
        }

        // Write directly to DB
        DatabaseMock.updatePlaylist(playlistId, payload);

        // Cache Invalidation (evict stale cache)
        String cacheKey = "playlist:" + playlistId;
        cache.delete(cacheKey);

        return new ApiResponse<>(200, "Updated Successfully", payload);
    }

    // GET /api/v1/users/{id}/recommendations
    public ApiResponse<String> getRecommendations(String clientIp, String userId) {
        if (!rateLimiter.isAllowed(clientIp)) {
            return new ApiResponse<>(429, "Too Many Requests", null);
        }

        String cacheKey = "recs:" + userId + ":" + recStrategy.getClass().getSimpleName();
        String data = cache.get(cacheKey);

        if (data == null) {
            // Delegate logic to Strategy
            data = recStrategy.generateRecommendations(userId);
            cache.set(cacheKey, data); // Cache expensive computation
        }

        return new ApiResponse<>(200, "Recommendations Generated", data);
    }
}
