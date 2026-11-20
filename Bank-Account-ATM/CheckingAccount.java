public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String customerName,
                           double balance, int pin,
                           double overdraftLimit) {

        super(accountNumber, customerName, balance, pin);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (amount > balance + overdraftLimit) {
            throw new InsufficientFundsException(
                "Withdrawal exceeds overdraft limit!"
            );
        }

        balance -= amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Remaining Balance: " + balance);
    }

    @Override
    public void calculateInterest() {

        System.out.println(
            "Checking accounts do not earn interest."
        );
    }
}
