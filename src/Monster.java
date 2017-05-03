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
        /**
         * 1- if trapped then don't move and don't do any skill
         * 2- decrease the freeze timer
         * 3- can perform skills? chek if there are any active skill
         * 4- decrease hide timer
         * 4- if no, then perform skill
         * 4- return the direction
         */

        // don't move if the monster is trapped
        if (isTrapped()) {
            return getCell();
        }

        if (canPerformSkill()) {
            performRandomSkill();
        }
        currentDirection = grid.getBestDirection(currentCell, player.getCell());
        currentCell = (grid.getCell(getCell(), getDirection()));
        return currentCell;
    }

    private void disableAllActiveSkills() {
        /*
        any other active skills that can be disabled
        should be disabled here
        */

        // disable the invisible skill
        hideTime = 0;
    }

    private boolean isTrapped() {
        Trap trap = grid.getCurrentTrap();
        if (trap != null) {
            if (trap.getCell().equals(getCell())) {
                if (trap.stepOver())
                    return false;
                disableAllActiveSkills();
                return true;
            }
        }
        return false;
    }

    private boolean isHiding() {
        if (hideTime == 0) {
            return false;
        }
        return true;
    }

    private boolean canPerformSkill() {

        if (getSkills().size() == 0)
            return false; // no skills available

        if (isHiding())
            return false; // invisible skill is active

        if(isTrapped())
            return false;

        return true;
    }

    /**
     * perform random monster capability
     */
    private void performRandomSkill() {
        // no skills available
        if (getSkills().size() == 0) return;
        /*
        random number between 1 and 10
        if the number is 5, monstor will perform a random capability
         */
        Random r = new Random();
        int random = r.nextInt(10 - 1 + 1) + 1;
        if (random == 5) {
            // pick a random skill from the available skills
            random = r.nextInt(getSkills().size());

            MonsterSkillsType skill = (MonsterSkillsType) getSkills().get(random);
            switch (skill) {
                case LEAP:
                    leap();
                    break;
                case INVISIBLE:
                    invisible();
                    break;
            }

        }
    }

    public boolean viewable() {
        // not hiding or monster catched the player
        if (!isHiding() || getCell().equals(player.getCell())) {
            return true;
        }
        hideTime--;
        return false;
    }

    /**
     * check if the monster is in the same row/column of the player
     *
     * @return
     */
    private boolean canLeap() {

        // row is legitimate for leaping
        if (this.getCell().row % 5 == 0 && player.getCell().row % 5 == 0) {
            // same row
            if (getCell().row == player.getCell().row)
                return true;
        }
        // column is legitimate for leaping
        else if (this.getCell().col % 5 == 0 && player.getCell().col % 5 == 0) {
            // same column
            if (getCell().col == player.getCell().col)
                return true;
        }
        return false;
    }

    @Override
    public void leap() {
        // monster can't leap to player's cell
        if (!canLeap()) return;

        // set the current cell to be the
        currentCell = player.getCell();
//        getCell().row = player.getCell().row;
//        getCell().col = player.getCell().col;
    }

    @Override
    public void invisible() {
        if (isHiding()) return;
        // random time between 5 and 10
        Random random = new Random();
        hideTime = random.nextInt(10 - 5 + 1) + 5;
    }
}