package example;

import java.io.Serializable;
import java.math.BigDecimal;

public class ETask implements Task<BigDecimal>, Serializable {
    private static final long serialVersionUID = 228L;
    private final int digits;

    public ETask(int digits) {
        this.digits = digits;
    }

    @Override
    public BigDecimal execute() {
        return ECalculator.calculateE(digits);
    }
}
