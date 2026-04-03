package model;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String showId;
    private Movie movie; // Association/Aggregation: Show knows about the Movie
    private String time;
    private List<Seat> seats; // Composition: Seats are tightly bound to this specific show instance

    public Show(String showId, Movie movie, String time, int totalSeats) {
        this.showId = showId;
        this.movie = movie;
        this.time = time;
        this.seats = new ArrayList<>();
        
        // Composition: Creating seats specifically for this show.
        // If the show is destroyed, these specific seat states are destroyed too.
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat("S" + i));
        }
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public String getTime() { return time; }
    public List<Seat> getSeats() { return seats; }
    
    public Seat getSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber)) {
                return seat;
            }
        }
        return null;
    }
    
    public void displayAvailableSeats() {
        System.out.println("Available seats for " + movie.getTitle() + " at " + time + ":");
        for (Seat seat : seats) {
            if (!seat.isBooked()) {
                System.out.print(seat.getSeatNumber() + " ");
            }
        }
        System.out.println();
    }
}
