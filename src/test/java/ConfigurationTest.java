import classes.Playground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.Configuration;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTest {
    private Configuration configuration;
    private Playground playground;
    private int garbageOnField = 1;

    @BeforeEach
    public void setup() {
    }

    @Test
    @Order(1)
    public void useConfigWhenValid() {
        configuration = new Configuration(10, 0.45f, 10);
        Optional<String> validationResult = configuration.validate();
        assertFalse(validationResult.isPresent());
        playground = new Playground(configuration.getSize(), configuration.getDensity());
        assertEquals(10, playground.getMatrix().length);
        for (int i = 0; i < playground.getMatrix().length; i++) {
            for (int j = 0; j < playground.getMatrix().length; j++) {
                garbageOnField += playground.getMatrix()[i][j].getGarbage().size() > 0 ? 1 : 0;
            }
        }
        assertEquals(Math.round(configuration.getSize() * configuration.getSize() * configuration.getDensity()), garbageOnField);
    }

    @Test
    @Order(2)
    public void useDefaultConfigWhenConfigInvalid() {
        Configuration configuration = Configuration.load("/");
        playground = new Playground(configuration.getSize(), configuration.getDensity());
        assertEquals(10, playground.getMatrix().length);
        for (int i = 0; i < playground.getMatrix().length; i++) {
            for (int j = 0; j < playground.getMatrix().length; j++) {
                garbageOnField += playground.getMatrix()[i][j].getGarbage().size() > 0 ? 1 : 0;
            }
        }
        assertEquals(Math.round(100 * 0.5f), garbageOnField);
    }

    @Test
    @Order(3)
    public void validateConfigCorrectly() {
        Configuration configuration = Configuration.load("/");
        Optional<String> validationResult = configuration.validate();
        assertFalse(validationResult.isPresent());
    }

    @Test
    @Order(4)
    public void validateInvalidConfigCorrectly() {
        final int validSize = 10;
        final float validDensity = 0.5f;
        final int validAntCount = 10;

        var invalidConfig = new Configuration[]{
                new Configuration(9, validDensity, validAntCount),
                new Configuration(51, validDensity, validAntCount),
                new Configuration(validSize, 0.04f, validAntCount),
                new Configuration(validSize, 0.76f, validAntCount),
                new Configuration(validSize, validDensity, 9),
                new Configuration(validSize, validDensity, 51),
        };

        Arrays.stream(invalidConfig).forEach(config -> {
            assertTrue(config.validate().isPresent());
        });
    }
}
