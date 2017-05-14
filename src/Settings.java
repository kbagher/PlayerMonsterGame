import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game Settings.
 *
 * This class contains all variables used in the game.
 * Any future settings should be added in this class and used
 * in the code where needed.
 *
 * Loading the settings to the GUI and storing the settings requires manual coding.
 * Check displaySettingsInUI() and saveSettings() in the Game class.
 *
 */
public class Settings implements Serializable {
    /**
     * The Game speed.
     */
    public int gameSpeed;
    /**
     * The Time allowed.
     */
    public int timeAllowed;
    /**
     * The Trap duration.
     */
    public int trapDuration;
    /**
     * The Trap energy.
     */
    public int trapEnergy;
    /**
     * The Trap effect duration.
     */
    public int trapEffectDuration;
    /**
     * The Nougat energy.
     */
    public int nougatEnergy;
    /**
     * The Player's initial energy.
     */
    public int initialEnergy;
    /**
     * The Player's step energy.
     */
    public int stepEnergy;
    /**
     * The Player's skills.
     */
    public ArrayList pSkills;
    /**
     * The Monster's skills.
     */
    public ArrayList mSkills;


    /**
     * Game grid structure
     */
    public GridStructure gridStructure;

    /**
     * Instantiates a new Settings.
     *
     * Default value wil be used if the player did not change
     * or save the settings or save the game
     *
     */
    public Settings() {
        nougatEnergy = 6;
        gameSpeed = 250;
        timeAllowed = 140;
        trapEnergy = 50;
        trapDuration = 10;
        trapEffectDuration = 5;
        initialEnergy = 40;
        stepEnergy = 2;
        gridStructure = new GridStructure();
        pSkills = new ArrayList<>();
        mSkills = new ArrayList<>();
    }
}
