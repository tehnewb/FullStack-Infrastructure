package util;

import io.ObjectPool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * A utility class for manipulating and mutating numeric values.
 *
 * @author Albert Beaupre
 */
public class Value extends Number {

    private static final ObjectPool<Value> pool = new ObjectPool<>(() -> new Value(0), 100);
    private double number; // the number to mutate    // Object pool to reuse instances

    /**
     * Constructs a new Value object using a number to provide an initial value.
     *
     * @param number The initial number.
     */
    public Value(double number) {
        this.number = number;
    }

    /**
     * Creates a Value object with a constant numeric value.
     *
     * @param number The initial numeric value.
     * @return A new Value object with the specified initial value.
     */
    public static Value of(double number) {
        return pool.borrowObject().set(number);
    }

    /**
     * Sets the number of this Value to the specified number.
     *
     * @param number The number to set
     * @return This Value object after the number change.
     */
    public Value set(double number) {
        this.number = number;
        return this;
    }

    /**
     * Mutates the value by a specified percentage.
     *
     * @param input The percentage by which to adjust the value.
     * @return This Value object after the mutation.
     */
    public Value percent(double input) {
        number = number * (input / 100D);
        return this;
    }

    /**
     * Restricts the value to be within a specified range.
     *
     * @param min The minimum value in the range.
     * @param max The maximum value in the range.
     * @return This Value object after the restriction.
     */
    public Value clamp(double min, double max) {
        number = Math.min(Math.max(number, min), max);
        return this;
    }

    /**
     * Checks if this value is within the specified range.
     *
     * @param min The minimum value in the range.
     * @param max The maximum value in the range.
     * @return This Value object after the restriction.
     */
    public boolean within(double min, double max) {
        boolean result = number >= min && number <= max;
        pool.returnObject(this);
        return result;
    }

    /**
     * Adds a specified value to the current value.
     *
     * @param value The value to add.
     * @return This Value object after the addition.
     */
    public Value add(double value) {
        number += value;
        return this;
    }

    /**
     * Subtracts a specified value from the current value.
     *
     * @param value The value to subtract.
     * @return This Value object after the subtraction.
     */
    public Value subtract(double value) {
        number -= value;
        return this;
    }

    /**
     * Multiplies the current value by a specified factor.
     *
     * @param factor The factor by which to multiply the value.
     * @return This Value object after the multiplication.
     */
    public Value multiply(double factor) {
        number *= factor;
        return this;
    }

    /**
     * Divides the current value by a specified divisor.
     *
     * @param divisor The divisor by which to divide the value.
     * @return This Value object after the division.
     */
    public Value divide(double divisor) {
        if (divisor != 0) {
            number /= divisor;
        }
        return this;
    }

    /**
     * Raises the current value to a specified power.
     *
     * @param exponent The exponent to which to raise the value.
     * @return This Value object after the exponentiation.
     */
    public Value power(double exponent) {
        number = Math.pow(number, exponent);
        return this;
    }

    /**
     * Rounds the current value to a specified number of decimal places using the default rounding mode (HALF_UP).
     *
     * @param decimalPlaces The number of decimal places to round to.
     * @return This Value object after rounding.
     */
    public Value round(int decimalPlaces) {
        return round(decimalPlaces, RoundingMode.HALF_UP);
    }

    /**
     * Rounds the current value to a specified number of decimal places using the specified rounding mode.
     *
     * @param decimalPlaces The number of decimal places to round to.
     * @param roundingMode  The rounding mode to use.
     * @return This Value object after rounding.
     */
    public Value round(int decimalPlaces, RoundingMode roundingMode) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlaces, roundingMode);
        number = bd.doubleValue();
        return this;
    }

    /**
     * Ensures that the current value is positive.
     *
     * @return This Value object with a positive value.
     */
    public Value abs() {
        number = Math.abs(number);
        return this;
    }

    /**
     * Negates the current value.
     *
     * @return This Value object with the negated value.
     */
    public Value negate() {
        number = -number;
        return this;
    }

    /**
     * Sets the current value to a random number within a specified range.
     *
     * @param min The minimum value for the random number.
     * @param max The maximum value for the random number.
     * @return This Value object with the random value.
     */
    public Value randomize(double min, double max) {
        number = min + (Math.random() * (max - min));
        return this;
    }

    /**
     * Checks if the current value is equal to a specified value.
     *
     * @param otherValue The value to compare with.
     * @return true if the values are equal, false otherwise.
     */
    public boolean isEqualTo(double otherValue) {
        boolean result = Double.compare(number, otherValue) == 0;
        pool.returnObject(this);
        return result;
    }

    /**
     * Checks if the current value is greater than a specified value.
     *
     * @param otherValue The value to compare with.
     * @return true if the current value is greater, false otherwise.
     */
    public boolean isGreaterThan(double otherValue) {
        boolean result = number > otherValue;
        pool.returnObject(this);
        return result;
    }

    /**
     * Checks if the current value is less than a specified value.
     *
     * @param otherValue The value to compare with.
     * @return true if the current value is less, false otherwise.
     */
    public boolean isLessThan(double otherValue) {
        boolean result = number < otherValue;
        pool.returnObject(this);
        return result;
    }

    /**
     * Formats the current value as a string using the specified format pattern.
     *
     * @param formatPattern The format pattern to use.
     * @return The formatted string representation of the current value.
     */
    public String format(String formatPattern) {
        DecimalFormat df = new DecimalFormat(formatPattern);
        String result = df.format(number);
        pool.returnObject(this);
        return result;
    }

    /**
     * Converts the current value to an integer.
     *
     * @return The integer representation of the current value.
     */
    public int intValue() {
        int result = (int) number;
        pool.returnObject(this);
        return result;
    }

    /**
     * Converts the current value to a long.
     *
     * @return The long representation of the current value.
     */
    public long longValue() {
        long result = (long) number;
        pool.returnObject(this);
        return result;
    }

    /**
     * Converts the current value to a float.
     *
     * @return The float representation of the current value.
     */
    public float floatValue() {
        float result = (float) number;
        pool.returnObject(this);
        return result;
    }

    /**
     * Converts the current value to a double.
     *
     * @return The double representation of the current value.
     */
    public double doubleValue() {
        double result = number;
        pool.returnObject(this);
        return result;
    }

    /**
     * Gets the fractional part of the current value.
     *
     * @return The fractional part of the current value.
     */
    public double getFractPart() {
        double result = number - (int) number;
        pool.returnObject(this);
        return result;
    }

    /**
     * Increases the current value by a specified percentage.
     *
     * @param percentage The percentage by which to increase the value.
     * @return This Value object after the increase.
     */
    public Value incrByPerc(double percentage) {
        number *= (1 + (percentage / 100));
        return this;
    }

    /**
     * Decreases the current value by a specified percentage.
     *
     * @param percentage The percentage by which to decrease the value.
     * @return This Value object after the decrease.
     */
    public Value decrByPerc(double percentage) {
        number *= (1 - (percentage / 100));
        return this;
    }

    /**
     * Truncates the decimal part of the current value.
     *
     * @return This Value object after truncation.
     */
    public Value truncate() {
        number = (double) ((int) number);
        return this;
    }

    /**
     * Rounds the current value up to the nearest integer (ceiling).
     *
     * @return This Value object after rounding up.
     */
    public Value ceil() {
        number = Math.ceil(number);
        return this;
    }

    /**
     * Rounds the current value down to the nearest integer (floor).
     *
     * @return This Value object after rounding down.
     */
    public Value flr() {
        number = Math.floor(number);
        return this;
    }

    /**
     * Gets the signum (sign) of the current value.
     *
     * @return 1 if the value is positive, 0 if it's zero, -1 if it's negative.
     */
    public int signum() {
        int result = Double.compare(number, 0.0);
        pool.returnObject(this);
        return result;
    }

    /**
     * Calculates the percentage difference between the current value and another value.
     *
     * @param otherValue The value to compare with.
     * @return The percentage difference between the values.
     */
    public double percDiff(double otherValue) {
        if (otherValue == 0)
            return Double.POSITIVE_INFINITY;
        double result = (Math.abs(number - otherValue) / Math.abs(otherValue)) * 100.0;
        pool.returnObject(this);
        return result;
    }

    /**
     * Calculates the factorial of the current value (if it's a non-negative integer).
     *
     * @return This Value object after the factorial calculation.
     */
    public Value fact() {
        if (number >= 0 && number == Math.floor(number)) {
            double result = 1;
            for (int i = 1; i <= (int) number; i++) {
                result *= i;
            }
            number = result;
        } else {
            throw new ArithmeticException("Invalid Factorial Value: " + number);
        }
        return this;
    }

    /**
     * Calculates the exponential (e^x) of the current value.
     *
     * @return This Value object after the exponentiation.
     */
    public Value exp() {
        number = Math.exp(number);
        return this;
    }

    /**
     * Calculates the natural logarithm (ln) of the current value.
     *
     * @return This Value object after the logarithm calculation.
     */
    public Value log() {
        if (number > 0) {
            number = Math.log(number);
        } else {
            throw new ArithmeticException("Cannot calculate the natural logarithm for: " + number);
        }
        return this;
    }

    /**
     * Rounds the current value to the nearest Nth decimal place.
     *
     * @param decimalPlace The decimal place to round to.
     * @return This Value object after rounding.
     */
    public Value roundToDecimalPlace(int decimalPlace) {
        double scale = Math.pow(10, decimalPlace);
        number = Math.round(number * scale) / scale;
        return this;
    }

    /**
     * Calculates the sine of the current value (assuming the value is in radians).
     *
     * @return This Value object after calculating the sine.
     */
    public Value sin() {
        number = Math.sin(number);
        return this;
    }

    /**
     * Calculates the cosine of the current value (assuming the value is in radians).
     *
     * @return This Value object after calculating the cosine.
     */
    public Value cos() {
        number = Math.cos(number);
        return this;
    }

    /**
     * Calculates the tangent of the current value (assuming the value is in radians).
     *
     * @return This Value object after calculating the tangent.
     */
    public Value tan() {
        number = Math.tan(number);
        return this;
    }

    /**
     * Calculates the square root of the current value.
     *
     * @return This Value object after the square root calculation.
     * @throws ArithmeticException if the current value is negative.
     */
    public Value sqrt() {
        if (number >= 0) {
            number = Math.sqrt(number);
        } else {
            throw new ArithmeticException("Square root of a negative number is undefined.");
        }
        return this;
    }

    /**
     * Calculates the cube root of the current value.
     *
     * @return This Value object after the cube root calculation.
     */
    public Value cubeRoot() {
        number = Math.cbrt(number);
        return this;
    }

    /**
     * Calculates the hypotenuse of a right triangle with the current value as one of the sides.
     *
     * @param otherSide The length of the other side of the right triangle.
     * @return This Value object after the hypotenuse calculation.
     */
    public Value hypot(double otherSide) {
        number = Math.hypot(number, otherSide);
        return this;
    }

    /**
     * Calculates the result of raising the current value to a specified base.
     *
     * @param base The base to which the current value is raised.
     * @return This Value object after the exponentiation.
     */
    public Value pow(double base) {
        number = Math.pow(base, number);
        return this;
    }

    /**
     * Performs a bitwise AND operation with the current value and another integer.
     *
     * @param otherValue The integer to perform the bitwise AND operation with.
     * @return This Value object after the bitwise AND operation.
     */
    public Value bitAnd(int otherValue) {
        number = (int) number & otherValue;
        return this;
    }

    /**
     * Performs a bitwise OR operation with the current value and another integer.
     *
     * @param otherValue The integer to perform the bitwise OR operation with.
     * @return This Value object after the bitwise OR operation.
     */
    public Value bitOr(int otherValue) {
        number = (int) number | otherValue;
        return this;
    }

    /**
     * Performs a bitwise XOR operation with the current value and another integer.
     *
     * @param otherValue The integer to perform the bitwise XOR operation with.
     * @return This Value object after the bitwise XOR operation.
     */
    public Value bitXor(int otherValue) {
        number = (int) number ^ otherValue;
        return this;
    }

    /**
     * Calculates the logarithm of the current value with a specified base.
     *
     * @param base The base of the logarithm.
     * @return This Value object after the logarithm calculation.
     * @throws ArithmeticException if the current value is non-positive or if the base is non-positive.
     */
    public Value log(double base) {
        if (number > 0 && base > 0) {
            number = Math.log(number) / Math.log(base);
        } else {
            throw new ArithmeticException("Logarithm input must be positive.");
        }
        return this;
    }

    /**
     * Checks if the current value is approximately equal to another value within a specified epsilon.
     *
     * @param otherValue The value to compare with.
     * @param epsilon    The tolerance for approximate equality.
     * @return true if the values are approximately equal within the epsilon, false otherwise.
     */
    public boolean isApproxEqualTo(double otherValue, double epsilon) {
        boolean result = Math.abs(number - otherValue) <= epsilon;
        pool.returnObject(this);
        return result;
    }

    /**
     * Rounds the current value to the nearest multiple of a specified value.
     *
     * @param multiple The value to round to.
     * @return This Value object after rounding.
     */
    public Value roundToNearestMultiple(double multiple) {
        number = Math.round(number / multiple) * multiple;
        return this;
    }

    /**
     * Calculates the reciprocal (1/x) of the current value.
     *
     * @return This Value object after the reciprocal calculation.
     * @throws ArithmeticException if the current value is zero.
     */
    public Value recip() {
        if (number != 0) {
            number = 1 / number;
        } else {
            throw new ArithmeticException("Reciprocal of zero is undefined.");
        }
        return this;
    }

    /**
     * Checks if the current value is even.
     *
     * @return true if the current value is even, false otherwise.
     */
    public boolean isEven() {
        boolean result = ((int) number) % 2 == 0;
        pool.returnObject(this);
        return result;
    }

    /**
     * Checks if the current value is odd.
     *
     * @return true if the current value is odd, false otherwise.
     */
    public boolean isOdd() {
        boolean result = ((int) number) % 2 != 0;
        pool.returnObject(this);
        return result;
    }

    /**
     * Calculates the absolute difference between the current value and another value.
     *
     * @param otherValue The value to compare with.
     * @return The absolute difference between the values.
     */
    public double absDiff(double otherValue) {
        double result = Math.abs(number - otherValue);
        pool.returnObject(this);
        return result;
    }

    /**
     * Returns the current numeric value as a string.
     *
     * @return The string representation of the current numeric value.
     */
    @Override
    public String toString() {
        return String.valueOf(number);
    }


}
