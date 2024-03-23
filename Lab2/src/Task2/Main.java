import java.io.*;
import java.util.ArrayList;

abstract class Human {
    protected String firstName;
    protected String lastName;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Human{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

class Author extends Human {
    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}

class Book {
    private String title;
    private ArrayList<Author> authors;
    private int yearPublished;
    private int editionNumber;

    public Book(String title, ArrayList<Author> authors, int yearPublished, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.yearPublished = yearPublished;
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", yearPublished=" + yearPublished +
                ", editionNumber=" + editionNumber +
                '}';
    }
}

class Reader extends Human {
    public Reader(String firstName, String lastName) {
        super(firstName, lastName);
    }
}

class BookStore {
    private String name;
    private ArrayList<Book> books;

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

class BookReader extends Human {
    private int registrationNumber;
    private ArrayList<Book> booksRead;

    public BookReader(String firstName, String lastName, int registrationNumber) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.booksRead = new ArrayList<>();
    }

    public void addBookRead(Book book) {
        booksRead.add(book);
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationNumber=" + registrationNumber +
                ", booksRead=" + booksRead +
                '}';
    }
}

class Library {
    private String name;
    private ArrayList<BookStore> bookStores;
    private ArrayList<BookReader> readers;

    public Library(String name) {
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    public void addReader(BookReader reader) {
        readers.add(reader);
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", bookStores=" + bookStores +
                ", readers=" + readers +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");

        ArrayList<Author> authors1 = new ArrayList<>();
        authors1.add(author1);

        ArrayList<Author> authors2 = new ArrayList<>();
        authors2.add(author2);

        Book book1 = new Book("Java Programming", authors1, 2020, 1);
        Book book2 = new Book("Python Basics", authors2, 2019, 2);

        BookStore bookStore = new BookStore("BookStore1");
        bookStore.addBook(book1);
        bookStore.addBook(book2);

        Reader reader1 = new Reader("Alice", "Johnson");
        Reader reader2 = new Reader("Bob", "Smith");

        BookReader bookReader1 = new BookReader("Alice", "Johnson", 1001);
        bookReader1.addBookRead(book1);

        BookReader bookReader2 = new BookReader("Bob", "Smith", 1002);
        bookReader2.addBookRead(book2);

        Library library = new Library("Central Library");
        library.addBookStore(bookStore);
        library.addReader(bookReader1);
        library.addReader(bookReader2);

        // Displaying initial state of the library
        System.out.println("Initial state of the library:");
        System.out.println(library);

        // Serialization
        try {
            FileOutputStream fileOut = new FileOutputStream("library.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(library);
            out.close();
            fileOut.close();
            System.out.println("Library serialized to file library.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        // Deserialization
        Library deserializedLibrary = null;

        try {
            FileInputStream fileIn = new FileInputStream("library.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedLibrary = (Library) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Displaying state of deserialized system
        if (deserializedLibrary != null) {
            System.out.println("State of deserialized library:");
            System.out.println(deserializedLibrary);
        }
    }
}
