/* This class uses a partially hollow 2D array to represent the games grid.  
 * Row and column corresponds to the 2D array row and column respectively. 
 * Hence, for the standard grid both row and column must be in the range 
 * 0 to 10. Furthermore, either row or column must be 0, 5 or 10.    
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Grid implements Serializable {

    Cell cells[] = new Cell[57];
    Cell cells2D[][] = new Cell[11][11];
    Node nodes[][] = new Node[11][11];

    public Grid() {
        int k = 0;
        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)) {
                    cells2D[row][column] = new Cell(row, column);
                    cells[k++] = cells2D[row][column];
                    nodes[row][column] = new Node(cells2D[row][column]);
                }
            }
        }

        // build the graph
        for (int row = 0; row < 11; row++)
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)) {
                    Node v = nodes[row][column];
                    if (column % 5 == 0 && row > 0) {     // up cell
                        v.addEdge(new Edge(nodes[row - 1][column], 1));
                    }
                    if (column % 5 == 0 && row < 11 - 1) { // down cell
                        v.addEdge(new Edge(nodes[row + 1][column], 1));
                    }
                    if (row % 5 == 0 && column > 0) {     // left cell
                        v.addEdge(new Edge(nodes[row][column - 1], 1));
                    }
                    if (row % 5 == 0 && (column < 11 - 1)) { // right cell
                        v.addEdge(new Edge(nodes[row][column + 1], 1));
                    }
                }
            }
    }

    /* Returns a reference to the specified cell.
     * row and cell must be in the range 0 .. 10 and either row or col
     * must be 0, 5 or 10.
    */
    public Cell getCell(int row, int col) throws Exception {
        if ((row % 5 != 0 && col % 5 != 0) ||
                row < 0 || row > 10 || col < 0 || col > 10)
            throw new Exception("Invalid Coordiantes row = " + row + " column " + col);
        return cells2D[row][col];
    }

    /* Returns the cell in the specified direction of the given cell.
       Valid direction must be either 'R', 'L', 'U', 'D' or ' '.
       A null value will be returned if attempt to get a non-existent cell.
    */
    public Cell getCell(Cell cell, char direction, int steps) {
        Cell tempCell = null;
        try {
            tempCell = getCell(cell.row, cell.col);
        } catch (Exception e) {
            // TODO Auto-generated catch block
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

    public Cell getCell(Cell cell, char direction) {
        if (direction == ' ') return cell;
        if (direction == 'U') {
            if (cell.col % 5 == 0 && cell.row > 0)
                return cells2D[cell.row - 1][cell.col];
            return cell;
        } else if (direction == 'D') {
            if (cell.col % 5 == 0 && cell.row < 10)
                return cells2D[cell.row + 1][cell.col];
            return cell;
        } else if (direction == 'L') {
            if (cell.row % 5 == 0 && cell.col > 0)
                return cells2D[cell.row][cell.col - 1];
            return cell;
        } else if (direction == 'R') {
            if (cell.row % 5 == 0 && cell.col < 10)
                return cells2D[cell.row][cell.col + 1];
            return cell;
        }
        return null;
    }

    public Cell[] getAllCells() {
        return cells;
    }

    /* helper method to check whether val is in the range a to b
     */
    private boolean inBetween(int val, int a, int b) {
        if (val >= a && val <= b)
            return true;
        else return false;

    }

    private void clearGraph(){
        for (int row = 0; row < 11; row++)
            for (int column = 0; column < 11; column++) {
                if ((row % 5 == 0) || (column % 5 == 0 && row % 5 != 0)){
                    nodes[row][column].previous = null;
                    nodes[row][column].minDistance= Double.POSITIVE_INFINITY;
                }
            }
    }

    private void computePaths(Node sourceNode, Trap trap) {
        sourceNode.minDistance = 0.;
        PriorityQueue<Node> nodes = new PriorityQueue<Node>();
        nodes.add(sourceNode);
        while (!nodes.isEmpty()) {
            Node u = nodes.poll();
            // Visit each edge exiting u
            for (Edge e : u.linkedCells) {
                Node v = e.getTargetNode();
                double weight = e.getTargetNode().getCell().equals(trap.getCell()) ? trap.getAffectTime() + trap.getDurationTime() : e.getValue();
//                double weight = e.getValue();
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    nodes.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    nodes.add(v);
                }
            }
        }
    }

    private Cell getCellTo(Node from, Node to) {
        List<Node> path = new ArrayList<Node>();

        for (Node node = to; node != null; node = node.previous) {
            path.add(node);
        }
        Collections.reverse(path);
//        System.out.println("Path: " + path);
        return path.get(1).getCell();
    }


    /* returns the best direction from source cell to the target cell.
     * Assumed cells passed are valid cells in the grid.
     * you need to verify this method 
     */
    public char getBestDirection(Cell from, Cell to, Trap trap) {
        /*
        compute path from the monster to all node
         taking into consideration available traps
         */
//        System.out.println("from row:"+ from.col+" from col:"+from.col);
        clearGraph();
        computePaths(nodes[from.row][from.col], trap);
        Cell newTo = getCellTo(nodes[from.row][from.col], nodes[to.row][to.col]);
//        System.out.println("" + from.row + "-" + from.col);
//        System.out.println("" + newTo.row + "-" + newTo.col);
//        System.out.println("======");

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

        int row = newTo.row;
        int col = newTo.col;

        if (inBetween(newTo.row % 5, 1, 2))
            row = newTo.row / 5 * 5;
        else if (inBetween(to.row % 5, 3, 4))
            row = newTo.row / 5 * 5 + 5;
        if (inBetween(newTo.col % 5, 1, 2))
            col = newTo.col / 5 * 5;
        else if (inBetween(newTo.col % 5, 3, 4))
            col = newTo.col / 5 * 5 + 5;


        if (from.row % 5 == 0)
            if (from.col < col)
                return 'R';
            else if (from.col > col)
                return 'L';
        if (from.col % 5 == 0)
            if (from.row < row)
                return 'D';
            else if (from.row > row)
                return 'U';
        return ' ';
    }

    /* A helper method to get the absolute value */
    private int abs(int x) {
        if (x >= 0) return x;
        else return -x;
    }

    /* A helper method to get the minimum of three values */
    private int min(int x, int y, int z) {
        if (x <= y && x <= z) return x;
        if (y <= z && y <= x) return y;
        return z;
    }

    /* A method to get the shortest distance from one cell to another
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

    /* Test harness for Grid*/
    public static void main(String args[]) throws Exception {
        Grid grid = new Grid();
        Cell c1 = grid.getCell(0, 0);
        Cell c2 = grid.getCell(10, 10);
        Cell c3 = grid.getCell(0, 2);
        Cell c4 = grid.getCell(2, 0);
        Cell c5 = grid.getCell(8, 5);

//        System.out.println("Distance from (0,0) to (10,10) is "
//                + grid.distance(c1, c2));
//        System.out.println("Distance from (0,0) to (8,5) is "
//                + grid.distance(c1, c5));
//        System.out.println("From (0,0) to (0,2) best direction is "
//                + grid.getBestDirection(c1, c3));
//        System.out.println("From (0,0) to (2,0) best direction is "
//                + grid.getBestDirection(c1, c4));
    }
}