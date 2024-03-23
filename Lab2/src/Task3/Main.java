package Task3;

import java.io.*;
import java.util.ArrayList;

class Human {
    protected String firstName;
    protected String lastName;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class Author extends Human implements Externalizable {
    private static final long serialVersionUID = 1L;

    public Author() {
        super("", "");
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(firstName);
        out.writeObject(lastName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName = (String) in.readObject();
        lastName = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

class Reader extends Human implements Externalizable {
    private static final long serialVersionUID = 1L;
    private int registrationNumber;
    private ArrayList<Book> booksRead;

    public Reader() {
        super("", "");
    }

    public Reader(String firstName, String lastName, int registrationNumber) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.booksRead = new ArrayList<>();
    }

    public void addBook(Book book) {
        booksRead.add(book);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(firstName);
        out.writeObject(lastName);
        out.writeInt(registrationNumber);
        out.writeObject(booksRead);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName = (String) in.readObject();
        lastName = (String) in.readObject();
        registrationNumber = in.readInt();
        booksRead = (ArrayList<Book>) in.readObject();
    }

    @Override
    public String toString() {
        return "Reader{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationNumber=" + registrationNumber +
                ", booksRead=" + booksRead +
                '}';
    }
}

class Book implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String title;
    private ArrayList<Author> authors;
    private int yearPublished;
    private int editionNumber;

    public Book() {
        this.title = "";
        this.authors = new ArrayList<>();
        this.yearPublished = 0;
        this.editionNumber = 0;
    }

    public Book(String title, ArrayList<Author> authors, int yearPublished, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.yearPublished = yearPublished;
        this.editionNumber = editionNumber;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeObject(authors);
        out.writeInt(yearPublished);
        out.writeInt(editionNumber);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        authors = (ArrayList<Author>) in.readObject();
        yearPublished = in.readInt();
        editionNumber = in.readInt();
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

class BookStore implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Book> books;

    public BookStore() {
        this.name = "";
        this.books = new ArrayList<>();
    }

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(books);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        books = (ArrayList<Book>) in.readObject();
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

class Library implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<BookStore> bookStores;
    private ArrayList<Reader> readers;

    public Library() {
        this.name = "";
        this.bookStores = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Library(String name) {
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(bookStores);
        out.writeObject(readers);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        bookStores = (ArrayList<BookStore>) in.readObject();
        readers = (ArrayList<Reader>) in.readObject();
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
        // Creating objects of authors
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");

        // Creating objects of books
        ArrayList<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        Book book1 = new Book("Java Programming", authors1, 2020, 1);

        ArrayList<Author> authors2 = new ArrayList<>();
        authors2.add(author2);
        Book book2 = new Book("Python Basics", authors2, 2019, 2);

        // Creating objects of book stores
        BookStore bookStore1 = new BookStore("BookStore1");
        bookStore1.addBook(book1);
        bookStore1.addBook(book2);

        // Creating objects of readers
        Reader reader1 = new Reader("Alice", "Johnson", 1001);
        reader1.addBook(book1);

        Reader reader2 = new Reader("Bob", "Smith", 1002);
        reader2.addBook(book2);

        // Creating object of library
        Library library = new Library("Central Library");
        library.addBookStore(bookStore1);
        library.addReader(reader1);
        library.addReader(reader2);

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

        // Displaying state of deserialized library
        if (deserializedLibrary != null) {
            System.out.println("State of deserialized library:");
            System.out.println(deserializedLibrary);
        }
    }
}
