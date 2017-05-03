/*  This class encapsulates player position and direction  
 */
public class Player extends Moveable implements PlayerSkills {
    private boolean readyToStart = false;

    public Player(Grid g, int row, int col) throws Exception {
        super(g);
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
        Trap currentTrap = grid.getCurrentTrap();

        if (currentTrap!=null)
            return; // there is an active trap

        // TODO: call canPerformEnergyAction(calories)
        // enough calories to put a trap
        if (true)
        {
            grid.setTrap(getCell());
        }
    }
}