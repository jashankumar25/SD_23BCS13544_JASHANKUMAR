package model;

public class Booking {
    private static int bookingCounter = 1000;
    private String bookingId;
    private Show show;
    private Seat seat;
    private double totalAmount;
    private String paymentMethod;
    private String status;

    public Booking(Show show, Seat seat, double totalAmount, String paymentMethod) {
        this.bookingId = "BK" + (++bookingCounter);
        this.show = show;
        this.seat = seat;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = "CONFIRMED";
    }

    public String getBookingId() { return bookingId; }
    public Show getShow() { return show; }
    public Seat getSeat() { return seat; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }

    public void cancel() {
        this.status = "CANCELLED";
    }

    public void printTicket() {
        System.out.println("\n    +--------------------------------------------------+");
        System.out.println("    |           MOVIE TICKET - BOOKING RECEIPT          |");
        System.out.println("    +--------------------------------------------------+");
        System.out.printf("    | Booking ID   : %-33s |\n", bookingId);
        System.out.printf("    | Movie        : %-33s |\n", show.getMovie().getTitle());
        System.out.printf("    | Format       : %-33s |\n", show.getMovie().getFormat());
        System.out.printf("    | Show Time    : %-33s |\n", show.getShowTime());
        System.out.printf("    | Screen       : %-33s |\n", show.getScreenName());
        System.out.printf("    | Seat         : %-33s |\n", seat.getSeatId() + " (" + seat.getCategory() + ")");
        System.out.printf("    | Amount Paid  : Rs.%-30.2f |\n", totalAmount);
        System.out.printf("    | Payment Via  : %-33s |\n", paymentMethod);
        System.out.printf("    | Status       : %-33s |\n", status);
        System.out.println("    +--------------------------------------------------+");
    }
}
