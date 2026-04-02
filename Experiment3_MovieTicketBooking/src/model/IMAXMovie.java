package model;

public class IMAXMovie extends Movie {
    public IMAXMovie(String title, String genre, int durationMinutes, double basePrice) {
        super(title, genre, durationMinutes, basePrice * 1.8); // IMAX surcharge
    }

    @Override
    public String getFormat() {
        return "IMAX";
    }
}
