package life5observer;

public class AliveState implements CellState {
	// the following instance holds the single instance of AliveState
    private static AliveState instance = new AliveState();

    private AliveState() {
    }

    // the following returns the single instance of AliveState
    public static AliveState create() {
        return instance;
    }

    // the following returns true since this state represents an alive cell
    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void live(Cell cell) {
    }

    // the following changes the cell's state to dead
    @Override
    public void die(Cell cell) {
        cell.setState(DeadState.create());
    }
}
