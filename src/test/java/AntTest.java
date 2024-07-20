import classes.Ant;
import classes.Garbage;
import classes.Playground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AntTest {
    int x, y;
    private Playground playground;
    private Ant[] ants;

    @BeforeEach
    public void setup() {
        this.ants = new Ant[1];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new Ant();
        }
    }

    @Test
    @Order(1)
    public void initialisedCorrectly() {
        for (Ant ant : ants) {
            assertFalse(ant.hasGarbage());
        }
    }

    @Test
    @Order(2)
    public void takeGarbage() {
        playground = new Playground(10, 1);
        int length;
        for (Ant ant : ants) {
            // ensuring ants do not have garbage
            ant.setGarbage(null);
            assertFalse(ant.hasGarbage());

            // ensuring there's garbage on the spot
            do {
                x = playground.generateNewNumber();
                y = playground.generateNewNumber();
            } while (playground.getMatrix()[x][y].getGarbage().empty());

            length = playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size();
            assertTrue(length > 0);

            ant.move(playground);

            assertTrue(ant.hasGarbage());
            assertEquals(length - 1, playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size());
        }
    }

    @Test
    @Order(3)
    public void dropGarbage() {
        this.playground = new Playground(10, 0);
        int stackSize;
        for (Ant ant : ants) {
            assertFalse(ant.hasGarbage());
            x = playground.generateNewNumber();
            y = playground.generateNewNumber();
            stackSize = playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size();

            ant.move(playground);

            assertFalse(ant.hasGarbage());
            assertEquals(stackSize, playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size());
        }
    }

    @Test
    @Order(4)
    public void doNothingWithoutGarbage() {
        this.playground = new Playground(10, 0);
        int stackSize;
        for (int i = 0; i < ants.length; i++) {
            ants[i].setGarbage(new Garbage(i));
        }
        for (Ant ant : ants) {
            assertTrue(ant.hasGarbage());
            x = playground.generateNewNumber();
            y = playground.generateNewNumber();
            stackSize = playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size();

            ant.move(playground);

            assertFalse(ant.hasGarbage());
            assertEquals((stackSize + 1), playground.getMatrix()[ant.getX()][ant.getY()].getGarbage().size());
        }
    }

    @Test
    @Order(5)
    public void antMovesCorrectly() {
        this.playground = new Playground(1, 0);
        for (Ant ant : ants) {
            assertFalse(playground.getMatrix()[ant.getX()][ant.getY()].isHasAnt());
            ant.move(playground);
            assertTrue(playground.getMatrix()[ant.getX()][ant.getY()].isHasAnt());
        }
    }
}
