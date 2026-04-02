package strategy;

public class UPIPayment implements PaymentStrategy {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("    [UPI] Initiating payment of Rs.%.2f to UPI ID: %s...%n", amount, upiId);
        System.out.println("    [UPI] Payment confirmed via UPI.");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "UPI";
    }
}
