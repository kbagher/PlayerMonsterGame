/* This class encapsulates Monster position and direction
 * It also keeps a reference to the player it is tracking
 * The canView attribute can be used to limit monster visibility
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

public class Monster extends Moveable implements MonsterSkills, Serializable {
    private Player player;
    private Trap trap;
    private int hideTime;
    private boolean steppedOverTrap;

    public Monster(Grid g, Player p, Trap t, int row, int col) throws Exception {
        super(g);
        player = p;
        trap = t;
        setCell(grid.getCell(row, col));
        steppedOverTrap = false;
    }

    public Cell move() {
        // don't move if the monster is trapped
        if (isTrapped()) {
            return getCell();
        }
        steppedOverTrap=false;

        if (canPerformSkill()) {
            performRandomSkill();
        }
        if (currentCell.equals(player.getCell()))
            return currentCell;
        currentDirection = grid.getMoveDirection(currentCell, grid.getCell(player.getCell(),player.getDirection()),trap);
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
        if (!trap.isSet())
            return false;

        if (!trap.isTrapped(getCell()))
            return false;

        // monster is trapped
        if (!steppedOverTrap){
            trap.stepOver(); // first step
            steppedOverTrap=true;
            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("assets/monster_trapped.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // remove all active skills
        disableAllActiveSkills();
        return true;
    }

    private boolean isHiding() {
        return hideTime != 0;
    }

    private boolean canPerformSkill() {

        if (getSkills().size() == 0)
            return false; // no skills available

        if (isHiding())
            return false; // invisible skill is active

        return !isTrapped();
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

        System.out.println("LEAP :D");
        // set the current cell to be the
        currentCell = player.getCell();
    }

    @Override
    public void invisible() {
        if (isHiding()) return;
        // random time between 5 and 10
        Random random = new Random();
        hideTime = random.nextInt(10 - 5 + 1) + 5;
    }
}