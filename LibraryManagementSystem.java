package wiprotrainingday2;

import java.io.*;
import java.util.*;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Library library = new Library();
        library.loadLibraryData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Add Member");
            System.out.println("5. Update Member");
            System.out.println("6. Delete Member");
            System.out.println("7. Issue Book");
            System.out.println("8. Return Book");
            System.out.println("9. View Books");
            System.out.println("10. View Members");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> library.addBook();
                case 2 -> library.updateBook();
                case 3 -> library.deleteBook();
                case 4 -> library.addMember();
                case 5 -> library.updateMember();
                case 6 -> library.deleteMember();
                case 7 -> library.issueBook();
                case 8 -> library.returnBook();
                case 9 -> library.viewBooks();
                case 10 -> library.viewMembers();
                case 11 -> {
                    library.saveLibraryData();
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    private final String booksFile = "books.txt";
    private final String membersFile = "members.txt";

    // Load books and members from files
    public void loadLibraryData() {
        try {
            loadBooks();
            loadMembers();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public void saveLibraryData() {
        try {
            saveBooks();
            saveMembers();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Add a new book
    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        books.add(new Book(id, title, author));
        System.out.println("Book added successfully!");
    }

    // Update a book's details
    public void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = findBookById(id);
        if (book != null) {
            System.out.print("Enter new title: ");
            book.setTitle(scanner.nextLine());
            System.out.print("Enter new author: ");
            book.setAuthor(scanner.nextLine());
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    // Delete a book
    public void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();

        Book book = findBookById(id);
        if (book != null) {
            books.remove(book);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    // Add a new member
    public void addMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter member ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        members.add(new Member(id, name));
        System.out.println("Member added successfully!");
    }

    // Update member details
    public void updateMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter member ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Member member = findMemberById(id);
        if (member != null) {
            System.out.print("Enter new name: ");
            member.setName(scanner.nextLine());
            System.out.println("Member updated successfully!");
        } else {
            System.out.println("Member not found!");
        }
    }

    // Delete a member
    public void deleteMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter member ID to delete: ");
        int id = scanner.nextInt();

        Member member = findMemberById(id);
        if (member != null) {
            members.remove(member);
            System.out.println("Member deleted successfully!");
        } else {
            System.out.println("Member not found!");
        }
    }

    // Issue a book to a member
    public void issueBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to issue: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter member ID to issue to: ");
        int memberId = scanner.nextInt();

        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book != null && member != null) {
            transactions.add(new Transaction(book, member, "Issued"));
            System.out.println("Book issued successfully!");
        } else {
            System.out.println("Book or member not found!");
        }
    }

    // Return a book
    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to return: ");
        int bookId = scanner.nextInt();

        Book book = findBookById(bookId);
        if (book != null) {
            transactions.add(new Transaction(book, null, "Returned"));
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    // View all books
    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("Books in library:");
            books.forEach(System.out::println);
        }
    }

    // View all members
    public void viewMembers() {
        if (members.isEmpty()) {
            System.out.println("No members available.");
        } else {
            System.out.println("Library members:");
            members.forEach(System.out::println);
        }
    }

    // Helper methods to find books and members
    private Book findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }

    private Member findMemberById(int id) {
        return members.stream().filter(member -> member.getId() == id).findFirst().orElse(null);
    }

    // File handling for books
    private void saveBooks() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(booksFile))) {
            oos.writeObject(books);
        }
    }

    private void loadBooks() throws IOException, ClassNotFoundException {
        File file = new File(booksFile);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
            }
        }
    }

    // File handling for members
    private void saveMembers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(membersFile))) {
            oos.writeObject(members);
        }
    }

    private void loadMembers() throws IOException, ClassNotFoundException {
        File file = new File(membersFile);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                members = (List<Member>) ois.readObject();
            }
        }
    }
}

class Book implements Serializable {
    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author;
    }
}

class Member implements Serializable {
    private int id;
    private String name;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}

class Transaction {
    private Book book;
    private Member member;
    private String action;

    public Transaction(Book book, Member member, String action) {
        this.book = book;
        this.member = member;
        this.action = action;
    }

    @Override
    public String toString() {
        return "Book: " + book + ", Member: " + member + ", Action: " + action;
    }
}
