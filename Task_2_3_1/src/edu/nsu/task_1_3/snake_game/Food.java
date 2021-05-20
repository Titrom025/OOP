package edu.nsu.task_1_3.snake_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public final class Food {
    private int x;
    private int y;
    private int color;

    public void newFood(final int width, final int height) {
        Random rand = new Random();
        x = rand.nextInt(width);
        y = rand.nextInt(height);
        color = rand.nextInt(5);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void drawFood(final GraphicsContext gc, final int cellSize) {
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

    boolean checkForBarriersIntersection(final List<Barrier> barriers) {
        return barriers.stream().anyMatch(barrier -> barrier.getX() == x && barrier.getY() == y);
    }
}
