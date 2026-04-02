import model.*;
import factory.MovieFactory;
import observer.*;
import strategy.*;
import service.BookingService;

/**
 * Experiment 3: Online Movie Ticket Booking System
 * 
 * Design Patterns Demonstrated:
 *   1. Factory Method  - MovieFactory creates Standard, IMAX, and 3D movie objects
 *   2. Observer Pattern - DisplayBoard & Notification observers react to seat status changes
 *   3. Strategy Pattern - Multiple payment methods (CreditCard, UPI, NetBanking)
 * 
 * SOLID Principles Applied:
 *   S - Each class has a single responsibility (Movie, Seat, Show, Booking, Payment)
 *   O - New movie formats / payment methods can be added without modifying existing code
 *   L - StandardMovie, IMAXMovie, ThreeDMovie are substitutable for Movie
 *   I - PaymentStrategy & SeatObserver are small, focused interfaces
 *   D - BookingService depends on abstractions (PaymentStrategy), not concrete classes
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║      EXPERIMENT 3: ONLINE MOVIE TICKET BOOKING SYSTEM               ║");
        System.out.println("║      Design Patterns: Factory Method, Observer, Strategy             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

        // ─────────────────────────────────────────────
        // 1. FACTORY METHOD PATTERN - Creating Movies
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 1: FACTORY METHOD PATTERN — Creating Movie Objects");
        System.out.println("══════════════════════════════════════════════════════════════");

        Movie movie1 = MovieFactory.createMovie("STANDARD", "Pushpa 2: The Rule", "Action", 165, 200.0);
        Movie movie2 = MovieFactory.createMovie("IMAX", "Interstellar Re-Release", "Sci-Fi", 169, 250.0);
        Movie movie3 = MovieFactory.createMovie("3D", "Avatar: Fire and Ash", "Sci-Fi", 190, 300.0);

        System.out.println("  Movies created via MovieFactory:");
        System.out.println("    " + movie1);
        System.out.println("    " + movie2);
        System.out.println("    " + movie3);

        // ─────────────────────────────────────────────
        // 2. SETTING UP SHOWS & SEATS
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 2: Setting Up Shows and Seat Layout");
        System.out.println("══════════════════════════════════════════════════════════════");

        Show show1 = new Show("SH101", movie1, "06:30 PM", "Screen Audi-1");
        show1.initializeSeats(3, 4, 2); // 3 Gold, 4 Silver, 2 Platinum

        Show show2 = new Show("SH102", movie2, "09:00 PM", "IMAX Hall");
        show2.initializeSeats(2, 3, 2); // 2 Gold, 3 Silver, 2 Platinum

        show1.displaySeats();
        show2.displaySeats();

        // ─────────────────────────────────────────────
        // 3. OBSERVER PATTERN - Registering Observers
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 3: OBSERVER PATTERN — Registering Seat Observers");
        System.out.println("══════════════════════════════════════════════════════════════");

        DisplayBoardObserver lobbyBoard = new DisplayBoardObserver("Main Lobby");
        DisplayBoardObserver appDisplay = new DisplayBoardObserver("Mobile App");
        NotificationObserver notifService = new NotificationObserver();

        show1.addObserver(lobbyBoard);
        show1.addObserver(appDisplay);
        show1.addObserver(notifService);

        show2.addObserver(lobbyBoard);
        show2.addObserver(notifService);

        System.out.println("    Observers registered:");
        System.out.println("      - Main Lobby Display Board  -> Show SH101, SH102");
        System.out.println("      - Mobile App Display Board   -> Show SH101");
        System.out.println("      - Notification Service       -> Show SH101, SH102");

        // ─────────────────────────────────────────────
        // 4. STRATEGY PATTERN - Booking with Payments
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 4: STRATEGY PATTERN — Booking Tickets with Payments");
        System.out.println("══════════════════════════════════════════════════════════════");

        BookingService bookingService = new BookingService();

        // Booking 1: Credit Card
        System.out.println("\n  --- Booking 1: Pushpa 2 | Seat G1 | Credit Card ---");
        PaymentStrategy creditCard = new CreditCardPayment("4532015112830366", "Rahul Sharma");
        Booking booking1 = bookingService.createBooking(show1, "G1", creditCard);
        if (booking1 != null) booking1.printTicket();

        // Booking 2: UPI
        System.out.println("\n  --- Booking 2: Pushpa 2 | Seat P1 (Platinum) | UPI ---");
        PaymentStrategy upi = new UPIPayment("priya.kumar@paytm");
        Booking booking2 = bookingService.createBooking(show1, "P1", upi);
        if (booking2 != null) booking2.printTicket();

        // Booking 3: Net Banking
        System.out.println("\n  --- Booking 3: Interstellar IMAX | Seat G2 | Net Banking ---");
        PaymentStrategy netBanking = new NetBankingPayment("HDFC Bank", "HDFC-9087654321");
        Booking booking3 = bookingService.createBooking(show2, "G2", netBanking);
        if (booking3 != null) booking3.printTicket();

        // Booking 4: Another UPI
        System.out.println("\n  --- Booking 4: Pushpa 2 | Seat S1 | UPI ---");
        PaymentStrategy upi2 = new UPIPayment("amit.verma@gpay");
        Booking booking4 = bookingService.createBooking(show1, "S1", upi2);
        if (booking4 != null) booking4.printTicket();

        // ─────────────────────────────────────────────
        // 5. SHOW UPDATED SEAT MAP
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 5: Updated Seat Maps After Bookings");
        System.out.println("══════════════════════════════════════════════════════════════");
        show1.displaySeats();
        show2.displaySeats();

        // ─────────────────────────────────────────────
        // 6. DUPLICATE BOOKING ATTEMPT
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 6: Duplicate Booking Attempt on Already Booked Seat");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println("\n  --- Attempting to re-book Seat G1 in Show SH101 ---");
        PaymentStrategy upi3 = new UPIPayment("duplicate@upi");
        Booking duplicateBooking = bookingService.createBooking(show1, "G1", upi3);

        // ─────────────────────────────────────────────
        // 7. CANCELLATION FLOW
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 7: Booking Cancellation (Observer Notification)");
        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println("\n  --- Cancelling Booking: " + booking1.getBookingId() + " ---");
        bookingService.cancelBooking(booking1);
        booking1.printTicket();

        show1.displaySeats();

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 3 COMPLETED SUCCESSFULLY");
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
