/**
 * Created by kassem on 8/5/17.
 */
public class Edge {
    private Node targetNode;
    private double value;

    public Edge(Node argTarget, double argWeight) {
        targetNode = argTarget;
        value = argWeight;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public double getValue() {
        return value;
    }
}
