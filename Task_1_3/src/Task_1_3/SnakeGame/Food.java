package Task_1_3.SnakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    int x;
    int y;
    int color;

    public void newFood(int width, int height) {
        Random rand = new Random();
        x = rand.nextInt(width);
        y = rand.nextInt(height);
        color = rand.nextInt(5);
    }

    public void drawFood(GraphicsContext gc, int cellSize) {
        Color cc = switch (color) {
            case 0 -> Color.GREEN;
            case 1 -> Color.BLUE;
            case 2 -> Color.YELLOW;
            case 3 -> Color.RED;
            case 4 -> Color.ORANGE;
            default -> Color.WHITE;
        };
        gc.setFill(cc);
        gc.fillOval(x * cellSize + 5, y * cellSize + 5, cellSize - 10, cellSize - 10);
    }
}
