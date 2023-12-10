package files.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public FlatYaml(File file) throws IOException {
        this(Files.readString(Paths.get(file.toURI())));
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
            default -> value;
        };
    }

    private static class FlatYamlParseException extends RuntimeException {
        public FlatYamlParseException(String message) {
            super(message);
        }
    }
}