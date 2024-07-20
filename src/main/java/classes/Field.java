package classes;

import java.util.Stack;

public class Field {
    private final Stack<Garbage> garbage;
    private boolean hasAnt = false;

    public Field(Stack<Garbage> stack) {
        this.garbage = stack;
    }

    public boolean isHasAnt() {
        return hasAnt;
    }

    public void setHasAnt(boolean hasAnt) {
        this.hasAnt = hasAnt;
    }

    public Stack<Garbage> getGarbage() {
        return garbage;
    }

    public void addGarbage(Garbage garbage) {
        garbage.increaseMoveCount();
        this.garbage.push(garbage);
    }

    public void initGarbage(Garbage garbage) {
        this.garbage.push(garbage);
    }
}
