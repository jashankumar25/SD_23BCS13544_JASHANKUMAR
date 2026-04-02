package model;

import java.util.ArrayList;
import java.util.List;
import observer.SeatObserver;

public class Show {
    private String showId;
    private Movie movie;
    private String showTime;
    private String screenName;
    private List<Seat> seats;
    private List<SeatObserver> observers;

    public Show(String showId, Movie movie, String showTime, String screenName) {
        this.showId = showId;
        this.movie = movie;
        this.showTime = showTime;
        this.screenName = screenName;
        this.seats = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void initializeSeats(int goldCount, int silverCount, int platinumCount) {
        int seatNum = 1;
        for (int i = 0; i < platinumCount; i++) {
            seats.add(new Seat("P" + seatNum++, "PLATINUM", 2.0));
        }
        seatNum = 1;
        for (int i = 0; i < goldCount; i++) {
            seats.add(new Seat("G" + seatNum++, "GOLD", 1.5));
        }
        seatNum = 1;
        for (int i = 0; i < silverCount; i++) {
            seats.add(new Seat("S" + seatNum++, "SILVER", 1.0));
        }
    }

    // Observer Pattern methods
    public void addObserver(SeatObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SeatObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String seatId, boolean isBooked) {
        for (SeatObserver obs : observers) {
            obs.onSeatStatusChanged(showId, seatId, isBooked, getAvailableSeatsCount());
        }
    }

    public boolean bookSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId) && !seat.isBooked()) {
                seat.book();
                notifyObservers(seatId, true);
                return true;
            }
        }
        return false;
    }

    public boolean cancelSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId) && seat.isBooked()) {
                seat.release();
                notifyObservers(seatId, false);
                return true;
            }
        }
        return false;
    }

    public Seat getSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId)) return seat;
        }
        return null;
    }

    public int getAvailableSeatsCount() {
        int count = 0;
        for (Seat seat : seats) {
            if (!seat.isBooked()) count++;
        }
        return count;
    }

    public int getTotalSeatsCount() {
        return seats.size();
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public String getShowTime() { return showTime; }
    public String getScreenName() { return screenName; }
    public List<Seat> getSeats() { return seats; }

    public void displaySeats() {
        System.out.println("\n    Seat Map for Show: " + showId + " | " + movie.getTitle() + " | " + showTime);
        System.out.println("    Screen: " + screenName);
        System.out.println("    " + "=".repeat(60));

        System.out.print("    PLATINUM: ");
        for (Seat s : seats) {
            if (s.getCategory().equals("PLATINUM")) System.out.print(s + " ");
        }
        System.out.println();

        System.out.print("    GOLD    : ");
        for (Seat s : seats) {
            if (s.getCategory().equals("GOLD")) System.out.print(s + " ");
        }
        System.out.println();

        System.out.print("    SILVER  : ");
        for (Seat s : seats) {
            if (s.getCategory().equals("SILVER")) System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("    Available: " + getAvailableSeatsCount() + "/" + getTotalSeatsCount());
    }
}
