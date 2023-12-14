package files.parsers;

import math.DiscreteMath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;


/**
 * The FlatYaml class is designed to parse simple markup strings or files and convert them into a HashMap<String, Object> structure.
 * The markup format follows a basic key-value structure, with support for nested structures using curly braces and square brackets.
 * Comments can be added using the '#' symbol.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class FlatYaml extends HashMap<String, Object> {

    /**
     * Constructs a FlatYaml object by parsing the provided markup string.
     *
     * @param string The markup string to be parsed.
     */
    public FlatYaml(String string) {
        parseString(string);
    }

    /**
     * Constructs a FlatYaml object by parsing the content of the provided file.
     *
     * @param file The file containing the markup to be parsed.
     */
    public FlatYaml(File file) {
        try {
            parseString(Files.readString(Paths.get(file.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the String value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The String value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type String.
     * @throws NullPointerException If the specified key is null.
     */
    public String getString(String key) {
        return (String) get(key);
    }

    /**
     * Retrieves the byte value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The byte value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type Byte.
     * @throws NullPointerException If the specified key is null.
     */
    public int getByte(String key) {
        return (byte) get(key);
    }

    /**
     * Retrieves the integer value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The integer value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type Integer.
     * @throws NullPointerException If the specified key is null.
     */
    public int getInt(String key) {
        return (int) get(key);
    }

    /**
     * Retrieves the long value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The long value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type Long.
     * @throws NullPointerException If the specified key is null.
     */
    public long getLong(String key) {
        return (long) get(key);
    }

    /**
     * Retrieves the double value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The double value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type Double.
     * @throws NullPointerException If the specified key is null.
     */
    public double getDouble(String key) {
        return (double) get(key);
    }

    /**
     * Retrieves the float value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The float value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type Float.
     * @throws NullPointerException If the specified key is null.
     */
    public float getFloat(String key) {
        return (float) get(key);
    }

    /**
     * Retrieves a byte array value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved as a byte array.
     * @return The byte array value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type byte[].
     * @throws NullPointerException If the specified key is null.
     */
    public byte[] getByteArray(String key) {
        Object o = get(key);
        if (o instanceof byte[]) {
            return (byte[]) o;
        } else {
            if (o.getClass().isArray()) {
                String[] stringArr = (String[]) o;
                byte[] arr = new byte[stringArr.length];
                for (int i = 0; i < arr.length; i++)
                    arr[i] = Byte.parseByte(stringArr[i]);
                put(key, arr);
                return arr;
            } else throw new ClassCastException(key + " is not an array");
        }
    }

    /**
     * Retrieves an int array value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved as an int array.
     * @return The int array value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type int[].
     * @throws NullPointerException If the specified key is null.
     */
    public int[] getIntArray(String key) {
        Object o = get(key);
        if (o instanceof int[]) {
            return (int[]) o;
        } else {
            if (o.getClass().isArray()) {
                int[] arr = Arrays.stream((String[]) o).mapToInt(s -> Integer.parseInt(s.trim())).toArray();
                put(key, arr);
                return arr;
            } else throw new ClassCastException(key + " is not an array");
        }
    }

    /**
     * Retrieves a long array value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved as a long array.
     * @return The long array value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type long[].
     * @throws NullPointerException If the specified key is null.
     */
    public long[] getLongArray(String key) {
        Object o = get(key);
        if (o instanceof long[]) {
            return (long[]) o;
        } else {
            if (o.getClass().isArray()) {
                long[] arr = Arrays.stream((String[]) o).mapToLong(s -> Long.parseLong(s.trim())).toArray();
                put(key, arr);
                return arr;
            } else throw new ClassCastException(key + " is not an array");
        }
    }

    /**
     * Retrieves a float array value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved as a float array.
     * @return The float array value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type float[].
     * @throws NullPointerException If the specified key is null.
     */
    public float[] getFloatArray(String key) {
        Object o = get(key);
        if (o instanceof float[]) {
            return (float[]) o;
        } else {
            if (o.getClass().isArray()) {
                String[] stringArr = (String[]) o;
                float[] arr = new float[stringArr.length];
                for (int i = 0; i < arr.length; i++)
                    arr[i] = Float.parseFloat(stringArr[i]);
                put(key, arr);
                return arr;
            } else throw new ClassCastException(key + " is not an array");
        }
    }

    /**
     * Retrieves a double array value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved as a double array.
     * @return The double array value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type double[].
     * @throws NullPointerException If the specified key is null.
     */
    public double[] getDoubleArray(String key) {
        Object o = get(key);
        if (o instanceof double[]) {
            return (double[]) o;
        } else {
            if (o.getClass().isArray()) {
                double[] arr = Arrays.stream((String[]) o).mapToDouble(s -> Double.parseDouble(s.trim())).toArray();
                put(key, arr);
                return arr;
            } else throw new ClassCastException(key + " is not an array");
        }
    }

    /**
     * Retrieves the HashMap<String, Object> value associated with the specified key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The HashMap<String, Object> value associated with the key.
     * @throws ClassCastException   If the value associated with the key is not of type HashMap<String, Object>.
     * @throws NullPointerException If the specified key is null.
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getMap(String key) {
        return (HashMap<String, Object>) get(key);
    }

    /**
     * Parses the given markup string and populates the FlatYaml object with key-value pairs.
     * The markup should follow a simple key-value structure, with support for nested structures.
     *
     * @param string The markup string to be parsed.
     * @throws RuntimeException If an error occurs during the parsing process.
     */
    private void parseString(String string) {
        String[] lines = string.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.charAt(0) == '#' || line.isBlank()) // line start as comment
                continue;

            int identifierIndex = line.indexOf(':');

            if (identifierIndex == -1)  // don't attempt to parse incorrect line
                continue;

            int commentIndex = line.indexOf('#');
            String name = line.substring(0, identifierIndex).trim();
            String value = line.substring(identifierIndex + 1);
            if (commentIndex != -1)
                value = line.substring(identifierIndex + 1, commentIndex);

            put(name, parseObject(value.trim(), i + 1));
        }
    }

    /**
     * Parses a string representation of an object and returns the corresponding Object.
     * Supports simple values, arrays, and nested structures using curly braces.
     *
     * @param value The string representation of the object to be parsed.
     * @return The parsed object.
     */
    private Object parseObject(String value, int line) {
        return switch (value.charAt(0)) {
            case '{' -> {
                try {
                    HashMap<String, Object> map = new HashMap<>();
                    String[] parts = value.split(",");
                    for (String entry : parts) {
                        String[] entryParts = entry.replaceAll("[{}]", "").split(":");
                        String keyPart = entryParts[0].trim();
                        String valuePart = entryParts[1].trim();
                        map.put(keyPart, valuePart);
                    }
                    yield map;
                } catch (Exception e) {
                    throw new FlatYamlParseException("Cannot parse map object at line " + line);
                }
            }
            case '[' -> {
                try {
                    yield value.replaceAll("[\\[\\] ]", "").split(",");
                } catch (Exception e) {
                    throw new FlatYamlParseException("Cannot parse array object at line " + line);
                }
            }
            default -> {
                if (value.matches("-?\\d+")) { // it is a number
                    if (value.contains(".")) { // it has a decimal
                        double doubleValue = Double.parseDouble(value);
                        if (DiscreteMath.between(doubleValue, Float.MIN_VALUE, Float.MAX_VALUE)) {
                            yield Float.parseFloat(value);
                        } else yield doubleValue;
                    } else { // it's an integer type
                        long longValue = Long.parseLong(value);
                        if (DiscreteMath.between(longValue, Byte.MIN_VALUE, Byte.MAX_VALUE)) {
                            yield Byte.parseByte(value);
                        } else if (DiscreteMath.between(longValue, Short.MIN_VALUE, Short.MAX_VALUE)) {
                            yield Short.parseShort(value);
                        } else if (DiscreteMath.between(longValue, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
                            yield Integer.parseInt(value);
                        } else yield longValue;
                    }
                } else {
                    yield switch (value.toLowerCase().trim()) {
                        case "yes", "true" -> true;
                        case "no", "false" -> false;
                        default -> value;
                    };
                }
            }
        };
    }

    private static class FlatYamlParseException extends RuntimeException {
        public FlatYamlParseException(String message) {
            super(message);
        }
    }
}