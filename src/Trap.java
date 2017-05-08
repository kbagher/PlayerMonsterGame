import java.io.Serializable;

public class Trap extends Sprite implements Serializable {

    private int affectTime;

    public int getAffectTime() {
        return affectTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    private int durationTime;
    private boolean isSteppedOver;

    public Trap(Grid g) {
        super(g);
        isSteppedOver = false;
    }

    public boolean isTrapped(Cell cell) {
        if (getCell()==null) return false;
        if (getCell().equals(cell)) {
            if (affectTime > 0)
                return true;
        }
        return false;
    }

    public void stepOver() {
        isSteppedOver = true;
    }

    public boolean isSet() {
        return getCell() != null;
    }

    public void setTrap(Cell cell) {
        this.durationTime = Settings.getTrapDuration();
        this.affectTime = Settings.getTrapEffectDuration();
        setCell(cell);
    }

    private void removeTrap() {
        setCell(null);
        isSteppedOver = false;
        this.durationTime = 0;
        this.affectTime = 0;
    }

    public void update() {
        if (!isSet())
            return;

        if (isSteppedOver && affectTime > 0) {
            if (affectTime-- == 1)
                removeTrap();
            durationTime = 0;
        } else if (isSteppedOver && affectTime == 0) {
            removeTrap();
        } else if (durationTime <= 1) {
            removeTrap();
        } else {
            durationTime--;
        }
    }

}
