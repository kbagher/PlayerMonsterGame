public class Trap extends Sprite {

    private int affectTime;
    private int durationTime;
    private boolean isSteppedOver;

    public Trap(Grid g) {
        super(g);
        isSteppedOver = false;
    }

    public int getAffectTime() {
        return affectTime;
    }

    public void setAffectTime(int affectTime) {
        this.affectTime = affectTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public boolean isTrapped(Cell cell) {
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
        return getCell() == null ? false : true;
    }


    public void setTrap(Cell cell, int durationTime, int affectTime) {
        this.durationTime = durationTime;
        this.affectTime = affectTime;
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
