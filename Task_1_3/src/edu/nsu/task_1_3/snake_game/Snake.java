package edu.nsu.task_1_3.snake_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;

public final class Snake {
    private final List<SnakeCell> snake = new ArrayList<>();
    private static final Color SNAKE_COLOR = Color.rgb(191, 227, 78);
    private int size = 0;

    public Snake(final int x, final int y) {
        addCell(x, y);
        addCell(x, y);
        addCell(x, y);
    }

    void addCell(final int x, final int y) {
        snake.add(new SnakeCell(x, y, size++));
    }

    void move(final Direction direction) {
        for (int i = size - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
            snake.get(i).setDirection(snake.get(i - 1).getDirection());
        }

        switch (direction) {
            case up -> {
                snake.get(0).setY(snake.get(0).getY() - 1);
                snake.get(0).setDirection(CellDirection.up);

                if (snake.get(1).getDirection() == CellDirection.right) {
                    snake.get(1).setDirection(CellDirection.right_up);
                } else if (snake.get(1).getDirection() == CellDirection.left) {
                    snake.get(1).setDirection(CellDirection.left_up);
                }
            }
            case down -> {
                snake.get(0).setY(snake.get(0).getY() + 1);
                snake.get(0).setDirection(CellDirection.down);

                if (snake.get(1).getDirection() == CellDirection.right) {
                    snake.get(1).setDirection(CellDirection.right_down);
                } else if (snake.get(1).getDirection() == CellDirection.left) {
                    snake.get(1).setDirection(CellDirection.left_down);
                }
            }
            case left -> {
                snake.get(0).setX(snake.get(0).getX() - 1);
                snake.get(0).setDirection(CellDirection.left);

                if (snake.get(1).getDirection() == CellDirection.up) {
                    snake.get(1).setDirection(CellDirection.up_left);
                } else if (snake.get(1).getDirection() == CellDirection.down) {
                    snake.get(1).setDirection(CellDirection.down_left);
                }
            }
            case right -> {
                snake.get(0).setX(snake.get(0).getX() + 1);
                snake.get(0).setDirection(CellDirection.right);

                if (snake.get(1).getDirection() == CellDirection.up) {
                    snake.get(1).setDirection(CellDirection.up_right);
                } else if (snake.get(1).getDirection() == CellDirection.down) {
                    snake.get(1).setDirection(CellDirection.down_right);
                }
            }

            default -> { }
        }
    }

    void checkForBoundaryIntersection(final int height, final int width) {
        if (snake.get(0).getY() < 0) {
            snake.get(0).setY(height - 1);
        }
        if (snake.get(0).getY() >= height) {
            snake.get(0).setY(0);
        }
        if (snake.get(0).getX() < 0) {
            snake.get(0).setX(width - 1);
        }
        if (snake.get(0).getX() >= width) {
            snake.get(0).setX(0);
        }
    }

    boolean checkForBarriersIntersection(final List<Barrier> barriers) {
        for (Barrier barrier : barriers) {
            if (barrier.getX() == snake.get(0).getX()
                    && barrier.getY() == snake.get(0).getY()) {
                return true;
            }
        }
        return false;
    }

    boolean checkForFoodIntersection(final int x, final int y) {
        for (SnakeCell cell : snake) {
            if (x == cell.getX() && y == cell.getY()) {
                return true;
            }
        }
        return false;
    }

    void drawSnake(final GraphicsContext gc, final int cellSize) {
        for (SnakeCell cell : snake) {
            gc.setFill(Color.LIGHTCORAL);

            if (cell.getPosition() == 0) {
                gc.fillOval(
                        cell.getX() * cellSize + 3, cell.getY() * cellSize + 3,
                        cellSize - 6, cellSize - 6
                );
                switch (cell.getDirection()) {
                    case up -> gc.fillRect(
                            cell.getX() * cellSize + 5, (cell.getY() + 1) * cellSize - 15,
                            cellSize - 10,  15
                    );
                    case down -> gc.fillRect(
                            cell.getX() * cellSize + 5, cell.getY() * cellSize,
                            cellSize - 10,  15
                    );
                    case left -> gc.fillRect(
                            (cell.getX() + 1) * cellSize - 15, cell.getY() * cellSize + 5,
                            15,  cellSize - 10
                    );
                    case right -> gc.fillRect(
                            cell.getX() * cellSize, cell.getY() * cellSize + 5,
                            15,  cellSize - 10
                    );
                    default -> { }
                }
            } else if (cell.getPosition() == size - 1) {
                switch (cell.getDirection()) {
                    case up, right_up, left_up ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 5, (cell.getY() - 1)  * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    -180, 180, ArcType.ROUND
                            );

                    case down, right_down, left_down ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 5, cell.getY() * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    0, 180, ArcType.ROUND
                            );

                    case left, down_left, up_left ->
                            gc.fillArc(
                                    (cell.getX() - 1) * cellSize + 10, cell.getY() * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    -90, 180, ArcType.ROUND
                            );

                    case right, down_right, up_right ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 10, cell.getY() * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    90, 180, ArcType.ROUND
                            );

                    default -> { }
                }
            } else {
                switch (cell.getDirection()) {
                    case up, down -> gc.fillRect(
                            cell.getX() * cellSize + 5, cell.getY() * cellSize,
                            cellSize - 10, cellSize
                    );

                    case left, right -> gc.fillRect(
                            cell.getX() * cellSize, cell.getY() * cellSize + 5,
                            cellSize, cellSize - 10
                    );

                    case left_up, down_right -> {
                        gc.fillArc(
                                cell.getX() * cellSize + 5, cell.getY()  * cellSize - (cellSize - 20),
                                2 * cellSize - 20,  2 * cellSize - 25, -90, -90, ArcType.ROUND
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 5, cell.getY() * cellSize,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                (cell.getX() + 1) * cellSize - 5, cell.getY() * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                (cell.getX() + 1) * cellSize - 6, cell.getY() * cellSize + 1,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                (cell.getX() + 1) * cellSize - 5, cell.getY()  * cellSize - 3,
                                8,  8,
                                -90, -90, ArcType.ROUND
                        );
                    }

                    case left_down, up_right -> {
                        gc.fillArc(
                                cell.getX() * cellSize + 5, cell.getY()  * cellSize + (cellSize - 30),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                90, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 5, (cell.getY() + 1) * cellSize - 8,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                (cell.getX() + 1) * cellSize - 5, cell.getY() * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                (cell.getX() + 1) * cellSize - 6, (cell.getY() + 1) * cellSize - 6,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                (cell.getX() + 1) * cellSize - 5, (cell.getY() + 1)  * cellSize - 5,
                                8,  8,
                                90, 90, ArcType.ROUND
                        );
                    }

                    case right_up, down_left -> {
                        gc.fillArc(
                                cell.getX() * cellSize - 20, cell.getY() * cellSize - (cellSize - 20),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                -90, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 5, cell.getY() * cellSize,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                cell.getX() * cellSize, cell.getY() * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 1, cell.getY() * cellSize + 1,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                cell.getX() * cellSize - 3, cell.getY()  * cellSize - 3,
                                8,  8,
                                -90, 90, ArcType.ROUND
                        );
                    }

                    case right_down, up_left -> {
                        gc.fillArc(
                                cell.getX() * cellSize - 20, cell.getY()  * cellSize + (cellSize - 30),
                                2 * cellSize - 20,  2 * cellSize - 25,
                                0, 90, ArcType.ROUND
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 5, (cell.getY() + 1) * cellSize - 8,
                                cellSize - 10, 8
                        );
                        gc.fillRect(
                                cell.getX() * cellSize, cell.getY() * cellSize + 5,
                                5, cellSize - 10
                        );
                        gc.fillRect(
                                cell.getX() * cellSize + 1, (cell.getY() + 1) * cellSize - 6,
                                5, 5
                        );

                        gc.setFill(SNAKE_COLOR);
                        gc.fillArc(
                                cell.getX() * cellSize - 3, (cell.getY() + 1) * cellSize - 5,
                                8,  8,
                                0, 90, ArcType.ROUND
                        );
                    }

                    default -> { }
                }
            }
        }
    }

    boolean checkForSnakeIntersection() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                return true;
            }
        }
        return false;
    }
}
