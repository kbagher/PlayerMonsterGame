import java.util.ArrayList;

/**
 * Monster skills type and methods.
 *
 * This interface will imply the required monster skills which needs to be implemented
 * and should include all future added skills.
 *
 */
public interface MonsterSkills  {
    /**
     * List of all available skills
     */
    enum MonsterSkillsType{
        LEAP,INVISIBLE
    }
    /**
     * This monster can leap and eat any player in its
     * sight (horizontal and vertical)
     */
    void leap();

    /**
     * Allow monster to toggle between visible and invisible for
     * periods between 5-10 units of time
     */
    void invisible();
}
