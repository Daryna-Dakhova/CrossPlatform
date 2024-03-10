package additional_task2;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Створюємо об'єкт для серіалізації
        Person person = new Person("John Doe", 25);

        // Серіалізація об'єкта у рядок
        String serializedObject = serializeObject(person);
        System.out.println("Serialized Object: " + serializedObject);

        // Десеріалізація рядка у об'єкт
        Person deserializedPerson = deserializeObject(serializedObject);
        System.out.println("Deserialized Object: " + deserializedPerson);
    }

    // Серіалізація об'єкта у рядок
    private static String serializeObject(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(obj);
            return bos.toString("ISO-8859-1"); // Перетворення байтів у рядок

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Десеріалізація рядка у об'єкт
    private static <T> T deserializeObject(String serializedObject) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject.getBytes("ISO-8859-1"));
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            return (T) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Приклад класу для серіалізації та десеріалізації
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
