package factory;

import model.Movie;
import model.StandardMovie;
import model.IMAXMovie;
import model.ThreeDMovie;

// Factory Method Pattern - MovieFactory
public class MovieFactory {

    public static Movie createMovie(String format, String title, String genre, int duration, double basePrice) {
        switch (format.toUpperCase()) {
            case "IMAX":
                return new IMAXMovie(title, genre, duration, basePrice);
            case "3D":
                return new ThreeDMovie(title, genre, duration, basePrice);
            case "STANDARD":
            case "2D":
                return new StandardMovie(title, genre, duration, basePrice);
            default:
                throw new IllegalArgumentException("Unknown movie format: " + format);
        }
    }
}
