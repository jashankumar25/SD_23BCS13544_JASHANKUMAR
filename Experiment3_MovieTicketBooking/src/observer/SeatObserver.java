package observer;

// Observer interface for the Observer Pattern
public interface SeatObserver {
    void onSeatStatusChanged(String showId, String seatId, boolean isBooked, int availableSeats);
}
