package strategy;

import model.Song;

public class HighResLosslessStrategy implements StreamingStrategy {
    @Override
    public void stream(Song song) {
        System.out.println("    [STREAM] Preparing FLAC buffer (24-bit/192kHz) for: " + song.getTitle());
        System.out.println("    [STREAM] Note: Higher bandwidth required.");
        System.out.println("    [PLAYING] ♪♪ " + song.getTitle() + " ♪♪ (High-Res Lossless)");
    }

    @Override
    public String getQualityName() { return "High-Resolution Lossless"; }
}
