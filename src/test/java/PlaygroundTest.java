import classes.Ant;
import classes.Field;
import classes.Garbage;
import classes.Playground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlaygroundTest {
    private final int antCount = 2;
    private Playground playground;
    private Ant[] ants;

    @BeforeEach
    public void setup() {
        this.ants = new Ant[antCount];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new Ant();
        }
    }

    @Test
    @Order(1)
    public void initializePlaygroundCorrectly() {
        this.playground = new Playground(10, 1);
        int playgroundLength = this.playground.getMatrix().length;
        int garbageAmount = 1;
        Field[][] matrix = this.playground.getMatrix();
        for (int i = 0; i < playgroundLength; i++) {
            // check playground size
            assertEquals(10, playgroundLength);
            for (int j = 0; j < playgroundLength; j++) {
                garbageAmount += matrix[i][j].getGarbage().size() > 0 ? 1 : 0;
            }
        }
        // check correct garbage amount
        assertEquals(100, garbageAmount);
    }

    @Test
    @Order(2)
    public void initializeGarbageOnPlaygroundCorrectly() {
        // on default there should only be one piece of garbage on one spot at once
        this.playground = new Playground(10, 1);
        int playgroundLength = this.playground.getMatrix().length;
        for (int i = 0; i < playgroundLength; i++) {
            for (int j = 0; j < playgroundLength; j++) {
                assertTrue((playground.getMatrix()[i][j].getGarbage().size() > 0
                        && playground.getMatrix()[i][j].getGarbage().size() < 2)
                        || playground.getMatrix()[i][j].getGarbage().size() < 1);
            }
        }
    }

    @Test
    @Order(3)
    public void initializeAntsOnPlaygroundCorrectly() {
        // initialize right amount of ants
        Arrays.stream(ants).forEach(ant -> {
            assertEquals(0, ant.getX());
            assertEquals(0, ant.getY());
            assertFalse(ant.hasGarbage());
        });
        assertEquals(antCount, ants.length);
    }

    @Test
    @Order(4)
    public void takingGarbageWillDecreaseGarbageStackByOne() {
        this.playground = new Playground(3, 1);
        int stackSize;
        for (int i = 0; i < ants.length; i++) {
            stackSize = playground.getMatrix()[ants[i].getX()][ants[i].getY()].getGarbage().size();
            if (stackSize > 0) {
                ants[i].move(playground);
                assertEquals((stackSize - 1),
                        playground.getMatrix()[ants[i].getX()][ants[i].getY()].getGarbage().size());
            }
        }
    }

    @Test
    @Order(5)
    public void droppingGarbageWillIncreaseGarbageStackByOne() {
        this.playground = new Playground(3, 0);
        int stackSize;
        for (int i = 0; i < ants.length; i++) {
            ants[i].setGarbage(new Garbage(i));
            stackSize = playground.getMatrix()[ants[i].getX()][ants[i].getY()].getGarbage().size();
            if (stackSize == 0) {
                ants[i].move(playground);
                assertEquals((stackSize + 1),
                        playground.getMatrix()[ants[i].getX()][ants[i].getY()].getGarbage().size());
            }
        }
    }
}
