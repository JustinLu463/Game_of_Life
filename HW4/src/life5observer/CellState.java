package life5observer;

public interface CellState {
    boolean isAlive();

    // the following transitions the cell to alive
    void live(Cell cell);

    // the following transitions the cell to dead
    void die(Cell cell);
}
