
public class Trap {

    private int affectTime;
    private int durationTime;
    private Cell currentCell;
    private boolean isSteppedOver;

    public Trap(Cell cell,int affectTime, int durationTime) {
        this.affectTime = affectTime;
        this.durationTime = durationTime;
        this.currentCell = cell;
        isSteppedOver = false;
    }

    public Cell getCell(){
        return currentCell;
    }

    public int getDurationTime() {
        return durationTime;
    }


    public boolean stepOver(){
        isSteppedOver = true;
        if(affectTime!=0)
            return false;
        return true;
    }

    public int updateTime() {
        if (isSteppedOver){
            return affectTime--;
        }
        else if (durationTime == 0)
            return 0;
        else {
            return durationTime--;
        }
    }

}
