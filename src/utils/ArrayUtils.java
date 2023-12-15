package utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ArrayUtils realize string array operations.
 * @author Vsevolod Ashihmin.
 * @Date: 14.12.2023.
 */
public class ArrayUtils {

    /**
     * Split string by parts of given size. <br>
     * If last part's length less than given size prolongs it by nulls.
     */
    public static String[] splitStringToParts(String string, int partSize) {
        ArrayList<String> parts = new ArrayList<>();

        for (int i = 0; i < string.length(); i += partSize) {
            if (i + partSize < string.length()) {
                parts.add(string.substring(i, i + partSize));
            } else {
                int difference = Math.abs(string.length() - (i + partSize));
                parts.add(string.substring(i) + "0".repeat(difference));
            }
        }

        return parts.toArray(String[]::new);
    }

    /**
     * Reduces string array to one string.
     */
    public static String reduceArray(String[] array) {
        return Arrays.stream(array)
                    .reduce((sum, item) -> sum += item)
                    .get();
    }
}
