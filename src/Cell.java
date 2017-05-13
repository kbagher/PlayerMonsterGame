/* This class represents the individual cell in the grid.
 */

import java.io.Serializable;

/**
 * Cell class represents a grid cell
 */
public class Cell implements Serializable {
    /**
     * The Row.
     */
    protected int row;
    /**
     * The Col.
     */
    protected int col;
    /**
     * The Nougat.
     */
    protected Nougat nougat;

    /**
     * Instantiates a new Cell.
     *
     * @param r grid row
     * @param c grid column
     */
    public Cell(int r, int c) {
        this.nougat = new Nougat();
        row = r;
        col = c;
    }

    @Override
    public String toString() {
        return "(" + row + "." + col + ")";
    }
}