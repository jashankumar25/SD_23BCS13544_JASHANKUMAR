import model.Movie;
import model.Show;
import model.Seat;
import model.User;
import model.Booking;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Movie Ticket Booking System ===");
        
        // 1. Independent Object: Movie
        Movie incep = new Movie("Inception", "Sci-Fi", 148);
        System.out.println("Created Movie: " + incep);
        
        // 2. Association/Aggregation & Composition
        // Show aggregates Movie (Show has a reference to an independent Movie)
        // Show composes Seats (Show creates specific Seat objects that belong directly to it)
        Show show1 = new Show("SH101", incep, "18:00", 10); // A show with 10 seats
        System.out.println("Created Show: " + show1.getShowId() + " for movie " + show1.getMovie().getTitle());
        
        // Display available seats
        show1.displayAvailableSeats();
        
        // 3. Independent Object: User
        User alice = new User("Alice", "alice@example.com");
        
        // 4. Association & Aggregation
        // Booking associates with User and Show.
        // Booking aggregates Seats (the seats exist independently in the Show, Booking just holds a reference)
        System.out.println("\n--- Processing Booking ---");
        List<Seat> seatsToBook = new ArrayList<>();
        
        System.out.println("User " + alice.getName() + " is trying to book S3 and S4...");
        Seat s1 = show1.getSeat("S3");
        Seat s2 = show1.getSeat("S4");
        
        if (s1 != null && !s1.isBooked() && s2 != null && !s2.isBooked()) {
            seatsToBook.add(s1);
            seatsToBook.add(s2);
            Booking booking1 = new Booking(alice, show1, seatsToBook);
            booking1.displayBookingDetails();
        } else {
            System.out.println("Seats not available for booking.");
        }
        
        // 5. Display available seats after booking to prove the state changed inside Show
        System.out.println("\nState of show after booking:");
        show1.displayAvailableSeats();
    }
}
