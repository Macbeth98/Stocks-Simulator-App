import java.util.Random;

public class Utils {
  public static int getNextRandomInteger(Random r, int minBound, int maxBound) {
    return r.nextInt((maxBound - minBound) + 1) + minBound;
  }
}
