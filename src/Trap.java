import java.io.Serializable;

/**
 * Trap used by the user
 */
public class Trap extends Sprite implements Serializable {

    /**
     * current effect time
     * this will be decreased (countdown) once the trap is activated
     */
    private int effectTime;
    /**
     * Current trap lifetime duration
     * this will be decreased (countdown) once the trap is placed
     * and the countdown will stop once the trap is activated or expired
     */
    private int lifetime;
    /**
     * Determine if the monster has stepped over the trap or not
     */
    private boolean isSteppedOver;

    /**
     * Gets current effect time.
     *
     * @return the effect time
     */
    public int getEffectTime() {
        return effectTime;
    }

    /**
     * Gets the trap duration time.
     *
     * @return the duration time
     */
    public int getLifetime() {
        return lifetime;
    }

    /**
     * Instantiates a new Trap.
     *
     * @param g the grid object
     */
    public Trap(Grid g) {
        super(g);
        isSteppedOver = false;
    }

    /**
     * Determine if the monster should be trapped ot not based on the passed cell
     *
     * @param cell the cell
     * @return the boolean
     */
    public boolean isTrapped(Cell cell) {
        // trap is not set
        if (getCell() == null) return false;

        // compare passed cell with the trap cell
        if (getCell().equals(cell)) {
            if (effectTime > 0)
                return true; // trapped and still active
        }
        // either not trapped or the effect time has expired
        return false;
    }

    /**
     * Activate the trap once stepped over it
     */
    public void stepOver() {
        isSteppedOver = true;
    }

    /**
     * Determine if the trap is placed on the grid or not
     *
     * @return trap placement status
     */
    public boolean isSet() {
        return getCell() != null;
    }

    /**
     * Place a trap on the grid
     *
     * @param cell the cel which will contain the trap
     */
    public void setTrap(Cell cell) {
        this.lifetime = Game.settings.trapDuration;
        this.effectTime = Game.settings.trapEffectDuration;
        setCell(cell);
    }

    /**
     * Remove the current trap from the grid
     */
    private void removeTrap() {
        setCell(null);
        isSteppedOver = false;
        this.lifetime = 0;
        this.effectTime = 0;
    }

    /**
     * Update trap status
     * This is called in every step to update the traps's status and timers
     */
    public void update() {
        if (!isSet())
            return; // trap not placed

        if (isSteppedOver && effectTime > 0) { // monster stepped on trap
            if (effectTime-- == 1)
                removeTrap();
            lifetime = 0;
        } else if (isSteppedOver && effectTime == 0) {
            removeTrap(); // effective time has expired
        } else if (lifetime <= 1) {
            removeTrap(); // lifetime has expired without stepping on the trap
        } else {
            lifetime--; // placed and not stepped over
        }
    }

}
