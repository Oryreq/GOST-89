package ascii;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Map.entry;

/**
 * AsciiRussianConverter realize ascii converters.
 * @author Vsevolod Ashihmin.
 * @Date: 14.12.2023.
 */
public class AsciiRussianConverter {

    /**
     * Russian Ascii alphabet and codes.
     */
    private static final Map<Character, Integer> RUSSIAN_ASCII_CODES =  Map.ofEntries(
            entry('�', 128),
            entry('�', 129),
            entry('�', 130),
            entry('�', 131),
            entry('�', 132),
            entry('�', 133),
            entry('�', 134),
            entry('�', 135),
            entry('�', 136),
            entry('�', 137),
            entry('�', 138),
            entry('�', 139),
            entry('�', 140),
            entry('�', 141),
            entry('�', 142),
            entry('�', 143),
            entry('�', 144),
            entry('�', 145),
            entry('�', 146),
            entry('�', 147),
            entry('�', 148),
            entry('�', 149),
            entry('�', 150),
            entry('�', 151),
            entry('�', 152),
            entry('�', 153),
            entry('�', 154),
            entry('�', 155),
            entry('�', 156),
            entry('�', 157),
            entry('�', 158),
            entry('�', 159),
            entry('�', 160),
            entry('�', 161),
            entry('�', 162),
            entry('�', 163),
            entry('�', 164),
            entry('�', 165),
            entry('�', 166),
            entry('�', 167),
            entry('�', 168),
            entry('�', 169),
            entry('�', 170),
            entry('�', 171),
            entry('�', 172),
            entry('�', 173),
            entry('�', 174),
            entry('�', 175),
            entry('�', 224),
            entry('�', 225),
            entry('�', 226),
            entry('�', 227),
            entry('�', 228),
            entry('�', 229),
            entry('�', 230),
            entry('�', 231),
            entry('�', 232),
            entry('�', 233),
            entry('�', 234),
            entry('�', 235),
            entry('�', 236),
            entry('�', 237),
            entry('�', 238),
            entry('�', 239),
            entry('�', 240),
            entry('�', 241)
    );

    /**
     * Full Ascii table.
     */
    private static final HashMap<Character, Integer> ASCII_CODES = new HashMap<>();

    static {
        addEnglishAsciiSymbols();
        addRussianAsciiSymbols();
    }

    /**
     * Convert symbol to ascii code.
     */
    public static int toAsciiCode(char symbol) {
        return ASCII_CODES.get(symbol);
    }

    /**
     * Convert ascii code to symbol.
     */
    public static char toSymbol(int codeSymbol) {
        for (Map.Entry<Character, Integer> entry : ASCII_CODES.entrySet()) {
            if (entry.getValue() == codeSymbol) {
                return entry.getKey();
            }
        }
        return '0';
    }

    /**
     * Init Ascii table by english symbols.
     */
    private static void addEnglishAsciiSymbols() {
        IntStream.range(0, 127)
                .forEach(i -> ASCII_CODES.put((char) i, i));
    }

    /**
     * Init Ascii table by russian symbols.
     */
    private static void addRussianAsciiSymbols() {
        ASCII_CODES.putAll(RUSSIAN_ASCII_CODES);
    }
}
