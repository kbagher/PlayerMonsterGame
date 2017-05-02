/* This class encapsulates Monster position and direction
 * It also keeps a reference to the player it is tracking
 * The canView attribute can be used to limit monster visibility
 */

import java.util.Random;

public class Monster extends Moveable implements MonsterSkills {
    private Player player;
    private int hideTime;

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
        int random= r.nextInt(10 - 1 + 1) + 1;
        if (random ==5){
            invisible();
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

    @Override
    public void leap() {

    }

    @Override
    public void invisible() {
        if (isHiding()) return;
        // random time between 5 and 10
        Random random = new Random();
        hideTime = random.nextInt(10 - 5 + 1) + 5;
    }
}