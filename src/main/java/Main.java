import classes.Ant;
import classes.Playground;
import utils.Configuration;
import utils.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    // default settings
    private final static int ITERATIONS = 10000;
    private final static String configFile = "config.json";
    private static final String PLAYGROUND_DEFAULT_FILE = "playground-default.txt";
    private static final String PLAYGROUND_FILE = "playground.txt";
    private static final String GARBAGE_MOVEMENT_FILE = "garbageMovement.txt";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Configuration configuration = Configuration.load(configFile);
        System.out.println("Using config: " + configuration);
        Optional<String> validationResult = configuration.validate();
        if (validationResult.isPresent()) {
            System.out.println("Invalid configuration in json.config: " + validationResult.get());
            System.out.println("Default config is used instead");
            return;
        }

        // initialise new Playground
        Playground playground = new Playground(configuration.getSize(), configuration.getDensity());

        // initialise ants
        Ant[] ants = new Ant[configuration.getAntCount()];
        for (int i = 0; i < configuration.getAntCount(); i++) {
            ants[i] = new Ant();

        }

        // logging start playground
        Logger.logPlaygroundToFile(playground.getMatrix(), PLAYGROUND_DEFAULT_FILE);

        // start algorithm
        for (int i = 0; i < ITERATIONS; i++) {
            var futures = new ArrayList<CompletableFuture<?>>();
            for (Ant ant : ants) {
                futures.add(CompletableFuture.runAsync(() -> ant.move(playground)));
            }
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        }

        // logging result to files
        Logger.logPlaygroundToFile(playground.getMatrix(), PLAYGROUND_FILE);
        Logger.logGarbageMovementToFile(playground.getMatrix(), ants, GARBAGE_MOVEMENT_FILE);
    }
}
