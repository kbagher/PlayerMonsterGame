
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Encapsulates grid design.
 * <p>
 * This class contains all of the grid columns and rows positions,
 * along with the grid size
 * <p>
 * It must be passed to the Grid object to build it.
 * <p>
 * A default constructor contains the default grid design of
 * a 11x11 grid with 3 rows and 3 columns.
 */
public class GridStructure implements Serializable {
    private ArrayList<Integer> rows = new ArrayList<>();
    private ArrayList<Integer> columns = new ArrayList<>();
    private int size;

    /**
     * Instantiates a new Grid structure.
     * <p>
     * default constructor contains the default grid
     * structure of 11x11 grid with 3 rows and 3 columns.
     */
    public GridStructure() {
        size = 11;
        rows.add(0);
        rows.add(5);
        rows.add(10);
        columns.add(0);
        columns.add(5);
        columns.add(10);
    }

    /**
     * Instantiates a new Grid structure with a grid size.
     * <p>
     * Calling this constructor will build the basic border rows and columns.
     *
     * @param s the grid size must be 3 or bigger
     *
     * @throws Exception the exception
     */
    public GridStructure(int s) throws Exception {
        if (s < 3)
            throw new Exception("Grid Structure size can't be less than 3");
        size = s;

        /*
         * Border rows and columns
         */
        rows.add(0);
        rows.add(size - 1);
        columns.add(0);
        columns.add(size - 1);
    }

    /**
     * Gets rows.
     *
     * @return the grid rows
     */
    public ArrayList<Integer> getRows() {
        return rows;
    }

    /**
     * Add row.
     *
     * @param row the row
     *
     * @throws Exception the exception
     */
    public void addRow(int row) throws Exception {
        if (row >= size || row < 0)
            throw new Exception("invalid row");
        if (rows.contains(row)) return;

        if (rows.size() == 0) {
            rows.add(row);

        } else {
            for (int x = 0; x < rows.size(); x++) {
                if (Math.abs(row-rows.get(x))==1)
                    throw new Exception("A space between rows is required");
            }
            rows.add(row);
        }
    }

    /**
     * Add column.
     *
     * @param column the column
     *
     * @throws Exception the exception
     */
    public void addColumn(int column) throws Exception {

        if (column >= size || column < 0)
            throw new Exception("invalid column");

        if (columns.contains(column)) return;

        if (columns.size() == 0) {
            columns.add(column);

        } else {
            for (int x = 0; x < columns.size(); x++) {
                if (Math.abs(column-columns.get(x))==1)
                    throw new Exception("A space between columns is required");
            }
            columns.add(column);
        }
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public ArrayList<Integer> getColumns() {
        return columns;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

}
