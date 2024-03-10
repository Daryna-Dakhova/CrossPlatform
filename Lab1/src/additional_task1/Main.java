package additional_task1;
import java.lang.reflect.Method;
import java.util.Scanner;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void greet() {
        System.out.println("Hello! I am " + name + ".");
    }

    public void celebrateBirthday() {
        age++;
        System.out.println("Happy birthday! Age is now: " + age);
    }

    public void setAge(int newAge) {
        age = newAge;
        System.out.println("Age set to: " + age);
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }

    public void setName(String newName) {
        name = newName;
        System.out.println("Name set to: " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the age: ");
        int age = scanner.nextInt();

        Person person = new Person(name, age);

        System.out.println("\nObject created with the following details:");
        System.out.println(person);

        Class<?> personClass = person.getClass();
        Method[] methods = personClass.getDeclaredMethods();

        System.out.println("\nAvailable methods:");
        for (int i = 0; i < methods.length; i++) {
            System.out.println(i + 1 + ". " + methods[i].getName());
        }

        System.out.print("\nEnter the method number to invoke: ");
        int methodNumber = scanner.nextInt();

        try {
            Method selectedMethod = methods[methodNumber - 1];

            if (selectedMethod.getParameterCount() > 0) {
                // Якщо метод приймає параметри, введіть їх значення
                System.out.print("Enter the method parameter(s): ");
                Object[] params = new Object[selectedMethod.getParameterCount()];
                for (int i = 0; i < params.length; i++) {
                    params[i] = scanner.next();
                }
                selectedMethod.invoke(person, params);
            } else {
                selectedMethod.invoke(person);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
