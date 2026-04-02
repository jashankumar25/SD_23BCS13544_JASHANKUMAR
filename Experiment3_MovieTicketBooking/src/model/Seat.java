package model;

public class Seat {
    private String seatId;
    private String category; // GOLD, SILVER, PLATINUM
    private boolean isBooked;
    private double priceMultiplier;

    public Seat(String seatId, String category, double priceMultiplier) {
        this.seatId = seatId;
        this.category = category;
        this.priceMultiplier = priceMultiplier;
        this.isBooked = false;
    }

    public String getSeatId() { return seatId; }
    public String getCategory() { return category; }
    public boolean isBooked() { return isBooked; }
    public double getPriceMultiplier() { return priceMultiplier; }

    public void book() {
        this.isBooked = true;
    }

    public void release() {
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return String.format("[%s-%s:%s]", seatId, category, isBooked ? "XX" : "OK");
    }
}
