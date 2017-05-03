import java.util.ArrayList;

/**
 * Created by kassem on 3/5/17.
 */
public class Sprite {
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
