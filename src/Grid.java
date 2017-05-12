/* This class uses a partially hollow 2D array to represent the games grid.  
 * Row and column corresponds to the 2D array row and column respectively. 
 * Hence, for the standard grid both row and column must be in the range 
 * 0 to 10. Furthermore, either row or column must be 0, 5 or 10.    
*/

import java.io.Serializable;
import java.util.*;

/**
 * Game board grid.
 * <p>
 * Represents the game grid and responsible of it's related
 * operations, including building the grid and maintaining it's cells
 */
public class Grid implements Serializable {

    /**
     * All grid cells
     */
    private Cell[] cells = new Cell[57];
    /**
     * The Cells 2D representation.
     */
    private Cell[][] cells2D = new Cell[11][11];
    /**
     * Graph sprite nodes 2D representation
     */
    private SpriteNode[][] spriteNodes = new SpriteNode[11][11];

    /**
     * Instantiates a new Grid.
     *
     * @throws Exception the exception
     */
    public Grid() throws Exception {

        /*
         * Building the grid and the cells'
         * representation on the board
         */
        int k = 0;
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)) {
                    cells2D[row][column] = new Cell(row, column);
                    cells[k++] = cells2D[row][column];
                    // track cell's graph node position representation on the 2D grid
                    spriteNodes[row][column] = new SpriteNode(this, row, column);
                }
            }
        }
        // build the graph
        buildGraph();
    }

    /**
     * Build a graph by linking all related nodes
     * with each other to allow traveling between nodes.
     * <p>
     * i.e. node 0.0 will be linked to the following two nodes:
     * 0.1 Right direction
     * 1.0 Down direction
     */
    private void buildGraph() {
        for (int row = 0; row < 11; row++)
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)) {
                    SpriteNode node = spriteNodes[row][column];
                    if (column % 5 == 0 && row > 0) {     // up node
                        node.addEdge(new Edge(spriteNodes[row - 1][column], 1));
                    }
                    if (column % 5 == 0 && row < 11 - 1) { // down node
                        node.addEdge(new Edge(spriteNodes[row + 1][column], 1));
                    }
                    if (row % 5 == 0 && column > 0) {     // left node
                        node.addEdge(new Edge(spriteNodes[row][column - 1], 1));
                    }
                    if (row % 5 == 0 && (column < 11 - 1)) { // right node
                        node.addEdge(new Edge(spriteNodes[row][column + 1], 1));
                    }
                }
            }
    }

    /**
     * Returns a reference to the specified cell.
     * row and column must be in the range 0 .. 10 and either row or col
     * must be 0, 5 or 10.
     *
     * @param row cell row
     * @param col cell column
     *
     * @return cell object reference
     *
     * @throws Exception the exception
     */
    public Cell getCell(int row, int col) throws Exception {
        if ((row % 5 != 0 && col % 5 != 0) ||
                row < 0 || row > 10 || col < 0 || col > 10)
            throw new Exception("Invalid Coordinates row = " + row + " column " + col);
        return cells2D[row][col];
    }

    /**
     * Returns a reference to the specified node.
     * row and column must be in the range 0 .. 10 and either row or col
     * must be 0, 5 or 10
     *
     * @param row node row
     * @param col node column
     *
     * @return node object reference
     *
     * @throws Exception the exception
     */
    public SpriteNode getNode(int row, int col) throws Exception {
        if ((row % 5 != 0 && col % 5 != 0) ||
                row < 0 || row > 10 || col < 0 || col > 10)
            throw new Exception("Invalid Coordinates row = " + row + " column " + col);
        return spriteNodes[row][col];
    }


    /**
     * Returns the cell in the specified direction of the given cell
     * and the number of steps in the given direction.
     * <p>
     * Valid direction must be either 'R', 'L', 'U', 'D' or ' '.
     * <p>
     * A null value will be returned if attempt to instance a non-existent cell.
     * <p>
     * If the number of steps is invalid, the max number of possible steps
     * (not exceeding the given number steps) will be considered
     *
     * @param cell      cell reference
     * @param direction movement direction
     * @param steps     movement steps in teh specified direction
     *
     * @return cell reference
     */
    public Cell getCell(Cell cell, char direction, int steps) {
        Cell tempCell = null;
        try {
            // get a reference to the cell
            tempCell = getCell(cell.row, cell.col);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= steps; i++) {
            if (getCell(tempCell, direction) == null)
                return tempCell;
            else
                tempCell = getCell(tempCell, direction);
        }
        return tempCell;
    }

    /**
     * Returns the cell in the specified direction of the given cell
     * <p>
     * Valid direction must be either 'R', 'L', 'U', 'D' or ' '.
     * <p>
     * A null value will be returned if attempt to instance a non-existent cell.
     *
     * @param cell      current cell
     * @param direction movement direction
     *
     * @return cell reference
     */
    public Cell getCell(Cell cell, char direction) {

        if (direction == ' ') return cell; // no direction

        if (direction == 'U') { // up
            if (cell.col % 5 == 0 && cell.row > 0)
                return cells2D[cell.row - 1][cell.col];
            return cell;
        } else if (direction == 'D') { // down
            if (cell.col % 5 == 0 && cell.row < 10)
                return cells2D[cell.row + 1][cell.col];
            return cell;
        } else if (direction == 'L') { // left
            if (cell.row % 5 == 0 && cell.col > 0)
                return cells2D[cell.row][cell.col - 1];
            return cell;
        } else if (direction == 'R') { // right
            if (cell.row % 5 == 0 && cell.col < 10)
                return cells2D[cell.row][cell.col + 1];
            return cell;
        }
        return null; // unknown direction
    }

    /**
     * Get all cells
     *
     * @return cells array
     */
    public Cell[] getAllCells() {
        return cells;
    }

    /**
     * helper method to check whether val is in the range a to b
     *
     * @param val value to be checked
     * @param a   from value
     * @param b   to value
     *
     * @return true if the given value is between the given two values
     */
    private boolean inBetween(int val, int a, int b) {
        if (val >= a && val <= b)
            return true;
        else return false;
    }

    /**
     * Reset all nodes' path links and calculated distance.
     * <p>
     * This method should be called if either the monster
     * or player moved.
     */
    private void clearGraph() {
        for (int row = 0; row < 11; row++)
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)) {
                    spriteNodes[row][column].previous = null;
                    spriteNodes[row][column].distance = Double.POSITIVE_INFINITY;
                }
            }
    }

    /**
     * Compute the path from the starting node to all available nodes.
     * <p>
     * Using Dijkstra's algorithm allows us to calculate the minimum path
     * to all other nodes.
     * <p>
     * If a trap is set in the grid, the the trap life and effect duration
     * is used calculate the distance.
     * <p>
     * The monster will avoid any path containing a trap. however,
     * if moving over a trap is considered shorter than any other path,
     * the monster will step over the trap
     *
     * @param sourceNode monster node
     * @param trap       trap reference
     */
    private void computePathsFromNode(SpriteNode sourceNode, Trap trap) {
        // Always set starting node distance to be 0
        sourceNode.distance = 0.;

        /*
         * Priority queue of all visited nodes (following Dijkstra's Algorithm)
         * to use the comparable method of the SpriteNode object.
         *
         * The queue will always contain the source node as the first element,
         * Since it also has a distance value of 0.
         *
         * The list will store object ordered from low to high distance
         */
        PriorityQueue<SpriteNode> visitedNodes = new PriorityQueue<>();
        visitedNodes.add(sourceNode);


        while (!visitedNodes.isEmpty()) {
            SpriteNode currentNode = visitedNodes.poll();
            /*
             * Visit all linked nodes to calculate it's distance
             * considering the trap weight
             */
            for (Edge edge : currentNode.linkedNodes) {
                SpriteNode linkedNode = edge.getTargetNode();
                /*
                 * calculate trap weight if it's set
                 */
                double trapWeight = 0;
                if (trap.isSet()){ // traps is set
                    if(edge.getTargetNode().getCell().equals(trap.getCell())) // trap is in same node
                        trapWeight = trap.getLifetime() + trap.getEffectTime();
                }
                /*
                 * Calculate the node's distance considering the trap's weight
                 */
                double calculatedWeight = trapWeight + edge.getWeight();
                double distanceThroughLinkedNode = currentNode.distance + calculatedWeight;

                /*
                 * Update the current node's distance if the new calculated distance
                 * is shorter.
                 *
                 * This will always update the node with the shortest path available
                 */
                if (distanceThroughLinkedNode < linkedNode.distance) {
                    /*
                     * remove the linked current node
                     */
                    visitedNodes.remove(linkedNode);

                    /*
                     * update the current linked node
                     */
                    linkedNode.distance = distanceThroughLinkedNode;
                    linkedNode.previous = currentNode;

                    /*
                     * add the node again to allow reordering using the
                     * compareTo method
                     */
                    visitedNodes.add(linkedNode);
                }
            }
        }
    }

    /**
     * Get the next movement cell in the shortest path.
     *
     * This method will get the shortest calculated path
     * to the player's node and return the next cell that
     * which the monster needs to move to.
     *
     * @param toNode player's node
     * @return monster's next movement cell
     */
    private Cell getPathToNode(SpriteNode toNode) {
        /*
         * Travel from the target node to the starting node (monster node)
         *
         * if the current node doesn't have 2 previous nodes, it means
         * that the current cell the next step for the monster
         */
        for (SpriteNode spriteNode = toNode; spriteNode != null; spriteNode = spriteNode.previous) {
            if (spriteNode.previous.previous==null)
                return spriteNode.getCell();
        }
        return null;
    }


    /**
     * Gets move direction.
     *
     * @param from the from
     * @param to   the to
     * @param trap the trap
     *
     * @return the move direction
     */
/* returns the best direction from source cell to the target cell.
     * Assumed cells passed are valid cells in the grid.
     * you need to verify this method 
     */
    public char getMoveDirection(Cell from, Cell to, Trap trap) {
        /*
        compute path from the monster to all node
         taking into consideration available traps
         */
        clearGraph();
        computePathsFromNode(spriteNodes[from.row][from.col], trap);
        Cell newTo = getPathToNode(spriteNodes[to.row][to.col]);

        if (from.row == newTo.row) {
            if (from.col < newTo.col)
                return 'R';
            else if (from.col > newTo.col)
                return 'L';
        } else if (from.col == newTo.col) {
            if (from.row < newTo.row)
                return 'D';
            else if (from.row > newTo.row)
                return 'U';
        }
        return ' ';
    }

    /* A helper method to instance the absolute value */
    private int abs(int x) {
        if (x >= 0) return x;
        else return -x;
    }

    /* A helper method to instance the minimum of three values */
    private int min(int x, int y, int z) {
        if (x <= y && x <= z) return x;
        if (y <= z && y <= x) return y;
        return z;
    }

    /**
     * Distance int.
     *
     * @param from the from
     * @param to   the to
     *
     * @return the int
     */
/* A method to instance the shortest distance from one cell to another
     * Assumed cells are valid cells in the grid
     */
    public int distance(Cell from, Cell to) {
        int d = 0;
        // compute minimum horizontal distance:
        if (from.row == to.row) d += abs(to.col - from.col);
        else
            d += min(from.col + to.col
                    , abs(from.col - 5) + abs(to.col - 5)
                    , abs(from.col - 10) + abs(to.col - 10));

        // compute minimum vertical distance as follows:
        if (from.col == to.col) d += abs(to.row - from.row);
        else d += min(from.row + to.row,
                abs(from.row - 5) + abs(to.row - 5),
                abs(from.row - 10) + abs(to.row - 10));
        return d;
    }

    /**
     * Main.
     *
     * @param args the args
     *
     * @throws Exception the exception
     */
/* Test harness for Grid*/
    public static void main(String args[]) throws Exception {
        Grid grid = new Grid();
        Cell c1 = grid.getCell(0, 0);
        Cell c2 = grid.getCell(10, 10);
        Cell c3 = grid.getCell(0, 2);
        Cell c4 = grid.getCell(2, 0);
        Cell c5 = grid.getCell(8, 5);

        System.out.println("Distance from (0,0) to (10,10) is "
                + grid.distance(c1, c2));
        System.out.println("Distance from (0,0) to (8,5) is "
                + grid.distance(c1, c5));

        System.out.println("From (0,0) to (0,2) best direction is "
                + grid.getMoveDirection(c1, c3, new Trap(null)));
        System.out.println("From (0,0) to (2,0) best direction is "
                + grid.getMoveDirection(c1, c4, new Trap(null)));
    }
}