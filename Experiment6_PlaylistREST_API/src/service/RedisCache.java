package service;

import java.util.HashMap;
import java.util.Map;

// Simulates a Redis key-value store for Cache-Aside implementation
public class RedisCache {
    private static RedisCache instance;
    private Map<String, String> cache;

    private RedisCache() {
        this.cache = new HashMap<>();
    }

    public static synchronized RedisCache getInstance() {
        if (instance == null) {
            instance = new RedisCache();
        }
        return instance;
    }

    public String get(String key) {
        if (cache.containsKey(key)) {
            System.out.println("    [REDIS] Cache HIT for key: " + key);
            return cache.get(key);
        }
        System.out.println("    [REDIS] Cache MISS for key: " + key);
        return null;
    }

    public void set(String key, String value) {
        System.out.println("    [REDIS] Saving to cache -> key: " + key);
        cache.put(key, value);
    }
    
    public void delete(String key) {
        if(cache.remove(key) != null) {
            System.out.println("    [REDIS] Invalidated cache for key: " + key);
        }
    }
}
