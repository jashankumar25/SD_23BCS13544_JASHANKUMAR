package model;

// Base Movie class
public abstract class Movie {
    protected String title;
    protected String genre;
    protected int durationMinutes;
    protected double basePrice;

    public Movie(String title, String genre, int durationMinutes, double basePrice) {
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.basePrice = basePrice;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getBasePrice() { return basePrice; }

    public abstract String getFormat();

    @Override
    public String toString() {
        return String.format("%-30s | Genre: %-12s | Duration: %d min | Format: %-6s | Base Price: Rs.%.2f",
                title, genre, durationMinutes, getFormat(), basePrice);
    }
}
