package observer;

import model.Song;
import java.util.ArrayList;
import java.util.List;

// Subject in Observer Pattern
public class Artist {
    private String name;
    private List<Follower> followers;
    private List<Song> discography;

    public Artist(String name) {
        this.name = name;
        this.followers = new ArrayList<>();
        this.discography = new ArrayList<>();
    }

    public String getName() { return name; }

    public void addFollower(Follower follower) {
        followers.add(follower);
        System.out.println("    [SYSTEM] A new user started following " + name);
    }

    public void removeFollower(Follower follower) {
        followers.remove(follower);
    }

    public void releaseNewSong(Song song) {
        discography.add(song);
        System.out.println("\n    [ARTIST] " + name + " just released a new track: " + song.getTitle());
        notifyFollowers(song);
    }

    private void notifyFollowers(Song song) {
        for (Follower follower : followers) {
            follower.update(this.name, song);
        }
    }
}
