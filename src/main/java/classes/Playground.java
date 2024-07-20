package classes;

import java.util.Random;
import java.util.Stack;

public class Playground {
    private final Field[][] matrix;

    public Playground(int size, float density) {
        this.matrix = new Field[size][size];

        // initialising field
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.matrix[i][j] = new Field(new Stack<>());
            }
        }

        // determining garbage amount to spread on playground
        int garbageCount = Math.round(size * size * density);

        // spreading garbage on field
        while (garbageCount > 1) {
            int xPos = (int) (Math.random() * size);
            int yPos = (int) (Math.random() * size);
            if (this.matrix[xPos][yPos].getGarbage().empty()) {
                this.matrix[xPos][yPos].initGarbage(new Garbage(garbageCount));
                garbageCount -= 1;
            }
        }
    }

    public Field[][] getMatrix() {
        return matrix;
    }

    public int generateNewNumber() {
        return new Random().nextInt(matrix.length);
    }
}
