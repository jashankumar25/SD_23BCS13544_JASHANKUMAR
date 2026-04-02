package observer;

import model.Song;

public interface Follower {
    void update(String artistName, Song newSong);
}
