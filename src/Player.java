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
        increaseCalories(currentCell.nougat.consume());
        if (canPerformEnergyAction(calculateCalories(steps))) {
            if (currentDirection != ' ') {
                System.out.println(calories);
                Cell tempcell = grid.getCell(currentCell, currentDirection, steps);
                steps = grid.distance(currentCell, tempcell);
                reduceCalories(calculateCalories(steps));
                if (tempcell != null)
                    currentCell = tempcell;
                steps = 1;
                return currentCell;
            }
            steps = 1;
            return currentCell;
        }
        steps = 1;
        return currentCell;
    }

    public int calculateCalories(int steps) {
        int sum = 0;
        for (int i = 1; i <= steps; i++) {
            sum += Math.pow(Settings.getStepCalories(), i);
        }
        return sum;
    }

    public boolean canPerformEnergyAction(int requiredCalories) {
        if (requiredCalories <= calories)
            return true;
        return false;
    }

    public void setReady(boolean val) {
        readyToStart = val;
    }

    public boolean isReady() {
        return readyToStart;
    }

    public void reduceCalories(int value) {
        calories -= value;
    }

    public void increaseCalories(int value) {
        calories += value;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public void skip() {
        if (!hasSkill(PlayerSkillsType.SKIP))  return;
        if (steps >= 3) return;
        steps++;
    }

    @Override
    public void putTrap() {

        if (!hasSkill(PlayerSkillsType.TRAP))  return;

        if (trap.getCell() != null)
            return; // there is an active trap

        // TODO: call canPerformEnergyAction(calories)
        // enough calories to put a trap
        if (canPerformEnergyAction(Settings.getTrapRequiredEnergy())) {
            System.out.println("Setting Trap");
            trap.setTrap(getCell());
            reduceCalories(Settings.getTrapRequiredEnergy());
            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("place_trap.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
