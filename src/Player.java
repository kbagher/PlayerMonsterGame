import java.io.*;

/**
 * This class encapsulates player position and direction
 */
public class Player extends Moveable implements PlayerSkills, Serializable {
    /**
     * Player's status to start the game or not
     */
    private boolean readyToStart = false;
    /**
     * Player's movement steps, 1 is the default and 3 is the max
     */
    private int steps;
    /**
     * Player's energy
     */
    private int energy;
    /**
     * Grid's trap
     */
    private Trap trap;

    /**
     * Instantiates a new Player.
     *
     * @param g             grid
     * @param t             trap
     * @param row           player's row in the grid
     * @param col           player's column in the grid
     * @param caloriesValue initial energy
     * @throws Exception the exception
     */
    public Player(Grid g, Trap t, int row, int col, int caloriesValue) throws Exception {
        super(g);
        currentCell = grid.getCell(row, col);
        currentDirection = ' ';
        energy = caloriesValue;
        trap = t;
    }

    /**
     * Moves the player on the grid
     * @return
     */
    public Cell move() {
        // consume the nougat on the current cell if available
        increaseEnergy(currentCell.nougat.consume());
        /**
         * check if the player have enough energy to move
         */
        if (canPerformEnergyAction(calculateCalories(steps))) { // have enough energy to move
            if (currentDirection != ' ') { // player have a direction to move
                /**
                 * Determine the max cell that can be reached according
                 * to the user's provided steps.
                 * Some cases where the user want's to move more than one step where
                 * he can only perform one step as he will reach the end of the grid
                 */
                Cell tempCell = grid.getCell(currentCell, currentDirection, steps);
                /**
                 * Recalculate the steps to reflect the new max cell which wil be reached
                 */
                steps = grid.distance(currentCell, tempCell);
                /**
                 * Deduct energy from the user after updating the possible steps
                 */
                decreaseEnergy(calculateCalories(steps));
                if (tempCell != null)
                    currentCell = tempCell; // user did not reach a dead end
                steps = 1; // reset steps to 1
                return currentCell;
            }
            steps = 1; // reset steps to 1
            return currentCell; // user is not moving
        }
        steps = 1; // reset steps to 1
        return currentCell;
    }

    /**
     * Calculate moving energy required based on the given steps.
     *
     * Calculating steps is used by summing the exponential movement steps
     * @param steps movement steps
     * @return energy required
     */
    public int calculateCalories(int steps) {
        int sum = 0;
        for (int i = 1; i <= steps; i++) {
            sum += Math.pow(Game.settings.stepEnergy, i);
        }
        return sum;
    }

    /**
     * Check if the player have enough energy to perform
     * an action based on the given energy
     *
     * @param requiredEnergy the required energy
     * @return true if the player have enough energy
     */
    public boolean canPerformEnergyAction(int requiredEnergy) {
        return requiredEnergy <= energy;
    }

    /**
     * Set the player to status ready or not for the game
     *
     * @param val ready status (false or true)
     */
    public void setReady(boolean val) {
        readyToStart = val;
    }

    /**
     * Check if the player is ready or not
     *
     * @return true if the player is ready
     */
    public boolean isReady() {
        return readyToStart;
    }

    /**
     * Decrease the player's energy by the given value
     *
     * @param value the value
     */
    public void decreaseEnergy(int value) {
        energy -= value;
    }

    /**
     * Increase the player's energy by the given value
     *
     * @param value the value
     */
    public void increaseEnergy(int value) {
        energy += value;
    }

    /**
     * Get the player's current energy value
     *
     * @return player's energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Sets the player's energy
     *
     * @param energy initial energy
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public void skip() {
        if (!hasSkill(PlayerSkillsType.SKIP))  return; // player does not have the skill
        if (steps >= 3) return; // can't do more than 3 steps

        steps++;
    }

    @Override
    public void putTrap() {
        if (!hasSkill(PlayerSkillsType.TRAP)) return; // player does not have the skilltrap.isSet()

        if (trap.isSet())
            return; // there is an active trap

        /**
         * Check if the player have enough energy
         */
        if (canPerformEnergyAction(Game.settings.trapEnergy)) { // have enough energy
            /**
             * place the trap and decrease the player's energy
             */
            trap.setTrap(getCell());
            decreaseEnergy(Game.settings.trapEnergy);

            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("place_trap.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
