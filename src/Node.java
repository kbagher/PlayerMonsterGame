import java.util.ArrayList;

/**
 * Created by kassem on 8/5/17.
 */
public class Node  implements Comparable<Node> {

    //    public final String name;
    private Cell cell;
    public ArrayList<Edge> linkedCells = new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;


    public Cell getCell() {
        return cell;
    }
    public Node(Cell cell) {
        this.cell = cell;
    }

    public String toString() {
        return ""+ cell.row+"."+cell.col;
    }

    public void addEdge(Edge e) {
        linkedCells.add(e);
    }

    public int compareTo(Node other) {
        return Double.compare(minDistance, other.minDistance);
    }

}