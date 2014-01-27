package deludobellico.hexadroid.app.util;

import java.util.Random;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RandomGenerator extends Random {

    private static final RandomGenerator INSTANCE = new RandomGenerator();

    private RandomGenerator() {
        super(System.currentTimeMillis());
    }

    public static RandomGenerator getInstance() {
        return INSTANCE;
    }

    public static boolean probabilityCheck(double probability) {
        return (INSTANCE.nextDouble() > probability);
    }
}