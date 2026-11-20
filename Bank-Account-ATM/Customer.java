public class Customer {

    private String customerName;
    private String customerId;
    private Account account;

    public Customer(String customerName, String customerId,
                    Account account) {

        this.customerName = customerName;
        this.customerId = customerId;
        this.account = account;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Account getAccount() {
        return account;
    }

    public void displayCustomerDetails() {

        System.out.println("\n========== CUSTOMER DETAILS ==========");

        System.out.println("Customer Name: " + customerName);
        System.out.println("Customer ID: " + customerId);

        account.displayBalance();

        System.out.println("======================================");
    }
}
