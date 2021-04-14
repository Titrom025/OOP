package Task_1_3.SnakeGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<SnakeCell> snake = new ArrayList<>();
    private static final Color SNAKE_COLOR = Color.rgb(191, 227, 78);
    int size = 0;

    public Snake(int x, int y) {
        addCell(x, y);
        addCell(x, y);
        addCell(x, y);
    }

    void addCell(int x, int y) {
        snake.add(new SnakeCell(x, y, size++));
    }

    void move(Direction direction) {
        for (int i = size - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
            snake.get(i).direction = snake.get(i - 1).direction;
        }

        switch (direction) {
            case up -> {
                snake.get(0).y--;
                snake.get(0).direction = CellDirection.up;

                if (snake.get(1).direction == CellDirection.right) {
                    snake.get(1).direction = CellDirection.right_up;
                } else if (snake.get(1).direction == CellDirection.left) {
                    snake.get(1).direction = CellDirection.left_up;
                }
            }
            case down -> {
                snake.get(0).y++;
                snake.get(0).direction = CellDirection.down;

                if (snake.get(1).direction == CellDirection.right) {
                    snake.get(1).direction = CellDirection.right_down;
                } else if (snake.get(1).direction == CellDirection.left) {
                    snake.get(1).direction = CellDirection.left_down;
                }
            }
            case left -> {
                snake.get(0).x--;
                snake.get(0).direction = CellDirection.left;

                if (snake.get(1).direction == CellDirection.up) {
                    snake.get(1).direction = CellDirection.up_left;
                } else if (snake.get(1).direction == CellDirection.down) {
                    snake.get(1).direction = CellDirection.down_left;
                }
            }
            case right -> {
                snake.get(0).x++;
                snake.get(0).direction = CellDirection.right;

                if (snake.get(1).direction == CellDirection.up) {
                    snake.get(1).direction = CellDirection.up_right;
                } else if (snake.get(1).direction == CellDirection.down) {
                    snake.get(1).direction = CellDirection.down_right;
                }
            }
        }
    }

    boolean chechForBoundaryIntersection(int height, int width) {
        return  (snake.get(0).y < 0 || snake.get(0).y >= height || snake.get(0).x < 0 || snake.get(0).x >= width);
    }

    boolean checkForFoodIntersection(int x, int y) {
        return (x == snake.get(0).x && y == snake.get(0).y);
    }

    void drawSnake(GraphicsContext gc, int cellSize) {
        for (SnakeCell c : snake) {

            gc.setFill(Color.LIGHTCORAL);

            if (c.position == 0) {
                gc.fillOval(
                        c.x * cellSize + 3, c.y * cellSize + 3,
                        cellSize - 6, cellSize - 6
                );
                switch (c.direction) {
                    case up -> gc.fillRect(
                            c.x * cellSize + 5, (c.y + 1) * cellSize - 15,
                            cellSize - 10,  15
                    );
                    case down -> gc.fillRect(
                            c.x * cellSize + 5, c.y * cellSize,
                            cellSize - 10,  15
                    );
                    case left -> gc.fillRect(
                            (c.x + 1) * cellSize - 15, c.y * cellSize + 5,
                            15,  cellSize - 10
                    );
                    case right -> gc.fillRect(
                            c.x * cellSize, c.y * cellSize + 5,
                            15,  cellSize - 10
                    );
                }
            } else if (c.position == size - 1) {
                switch (c.direction) {
                    case up, right_up, left_up ->
                            gc.fillArc(
                                    c.x * cellSize + 5, (c.y - 1)  * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    -180, 180, ArcType.ROUND
                            );

                    case down, right_down, left_down ->
                            gc.fillArc(
                                    c.x * cellSize + 5, c.y * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    0, 180, ArcType.ROUND
                            );

                    case left, down_left, up_left ->
                            gc.fillArc(
                                    (c.x - 1) * cellSize + 10, c.y * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    -90, 180, ArcType.ROUND
                            );

                    case right, down_right, up_right ->
                            gc.fillArc(
                                    c.x * cellSize + 10, c.y * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    90, 180, ArcType.ROUND
                            );

                }
            } else {
                switch (c.direction) {
                    case up, down -> gc.fillRect(
                            c.x * cellSize + 5, c.y * cellSize,
                            cellSize - 10, cellSize
                    );

                    case left, right -> gc.fillRect(
                            c.x * cellSize, c.y * cellSize + 5,
                            cellSize, cellSize - 10
                    );

                    case left_up, down_right -> {
                        gc.fillArc(
                                c.x * cellSize + 5, c.y  * cellSize - (cellSize - 20),
                                2 * cellSize - 20,  2 * cellSize - 25, -90, -90, ArcType.ROUND
                        );
                        gc.fillRect(
                                c.x * cellSize + 5, c.y * cellSize,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                (c.x + 1) * cellSize - 5, c.y * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                (c.x + 1) * cellSize - 6, c.y * cellSize + 1,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                (c.x + 1) * cellSize - 5, c.y  * cellSize - 3,
                                8,  8,
                                -90, -90, ArcType.ROUND
                        );
                    }

                    case left_down, up_right -> {
                        gc.fillArc(
                                c.x * cellSize + 5, c.y  * cellSize + (cellSize - 30),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                90, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                c.x * cellSize + 5, (c.y + 1) * cellSize - 8,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                (c.x + 1) * cellSize - 5, c.y * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                (c.x + 1) * cellSize - 6, (c.y + 1) * cellSize - 6,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                (c.x + 1) * cellSize - 5, (c.y + 1)  * cellSize - 5,
                                8,  8,
                                90, 90, ArcType.ROUND
                        );
                    }

                    case right_up, down_left -> {
                        gc.fillArc(
                                c.x * cellSize - 20, c.y * cellSize - (cellSize - 20),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                -90, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                c.x * cellSize + 5, c.y * cellSize,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                c.x * cellSize, c.y * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                c.x * cellSize + 1, c.y * cellSize + 1,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                c.x * cellSize - 3, c.y  * cellSize - 3,
                                8,  8,
                                -90, 90, ArcType.ROUND
                        );
                    }

                    case right_down, up_left -> {
                        gc.fillArc(
                                c.x * cellSize - 20, c.y  * cellSize + (cellSize - 30),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                0, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                c.x * cellSize + 5, (c.y + 1) * cellSize - 8,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                c.x * cellSize, c.y * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                c.x * cellSize + 1, (c.y + 1) * cellSize - 6,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                c.x * cellSize - 3, (c.y + 1) * cellSize - 5,
                                8,  8,
                                0, 90, ArcType.ROUND
                        );
                    }
                }
            }
        }
    }

    boolean checkForSnakeIntersection() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                return true;
            }
        }
        return false;
    }
}
