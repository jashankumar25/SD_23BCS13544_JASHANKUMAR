package strategy;

// Strategy interface for payments
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethodName();
}
