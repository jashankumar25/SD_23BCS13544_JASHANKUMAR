package strategy;

import model.Song;

public interface StreamingStrategy {
    void stream(Song song);
    String getQualityName();
}
