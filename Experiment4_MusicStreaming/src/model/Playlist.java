package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String playlistId;
    private String name;
    private List<Song> songs;

    public Playlist(String playlistId, String name) {
        this.playlistId = playlistId;
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public String getPlaylistId() { return playlistId; }
    public String getName() { return name; }
    public List<Song> getSongs() { return songs; }

    public void displayPlaylist() {
        System.out.println("🎵 Playlist: " + name);
        if (songs.isEmpty()) {
            System.out.println("   [Empty]");
        } else {
            for (int i = 0; i < songs.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + songs.get(i).toString());
            }
        }
    }
}
