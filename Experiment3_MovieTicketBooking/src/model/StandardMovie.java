package model;

public class StandardMovie extends Movie {
    public StandardMovie(String title, String genre, int durationMinutes, double basePrice) {
        super(title, genre, durationMinutes, basePrice);
    }

    @Override
    public String getFormat() {
        return "2D";
    }
}
