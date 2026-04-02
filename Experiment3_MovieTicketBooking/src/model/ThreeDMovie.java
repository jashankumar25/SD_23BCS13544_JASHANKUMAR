package model;

public class ThreeDMovie extends Movie {
    public ThreeDMovie(String title, String genre, int durationMinutes, double basePrice) {
        super(title, genre, durationMinutes, basePrice * 1.4); // 3D surcharge
    }

    @Override
    public String getFormat() {
        return "3D";
    }
}
