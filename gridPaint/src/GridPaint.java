import java.net.URL;
import java.util.Objects;

/**
 * Sets up and initializes both the grid and cursor
 */
public class GridPaint {

    public static void main(String[] args) {

        Grid grid = new Grid(20, 20);
        grid.drawGrid();

        Cursor cursor = new Cursor(grid);
        cursor.drawCursor();
    }
}