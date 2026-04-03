package model;

import java.util.List;
import java.util.UUID;

public class Booking {
    private String bookingId;
    private User user; // Association: A booking must have a user associated
    private Show show; // Association: A booking is for a particular show
    private List<Seat> bookedSeats; // Aggregation: Booked seats are just a collection of existing seats in the Show

    public Booking(User user, Show show, List<Seat> bookedSeats) {
        // Generating a simple 8-character ID for demonstration
        this.bookingId = UUID.randomUUID().toString().substring(0, 8);
        this.user = user;
        this.show = show;
        this.bookedSeats = bookedSeats;
        
        // Mark seats as booked
        for (Seat seat : bookedSeats) {
            seat.bookSeat();
        }
    }

    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getBookedSeats() { return bookedSeats; }

    public void displayBookingDetails() {
        System.out.println("--- Booking Receipt ---");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("User: " + user.getName());
        System.out.println("Movie: " + show.getMovie().getTitle());
        System.out.println("Time: " + show.getTime());
        System.out.print("Seats: ");
        for (Seat seat : bookedSeats) {
            System.out.print(seat.getSeatNumber() + " ");
        }
        System.out.println("\n-----------------------");
    }
}
