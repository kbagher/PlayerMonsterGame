/* The abstract base class for Moster and Player
 * The abstract method move() must be overridden by Player and Monster classes
 */

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Moveable extends Sprite implements Serializable {
    protected char currentDirection;
    private ArrayList<Object> skills;

    public Moveable(Grid g) {
        super(g);
        skills = new ArrayList<>();
    }

    public void setDirection(char d) {
        currentDirection = d;
    }

    // TODO: throw an exception
    public boolean addSkill(Object skill) {
        if (skills.size() == 2)
            return false;
        if (hasSkill(skill))
            return true;
        skills.add(skill);
        return true;
    }

    public void removeSkills(){
        skills.clear();
    }

    public boolean updateSkills(ArrayList skillsArr) {
        if (skillsArr.size()>2)
            return false;
        skills.clear();
        for (int x=0;x<skillsArr.size();x++){
            if (!hasSkill(skillsArr.get(x)))
                skills.add(skillsArr.get(x));
        }
        return true;
    }

    public boolean hasSkills(){
        return skills.size()==0? false:true;
    }

    public void removeSkill(Object skill) {
        if (skills.size()==0) return;

        if (skills.contains(skill))
            skills.remove(skill);
    }

    public boolean hasSkill(Object skill){
        if (getSkills().size() == 0) return false;

        return getSkills().contains(skill);
    }


    public ArrayList<?> getSkills() {
        return skills;
    }

    public char getDirection() {
        return currentDirection;
    }

    public abstract Cell move();
}