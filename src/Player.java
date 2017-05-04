/*  This class encapsulates player position and direction  
 */
public class Player extends Moveable {
   private boolean readyToStart = false;
   private int steps;
   private int calories;
   public Player(Grid g, int row, int col,int caloriesValue) throws Exception
   {
	   super(g);
	   currentCell = grid.getCell(row, col);   
	   currentDirection = ' ';
	   calories=caloriesValue;
   }
   public Cell move()
   {
	   if (canPerformEnergyAction(calculateCalories(2)))
	   {
		   if (currentDirection != ' '){
			   //System.out.println(calories+"BEFORe");			  
			   Cell tempcell= grid.getCell(currentCell,currentDirection,2);
			   steps=grid.distance(currentCell, tempcell);
			   calories-=calculateCalories(steps);
			   //System.out.println(calories+"After "+ steps);
			   if (steps==0)
			   {
				   currentCell=tempcell;
				   return currentCell;
			   }
			   else 
			   {
				   currentCell=tempcell;
				   if (currentCell.nougat.isConsumed())
				   {
					   calories+= currentCell.nougat.getValue();   
					   return currentCell;
				   }
					   
				   calories+= currentCell.nougat.getValue();
				   currentCell.nougat.setConsumed();
				   return currentCell;   
				   
			   }
			      
	   } return currentCell;
	   }else 
		   return currentCell; 
   }

   public int calculateCalories(int steps)
   {
	   int sum = 0;
			   for (int i =1;i<=steps;i++)
			   {
				   sum+= Math.pow(2, i);
				   
			   }
			   return sum;
   } 
   public boolean canPerformEnergyAction(int requiredCalories)
   {
	   if (requiredCalories<=calories)
		   return true;
		return false;
   }
   
   public int maxCellsPerMove()
   {
	   return 1;
   }
   public  int pointsRemaining()
   {
	   return -1;  // not implemented
   }
   public void setReady(boolean val)
   {
	   readyToStart = val;
   }
   public boolean isReady()
   {   return readyToStart;
   }
}