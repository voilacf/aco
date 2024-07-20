package classes;

import java.util.Objects;
import java.util.Random;

public class Ant {
    int x, y = 0;
    private Garbage garbage;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void move(Playground playground) {
        synchronized (playground.getMatrix()) {
            var spots = playground.getMatrix();
            int xPos, yPos;
            spots[x][y].setHasAnt(false);

            // get a new free random spot for ant
            do {
                xPos = new Random().nextInt(spots.length);
                yPos = new Random().nextInt(spots.length);
            } while (spots[xPos][yPos].isHasAnt());

            this.x = xPos;
            this.y = yPos;

            spots[xPos][yPos].setHasAnt(true);

            if (hasGarbage()) {
                // drop garbage on spot, if ant has garbage
                spots[xPos][yPos].addGarbage(this.garbage);
                setGarbage(null);
            } else if (!spots[xPos][yPos].getGarbage().empty()) {
                // take garbage from spot, if ant has no garbage
                this.garbage = spots[xPos][yPos].getGarbage().pop();
            }
        }
    }

    public Garbage getGarbage() {
        return garbage;
    }

    public void setGarbage(Garbage garbage) {
        this.garbage = garbage;
    }

    public boolean hasGarbage() {
        return Objects.nonNull(this.garbage);
    }
}
