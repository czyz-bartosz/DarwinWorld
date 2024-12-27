package darwinWorld.utils;

import java.util.Random;

public class RandomNumberGenerator {
    private static Random random = new Random();

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public static int integerFromRange(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }

    public static double randomDouble() {
        return random.nextDouble();
    }

}