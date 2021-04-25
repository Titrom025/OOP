package edu.nsu.task_1_3.snake_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public final class Snake {
    private static final int OPPONENT_ATTEMPT_LIMIT = 20;
    private final int CHANCE_TO_CHANGE_DIR;
    private final List<SnakeCell> snake = new ArrayList<>();
    private final Color SNAKE_COLOR;
    private static Color FIELD_COLOR = Color.rgb(191, 227, 78);
    private int size = 0;
    private Direction direction = Direction.LEFT;

    public Snake(final int x, final int y, Color color, int chance) {
        SNAKE_COLOR = color;
        CHANCE_TO_CHANGE_DIR = chance;
        addCell(x, y);
        addCell(x, y);
        addCell(x, y);
    }

    int getHeadX() {
        return snake.get(0).getX();
    }

    int getHeadY() {
        return snake.get(0).getY();
    }

    int getSize() {
        return size;
    }

    List<SnakeCell> getCells() {
        return snake;
    }
    void addCell(final int x, final int y) {
        snake.add(new SnakeCell(x, y, size++));
    }

    void move(final Direction direction) {
        if (size == 0) {
            return;
        }
        for (int i = size - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
            snake.get(i).setDirection(snake.get(i - 1).getDirection());
        }

        switch (direction) {
            case UP -> {
                snake.get(0).setY(snake.get(0).getY() - 1);
                snake.get(0).setDirection(CellDirection.UP);

                if (size > 1) {
                    if (snake.get(1).getDirection() == CellDirection.RIGHT) {
                        snake.get(1).setDirection(CellDirection.RIGHT_UP);
                    } else if (snake.get(1).getDirection() == CellDirection.LEFT) {
                        snake.get(1).setDirection(CellDirection.LEFT_UP);
                    }
                }
            }
            case DOWN -> {
                snake.get(0).setY(snake.get(0).getY() + 1);
                snake.get(0).setDirection(CellDirection.DOWN);

                if (size > 1) {
                    if (snake.get(1).getDirection() == CellDirection.RIGHT) {
                        snake.get(1).setDirection(CellDirection.RIGHT_DOWN);
                    } else if (snake.get(1).getDirection() == CellDirection.LEFT) {
                        snake.get(1).setDirection(CellDirection.LEFT_DOWN);
                    }
                }
            }
            case LEFT -> {
                snake.get(0).setX(snake.get(0).getX() - 1);
                snake.get(0).setDirection(CellDirection.LEFT);

                if (size > 1) {
                    if (snake.get(1).getDirection() == CellDirection.UP) {
                        snake.get(1).setDirection(CellDirection.UP_LEFT);
                    } else if (snake.get(1).getDirection() == CellDirection.DOWN) {
                        snake.get(1).setDirection(CellDirection.DOWN_LEFT);
                    }
                }
            }
            case RIGHT -> {
                snake.get(0).setX(snake.get(0).getX() + 1);
                snake.get(0).setDirection(CellDirection.RIGHT);

                if (size > 1) {
                    if (snake.get(1).getDirection() == CellDirection.UP) {
                        snake.get(1).setDirection(CellDirection.UP_RIGHT);
                    } else if (snake.get(1).getDirection() == CellDirection.DOWN) {
                        snake.get(1).setDirection(CellDirection.DOWN_RIGHT);
                    }
                }
            }

            default -> { }
        }
    }

    void checkForBoundaryIntersection(final int height, final int width) {
        if (size == 0) {
            return;
        }
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
        return barriers.stream().anyMatch(barrier -> barrier.getX() == snake.get(0).getX() && barrier.getY() == snake.get(0).getY());
    }

    boolean checkForBarriersIntersectionByXY(final List<Barrier> barriers, int x, int y) {
        return barriers.stream().anyMatch(barrier -> barrier.getX() == x && barrier.getY() == y);
    }

    boolean checkForFoodIntersection(final int x, final int y) {
        return snake.stream().anyMatch(cell -> cell.getX() == x && cell.getY() == y);
    }

    void deleteTail(final int fromIndex) {
        if (size > 0) {
            snake.subList(fromIndex, size).clear();
            size = fromIndex;
        }

    }

    int checkForAnotherSnake(List<SnakeCell> opponent) {
        Optional<SnakeCell> intersection = opponent.stream().filter(op -> op.getX() == getHeadX() && op.getY() == getHeadY()).findFirst();
        return intersection.map(opponent::indexOf).orElse(-1);
    }


    void drawSnake(final GraphicsContext gc, final int cellSize) {
        for (SnakeCell cell : snake) {
            gc.setFill(SNAKE_COLOR);

            if (cell.getPosition() == 0) {
                gc.fillOval(
                        cell.getX() * cellSize + 3, cell.getY() * cellSize + 3,
                        cellSize - 6, cellSize - 6
                );
                switch (cell.getDirection()) {
                    case UP -> gc.fillRect(
                            cell.getX() * cellSize + 5, (cell.getY() + 1) * cellSize - 15,
                            cellSize - 10,  15
                    );
                    case DOWN -> gc.fillRect(
                            cell.getX() * cellSize + 5, cell.getY() * cellSize,
                            cellSize - 10,  15
                    );
                    case LEFT -> gc.fillRect(
                            (cell.getX() + 1) * cellSize - 15, cell.getY() * cellSize + 5,
                            15,  cellSize - 10
                    );
                    case RIGHT -> gc.fillRect(
                            cell.getX() * cellSize, cell.getY() * cellSize + 5,
                            15,  cellSize - 10
                    );
                    default -> { }
                }
            } else if (cell.getPosition() == size - 1) {
                switch (cell.getDirection()) {
                    case UP, RIGHT_UP, LEFT_UP ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 5, (cell.getY() - 1)  * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    -180, 180, ArcType.ROUND
                            );

                    case DOWN, RIGHT_DOWN, LEFT_DOWN ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 5, cell.getY() * cellSize + 10,
                                    cellSize - 10,  2 * cellSize - 20,
                                    0, 180, ArcType.ROUND
                            );

                    case LEFT, DOWN_LEFT, UP_LEFT ->
                            gc.fillArc(
                                    (cell.getX() - 1) * cellSize + 10, cell.getY() * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    -90, 180, ArcType.ROUND
                            );

                    case RIGHT, DOWN_RIGHT, UP_RIGHT ->
                            gc.fillArc(
                                    cell.getX() * cellSize + 10, cell.getY() * cellSize + 5,
                                    2 * cellSize - 20,  cellSize - 10,
                                    90, 180, ArcType.ROUND
                            );

                    default -> { }
                }
            } else {
                switch (cell.getDirection()) {
                    case UP, DOWN -> gc.fillRect(
                            cell.getX() * cellSize + 5, cell.getY() * cellSize,
                            cellSize - 10, cellSize
                    );

                    case LEFT, RIGHT -> gc.fillRect(
                            cell.getX() * cellSize, cell.getY() * cellSize + 5,
                            cellSize, cellSize - 10
                    );

                    case LEFT_UP, DOWN_RIGHT -> {
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

                        gc.setFill(FIELD_COLOR);
                        gc.fillArc(
                                (cell.getX() + 1) * cellSize - 5, cell.getY()  * cellSize - 3,
                                8,  8,
                                -90, -90, ArcType.ROUND
                        );
                    }

                    case LEFT_DOWN, UP_RIGHT -> {
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

                        gc.setFill(FIELD_COLOR);
                        gc.fillArc(
                                (cell.getX() + 1) * cellSize - 5, (cell.getY() + 1)  * cellSize - 5,
                                8,  8,
                                90, 90, ArcType.ROUND
                        );
                    }

                    case RIGHT_UP, DOWN_LEFT -> {
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

                        gc.setFill(FIELD_COLOR);
                        gc.fillArc(
                                cell.getX() * cellSize - 3, cell.getY()  * cellSize - 3,
                                8,  8,
                                -90, 90, ArcType.ROUND
                        );
                    }

                    case RIGHT_DOWN, UP_LEFT -> {
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

                        gc.setFill(FIELD_COLOR);
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

    void moveOpponents(final int COLUMN_COUNT, final int ROW_COUNT, final List<Barrier> barriers) {

        Random rand = new Random();
        checkForBoundaryIntersection(COLUMN_COUNT, ROW_COUNT);
        boolean possibleToMove = false;

        Direction newDirection = direction;
        int attemps = 0;
        while (!possibleToMove) {
            if (attemps > OPPONENT_ATTEMPT_LIMIT) {
                size = 0;
                break;
            }
            if (rand.nextInt(100) >= CHANCE_TO_CHANGE_DIR) {
                switch (rand.nextInt(4)) {
                    case 0 -> {
                        if (direction != Direction.RIGHT) {
                            newDirection = Direction.LEFT;
                        }
                    }
                    case 1 -> {
                        if (direction != Direction.DOWN) {
                            newDirection = Direction.UP;
                        }
                    }
                    case 2 -> {
                        if (direction != Direction.LEFT) {
                            newDirection = Direction.RIGHT;
                        }
                    }
                    case 3 -> {
                        if (direction != Direction.UP) {
                            newDirection = Direction.DOWN;
                        }
                    }
                    default -> {
                    }
                }
                attemps++;
            }

            switch (newDirection) {
                case LEFT -> possibleToMove = !checkForBarriersIntersectionByXY(
                        barriers, getHeadX() - 1, getHeadY());

                case RIGHT -> possibleToMove = !checkForBarriersIntersectionByXY(
                        barriers, getHeadX() + 1, getHeadY());

                case UP -> possibleToMove = !checkForBarriersIntersectionByXY(
                        barriers, getHeadX(), getHeadY() - 1);

                case DOWN -> possibleToMove = !checkForBarriersIntersectionByXY(
                        barriers, getHeadX(), getHeadY() + 1);
            }
        }

        if (attemps < OPPONENT_ATTEMPT_LIMIT) {
            direction = newDirection;
            move(direction);
        }
    }

    boolean checkForSnakeIntersection() {
        return snake.stream().anyMatch(cell -> snake.indexOf(cell) > 0
                && cell.getX() == snake.get(0).getX()
                && cell.getY() == snake.get(0).getY());
    }
}
