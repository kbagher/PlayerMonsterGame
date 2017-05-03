/* The abstract base class for Moster and Player
 * The abstract method move() must be overridden by Player and Monster classes
 */

import java.util.ArrayList;

public abstract class Moveable {
    protected char currentDirection;
    protected Cell currentCell;
    protected Grid grid;
    private ArrayList<Object> skills;

    public Moveable(Grid g) {
        grid = g;
        skills = new ArrayList<>();
    }

    public void setDirection(char d) {
        currentDirection = d;
    }

    // TODO: throw an exception
    public boolean addSkill(Object skill) {
        if (skills.size() == 2)
            return false;
        skills.add(skill);
        return true;
    }

    public ArrayList<?> getSkills() {
        return skills;
    }

    public char getDirection() {
        return currentDirection;
    }

    public void setCell(Cell c) {
        currentCell = c;
    }

    public Cell getCell() {
        return currentCell;
    }

    public abstract Cell move();
}