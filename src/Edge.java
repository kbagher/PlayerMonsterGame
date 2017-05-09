import java.io.Serializable;

/**
 * Created by kassem on 8/5/17.
 */
public class Edge implements Serializable {
    private SpriteNode targetNode;
    private double weight;

    public Edge(SpriteNode targetNode, double edgeWeight) {
        this.weight = edgeWeight;
        this.targetNode = targetNode;
    }
    public double getWeight() {
        return weight;
    }
    public SpriteNode getTargetNode() {
        return targetNode;
    }

}
