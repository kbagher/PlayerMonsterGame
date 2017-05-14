import java.io.Serializable;

/**
 * Graph edge.
 * <p>
 * The edge will connect nodes together in order to build the graph
 */
public class Edge implements Serializable {
    /**
     * target node linked to the edge
     */
    private SpriteNode targetNode;
    /**
     * edge weight, will always be 1 as the grid has similar values.
     * however, if there are a trap at the target node, the weight will be the
     * traps's duration + effective time
     */
    private double weight;

    /**
     * Instantiates a new Edge.
     *
     * @param targetNode the target node
     * @param edgeWeight the edge weight
     */
    public Edge(SpriteNode targetNode, double edgeWeight) {
        this.weight = edgeWeight;
        this.targetNode = targetNode;
    }

    /**
     * Returns the edge weight.
     *
     * @return edge weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the target node.
     *
     * @return target node
     */
    public SpriteNode getTargetNode() {
        return targetNode;
    }

}
