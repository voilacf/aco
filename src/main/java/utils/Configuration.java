package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class Configuration {
    private final int size;
    private final float density;
    private final int antCount;

    public Configuration(int size, float density, int antCount) {
        this.size = size;
        this.density = density;
        this.antCount = antCount;
    }

    public static Configuration load(String fileName) {
        System.out.println("Loading configuration from " + fileName);
        try {
            String fileContent = Files.readString(Paths.get(fileName));
            JSONObject o = new JSONObject(fileContent);

            int size = o.getInt("size");

            float density = o.getFloat("density");
            int antCount = o.getInt("antCount");

            System.out.println("Successfully loaded configuration.");
            return new Configuration(size, density, antCount);
        } catch (IOException | JSONException e) {
            System.out.println("Cannot load config file, using defaults.");
            return new Configuration(10, 0.5f, 10);
        }
    }

    public int getSize() {
        return size;
    }

    public float getDensity() {
        return density;
    }

    public int getAntCount() {
        return antCount;
    }

    @Override
    public String toString() {
        return "Configurations\n" + "size: " + size
                + "\ndensity: " + density
                + "\nant count: " + antCount;
    }

    public Optional<String> validate() {
        if (size < 10 || size > 50) {
            return Optional.of("Tableau size must be between 10 and 50");
        }

        if (density < 0.05 || density > 0.75) {
            return Optional.of("Density must be between 0.05 and 0.75");
        }

        if (antCount < 10 || antCount > 50) {
            return Optional.of("Ant count must be between 10 and 50");
        }

        return Optional.empty();
    }
}
