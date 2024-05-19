package example;

import java.math.BigDecimal;

public class ECalculator {
    // Метод для обчислення значення e з точністю digits знаків після коми
    public static BigDecimal calculateE(int digits) {
        BigDecimal sum = BigDecimal.ONE; // Починаємо з першого доданку (a0 = 1)
        BigDecimal term = BigDecimal.ONE; // Початкове значення для додаткових членів

        // Цикл для обчислення додаткових членів до тих пір, поки їх абсолютне значення не стане менше потрібної точності
        for (int n = 1; term.compareTo(BigDecimal.ZERO) != 0; n++) {
            BigDecimal factorial = factorial(n); // Обчислюємо факторіал n
            BigDecimal xPowN = BigDecimal.ONE; // Ініціалізуємо x^n для даного n (x = 1)
            for (int i = 0; i < n; i++) {
                xPowN = xPowN.multiply(BigDecimal.ONE); // x^n = x^(n-1) * x
            }

            // Обчислюємо a_n = x^n / n!
            term = xPowN.divide(factorial, digits, BigDecimal.ROUND_HALF_UP);

            // Додаємо a_n до суми
            sum = sum.add(term);
        }

        return sum.setScale(digits, BigDecimal.ROUND_HALF_UP); // Заокруглюємо результат
    }

    // Метод для обчислення факторіалу n
    private static BigDecimal factorial(int n) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }
}
