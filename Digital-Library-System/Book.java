public class Book {

    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int year;
    private int copiesAvailable;

    public Book(String isbn, String title, String author,
                String genre, int year, int copiesAvailable) {

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.copiesAvailable = copiesAvailable;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public boolean isAvailable() {
        return copiesAvailable > 0;
    }

    public void checkout() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void returnBook() {
        copiesAvailable++;
    }

    public void displayBook() {
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Year: " + year);
        System.out.println("Available Copies: " + copiesAvailable);
        System.out.println("--------------------------------");
    }
}