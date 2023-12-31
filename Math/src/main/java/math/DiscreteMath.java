package math;

public class DiscreteMath {


    public static int getNextPowerOfTwo(int number) {
        if (number < 1)
            throw new IllegalArgumentException("Cannot get power of two on negative number");

        number--;
        number |= number >> 1;
        number |= number >> 2;
        number |= number >> 4;
        number |= number >> 8;
        number |= number >> 16;
        return number + 1;
    }

    public static boolean between(byte value, byte min, byte max) {
        return value >= min && value <= max;
    }

    public static boolean between(short value, short min, short max) {
        return value >= min && value <= max;
    }

    public static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean between(long value, long min, long max) {
        return value >= min && value <= max;
    }

    public static boolean between(float value, float min, float max) {
        return value >= min && value <= max;
    }

    public static boolean between(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static int clamp(int value, int minValue, int maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

    public static float clamp(float value, float minValue, float maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

    public static double clamp(double value, double minValue, double maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

    public static byte clamp(byte value, byte minValue, byte maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

    public static short clamp(short value, short minValue, short maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

    public static long clamp(long value, long minValue, long maxValue) {
        return (value < minValue) ? minValue : (value > maxValue) ? maxValue : value;
    }

}
