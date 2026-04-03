package model;

public class Seat {
    private String seatNumber;
    private boolean isBooked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public String getSeatNumber() { return seatNumber; }
    public boolean isBooked() { return isBooked; }
    
    public void bookSeat() {
        this.isBooked = true;
    }
    
    public void cancelBooking() {
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Seat " + seatNumber + (isBooked ? " (Booked)" : " (Available)");
    }
}
