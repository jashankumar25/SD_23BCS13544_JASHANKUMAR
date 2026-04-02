package strategy;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public boolean pay(double amount) {
        String maskedCard = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        System.out.printf("    [CREDIT CARD] Processing Rs.%.2f via card %s (Holder: %s)...%n",
                amount, maskedCard, cardHolder);
        System.out.println("    [CREDIT CARD] Payment authorized successfully.");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}
