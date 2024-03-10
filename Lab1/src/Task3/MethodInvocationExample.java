package Task3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocationExample {

    public static void main(String[] args) {
        // Приклад виклику методу
        try {
            Object obj = new SampleClass();
            invokeMethod(obj, "sampleMethod", "Hello, World!");
        } catch (FunctionNotFoundException e) {
            System.out.println("Функція не знайдена: " + e.getMessage());
        }
    }

    public static void invokeMethod(Object obj, String methodName, Object... parameters) throws FunctionNotFoundException {
        try {
            // Отримати клас об'єкта
            Class<?> objClass = obj.getClass();

            // Отримати масив класів параметрів
            Class<?>[] parameterTypes = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }

            // Отримати метод за ім'ям і типами параметрів
            Method method = objClass.getMethod(methodName, parameterTypes);

            // Викликати метод на об'єкті з переданими параметрами
            Object result = method.invoke(obj, parameters);

            // Вивести результат
            System.out.println("Результат виклику методу: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Якщо метод не знайдено або відбулась помилка виклику
            throw new FunctionNotFoundException("Метод не знайдено або виникла помилка виклику.");
        }
    }
}

class SampleClass {
    public String sampleMethod(String message) {
        return "Повідомлення: " + message;
    }
}

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}
