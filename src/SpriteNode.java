import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by kassem on 8/5/17.
 */
public class SpriteNode extends Sprite implements Serializable,Comparable<SpriteNode> {

    public LinkedList<Edge> linkedNodes = new LinkedList<>();
    public double distance = Double.MAX_VALUE;
    public SpriteNode previous;

    public Cell getCell() {
        return currentCell;
    }

    public SpriteNode(Grid g, int row, int column) throws Exception {
        super(g);
        currentCell = grid.getCell(row,column);
    }

    @Override
    public String toString() {
        return ""+ currentCell.row+"."+currentCell.col;
    }

    @Override
    public int compareTo(SpriteNode other) {
        return Double.compare(distance, other.distance);
    }

    public void addEdge(Edge e) {
        linkedNodes.add(e);
    }



}