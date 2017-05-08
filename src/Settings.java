import java.io.Serializable;

/**
 * Created by kassem on 3/5/17.
 */
public class Settings implements Serializable {
    private static int GAME_SPEED = 250;
    private static int TIME_ALLOWED = 300;
    private static int TRAP_DURATION = 10;
    private static int TRAP_REQUIRED_ENERGY = 1;
    private static int TRAP_EFFECT_DURATION = 5;
    private static int NOUGAT_CALORIES = 6;
    private static int CALORIES_INITIAL_VALUE=40;
    private static int STEP_CALORIES =2;

    public static int getGameSpeed() {
        return GAME_SPEED;
    }

    public static void setGameSpeed(int gameSpeed) {
        GAME_SPEED = gameSpeed;
    }

    public static int getTimeAllowed() {
        return TIME_ALLOWED;
    }

    public static void setTimeAllowed(int timeAllowed) {
        TIME_ALLOWED = timeAllowed;
    }

    public static int getTrapDuration() {
        return TRAP_DURATION;
    }

    public static void setTrapDuration(int trapDuration) {
        TRAP_DURATION = trapDuration;
    }

    public static int getTrapRequiredEnergy() {
        return TRAP_REQUIRED_ENERGY;
    }

    public static void setTrapRequiredEnergy(int trapRequiredEnergy) {
        TRAP_REQUIRED_ENERGY = trapRequiredEnergy;
    }

    public static int getTrapEffectDuration() {
        return TRAP_EFFECT_DURATION;
    }

    public static void setTrapEffectDuration(int trapEffectDuration) {
        TRAP_EFFECT_DURATION = trapEffectDuration;
    }

    public static int getNougatCalories() {
        return NOUGAT_CALORIES;
    }

    public static void setNougatCalories(int nougatCalories) {
        NOUGAT_CALORIES = nougatCalories;
    }

    public static int getCaloriesInitialValue() {
        return CALORIES_INITIAL_VALUE;
    }

    public static void setCaloriesInitialValue(int caloriesInitialValue) {
        CALORIES_INITIAL_VALUE = caloriesInitialValue;
    }

    public static int getStepCalories() {
        return STEP_CALORIES;
    }

    public static void setStepCalories(int stepCalories) {
        STEP_CALORIES = stepCalories;
    }
}
