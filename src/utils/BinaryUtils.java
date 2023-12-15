package utils;

import ascii.AsciiRussianConverter;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * BinaryUtils realize binary converters and xor operation.
 * @author Vsevolod Ashihmin.
 * @Date: 14.12.2023.
 */
public class BinaryUtils {
    private static final int MAX_LENGTH_FOR_BINARY = 8;

    /**
     * Split binary text to numbers which can be transform in Decimal system. <br>
     * Example: "1111111100000000" splits to "11111111" and "00000000". <br>
     * @return  split numbers as a stream.
     */
    public static Stream<String> splitBinaryNumbers(String binaryText) {
        IntFunction<String> getBinaryCodes = (i) -> binaryText.substring(i, i + MAX_LENGTH_FOR_BINARY);

        return IntStream.range(0, binaryText.length())
                        .filter(i -> i % 8 == 0)
                        .mapToObj(getBinaryCodes);
    }

    /**
     * Convert number from Decimal system to Binary system.
     */
    public static String toBinaryNumber(int number) {
       return BinaryConverter.toBinaryNumber(number);
    }

    /**
     * Convert number from Binary system to Decimal system.
     */
    public static String fromBinaryNumber(String binaryNumber) {
        return BinaryConverter.fromBinaryNumber(binaryNumber);
    }

    /**
     * Convert binary text to number in Decimal system.
     */
    public static String fromBinaryTextToNumber(String binaryText) {
        return BinaryConverter.fromBinaryText(binaryText);
    }

    /**
     * Products xor operation for two binary texts. <br>
     */
    public static String xor(String first, String second) {
        IntFunction<Boolean> xor = (i) -> !(first.charAt(i) == second.charAt(i));
        Function<Boolean, String> toDigits = (state) -> state ? "1" : "0";
        BinaryOperator<String> sumDigits = (sum, digit) -> sum += digit;

        return IntStream.range(0, first.length())
                        .mapToObj(xor)
                        .map(toDigits)
                        .reduce(sumDigits)
                        .get();
    }

    /**
     * Convert text to binary text.
     */
    public static String toBinaryText(String text) {
        return BinaryConverter.toBinaryText(text);
    }

    /**
     * Convert binary text to text.
     */
    public static String fromBinaryTextToText(String binaryText) {
        return BinaryConverter.fromBinaryTextToText(binaryText);
    }

    /**
     * Inner private class encapsulate binary converters.
     */
    private static class BinaryConverter {
        /**
         * Convert number from Decimal system to Binary system.
         */
        public static String toBinaryNumber(int number) {
            var binaryString = Long.toBinaryString(number);
            return "0".repeat(MAX_LENGTH_FOR_BINARY - binaryString.length()) + binaryString;
        }

        /**
         * Convert number from Binary system to Decimal system.
         */
        public static String fromBinaryNumber(String binaryNumber) {
            return Integer.parseInt(binaryNumber.substring(0, MAX_LENGTH_FOR_BINARY), 2) + "";
        }

        /**
         * Convert binary text to number in Decimal system.
         */
        public static String fromBinaryText(String binaryText) {
            var binaryNumbers = splitBinaryNumbers(binaryText).toArray(String[]::new);
            var decimalDigits = Arrays.stream(binaryNumbers).map(binaryNumber -> fromBinaryNumber(binaryNumber));
            return decimalDigits.reduce((decimalNumber, digit) -> decimalNumber += digit).get();
        }

        /**
         * Convert text to binary text.
         */
        public static String toBinaryText(String text) {
            Function<String, Character> fromStringToChar = (str) -> str.charAt(0);
            Function<Character, Integer> fromCharToAsciiCode = (ch) -> AsciiRussianConverter.toAsciiCode(ch);
            Function<Integer, String> fromAsciiCodeToBinaryCode = (asciiCode) -> toBinaryNumber(asciiCode);
            BinaryOperator<String> sumBinaryCodes = (sum, code) -> sum += code;


            return Arrays.stream(text.split(""))
                    .map(fromStringToChar)
                    .map(fromCharToAsciiCode)
                    .map(fromAsciiCodeToBinaryCode)
                    .reduce(sumBinaryCodes)
                    .get();
        }

        /**
         * Convert binary text to text.
         */
        public static String fromBinaryTextToText(String binaryText) {
            Function<String, Integer> fromBinaryCodeToAsciiCode = (binaryCode) -> Integer.valueOf(BinaryUtils.fromBinaryTextToNumber(binaryCode));
            Predicate<Integer> removeNullAsciiCode = (asciiCode) -> asciiCode != 0;
            Function<Integer, Character> fromAsciiCodeToChar = (code) -> AsciiRussianConverter.toSymbol(code);
            BinaryOperator<String> sumLetters = (sum, letter) -> sum += letter;

            return splitBinaryNumbers(binaryText)
                    .map(fromBinaryCodeToAsciiCode)
                    .filter(removeNullAsciiCode)
                    .map(fromAsciiCodeToChar)
                    .map(String::valueOf)
                    .reduce(sumLetters)
                    .get();
        }
    }
}
