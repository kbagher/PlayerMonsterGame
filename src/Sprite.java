import java.io.Serializable;
import java.util.ArrayList;

/**
 * Sprite represents a 2D object in the game such as; a player,monster or a trap
 */
public abstract class Sprite implements Serializable {
    /**
     * The Current cell.
     */
    protected Cell currentCell;
    /**
     * The game grid.
     */
    protected Grid grid;

    /**
     * Instantiates a new Sprite.
     *
     * @param g the grid object
     */
    public Sprite(Grid g) {
        grid = g;
    }

    /**
     * Sets cell.
     *
     * @param c the c
     */
    public void setCell(Cell c) {
        currentCell = c;
    }

    /**
     * Gets cell.
     *
     * @return the cell
     */
    public Cell getCell() {
        return currentCell;
    }

}
