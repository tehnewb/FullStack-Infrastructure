package math;

public class DiscreteMath {


    public static int getNextPowerOfTwo(int number) {
        if (number <= 0)
            throw new IllegalArgumentException("Cannot get power of two on negative number");

        number--;
        number |= number >> 1;
        number |= number >> 2;
        number |= number >> 4;
        number |= number >> 8;
        number |= number >> 16;
        return number + 1;
    }
}
