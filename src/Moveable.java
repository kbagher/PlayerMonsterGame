import java.io.Serializable;
import java.util.ArrayList;

/**
 * The abstract base class for Monster and Player.
 * <p>
 * The abstract method move() must be overridden by Player and Monster classes
 */
public abstract class Moveable extends Sprite implements MoveableSkills, Serializable {
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
     * <p>
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

    @Override
    public boolean isCompatibleSkill(Object skill) {
        /*
         * Both Movable and Movable skill type should contain similar Movable name.
         *
         * i.e. Monster and MonsterSkillType contains "Monster", etc...
         *
         * This will be used to check if the passed skill type is compatible
         * with the movable object or not
         */
        if(skill.getClass().getTypeName().toLowerCase().contains(
                this.getClass().getTypeName().toLowerCase()))
            return true;
        return false;
    }

    @Override
    public boolean addSkill(Object skill) throws Exception {
        if (!isCompatibleSkill(skill))
            throw new Exception("Skill type mismatch");
        if (skills.size() == 2)
            return false; // max skills reached
        if (hasSkill(skill))
            return true; // already has the skill
        skills.add(skill);
        return true; // skill added
    }

    @Override
    public void removeAllSkills() {
        skills.clear();
    }

    @Override
    public boolean replaceSkills(ArrayList skillsArr) {
        if (skillsArr.size() > 2)
            return false; // new skills are more than the max allowed
        removeAllSkills();
        for (Object aSkillsArr : skillsArr) {
            if (!hasSkill(aSkillsArr))
                skills.add(aSkillsArr);
        }
        return true; // skills replaced
    }

    @Override
    public boolean hasSkills() {
        return skills.size() != 0;
    }

    @Override
    public void removeSkill(Object skill) {
        if (hasSkills()) return; // no skills available

        if (skills.contains(skill))
            skills.remove(skill);
    }

    @Override
    public boolean hasSkill(Object skill) {
        return hasSkills() && getSkills().contains(skill); // has the skill
    }

    @Override
    public ArrayList<?> getSkills() {
        return skills;
    }

    /**
     * Get the current direction.
     * <p>
     * Direction can be one of the following:
     * 'U' for up
     * 'D' for down
     * 'L' for left
     * 'R' for right
     * ' ' not moving
     *
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