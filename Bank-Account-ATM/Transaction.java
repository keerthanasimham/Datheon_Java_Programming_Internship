import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String transactionType;
    private double amount;
    private double balanceAfterTransaction;
    private LocalDateTime timestamp;

    public Transaction(String transactionType, double amount,
                       double balanceAfterTransaction) {

        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = LocalDateTime.now();
    }

    public void displayTransaction() {

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println(
            timestamp.format(formatter) +
            " | " + transactionType +
            " | Amount: " + amount +
            " | Balance: " + balanceAfterTransaction
        );
    }
}
