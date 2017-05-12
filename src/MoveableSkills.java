import java.util.ArrayList;

/**
 * Moveable skills.
 *
 * The use of this interface will allow moveable objects to have all required operations
 * to handle it's available skills.
 */
public interface MoveableSkills {
    /**
     * Add a skill
     * <p>
     * A maximum of 2 skills can be loaded for each monster or player
     *
     * @param skill the skill
     * @return true if the skill is added
     */
    boolean addSkill(Object skill);

    /**
     * Remove all the available skills
     */
    void removeAllSkills();

    /**
     * Replace the current skills with the given ones
     *
     * @param skillsArr the skills arr
     * @return the boolean
     */
    boolean replaceSkills(ArrayList skillsArr);

    /**
     * Check weather the moveable has any skill loaded or not
     *
     * @return true if it has at least one skill
     */
    boolean hasSkills();

    /**
     * Remove a specific skill
     *
     * @param skill the skill to be removed
     */
    void removeSkill(Object skill);

    /**
     * Check weather the moveable has a specific skill loaded or not
     *
     * @param skill the skill to be checked against
     * @return true if it has the given skill
     */
    boolean hasSkill(Object skill);

    /**
     * get all available skills
     *
     * @return list of all loaded skills
     */
    ArrayList<?> getSkills();
}
