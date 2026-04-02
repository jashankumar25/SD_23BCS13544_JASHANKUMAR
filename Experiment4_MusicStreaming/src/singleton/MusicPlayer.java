package singleton;

import model.Playlist;
import model.Song;
import strategy.StreamingStrategy;

// Singleton Pattern
public class MusicPlayer {
    private static MusicPlayer instance;
    private StreamingStrategy streamingStrategy;
    private Song currentSong;
    private boolean isPlaying;

    private MusicPlayer() {
        this.isPlaying = false;
    }

    public static synchronized MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void setStreamingStrategy(StreamingStrategy strategy) {
        this.streamingStrategy = strategy;
        System.out.println("    [PLAYER] Current streaming quality set to: " + strategy.getQualityName());
    }

    public void playSong(Song song) {
        this.currentSong = song;
        this.isPlaying = true;
        System.out.println("\n    [PLAYER] Loading track...");
        if (streamingStrategy != null) {
            streamingStrategy.stream(song);
        } else {
            System.out.println("    [PLAYER] Playing " + song.getTitle() + " at Default Quality");
        }
    }

    public void pause() {
        if (isPlaying && currentSong != null) {
            this.isPlaying = false;
            System.out.println("    [PLAYER] Paused: " + currentSong.getTitle());
        }
    }

    public void playPlaylist(Playlist playlist) {
        System.out.println("\n    [PLAYER] Starting Playlist: " + playlist.getName());
        System.out.println("    " + "=".repeat(50));
        for (Song song : playlist.getSongs()) {
            playSong(song);
            try { Thread.sleep(600); } catch (InterruptedException e) {} // Simulating playing time
        }
        System.out.println("    [PLAYER] Playlist finished.");
    }
}
