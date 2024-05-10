import java.io.Serializable;

// Інтерфейс для результату
public interface Result extends Serializable {
    Object output();
    double scoreTime();
}
