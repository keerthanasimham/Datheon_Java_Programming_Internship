public abstract class Account {

    protected String accountNumber;
    protected String customerName;
    protected double balance;
    protected int pin;

    public Account(String accountNumber, String customerName,
                   double balance, int pin) {

        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean verifyPIN(int enteredPIN)
            throws InvalidPINException {

        if (this.pin != enteredPIN) {
            throw new InvalidPINException("Incorrect PIN!");
        }

        return true;
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        balance += amount;

        System.out.println("Deposit successful!");
        System.out.println("Current Balance: " + balance);
    }

    public abstract void withdraw(double amount)
            throws InsufficientFundsException;

    public abstract void calculateInterest();

    public void displayBalance() {
        System.out.println("Account Holder: " + customerName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
}