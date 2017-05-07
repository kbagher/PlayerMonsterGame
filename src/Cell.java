/* This class represents the individual cell in the grid.
 */

import java.io.Serializable;

public class Cell implements Serializable{
   protected int row;
   protected int col;
   protected Nougats nougat;
   
   public Cell(int i, int j )
   {
	  this.nougat= new Nougats();
	  row = i;
	  col = j;
	  
   }
}
