package project.games.dice;

import java.util.Random;

public class Die {
    private static final Random RANDOM = new Random();

    public static int roll(int faces) {
        return RANDOM.nextInt(1, faces+1);
    }

    public static int[] roll(int faces, int times) {
        int[] result = new int[times + 1];
        int sum = 0;
        for(int i = 0; i < times; i++) {
            result[i] = roll(faces);
            sum += result[i];
        }

        result[times] = sum;

        return result;
    }
}
