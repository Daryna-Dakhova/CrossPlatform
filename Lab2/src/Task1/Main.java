package Task1;

import java.io.*;
import java.util.Arrays;

// Абстрактний базовий клас, що представляє людину
abstract class Human implements Serializable {
    private static final long serialVersionUID = 1L;

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

// Клас, що представляє автора книги
class Author extends Human {
    private static final long serialVersionUID = 1L;

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}

// Клас, що представляє книгу
class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private Author[] authors;
    private int publicationYear;
    private int editionNumber;

    public Book(String title, Author[] authors, int publicationYear, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return title + " (Edition " + editionNumber + ", " + publicationYear + ")";
    }
}

// Клас, що представляє читача
class BookReader extends Human {
    private static final long serialVersionUID = 1L;

    private int registrationNumber;
    private Book[] borrowedBooks;

    public BookReader(String firstName, String lastName, int registrationNumber, Book[] borrowedBooks) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = borrowedBooks;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationNumber=" + registrationNumber +
                ", borrowedBooks=" + Arrays.toString(borrowedBooks) +
                '}';
    }
}

// Клас, що представляє книгосховище
class BookStore implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Book[] books;

    public BookStore(String name, Book[] books) {
        this.name = name;
        this.books = books;
    }

    public Book[] getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}

// Клас, що представляє бібліотеку
class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private BookStore[] bookStores;
    private BookReader[] readers;

    public Library(String name, BookStore[] bookStores, BookReader[] readers) {
        this.name = name;
        this.bookStores = bookStores;
        this.readers = readers;
    }

    public String getName() {
        return name;
    }

    public BookStore[] getBookStores() {
        return bookStores;
    }

    public BookReader[] getReaders() {
        return readers;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", bookStores=" + Arrays.toString(bookStores) +
                ", readers=" + Arrays.toString(readers) +
                '}';
    }
}

public class Main {
    // Метод для серіалізації об'єкта в файл
    public static void serializeObject(String fileName, Object obj) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(obj);
            System.out.println("Object serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для десеріалізації об'єкта з файлу
    public static Object deSerializeObject(String fileName) {
        Object obj = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
            obj = is.readObject();
            System.out.println("Object deserialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        Book book1 = new Book("Book1", new Author[]{author1, author2}, 2022, 1);
        Book book2 = new Book("Book2", new Author[]{author2}, 2023, 1);
        BookStore bookStore = new BookStore("BookStore1", new Book[]{book1, book2});
        BookReader reader1 = new BookReader("Alice", "Johnson", 12345, new Book[]{book1});
        BookReader reader2 = new BookReader("Bob", "Williams", 54321, new Book[]{book2});
        Library library = new Library("Central Library", new BookStore[]{bookStore}, new BookReader[]{reader1, reader2});

        // Показ поточного стану бібліотеки
        System.out.println("Current State of the Library:");
        System.out.println(library);

        // Серіалізація та десеріалізація
        serializeObject("library.ser", library);
        Library deserializedLibrary = (Library) deSerializeObject("library.ser");

        // Показ стану десеріалізованої бібліотеки
        System.out.println("\nDeserialized State of the Library:");
        if (deserializedLibrary != null) {
            System.out.println(deserializedLibrary);
        }
    }
}
