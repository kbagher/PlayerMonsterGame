
public class Trap {
	
	private int affectTime;
	private int durationTime;
	
	public Trap (int affectTime, int durationTime)
	{
		this.affectTime= affectTime;
		this.durationTime= durationTime;	
	}
	
	public int getAffectTime() {
		return affectTime;
	}



	public void setAffectTime(int affectTime) {
		this.affectTime = affectTime;
	}



	public int getDurationTime() {
		return durationTime;
	}



	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}
	
	public int reduceDurationTime ()
	{
		if (durationTime == 0)
			return 0;
		else {
			durationTime--;
		
			return durationTime+1;
		}
		
	}
	
}
