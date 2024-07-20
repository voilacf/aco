package classes;

public class Garbage {
    private final int id;
    private int moveCount = 0;

    public Garbage(int id) {
        this.id = id;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        this.moveCount++;
    }

    public int getId() {
        return id;
    }
}
