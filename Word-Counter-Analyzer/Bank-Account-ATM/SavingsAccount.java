public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber, String customerName,
                          double balance, int pin, double interestRate) {

        super(accountNumber, customerName, balance, pin);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient balance in savings account!"
            );
        }

        balance -= amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Remaining Balance: " + balance);
    }

    @Override
    public void calculateInterest() {

        double interest = balance * interestRate / 100;

        balance += interest;

        System.out.println("Interest Added: " + interest);
        System.out.println("Updated Balance: " + balance);
    }
}