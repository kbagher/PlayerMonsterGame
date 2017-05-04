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
		if (currentCell.nougat.isConsumed())
			calories = increaseCalories(currentCell.nougat.getValue());
		if (canPerformEnergyAction(calculateCalories(steps))) {
			if (currentDirection != ' ') {
				System.out.println(calories);
				Cell tempcell = grid.getCell(currentCell, currentDirection, steps);
				steps = grid.distance(currentCell, tempcell);
				calories = reduceCalories(calculateCalories(steps));
				// System.out.println(calories+"After "+ steps);

				if (tempcell != null)
					currentCell = tempcell;
				if (currentCell.nougat.isConsumed()) {
					calories = increaseCalories(currentCell.nougat.getValue());
					steps = 1;
					return currentCell;
				}
				calories += currentCell.nougat.getValue();
				currentCell.nougat.setConsumed();
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
			sum += Math.pow(Settings.STEP_CONSUMED_CALORIES, i);

        }
        return sum;
    }

    public void increaseSteps() {
        if (steps >= 3) return;
        steps++;
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

	public int reduceCalories(int value) {
		calories -= value;
		return calories;

	}

	public int increaseCalories(int value) {
		calories += value;
		return calories;
	}

	public int getCalories() {
		return calories;
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
        if (canPerformEnergyAction(Settings.TRAP_REQUIRED_ENERGY)) {
            System.out.println("Setting Trap");
            trap.setTrap(getCell(), Settings.TRAP_DURATION, Settings.TRAP_AFFECT_DURATION);
            calories = reduceCalories(Settings.TRAP_REQUIRED_ENERGY);

            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("place_trap.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
