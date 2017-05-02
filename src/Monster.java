/* This class encapsulates Monster position and direction
 * It also keeps a reference to the player it is tracking
 * The canView attribute can be used to limit monster visibility
 */

import java.util.Random;

public class Monster extends Moveable implements MonsterSkills {
    private Player player;
    private int hideTime;

    public enum Skills{
        LEAP,INVISIBLE
    }

    public Monster(Grid g, Player p, int row, int col) throws Exception {
        super(g);
        player = p;
        setCell(grid.getCell(row, col));

    }

    public Cell move() {
        currentDirection = grid.getBestDirection(currentCell, player.getCell());
        currentCell = (grid.getCell(getCell(), getDirection()));
        return currentCell;
    }

    private boolean isHiding(){
        if(hideTime==0){
            return false;
        }
        return true;
    }

    /**
     * perform random monsoter capablity
     */
    public void performCapability(){
        // TODO: check against loaded capabilities (need to ad capabilities list)
        /*
        random number between 1 and 10
        if the number is 5, monstor will perform a random capability
         */
        Random r = new Random();
        int random= r.nextInt(5 - 1 + 1) + 1;
        if (random ==2){
//            invisible();
            leap();
        }
    }

    public boolean viewable()  // can be used for hiding
    {
        // not hiding
        if(!isHiding()){
            return true;
        }
        hideTime--;
        return false;
    }

    /**
     * check if the monster is in the same row/column of the player
     * @return
     */
    private boolean canLeap(){

        // row is ligimit for leaping
        if(this.getCell().row %5==0 && player.getCell().row %5==0){
            // same row
            if(getCell().row==player.getCell().row)
                return true;
        }
        // column is ligimit for leaping
        else if (this.getCell().col %5==0 && player.getCell().col %5==0){
            // same column
            if(getCell().col==player.getCell().col)
                return true;
        }
        return false;
    }

    @Override
    public void leap() {
        // monster can't leap to player's cell
        if (!canLeap()) return;

        this.getCell().row=player.getCell().row;
        this.getCell().col=player.getCell().col;
    }

    @Override
    public void invisible() {
        if (isHiding()) return;
        // random time between 5 and 10
        Random random = new Random();
        hideTime = random.nextInt(10 - 5 + 1) + 5;
    }
}