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
            entry('À', 128),
            entry('Á', 129),
            entry('Â', 130),
            entry('Ã', 131),
            entry('Ä', 132),
            entry('Å', 133),
            entry('Æ', 134),
            entry('Ç', 135),
            entry('È', 136),
            entry('É', 137),
            entry('Ê', 138),
            entry('Ë', 139),
            entry('Ì', 140),
            entry('Í', 141),
            entry('Î', 142),
            entry('Ï', 143),
            entry('Ð', 144),
            entry('Ñ', 145),
            entry('Ò', 146),
            entry('Ó', 147),
            entry('Ô', 148),
            entry('Õ', 149),
            entry('Ö', 150),
            entry('×', 151),
            entry('Ø', 152),
            entry('Ù', 153),
            entry('Ú', 154),
            entry('Û', 155),
            entry('Ü', 156),
            entry('Ý', 157),
            entry('Þ', 158),
            entry('ß', 159),
            entry('à', 160),
            entry('á', 161),
            entry('â', 162),
            entry('ã', 163),
            entry('ä', 164),
            entry('å', 165),
            entry('æ', 166),
            entry('ç', 167),
            entry('è', 168),
            entry('é', 169),
            entry('ê', 170),
            entry('ë', 171),
            entry('ì', 172),
            entry('í', 173),
            entry('î', 174),
            entry('ï', 175),
            entry('ð', 224),
            entry('ñ', 225),
            entry('ò', 226),
            entry('ó', 227),
            entry('ô', 228),
            entry('õ', 229),
            entry('ö', 230),
            entry('÷', 231),
            entry('ø', 232),
            entry('ù', 233),
            entry('ú', 234),
            entry('û', 235),
            entry('ü', 236),
            entry('ý', 237),
            entry('þ', 238),
            entry('ÿ', 239),
            entry('¨', 240),
            entry('¸', 241)
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
