package service;

import model.*;
import strategy.PaymentStrategy;

public class BookingService {

    public Booking createBooking(Show show, String seatId, PaymentStrategy paymentStrategy) {
        Seat seat = show.getSeat(seatId);
        if (seat == null) {
            System.out.println("    [ERROR] Seat " + seatId + " does not exist in this show.");
            return null;
        }
        if (seat.isBooked()) {
            System.out.println("    [ERROR] Seat " + seatId + " is already booked.");
            return null;
        }

        // Calculate price
        double totalAmount = show.getMovie().getBasePrice() * seat.getPriceMultiplier();

        // Process payment using the Strategy pattern
        boolean paymentSuccess = paymentStrategy.pay(totalAmount);
        if (!paymentSuccess) {
            System.out.println("    [ERROR] Payment failed. Booking cancelled.");
            return null;
        }

        // Book the seat (triggers Observer notifications)
        show.bookSeat(seatId);

        // Create booking
        Booking booking = new Booking(show, seat, totalAmount, paymentStrategy.getPaymentMethodName());
        return booking;
    }

    public boolean cancelBooking(Booking booking) {
        if (booking == null || booking.getStatus().equals("CANCELLED")) {
            System.out.println("    [ERROR] Invalid booking or already cancelled.");
            return false;
        }
        booking.getShow().cancelSeat(booking.getSeat().getSeatId());
        booking.cancel();
        System.out.println("    [SYSTEM] Booking " + booking.getBookingId() + " has been cancelled. Refund initiated.");
        return true;
    }
}
