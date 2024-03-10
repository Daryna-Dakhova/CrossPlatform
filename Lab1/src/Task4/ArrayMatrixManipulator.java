package Task4;

import java.util.Arrays;

public class ArrayMatrixManipulator {

    public static void main(String[] args) {
        // Створення та виведення примітивного масиву
        int[] primitiveArray = createPrimitiveArray(5);
        System.out.println("Primitive Array: " + arrayToString(primitiveArray));

        // Створення та виведення масиву посилань
        String[] referenceArray = createReferenceArray(4);
        System.out.println("Reference Array: " + arrayToString(referenceArray));

        // Створення та виведення примітивної матриці
        int[][] primitiveMatrix = createPrimitiveMatrix(3, 3);
        System.out.println("Primitive Matrix: \n" + matrixToString(primitiveMatrix));

        // Створення та виведення матриці посилань
        String[][] referenceMatrix = createReferenceMatrix(2, 2);
        System.out.println("Reference Matrix: \n" + matrixToString(referenceMatrix));

        // Зміна розміру та виведення результату
        resizeAndPrint(primitiveArray, 8);
        resizeAndPrint(referenceMatrix, 3, 4);
    }

    // Створення примітивного масиву
    public static int[] createPrimitiveArray(int size) {
        return new int[size];
    }

    // Створення масиву посилань
    public static String[] createReferenceArray(int size) {
        return new String[size];
    }

    // Створення примітивної матриці
    public static int[][] createPrimitiveMatrix(int rows, int cols) {
        return new int[rows][cols];
    }

    // Створення матриці посилань
    public static String[][] createReferenceMatrix(int rows, int cols) {
        return new String[rows][cols];
    }

    // Зміна розміру масиву та виведення результату
    public static void resizeAndPrint(int[] array, int newSize) {
        array = resizeArray(array, newSize);
        System.out.println("Resized Primitive Array: " + arrayToString(array));
    }

    // Зміна розміру матриці та виведення результату
    public static void resizeAndPrint(String[][] matrix, int newRows, int newCols) {
        matrix = resizeMatrix(matrix, newRows, newCols);
        System.out.println("Resized Reference Matrix: \n" + matrixToString(matrix));
    }

    // Зміна розміру примітивного масиву
    public static int[] resizeArray(int[] array, int newSize) {
        return Arrays.copyOf(array, newSize);
    }

    // Зміна розміру матриці посилань
    public static String[][] resizeMatrix(String[][] matrix, int newRows, int newCols) {
        String[][] resizedMatrix = new String[newRows][newCols];
        for (int i = 0; i < Math.min(matrix.length, newRows); i++) {
            resizedMatrix[i] = Arrays.copyOf(matrix[i], newCols);
        }
        return resizedMatrix;
    }

    // Перетворення одновимірного масиву в рядок
    public static String arrayToString(Object array) {
        return Arrays.deepToString(new Object[]{array});
    }

    // Перетворення двовимірної матриці в рядок
    public static String matrixToString(Object matrix) {
        return Arrays.deepToString((Object[]) matrix);
    }
}
