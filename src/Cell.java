/* This class represents the individual cell in the grid.
 */

import java.io.Serializable;

/**
 * Cell class represents a grid cell
 */
public class Cell implements Serializable{
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
     * @param i the
     * @param j the j
     */
    public Cell(int i, int j )
   {
	  this.nougat= new Nougat();
	  row = i;
	  col = j;
   }
}