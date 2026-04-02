package strategy;

public class NetBankingPayment implements PaymentStrategy {
    private String bankName;
    private String accountId;

    public NetBankingPayment(String bankName, String accountId) {
        this.bankName = bankName;
        this.accountId = accountId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("    [NET BANKING] Redirecting to %s portal for Rs.%.2f (Account: %s)...%n",
                bankName, amount, accountId);
        System.out.println("    [NET BANKING] Transaction approved by " + bankName + ".");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Net Banking (" + bankName + ")";
    }
}
