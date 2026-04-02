package model;

import observer.Follower;
import java.util.ArrayList;
import java.util.List;

public class User implements Follower {
    private String userId;
    private String username;
    private List<Playlist> playlists;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.playlists = new ArrayList<>();
    }
    
    public String getUserId() { return userId; }
    public String getUsername() { return username; }

    public void createPlaylist(String name) {
        String id = "PL" + (playlists.size() + 1);
        playlists.add(new Playlist(id, name));
        System.out.println("    [" + username + "] Created playlist: " + name);
    }

    public Playlist getPlaylist(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    @Override
    public void update(String artistName, Song newSong) {
        System.out.println("    [NOTIFICATION TO " + username + "] Your favorite artist " + 
                artistName + " just released a new song: " + newSong.getTitle() + "!");
    }
}
