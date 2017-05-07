import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kassem on 3/5/17.
 */
public class Sprite implements Serializable {
    protected Cell currentCell;
    protected Grid grid;

    public Sprite(Grid g) {
        grid = g;
    }
    public void setCell(Cell c) {
        currentCell = c;
    }

    public Cell getCell() {
        return currentCell;
    }

}
