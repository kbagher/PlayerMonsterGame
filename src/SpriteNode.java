import java.io.Serializable;
import java.util.LinkedList;

/**
 * Graph node.
 * <p>
 * The node will hold all the linked nodes simulating a grid cell.
 * <p>
 * Although SpriteNode does not represent a bitmap object, we will inherit the sprite characteristics
 * to link it with a cell on the grid
 */
public class SpriteNode extends Sprite implements Serializable, Comparable<SpriteNode> {

    /**
     * The Linked nodes.
     */
    public LinkedList<Edge> linkedNodes = new LinkedList<>();
    /**
     * The shortest distance to this node
     * The initial values will always be infinity and will always
     * be updated with the minimum distance found
     */
    public double distance = Double.POSITIVE_INFINITY;
    /**
     * The Previous linked node using the shortest path (minimum distance)
     */
    public SpriteNode previous;

    /**
     * Instantiates a new Sprite node.
     *
     * @param g      The grid
     * @param row    row on the grid
     * @param column column on the grid
     *
     * @throws Exception exception
     */
    public SpriteNode(Grid g, int row, int column) throws Exception {
        super(g);
        currentCell = grid.getCell(row, column);
    }

    @Override
    public String toString() {
        return "" + currentCell.row + "." + currentCell.col;
    }

    @Override
    public int compareTo(SpriteNode other) {
        return Double.compare(distance, other.distance);
    }

    /**
     * Add a new edge to the node
     *
     * @param edge node edge
     */
    public void addEdge(Edge edge) {
        linkedNodes.add(edge);
    }


}