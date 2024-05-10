import java.io.Serializable;
import java.math.BigInteger;

public class FactorialTask implements Executable, Serializable {
    private static final long serialVersionUID = 1L;
    private int number;

    public FactorialTask(int number) {
        this.number = number;
    }

    @Override
    public Object execute() {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public int getNumber() {
        return number;
    }
}
