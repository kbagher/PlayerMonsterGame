import java.io.*;

/*  This class encapsulates player position and direction
 */
public class Player extends Moveable implements PlayerSkills,Serializable {
    private boolean readyToStart = false;
    private Trap trap;

    public Player(Grid g,Trap t, int row, int col) throws Exception {
        super(g);
        trap=t;
        currentCell = grid.getCell(row, col);
        currentDirection = ' ';
    }

    public Cell move() {
        currentCell = grid.getCell(currentCell, currentDirection);
        currentCell.nougat.setConsumed();
        return currentCell;
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
        if (trap.getCell()!=null)
            return; // there is an active trap

        // TODO: call canPerformEnergyAction(calories)
        // enough calories to put a trap
        if (true) {
            System.out.println("Setting Trap");
            trap.setTrap(getCell(),Settings.TRAP_DURATION,Settings.TRAP_AFFECT_DURATION);
            try {
                GameAudioPlayer player = new GameAudioPlayer();
                player.playAudio("place_trap.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}