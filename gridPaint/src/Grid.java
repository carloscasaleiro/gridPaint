import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

/**
 * Responsible for drawing a grid with rows and columns using the Simple Graphics library
 */
public class Grid {

    public static final int PADDING = 10;
    public static final int CELLSIZE = 30;
    private final int cols;
    private final int rows;

    //Simple graphics grid constructor with a certain number of rows and columns
    public Grid(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
    }

    //Initializes the field simple graphics rectangle and draws the grid lines
    public void drawGrid() {
        Rectangle rectangle = new Rectangle(PADDING, PADDING, cols*CELLSIZE, rows*CELLSIZE);
        rectangle.draw();
        rectangle.setColor(Color.BLACK);
        rectangle.fill();

        for (int i = 0; i <= rows; i++) {
            Line lineX = new Line(PADDING, PADDING+CELLSIZE*i, PADDING+cols*CELLSIZE,PADDING+CELLSIZE*i);
            lineX.setColor(Color.WHITE);
            lineX.draw();
        }

        for (int i = 0; i <= cols; i++) {
            Line lineY = new Line(PADDING+CELLSIZE*i, PADDING, PADDING+CELLSIZE*i, PADDING+rows*CELLSIZE);
            lineY.setColor(Color.WHITE);
            lineY.draw();
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    //Auxiliary method to compute the x value that corresponds to a specific column
    public static int columnToX(int column) {
        return PADDING + (CELLSIZE * column);
    }

    //Auxiliary method to compute the y value that corresponds to a specific row
    public static int rowToY(int row) {
        return PADDING + (CELLSIZE * row);
    }
}
