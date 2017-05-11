/* This class encapsulates Monster position and direction
 * It also keeps a reference to the player it is tracking
 * The canView attribute can be used to limit monster visibility
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * Monster class
 */
public class Monster extends Moveable implements MonsterSkills, Serializable {
    /**
     * Player reference
     */
    private Player player;
    /**
     * Trap reference
     */
    private Trap trap;
    /**
     * Invisible skill timer
     */
    private int hideTime;
    /**
     * Determines if the monster has stepped over a trap or not
     */
    private boolean steppedOverTrap;

    /**
     * Instantiates a new Monster.
     *
     * @param g   grid
     * @param p   player
     * @param t   trap
     * @param row initial row on the grid
     * @param col initial column on the grid
     * @throws Exception the exception
     */
    public Monster(Grid g, Player p, Trap t, int row, int col) throws Exception {
        super(g);
        player = p;
        trap = t;
        setCell(grid.getCell(row, col));
        steppedOverTrap = false;
    }

    /**
     * Move the monster
     * @return
     */
    public Cell move() {
        /**
         * Avoid moving if trapped
         */
        if (isTrapped()) {
            return getCell(); // monster is trapped
        }
        steppedOverTrap=false;

        /**
         * Perform a random skill
         */
        if (canPerformSkill()) {
            performRandomSkill(); // perform random skill
        }

        /**
         * Monster leaped to user's cell
         */
        if (currentCell.equals(player.getCell()))
            return currentCell;

        currentDirection = grid.getMoveDirection(currentCell, grid.getCell(player.getCell(),player.getDirection()),trap);
        currentCell = (grid.getCell(getCell(), getDirection()));
        return currentCell;
    }

    /**
     * Disable monster's current active skills.
     *
     * Any other active skills that can be disabled
     * should be disabled here
     */
    private void disableAllActiveSkills() {
        // disable the invisible skill
        hideTime = 0;
    }

    /**
     * Check if the monster is trapped or not
     * @return true if monster is trapped
     */
    private boolean isTrapped() {

        if (!trap.isSet())
            return false; // trap is not placed

        if (!trap.isTrapped(getCell()))
            return false; // not trapped


        if (!steppedOverTrap){ // just got trapped
            /**
             * inform the trap that the monster has stepped over it
             */
            trap.stepOver();
            steppedOverTrap=true;
            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("monster_trapped.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // disable all active skill since monster is trapped
        disableAllActiveSkills();
        return true;
    }

    /**
     * Check if the monster's invisible skill is active or not
     * @return true if invisible skill is active
     */
    private boolean isHiding() {
        return hideTime != 0;
    }

    /**
     * Check if the monster can perform a skill or not
     * @return true if the monster can perform any skill
     */
    private boolean canPerformSkill() {

        /**
         * check if there are any skills available
         */
        if (!hasSkills()) return false; // no skills available

        /**
         * Check if there are any currently active skills
         *
         * The monster can't have more than one active skill at the same time
         */
        if (isHiding()) return false; // invisible skill is active

        /**
         * Check if the monster is no affected by any of the player's skills
         *
         * The monster can't use his skills if he is currently affected by any
         * of the player's skills
         */
        if (isTrapped()) return false;

        return true; // monster can perform any skill
    }

    /**
     * Perform a random monster skill
     */
    private void performRandomSkill() {
        if (!hasSkills()) return; // no skills available
        /**
         * probability of a monster to perform a skill
         */
        Random r = new Random();
        int random = r.nextInt(100);
        /**
         * 10 = 10%
         * 50 = 50%
         * ...
         */
        if (random == 50) {
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

    /**
     * Viewable boolean.
     *
     * @return the boolean
     */
    public boolean viewable() {
        // not hiding or monster already reached the player
        if (!isHiding() || getCell().equals(player.getCell())) {
            return true;
        }
        if (isHiding())
            hideTime--;
        return false;
    }

    /**
     * check if the monster can perform leap skill
     *
     * @return true if the monster can leap
     */
    private boolean canLeap() {

        /**
         * check if row is legitimate for leaping
         */
        if (this.getCell().row % 5 == 0 && player.getCell().row % 5 == 0) {
            if (getCell().row == player.getCell().row)
                return true; // monster and player are in the same row
        }
        /**
         * check if column is legitimate for leaping
         */
        else if (this.getCell().col % 5 == 0 && player.getCell().col % 5 == 0) {
            if (getCell().col == player.getCell().col)
                return true; // monster and player are in the same column
        }
        return false; // can't not in same legitimate row or column
    }

    @Override
    public void leap() {
        if (!canLeap()) return; // monster can't leap to player's cell

        currentCell = player.getCell(); // leap to user's sell
    }

    @Override
    public void invisible() {
        if (isHiding()) return; // monster is already hiding

        /**
         * random hiding time between 5 to 10 game time units
         */
        Random random = new Random();
        hideTime = random.nextInt(10 - 5 + 1) + 5;
    }
}