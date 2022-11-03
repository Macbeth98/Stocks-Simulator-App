import java.util.Random;

/**
 * This class contains methods which are used as utility functions.
 */
public class Utils {
  public static int getNextRandomInteger(Random r, int minBound, int maxBound) {
    return r.nextInt((maxBound - minBound) + 1) + minBound;
  }
}
