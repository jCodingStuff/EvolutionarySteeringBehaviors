public class Utils {

  public static float constrain(float x, float min, float max) {

    return Math.min(max, Math.max(min, x));

  }

}
