package example;

import java.math.BigDecimal;

public class Pi implements Task<BigDecimal> {
    private static final long serialVersionUID = 227L;
    private final int digits;

    public Pi(int digits) {
        this.digits = digits;
    }

    public BigDecimal execute() {
        return computePi(digits);
    }

    public static BigDecimal computePi(int digits) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;
        BigDecimal sixteen = new BigDecimal(16);

        for (int i = 0; i < digits; i++) {
            BigDecimal factor = new BigDecimal(1).divide(new BigDecimal(16).pow(i), digits, BigDecimal.ROUND_HALF_UP);
            BigDecimal first = new BigDecimal(4).divide(new BigDecimal(8).multiply(new BigDecimal(i)).add(new BigDecimal(1)), digits, BigDecimal.ROUND_HALF_UP);
            BigDecimal second = new BigDecimal(2).divide(new BigDecimal(8).multiply(new BigDecimal(i)).add(new BigDecimal(4)), digits, BigDecimal.ROUND_HALF_UP);
            BigDecimal third = new BigDecimal(1).divide(new BigDecimal(8).multiply(new BigDecimal(i)).add(new BigDecimal(5)), digits, BigDecimal.ROUND_HALF_UP);
            BigDecimal fourth = new BigDecimal(1).divide(new BigDecimal(8).multiply(new BigDecimal(i)).add(new BigDecimal(6)), digits, BigDecimal.ROUND_HALF_UP);

            term = first.subtract(second).subtract(third).subtract(fourth).multiply(factor);
            sum = sum.add(term);
        }

        return sum.setScale(digits, BigDecimal.ROUND_HALF_UP);
    }
}
