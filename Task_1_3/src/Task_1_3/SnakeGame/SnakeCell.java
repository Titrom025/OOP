package Task_1_3.SnakeGame;

public class SnakeCell {
    int x = 0;
    int y = 0;
    int position;
    CellDirection direction = CellDirection.left;

    public SnakeCell(int x, int y, int position) {
        this.x = x;
        this.y = y;
        this.position = position;
    }
}