import java.io.Serializable;

public class Nougats  implements Serializable{
	
	private int value ;
	private boolean consumed;
	
	public Nougats (int value){
		this.value= value;
		consumed=false;
		
	}
	
	public int getValue (){
		return value;
	}

	public void setConsumed (){
		value =0;
		consumed=true;
		
	}
}
