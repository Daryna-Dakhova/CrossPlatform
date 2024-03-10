package Task5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

interface MyInterface {
    String myMethod(String input);
}

class MyImplementation implements MyInterface {
    @Override
    public String myMethod(String input) {
        return "Hello, " + input + "!";
    }
}

class ProfilingInvocationHandler implements InvocationHandler {
    private final Object target;

    public ProfilingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();

        System.out.println("Час виконання методу " + method.getName() + ": " + (endTime - startTime) + " наносекунд");

        return result;
    }
}

class TracingInvocationHandler implements InvocationHandler {
    private final Object target;

    public TracingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Виклик методу " + method.getName() + " з параметрами: " + Arrays.toString(args));
        Object result = method.invoke(target, args);
        System.out.println("Метод повернув значення: " + result);

        return result;
    }
}

public class DynamicProxyExample {

    public static void main(String[] args) {
        MyInterface realObject = new MyImplementation();

        MyInterface profilingProxy = (MyInterface) Proxy.newProxyInstance(
                DynamicProxyExample.class.getClassLoader(),
                new Class[]{MyInterface.class},
                new ProfilingInvocationHandler(realObject)
        );

        profilingProxy.myMethod("John");

        MyInterface tracingProxy = (MyInterface) Proxy.newProxyInstance(
                DynamicProxyExample.class.getClassLoader(),
                new Class[]{MyInterface.class},
                new TracingInvocationHandler(realObject)
        );

        tracingProxy.myMethod("Jane");
    }
}
