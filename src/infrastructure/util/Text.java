package infrastructure.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * The Text class provides utility methods for manipulating text strings.
 */
public class Text {
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "A", "E", "I", "O", "U"};

    private String string;

    /**
     * Constructs a Text object with the given string.
     *
     * @param string The input text string.
     */
    public Text(String string) {
        this.string = string;
    }

    /**
     * Creates a Text object from a given string.
     *
     * @param string The input text string.
     * @return A Text object.
     */
    public static Text of(String string) {
        return new Text(string);
    }

    /**
     * Wraps the text to fit within the specified line length.
     *
     * @param lineLength The maximum line length.
     * @param wrapWords  If true, words will be wrapped to the next line if they exceed the line length.
     *                   If false, the text will be broken at the line length boundary regardless of word boundaries.
     * @return The modified Text object with wrapped text.
     */
    public Text wrapText(int lineLength, boolean wrapWords) {
        String[] words = string.split("\\s+");
        StringBuilder wrappedText = new StringBuilder();
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() <= lineLength) {
                wrappedText.append(word).append(" ");
                currentLineLength += word.length() + 1; // +1 for the space
            } else {
                if (wrapWords) {
                    wrappedText.append("\n").append(word).append(" ");
                    currentLineLength = word.length() + 1; // +1 for the space
                } else {
                    wrappedText.append("\n").append(word).append(" ");
                    currentLineLength = 0;
                }
            }
        }

        string = wrappedText.toString().trim();
        return this;
    }

    /**
     * Adds a prefix "a" or "an" to the text based on the presence of vowels.
     *
     * @param string The prefix string to be added.
     * @return The modified Text object.
     */
    public Text withPrefix(String string) {
        for (String vowel : VOWELS) {
            if (string.startsWith(vowel)) {
                string = "an " + string;
                return this;
            }
        }
        string = "a " + string;
        return this;
    }

    /**
     * Capitalizes the first letter of the text and converts the rest to lowercase.
     *
     * @return The modified Text object.
     */
    public Text upperFirst() {
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return this;
    }

    /**
     * Appends a suffix to the text.
     *
     * @param suffix The suffix string to be added.
     * @return The modified Text object.
     */
    public Text withSuffix(String suffix) {
        string += " " + suffix;
        return this;
    }

    /**
     * Replaces all occurrences of a substring with another string.
     *
     * @param target      The target substring to be replaced.
     * @param replacement The replacement string.
     * @return The modified Text object.
     */
    public Text replaceAll(String target, String replacement) {
        string = string.replaceAll(target, replacement);
        return this;
    }

    /**
     * Truncates the text to a specified length.
     *
     * @param length The maximum length of the text.
     * @return The modified Text object.
     */
    public Text truncate(int length) {
        if (string.length() > length) {
            string = string.substring(0, length);
        }
        return this;
    }

    /**
     * Counts the number of words in the text.
     *
     * @return The number of words in the text.
     */
    public int countWords() {
        String[] words = string.split("\\s+");
        return words.length;
    }

    /**
     * Reverses the order of words in the text.
     *
     * @return The modified Text object with reversed words.
     */
    public Text reverseWords() {
        String[] words = string.split("\\s+");
        StringBuilder reversed = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]).append(" ");
        }
        string = reversed.toString().trim();
        return this;
    }

    /**
     * Checks if the text is a pangram (contains every letter of the alphabet at least once).
     *
     * @return true if the text is a pangram, otherwise false.
     */
    public boolean isPangram() {
        String lowercaseText = string.toLowerCase();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (lowercaseText.indexOf(letter) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all non-alphanumeric characters from the text.
     *
     * @return The modified Text object with non-alphanumeric characters removed.
     */
    public Text removeNonAlphaNumeric() {
        string = string.replaceAll("[^a-zA-Z0-9\\s]", "");
        return this;
    }

    /**
     * Reverses the characters in the text.
     *
     * @return The modified Text object with reversed characters.
     */
    public Text reverse() {
        string = new StringBuilder(string).reverse().toString();
        return this;
    }

    /**
     * Checks if the text is a palindrome (reads the same forwards and backwards).
     *
     * @return true if the text is a palindrome, otherwise false.
     */
    public boolean isPalindrome() {
        String reversed = new StringBuilder(string).reverse().toString();
        return string.equalsIgnoreCase(reversed);
    }

    /**
     * Removes HTML tags from the text.
     *
     * @return The modified Text object with HTML tags removed.
     */
    public Text stripHtmlTags() {
        string = string.replaceAll("\\<.*?\\>", "");
        return this;
    }

    /**
     * Converts a camelCase or PascalCase string to separate words.
     *
     * @return The modified Text object with words separated.
     */
    public Text camelCaseToWords() {
        string = string.replaceAll("([a-z])([A-Z])", "$1 $2");
        return this;
    }

    /**
     * Counts the number of lines in the text.
     *
     * @return The number of lines in the text.
     */
    public int countLines() {
        String[] lines = string.split("\\r?\\n");
        return lines.length;
    }

    /**
     * Converts the text to title case.
     *
     * @return The modified Text object in title case.
     */
    public Text toTitleCase() {
        String[] words = string.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        string = result.toString().trim();
        return this;
    }

    /**
     * Abbreviates the text to a specified maximum length, adding an ellipsis if truncated.
     *
     * @param maxLength The maximum length of the text.
     * @return The modified Text object.
     */
    public Text abbreviate(int maxLength) {
        if (string.length() > maxLength) {
            string = string.substring(0, maxLength - 3) + "...";
        }
        return this;
    }

    /**
     * Shuffles the characters within the text randomly.
     *
     * @return The modified Text object with shuffled characters.
     */
    public Text shuffle() {
        char[] chars = string.toCharArray();
        List<Character> charList = new ArrayList<>();
        for (char c : chars) {
            charList.add(c);
        }
        Collections.shuffle(charList);
        StringBuilder shuffled = new StringBuilder();
        for (Character c : charList) {
            shuffled.append(c);
        }
        string = shuffled.toString();
        return this;
    }

    /**
     * Checks if the text represents a numeric value.
     *
     * @return true if the text is numeric, otherwise false.
     */
    public boolean isNumeric() {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Counts the number of consonants in the text (non-vowel, non-digit characters).
     *
     * @return The count of consonants.
     */
    public int countConsonants() {
        String consonants = string.replaceAll("[aeiouAEIOU0-9\\s]", "");
        return consonants.length();
    }

    /**
     * Concatenates multiple strings to the end of the current text.
     *
     * @param strings The strings to concatenate.
     * @return The modified Text object with concatenated text.
     */
    public Text concatenate(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str);
        }
        string = builder.toString();
        return this;
    }

    /**
     * URL encodes the text using UTF-8 encoding.
     *
     * @return The modified Text object with URL-encoded text.
     * @throws UnsupportedEncodingException If the encoding is not supported.
     */
    public Text urlEncode() throws UnsupportedEncodingException {
        string = URLEncoder.encode(string, StandardCharsets.UTF_8);
        return this;
    }

    /**
     * URL decodes the text using UTF-8 encoding.
     *
     * @return The modified Text object with URL-decoded text.
     * @throws UnsupportedEncodingException If the encoding is not supported.
     */
    public Text urlDecode() throws UnsupportedEncodingException {
        string = URLDecoder.decode(string, StandardCharsets.UTF_8);
        return this;
    }

    /**
     * Base64 encodes the text using the default character encoding (UTF-8).
     *
     * @return The modified Text object with Base64-encoded text.
     */
    public Text base64Encode() {
        byte[] encodedBytes = Base64.getEncoder().encode(string.getBytes());
        string = new String(encodedBytes, StandardCharsets.UTF_8);
        return this;
    }

    /**
     * Base64 decodes the text to its original form using the default character encoding (UTF-8).
     *
     * @return The modified Text object with Base64-decoded text.
     */
    public Text base64Decode() {
        byte[] decodedBytes = Base64.getDecoder().decode(string);
        string = new String(decodedBytes, StandardCharsets.UTF_8);
        return this;
    }


    /**
     * Retrieves the byte array tied to the internal string.
     *
     * @return The byte array.
     */
    public byte[] toBytes() {
        return string.getBytes();
    }

    /**
     * Gets the current text string.
     *
     * @return The current text string.
     */
    public String getString() {
        return string;
    }
}