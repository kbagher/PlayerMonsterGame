/**
 * Created by kassem on 2/5/17.
 */
public interface PlayerSkills {
    public enum PlayerSkillsType{
        TRAP,SKIP
    }
    public void skip(int moves);
    public void putTrap(Cell cell);
}
