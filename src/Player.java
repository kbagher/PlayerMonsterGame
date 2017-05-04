import java.io.*;

/*  This class encapsulates player position and direction
 */
public class Player extends Moveable implements PlayerSkills, Serializable {
    private boolean readyToStart = false;
    private int steps;
    private int calories;
    private Trap trap;

    public Player(Grid g, Trap t, int row, int col, int caloriesValue) throws Exception {
        super(g);
        currentCell = grid.getCell(row, col);
        currentDirection = ' ';
        calories = caloriesValue;
        trap = t;
    }

    public Cell move() {
        if (canPerformEnergyAction(calculateCalories(2))) {
            if (currentDirection != ' ') {
                //System.out.println(calories+"BEFORe");
                Cell tempcell = grid.getCell(currentCell, currentDirection, 2);
                steps = grid.distance(currentCell, tempcell);
                calories -= calculateCalories(steps);
                //System.out.println(calories+"After "+ steps);
                if (steps == 0) {
                    currentCell = tempcell;
                    return currentCell;
                } else {
                    currentCell = tempcell;
                    if (currentCell.nougat.isConsumed()) {
                        calories += currentCell.nougat.getValue();
                        return currentCell;
                    }

                    calories += currentCell.nougat.getValue();
                    currentCell.nougat.setConsumed();
                    return currentCell;

                }

            }
            return currentCell;
        } else
            return currentCell;
    }

    public int calculateCalories(int steps) {
        int sum = 0;
        for (int i = 1; i <= steps; i++) {
            sum += Math.pow(2, i);

        }
        return sum;
    }

    public boolean canPerformEnergyAction(int requiredCalories) {
        if (requiredCalories <= calories)
            return true;
        return false;
    }

    public int maxCellsPerMove() {
        return 1;
    }

    public int pointsRemaining() {
        return -1;  // not implemented
    }

    public void setReady(boolean val) {
        readyToStart = val;
    }

    public boolean isReady() {
        return readyToStart;
    }

    @Override
    public void skip(int moves) {

    }

    @Override
    public void putTrap() {
//        Trap currentTrap = grid.getCurrentTrap();
        if (trap.getCell() != null)
            return; // there is an active trap

        // TODO: call canPerformEnergyAction(calories)
        // enough calories to put a trap
        if (true) {
            System.out.println("Setting Trap");
            trap.setTrap(getCell(), Settings.TRAP_DURATION, Settings.TRAP_AFFECT_DURATION);
            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("place_trap.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
