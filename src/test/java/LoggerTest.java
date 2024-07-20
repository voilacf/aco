import classes.Ant;
import classes.Playground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
    private static final String playgroundFile = "testPlayground";
    private static final String garbageFile = "testGarbage";
    private static final int ITERATIONS = 10000;
    private Playground playground;
    private Ant[] ants;

    @BeforeEach
    public void setup() {
        playground = new Playground(10, 0.75f);
        this.ants = new Ant[5];
        for (int i = 0; i < ants.length; i++) {
            ants[i] = new Ant();
        }
        // Start algorithm and wait for completion
        for (int i = 0; i < ITERATIONS; i++) {
            var futures = new ArrayList<CompletableFuture<?>>();
            for (Ant ant : ants) {
                futures.add(CompletableFuture.runAsync(() -> ant.move(playground)));
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }
        // Move ants
        for (Ant ant : ants) {
            ant.move(playground);
        }
    }

    @Test
    @Order(1)
    public void logPlaygroundToFile() throws IOException {
        Logger.logPlaygroundToFile(playground.getMatrix(), playgroundFile);
        var file = new File(playgroundFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            var lines = br.lines().toArray(String[]::new);
            var matrix = new Integer[playground.getMatrix().length][playground.getMatrix().length];
            for (int i = 0; i < playground.getMatrix().length; i++) {
                var columns = lines[i].replace(" |", "").split(" ");
                for (int j = 0; j < playground.getMatrix().length; j++) {
                    matrix[i][j] = Integer.parseInt(columns[j]);
                }
            }
            assertEquals(playground.getMatrix().length, matrix.length);
            for (int i = 0; i < playground.getMatrix().length; i++) {
                for (int j = 0; j < playground.getMatrix().length; j++) {
                    assertEquals(playground.getMatrix()[i][j].getGarbage().size(), matrix[i][j]);
                }
            }
        }
    }

    @Test
    @Order(2)
    public void logGarbageMovementToFile() throws IOException {
        Logger.logGarbageMovementToFile(playground.getMatrix(), ants, garbageFile);
        var file = new File(garbageFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 1);

        HashMap<Integer, Integer> items = new HashMap<>();
        for (int i = 0; i < playground.getMatrix().length; i++) {
            for (int j = 0; j < playground.getMatrix().length; j++) {
                playground.getMatrix()[i][j].getGarbage().forEach(e -> items.put(e.getId(), e.getMoveCount()));
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(line -> {
                var columns = line.split(":");
                var id = Integer.parseInt(columns[0]);
                var value = Integer.parseInt(columns[1]);
                if (items.get(id) != null) {
                    assertEquals(items.get(id), value);
                }
            });
        }
    }
}
