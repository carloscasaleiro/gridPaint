import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;
import java.util.ArrayList;

/**
 * Draw and interact with cells on a grid using the Simple Graphics library
 * Handles user input through the keyboard and provides methods for manipulating the grid and saving/loading cell data
 */
public class Cursor implements KeyboardHandler {

    private final String filename = "GridPaint.txt";
    private final Rectangle cursor;
    private final Grid grid;
    ArrayList<Rectangle> cells = new ArrayList<>();

    public Cursor(Grid grid) {
        this.grid = grid;
        this.cursor = new Rectangle(Grid.columnToX(0), Grid.columnToX(0), Grid.CELLSIZE, Grid.CELLSIZE);
    }

    public void drawCursor() {
        cursor.setColor(Color.WHITE);
        cursor.draw();
        cursor.fill();
        keyboardInit();
    }

    public void fillCell() {

        // Check if a cell already exists at the cursor's position
        boolean cellExists = false;
        for (Rectangle existingCell : cells) {
            if (cursor.getX() == existingCell.getX() && cursor.getY() == existingCell.getY()) {
                cellExists = true;
                break;
            }
        }

        if (!cellExists) {
            Rectangle cell = new Rectangle(cursor.getX(), cursor.getY(), Grid.CELLSIZE, Grid.CELLSIZE);
            cell.setColor(Color.GREEN);
            cell.draw();
            cell.fill();

            cells.add(cell);
        }
    }

    public void deleteCell() {

        for (int i = 0; i < cells.size(); i++) {

            if ((cursor.getX() == cells.get(i).getX()) && (cursor.getY() == cells.get(i).getY())) {

                cells.get(i).delete();
                cells.remove(i);
            }
        }
    }

    private void clearAll() {

        for (Rectangle cell : cells) {

            cell.delete();
        }

        cells.clear();
    }

    public void writeToFile() throws IOException {

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename))) {
            for (Rectangle cell : cells) {
                bWriter.write(cell.toString());
                bWriter.newLine();
            }

            bWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }

    private void loadFromFile() throws IOException {

        clearAll();

        try (BufferedReader bReader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = bReader.readLine()) != null) {
                String[] values = line.split(",");

                int x = Integer.parseInt(values[0].substring(values[0].indexOf("=") + 1));
                int y = Integer.parseInt(values[1].substring(values[1].indexOf("=") + 1));

                Rectangle rectangle = new Rectangle(x, y, Grid.CELLSIZE, Grid.CELLSIZE);
                rectangle.setColor(Color.BLACK);
                rectangle.draw();
                rectangle.fill();

                cells.add(rectangle);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + e.getMessage(), e);
        }

        int random = (int) (Math.random() * colors.length);

        for (Rectangle cell : cells) {

            cell.setColor(colors[random]);
        }
    }

    private static final Color[] colors = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.MAGENTA,
            Color.YELLOW,
            Color.ORANGE,
            Color.CYAN,
            Color.PINK,
            Color.WHITE
    };

    public void keyboardInit() {

        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent rightPress = new KeyboardEvent();
        rightPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        rightPress.setKey(KeyboardEvent.KEY_RIGHT);

        KeyboardEvent leftPress = new KeyboardEvent();
        leftPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        leftPress.setKey(KeyboardEvent.KEY_LEFT);

        KeyboardEvent upPress = new KeyboardEvent();
        upPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        upPress.setKey(KeyboardEvent.KEY_UP);

        KeyboardEvent downPress = new KeyboardEvent();
        downPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        downPress.setKey(KeyboardEvent.KEY_DOWN);

        KeyboardEvent spacePress = new KeyboardEvent();
        spacePress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        spacePress.setKey(KeyboardEvent.KEY_SPACE);

        KeyboardEvent deletePress = new KeyboardEvent();
        deletePress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        deletePress.setKey(KeyboardEvent.KEY_D);

        KeyboardEvent writePress = new KeyboardEvent();
        writePress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        writePress.setKey(KeyboardEvent.KEY_W);

        KeyboardEvent loadPress = new KeyboardEvent();
        loadPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        loadPress.setKey(KeyboardEvent.KEY_L);

        KeyboardEvent testPress = new KeyboardEvent();
        testPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        testPress.setKey(KeyboardEvent.KEY_T);

        KeyboardEvent clearAllPress = new KeyboardEvent();
        clearAllPress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        clearAllPress.setKey(KeyboardEvent.KEY_C);

        keyboard.addEventListener(rightPress);
        keyboard.addEventListener(leftPress);
        keyboard.addEventListener(upPress);
        keyboard.addEventListener(downPress);
        keyboard.addEventListener(spacePress);
        keyboard.addEventListener(deletePress);
        keyboard.addEventListener(writePress);
        keyboard.addEventListener(loadPress);
        keyboard.addEventListener(testPress);
        keyboard.addEventListener(clearAllPress);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_RIGHT:
                System.out.println("Right");
                if (cursor.getX() < Grid.columnToX(grid.getCols() - 1)) {
                    cursor.translate(Grid.CELLSIZE, 0);
                }
                break;

            case KeyboardEvent.KEY_LEFT:
                System.out.println("Left");
                if (cursor.getX() > Grid.PADDING) {
                    cursor.translate(-Grid.CELLSIZE, 0);
                }
                break;

            case KeyboardEvent.KEY_UP:
                System.out.println("Up");
                if (cursor.getY() > Grid.PADDING) {
                    cursor.translate(0, -Grid.CELLSIZE);
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                System.out.println("Down");
                if (cursor.getY() < Grid.rowToY(grid.getRows() - 1)) {
                    cursor.translate(0, Grid.CELLSIZE);
                }
                break;
            case KeyboardEvent.KEY_SPACE:
                System.out.println("Space");
                fillCell();
                break;
            case KeyboardEvent.KEY_D:
                System.out.println("Delete");
                deleteCell();
                break;
            case KeyboardEvent.KEY_W:
                System.out.println("Write to file");
                try {
                    writeToFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case KeyboardEvent.KEY_L:
                System.out.println("Load from file");
                clearAll();
                cells.clear();
                try {
                    loadFromFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    loadFromFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case KeyboardEvent.KEY_C:
                System.out.println("Clear All");
                clearAll();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        //I don't want to use this one right now.
    }
}
