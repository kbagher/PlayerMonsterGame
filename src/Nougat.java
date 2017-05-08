import java.io.IOException;
import java.io.Serializable;

public class Nougat implements Serializable {

    private boolean consumed;

    public Nougat() {
        consumed = false;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public int consume() {
        if (!consumed) {
            consumed = true;
            return Settings.getNougatCalories();
        }
        return 0;
    }
}
