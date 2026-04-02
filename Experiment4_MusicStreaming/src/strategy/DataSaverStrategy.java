package strategy;

import model.Song;

public class DataSaverStrategy implements StreamingStrategy {
    @Override
    public void stream(Song song) {
        System.out.println("    [STREAM] Compressing to 96kbps AAC for: " + song.getTitle());
        System.out.println("    [STREAM] Note: Data saving mode active. Fast buffering.");
        System.out.println("    [PLAYING] ♪♪ " + song.getTitle() + " ♪♪ (Data Saver)");
    }

    @Override
    public String getQualityName() { return "Data Saver"; }
}
