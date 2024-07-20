package utils;

import classes.Ant;
import classes.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    // logs state of playground to a file
    public static void logPlaygroundToFile(Field[][] matrix, String fileName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sb.append(matrix[i][j].getGarbage().size()).append(" | ");
            }
            sb.append("\n");
        }
        try {
            Files.write(Paths.get(fileName), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Failed to log matrix to file");
        }
    }

    // logs how often each garbage piece has been moved
    public static void logGarbageMovementToFile(Field[][] matrix, Ant[] ants, String fileName) {
        StringBuilder sb = new StringBuilder();
        HashMap<Integer, Integer> items = new HashMap<>();
        for (int i = 0; i < ants.length; i++) {
            var garbage = ants[i].getGarbage();
            if (garbage != null) {
                items.put(garbage.getId(), garbage.getMoveCount());
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j].getGarbage().forEach(e -> items.put(e.getId(), e.getMoveCount()));
            }
        }

        // log hashmap
        items.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            var key = entry.getKey();
            var value = entry.getValue();
            sb.append(key).append(":").append(value).append("\n");
        });
        try {
            Files.write(Paths.get(fileName), sb.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Failed to log garbage movement to file");
        }
    }
}
