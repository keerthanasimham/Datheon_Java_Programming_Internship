import java.util.ArrayList;
import java.util.List;

public class Member {

    private String memberId;
    private String name;
    private List<String> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public boolean hasBorrowed(String isbn) {
        return borrowedBooks.contains(isbn);
    }

    public void displayMember() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Borrowed Books: " + borrowedBooks.size());
        System.out.println("------------------------------");
    }
}
