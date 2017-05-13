
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by kassem on 13/5/17.
 */
public class GridStructure  implements Serializable{
    private ArrayList<Integer> rows = new ArrayList<>();
    private ArrayList<Integer> columns = new ArrayList<>();
    private int size;

    public GridStructure(){
        size = 11;
        rows.add(0);
        rows.add(5);
        rows.add(10);
        columns.add(0);
        columns.add(5);
        columns.add(10);
    }

    public GridStructure(int s) throws Exception {
        if (s<2)
            throw new Exception("Grid Structure size can't be less than 2");
        size = s;

        /*
         * Border rows and columns
         */
        rows.add(0);
        rows.add(size-1);
        columns.add(0);
        columns.add(size-1);
    }

    public ArrayList<Integer> getRows() {
        return rows;
    }

    public void addRow(int row) throws Exception {
        if (row>size)
            throw new Exception("Row can't exceed the grid size");
        if (rows.contains(row)) return;
        rows.add(row);
    }

    public void addColumn(int column) throws Exception {
        if (column>size)
            throw new Exception("Column can't exceed the grid size");

        if (columns.contains(column)) return;

        columns.add(column);
    }
    public ArrayList<Integer>  getColumns() {
        return columns;
    }

    public int getSize() {
        return size;
    }

}
