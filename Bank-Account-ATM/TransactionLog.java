import java.util.ArrayList;

public class TransactionLog {

    private ArrayList<Transaction> transactions;

    public TransactionLog() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(String type, double amount,
                               double balance) {

        Transaction transaction =
            new Transaction(type, amount, balance);

        transactions.add(transaction);
    }

    public void displayTransactions() {

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }

        System.out.println("=========================================");
    }
}
