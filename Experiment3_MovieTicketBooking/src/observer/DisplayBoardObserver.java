package observer;

// Concrete observer - Display Board
public class DisplayBoardObserver implements SeatObserver {
    private String boardName;

    public DisplayBoardObserver(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public void onSeatStatusChanged(String showId, String seatId, boolean isBooked, int availableSeats) {
        String action = isBooked ? "BOOKED" : "RELEASED";
        System.out.printf("    [DISPLAY BOARD - %s] Show %s => Seat %s %s | Remaining Seats: %d%n",
                boardName, showId, seatId, action, availableSeats);
    }
}
