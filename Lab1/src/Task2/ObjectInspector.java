package Task2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class ObjectInspector {

    public static void inspectObject(Object obj) {
        // Display the actual type of the object
        System.out.println("Actual type of the object: " + obj.getClass().getName());

        // Display the state of the object - fields and their values
        System.out.println("Object state:");
        printFields(obj);

        // Display public methods of the class
        System.out.println("Public methods of the class:");
        printMethods(obj);

        // Allow the user to select a method for invocation
        invokeSelectedMethod(obj);
    }

    private static void printFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(field.getName() + ": " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMethods(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                System.out.println(method.getName());
            }
        }
    }

    private static void invokeSelectedMethod(Object obj) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the method name for invocation: ");
        String methodName = scanner.nextLine();

        try {
            Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            System.out.println("Result of invoking method " + methodName + ": " + result);
        } catch (Exception e) {
            System.out.println("Error invoking method: " + e.getMessage());
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Example 1: MyClass
        MyClass myObject = new MyClass("Hello, World!", 42);
        System.out.println("\nExample 1: MyClass");
        inspectObject(myObject);

        // Example 2: Person
        Person person = new Person("John Doe", 25);
        System.out.println("\nExample 2: Person");
        inspectObject(person);

        // Example 3: Car
        Car car = new Car("Toyota", "Camry", 2022);
        System.out.println("\nExample 3: Car");
        inspectObject(car);
    }
}

class MyClass {
    private String message;
    private int number;

    public MyClass(String message, int number) {
        this.message = message;
        this.number = number;
    }

    public void displayMessage() {
        System.out.println("Message: " + message);
    }

    public int getNumber() {
        return number;
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void introduce() {
        System.out.println("Hello, my name is " + name + " and I am " + age + " years old.");
    }
}

class Car {
    private String make;
    private String model;
    private int year;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public void startEngine() {
        System.out.println("Engine started for " + make + " " + model);
    }
}
