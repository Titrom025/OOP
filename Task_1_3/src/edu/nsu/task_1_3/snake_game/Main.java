package edu.nsu.task_1_3.snake_game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Main extends Application {

    private static final Color CELL_BORDER_COLOR = Color.rgb(161, 181, 91);
    private static final Color SNAKE_COLOR = Color.rgb(191, 227, 78);

    private static final int CELL_SIZE = 35;

    private static final int ROW_COUNT = 20;
    private static final int COLUMN_COUNT = 20;

    private static final int GAME_WIDTH = ROW_COUNT * CELL_SIZE;
    private static final int GAME_HEIGHT = COLUMN_COUNT * CELL_SIZE;
    private static final int MENU_WIDTH = 200;

    private static final int CENTER_X = COLUMN_COUNT / 2;
    private static final int CENTER_Y = ROW_COUNT / 2;

    private static final int GAMEOVER_TEXT_SIZE = 50;
    private static final int GAMEOVER_TEXT_OFFSET_X = CENTER_X * CELL_SIZE - 150;
    private static final int GAMEOVER_TEXT_OFFSET_Y = CENTER_Y * CELL_SIZE;

    private static final int SCORE_TEXT_SIZE = 30;
    private static final int SCORE_TEXT_OFFSET_X = 50;
    private static final int SCORE_TEXT_OFFSET_Y = 50;

    private static final int BARRIERS_COUNT = 5;

    private static final long BASE_TIME_INTERVAL = 500_000_000;

    private static double speed = 2;
    private static final double SPEED_STEP = 0.1;

    private final Snake snake = new Snake(CENTER_X, CENTER_Y);
    private final Food food = new Food();
    private final List<Barrier> barriers = new ArrayList<>();

    private static Direction direction = Direction.left;
    private static Direction directionToSet = direction;

    private static boolean gameOver = false;


    public void start(final Stage primaryStage) {
        try {
            createFood();
            for (int i = 0; i < BARRIERS_COUNT; i++) {
                generateBarriers(COLUMN_COUNT, ROW_COUNT);
            }

            VBox root = new VBox();
            Canvas c = new Canvas(GAME_WIDTH + MENU_WIDTH, GAME_HEIGHT);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);
            new AnimationTimer() {
                private long lastTick = 0;
                public void handle(final long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);

                        return;
                    }
                    if (now - lastTick > BASE_TIME_INTERVAL / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();
            Scene scene = new Scene(root, GAME_WIDTH + MENU_WIDTH, GAME_HEIGHT);

            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W && direction != Direction.down) {
                    directionToSet = Direction.up;
                }
                if (key.getCode() == KeyCode.A && direction != Direction.right) {
                    directionToSet = Direction.left;
                }
                if (key.getCode() == KeyCode.S && direction != Direction.up) {
                    directionToSet = Direction.down;
                }
                if (key.getCode() == KeyCode.D && direction != Direction.left) {
                    directionToSet = Direction.right;
                }
            });

            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick(final GraphicsContext gc) {
        if (gameOver) {
            return;
        }

        direction = directionToSet;

        if (snake.checkForFoodIntersection(food.getX(), food.getY())) {
            createFood();
            snake.addCell(CENTER_X, CENTER_Y);
            speed += SPEED_STEP;
        }

        snake.move(direction);
        snake.checkForBoundaryIntersection(COLUMN_COUNT, ROW_COUNT);

        if (snake.checkForSnakeIntersection() || snake.checkForBarriersIntersection(barriers)) {
            gameOver = true;
            gc.setFill(Color.RED);
            gc.setFont(new Font("", GAMEOVER_TEXT_SIZE));
            gc.fillText("GAME OVER", GAMEOVER_TEXT_OFFSET_X, GAMEOVER_TEXT_OFFSET_Y);
        } else {
            drawFrame(gc);
        }
    }


    private void drawFrame(final GraphicsContext gc) {
        drawGame(gc);
        drawMenu(gc);
    }

    private void drawGame(final GraphicsContext gc) {
        gc.setFill(CELL_BORDER_COLOR);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                gc.setFill(SNAKE_COLOR);
                gc.fillRect(i * CELL_SIZE + 1, j * CELL_SIZE + 1,
                        CELL_SIZE - 2, CELL_SIZE - 2);
            }
        }

        for (Barrier barrier: barriers) {
            gc.setFill(Color.GREEN);
            gc.fillRect(barrier.getX() * CELL_SIZE, barrier.getY() * CELL_SIZE,
                    CELL_SIZE, CELL_SIZE);
        }


        food.drawFood(gc, CELL_SIZE);
        snake.drawSnake(gc, CELL_SIZE);
    }

    private void drawMenu(final GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(GAME_WIDTH, 0, MENU_WIDTH, GAME_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", SCORE_TEXT_SIZE));
        gc.fillText("Score: " + ((int) (10 * speed - 20)),
                GAME_WIDTH + SCORE_TEXT_OFFSET_X, SCORE_TEXT_OFFSET_Y);
    }

    private void createFood() {
        do {
            food.newFood(ROW_COUNT, COLUMN_COUNT);
        } while (snake.checkForFoodIntersection(food.getX(), food.getY())
                || food.checkForBarriersIntersection(barriers));
    }

    private void generateBarriers(final int columnCount, final int rowCount) {
        Random rand = new Random();
        int length = rand.nextInt(5) + 3;
        int x;
        int y;
        int xDirection;
        int yDirection;

        do {
            x = rand.nextInt(columnCount - 4) + 2;
            y = rand.nextInt(rowCount - 4) + 2;
        } while (Math.abs(x - CENTER_X) < 5 && Math.abs(y - CENTER_Y) < 5);

        do {
            xDirection = rand.nextInt(3) - 1;
            yDirection = rand.nextInt(3) - 1;
        } while (xDirection == 0 && yDirection == 0);

        for (int i = 0; i < length; i++) {
            if (x + xDirection * i >= 0 && x + xDirection * i < columnCount
                    && y + yDirection * i >= 0 && y + yDirection * i < rowCount
                    && !((y + yDirection * i == CENTER_Y)
                        && (CENTER_X -  x - xDirection * i >= 0
                        && CENTER_X -  x - xDirection * i <= 5))) {
                barriers.add(new Barrier(x + xDirection * i, y + yDirection * i));
            }
        }
    }
    public static void main(final String[] args) {
        launch(args);
    }
}
