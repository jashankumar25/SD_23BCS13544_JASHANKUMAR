package observer;

// Concrete observer - Notification Service
public class NotificationObserver implements SeatObserver {

    @Override
    public void onSeatStatusChanged(String showId, String seatId, boolean isBooked, int availableSeats) {
        if (availableSeats <= 3 && availableSeats > 0) {
            System.out.printf("    [NOTIFICATION] ALERT: Show %s is almost FULL! Only %d seat(s) left.%n",
                    showId, availableSeats);
        } else if (availableSeats == 0) {
            System.out.printf("    [NOTIFICATION] Show %s is now SOLD OUT!%n", showId);
        }
    }
}
