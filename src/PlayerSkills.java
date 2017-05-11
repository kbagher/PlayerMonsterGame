/**
 * Players skills type and methods.
 *
 * This interface will imply the required player skills which needs to be implemented
 * and should include all future added skills.
 *
 */
public interface PlayerSkills {

    /**
     * List of all available skills
     */
    enum PlayerSkillsType{
        TRAP,SKIP
    }

    /**
     * Allow player to skip over multiple cells by pressing the (same)
     * button key repeatedly within the stipulated time slot.
     * But maximum number of cells is limited to three.
     * Each additional step in a move takes up double the
     * energy (2 for 1 step move, 2+ 4 for 2-steps move, 2+4+8 for 3-steps move)
     */
    void skip();

    /**
     * Allow player to put a trap in its current cell.
     * If a monster moves into that cell it will be prevented
     * from moving or eating anything for X time units
     * (from the time monster moved into the active trap).
     * A player can move over a trapped monster. Putting a trap
     * costs X calories and will last for X time units.
     * A player cannot place a new trap while the previous trap is still active.
     */
    void putTrap();
}
