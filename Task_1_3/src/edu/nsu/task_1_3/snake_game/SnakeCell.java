package edu.nsu.task_1_3.snake_game;

public final class SnakeCell {
    private int x;
    private int y;
    private final int position;
    private CellDirection direction = CellDirection.LEFT;

    public SnakeCell(final int x, final int y, final int position) {
        this.x = x;
        this.y = y;
        this.position = position;
    }

    int getX() {
        return x;
    }

    void setX(final int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(final int y) {
        this.y = y;
    }

    int getPosition() {
        return position;
    }

    CellDirection getDirection() {
        return direction;
    }

    void setDirection(final CellDirection direction) {
        this.direction = direction;
    }
}
