import java.util.Scanner;

public class ATM {

    private Customer customer;
    private TransactionLog transactionLog;
    private Scanner scanner;

    public ATM(Customer customer, TransactionLog transactionLog,
               Scanner scanner) {
        this.customer = customer;
        this.transactionLog = transactionLog;
        this.scanner = scanner;
    }

    public void start() {

        Account account = customer.getAccount();

        System.out.println("\n================================");
        System.out.println("       WELCOME TO JAVA ATM");
        System.out.println("================================");

        customer.displayCustomerDetails();

        int choice;

        do {
            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;

                case 2:
                    depositMoney(account);
                    break;

                case 3:
                    withdrawMoney(account);
                    break;

                case 4:
                    calculateInterest(account);
                    break;

                case 5:
                    transactionLog.displayTransactions();
                    break;

                case 6:
                    System.out.println("Thank you for using Java ATM!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    private boolean authenticate(Account account) {

        System.out.print("Enter your PIN: ");

        int enteredPIN = scanner.nextInt();

        try {
            return account.verifyPIN(enteredPIN);
        } catch (InvalidPINException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void checkBalance(Account account) {

        if (authenticate(account)) {
            account.displayBalance();
        }
    }

    private void depositMoney(Account account) {

        if (!authenticate(account)) {
            return;
        }

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        double oldBalance = account.getBalance();

        account.deposit(amount);

        if (account.getBalance() > oldBalance) {
            transactionLog.addTransaction(
                "DEPOSIT", amount, account.getBalance()
            );
        }
    }

    private void withdrawMoney(Account account) {

        if (!authenticate(account)) {
            return;
        }

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        try {
            account.withdraw(amount);

            if (amount > 0) {
                transactionLog.addTransaction(
                    "WITHDRAWAL", amount, account.getBalance()
                );
            }

        } catch (InsufficientFundsException e) {
            System.out.println("Transaction Failed: " + e.getMessage());
        }
    }

    private void calculateInterest(Account account) {

        if (!authenticate(account)) {
            return;
        }

        double oldBalance = account.getBalance();

        account.calculateInterest();

        double interest = account.getBalance() - oldBalance;

        if (interest > 0) {
            transactionLog.addTransaction(
                "INTEREST", interest, account.getBalance()
            );
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("     BANK ACCOUNT SELECTION");
        System.out.println("================================");
        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");
        System.out.print("Choose account type: ");

        int type = input.nextInt();

        Account account;

        if (type == 1) {
            account = new SavingsAccount(
                "SA101", "Keerthana", 10000, 1234, 4.0
            );

        } else if (type == 2) {
            account = new CheckingAccount(
                "CA101", "Keerthana", 10000, 1234, 5000
            );

        } else {
            System.out.println("Invalid account type!");
            input.close();
            return;
        }

        Customer customer = new Customer(
            "Keerthana", "C101", account
        );

        TransactionLog log = new TransactionLog();

        ATM atm = new ATM(customer, log, input);

        atm.start();

        input.close();
    }
}