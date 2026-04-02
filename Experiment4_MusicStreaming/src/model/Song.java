package model;

public class Song {
    private String songId;
    private String title;
    private String artist;
    private String album;
    private int durationSeconds;

    public Song(String songId, String title, String artist, String album, int durationSeconds) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSeconds = durationSeconds;
    }

    public String getSongId() { return songId; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSeconds() { return durationSeconds; }

    @Override
    public String toString() {
        return String.format("%s by %s (Album: %s) [%02d:%02d]", 
                title, artist, album, durationSeconds / 60, durationSeconds % 60);
    }
}
