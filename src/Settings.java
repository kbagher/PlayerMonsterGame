import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kassem on 3/5/17.
 */
public class Settings implements Serializable {
    public int gameSpeed;
    public int timeAllowed;
    public int trapDuration;
    public int trapEnergy;
    public int trapEffectDuration;
    public int nougatEnergy;
    public int initialEnergy;
    public int stepEnergy;
    public ArrayList pSkills;
    public ArrayList mSkills;

    public Settings() {
        nougatEnergy = 6;
        gameSpeed = 250;
        timeAllowed = 140;
        trapEnergy = 50;
        trapDuration = 10;
        trapEffectDuration = 5;
        initialEnergy = 40;
        stepEnergy = 2;
        pSkills = new ArrayList<>();
        mSkills = new ArrayList<>();
    }
}
