/* This class represents the individual cell in the grid.
 */

public class Cell {
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
