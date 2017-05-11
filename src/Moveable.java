import java.io.Serializable;
import java.util.ArrayList;

/**
 * The abstract base class for Monster and Player.
 * The abstract method move() must be overridden by Player and Monster classes
 */
public abstract class Moveable extends Sprite implements Serializable {
    /**
     * The Current direction.
     */
    protected char currentDirection;
    /**
     * Available skills
     */
    private ArrayList<Object> skills;

    /**
     * Instantiates a new Moveable.
     *
     * @param g grid
     */
    public Moveable(Grid g) {
        super(g);
        skills = new ArrayList<>();
    }

    /**
     * Sets direction.
     *
     * Direction can be one of the following:
     * 'U' for moving up
     * 'D' for moving down
     * 'L' for moving left
     * 'R' for moving right
     * ' ' to stay still at the current position
     *
     * @param d required direction
     */
    public void setDirection(char d) {
        currentDirection = d;
    }

    /**
     * Add a skill
     *
     * A maximum of 2 skills can be loaded for each monster or player
     * @param skill the skill
     * @return true if the skill is added
     */
    public boolean addSkill(Object skill) {
        if (skills.size() == 2)
            return false; // max skills reached
        if (hasSkill(skill))
            return true; // already has the skill
        skills.add(skill);
        return true; // skill added
    }

    /**
     * Remove all the available skills
     */
    public void removeSkills(){
        skills.clear();
    }

    /**
     * Replace the current skills with the given ones
     *
     * @param skillsArr the skills arr
     * @return the boolean
     */
    public boolean replaceSkills(ArrayList skillsArr) {
        if (skillsArr.size()>2)
            return false; // new skills are more than the max allowed
        removeSkills();
        for (int x=0;x<skillsArr.size();x++){
            if (!hasSkill(skillsArr.get(x)))
                skills.add(skillsArr.get(x));
        }
        return true; // skills replaced
    }

    /**
     * Check weather the moveable has any skill loaded or not
     *
     * @return true if it has at least one skill
     */
    public boolean hasSkills(){
        return skills.size()==0? false:true;
    }

    /**
     * Remove a specific skill
     *
     * @param skill the skill to be removed
     */
    public void removeSkill(Object skill) {
        if (hasSkills()) return; // no skills available

        if (skills.contains(skill))
            skills.remove(skill);
    }

    /**
     * Check weather the moveable has a specific skill loaded or not
     *
     * @param skill the skill to be checked against
     * @return true if it has the given skill
     */
    public boolean hasSkill(Object skill){
        if (hasSkills()) return false; // no skills available

        return getSkills().contains(skill);
    }


    /**
     * get all available skills
     *
     * @return list of all loaded skills
     */
    public ArrayList<?> getSkills() {
        return skills;
    }

    /**
     * Get the current direction.
     *
     * Direction can be one of the following:
     * 'U' for up
     * 'D' for down
     * 'L' for left
     * 'R' for right
     * ' ' not moving
     * @return the direction
     */
    public char getDirection() {
        return currentDirection;
    }

    /**
     * Move the player or monster to the proper cell based on his direction
     *
     * @return the destination cell
     */
    public abstract Cell move();
}