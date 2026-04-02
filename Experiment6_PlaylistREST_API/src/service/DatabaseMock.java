package service;

import java.util.HashMap;
import java.util.Map;

public class DatabaseMock {
    private static Map<String, String> db = new HashMap<>();
    
    static {
        db.put("PL100", "{\"id\":\"PL100\", \"name\":\"Chill Vibes\", \"songs\":45}");
        db.put("PL101", "{\"id\":\"PL101\", \"name\":\"Gym Beast\", \"songs\":30}");
    }

    public static String getPlaylist(String id) {
        System.out.println("    [DATABASE] Querying SQL Database for Playlist " + id + " (Disk I/O)...");
        try { Thread.sleep(500); } catch(Exception e){} // simulate network/db lag
        return db.get(id);
    }
    
    public static void updatePlaylist(String id, String content) {
        System.out.println("    [DATABASE] Updating SQL Database for Playlist " + id + "...");
        db.put(id, content);
    }
}
