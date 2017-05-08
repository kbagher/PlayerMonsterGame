/* This class represents the individual cell in the grid.
 */

import java.io.Serializable;

public class Cell implements Serializable{
   protected int row;
   protected int col;
   protected Nougat nougat;
   
   public Cell(int i, int j )
   {
	  this.nougat= new Nougat();
	  row = i;
	  col = j;
   }
}