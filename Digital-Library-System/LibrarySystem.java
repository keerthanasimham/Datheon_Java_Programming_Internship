import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibrarySystem {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double DAILY_FINE = 5.0;

    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private Map<String, Loan> loans = new HashMap<>();

    private Scanner input = new Scanner(System.in);

    private static class Loan {
        String isbn;
        String memberId;
        LocalDate checkoutDate;
        LocalDate dueDate;

        Loan(String isbn, String memberId,
             LocalDate checkoutDate, LocalDate dueDate) {
            this.isbn = isbn;
            this.memberId = memberId;
            this.checkoutDate = checkoutDate;
            this.dueDate = dueDate;
        }
    }

    public LibrarySystem() {
        loadBooks();
    }

    private void loadBooks() {

        try (BufferedReader reader =
                new BufferedReader(new FileReader("books_catalog.csv"))) {

            String line;
            reader.readLine(); // Skip CSV header

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 6) {
                    Book book = new Book(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4]),
                        Integer.parseInt(data[5])
                    );

                    books.put(book.getIsbn(), book);
                }
            }

            System.out.println("Book catalogue loaded successfully!");

        } catch (IOException e) {
            System.out.println("Error loading catalogue: " + e.getMessage());
        }
    }

    private void displayBooks() {

        System.out.println("\n========== BOOK CATALOGUE ==========");

        for (Book book : books.values()) {
            book.displayBook();
        }
    }

    private void addMember() {

        System.out.print("Enter Member ID: ");
        String id = input.nextLine();

        if (members.containsKey(id)) {
            System.out.println("Member ID already exists!");
            return;
        }

        System.out.print("Enter Member Name: ");
        String name = input.nextLine();

        Member member = new Member(id, name);
        members.put(id, member);

        System.out.println("Member registered successfully!");
    }

    private void searchBooks() {

        System.out.println("\n1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Show Available Books");
        System.out.print("Choose: ");

        int choice = readChoice();

        if (choice == 3) {
            for (Book book : books.values()) {
                if (book.isAvailable()) {
                    book.displayBook();
                }
            }
            return;
        }

        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.print("Enter search text: ");
        String search = input.nextLine().toLowerCase();

        boolean found = false;

        for (Book book : books.values()) {

            String value = choice == 1
                ? book.getTitle()
                : book.getAuthor();

            if (value.toLowerCase().contains(search)) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    private void checkoutBook() {

        System.out.print("Enter Member ID: ");
        String memberId = input.nextLine();

        Member member = members.get(memberId);

        if (member == null) {
            System.out.println("Member not found! Register first.");
            return;
        }

        System.out.print("Enter Book ISBN: ");
        String isbn = input.nextLine();

        Book book = books.get(isbn);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is currently unavailable!");
            return;
        }

        if (member.hasBorrowed(isbn)) {
            System.out.println("Member has already borrowed this book!");
            return;
        }

        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(LOAN_PERIOD_DAYS);

        book.checkout();
        member.borrowBook(isbn);

        String loanKey = memberId + "|" + isbn;

        loans.put(
            loanKey,
            new Loan(isbn, memberId, checkoutDate, dueDate)
        );

        System.out.println("\nBook checked out successfully!");
        System.out.println("Checkout Date: " + checkoutDate);
        System.out.println("Due Date: " + dueDate);
    }

    private void returnBook() {

        System.out.print("Enter Member ID: ");
        String memberId = input.nextLine();

        Member member = members.get(memberId);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.print("Enter Book ISBN: ");
        String isbn = input.nextLine();

        String loanKey = memberId + "|" + isbn;

        Loan loan = loans.get(loanKey);

        if (loan == null) {
            System.out.println("No checkout record found!");
            return;
        }

        LocalDate returnDate = LocalDate.now();

        long overdueDays = Math.max(
            0,
            ChronoUnit.DAYS.between(loan.dueDate, returnDate)
        );

        double fine = overdueDays * DAILY_FINE;

        Book book = books.get(isbn);

        book.returnBook();
        member.returnBook(isbn);
        loans.remove(loanKey);

        System.out.println("\nBook returned successfully!");
        System.out.println("Due Date: " + loan.dueDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Overdue Days: " + overdueDays);
        System.out.printf("Late Fine: Rs. %.2f%n", fine);
    }

    private void displayMembers() {

        System.out.println("\n========== REGISTERED MEMBERS ==========");

        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        for (Member member : members.values()) {
            member.displayMember();
        }
    }

    private int readChoice() {

        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {

        int choice;

        do {
            System.out.println("\n====================================");
            System.out.println("     DIGITAL LIBRARY MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Display All Books");
            System.out.println("2. Register Member");
            System.out.println("3. Search Books");
            System.out.println("4. Checkout Book");
            System.out.println("5. Return Book");
            System.out.println("6. Display Members");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = readChoice();

            switch (choice) {

                case 1:
                    displayBooks();
                    break;

                case 2:
                    addMember();
                    break;

                case 3:
                    searchBooks();
                    break;

                case 4:
                    checkoutBook();
                    break;

                case 5:
                    returnBook();
                    break;

                case 6:
                    displayMembers();
                    break;

                case 7:
                    System.out.println("Thank you for using the library system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 7);

        input.close();
    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.start();
    }
}
