/* This class represents the individual cell in the grid.
 */

import java.io.Serializable;

public class Cell implements Serializable{
   protected int row;
   protected int col;
   protected Nougats nougat;
   
   public Cell(int i, int j, int nougatsValue )
   {
	  this.nougat= new Nougats(nougatsValue); 
	  row = i;
	  col = j;
	  
   }
}
