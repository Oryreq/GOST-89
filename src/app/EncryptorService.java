package app;

import utils.BinaryUtils;
import utils.ArrayUtils;

import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Map.entry;

/**
 * EncryptionService realize encryption and decryption by GOST-89. <br>
 * @author Vsevolod Ashihmin.
 * @Date: 14.12.2023.
 */
public class EncryptorService {
    private record BlockPair(String L, String R){};

    /**
     * Standard block size.
     */
    private static final int STANDARD_BLOCK_SIZE = 64;

    /**
     * Standard half block size.
     */
    private static final int STANDARD_HALF_BLOCK_SIZE = STANDARD_BLOCK_SIZE / 2;

    /**
     * Standard key part size.
     */
    private static final int STANDARD_KEY_PART_SIZE = 32;

    /**
     * Standard mixing count. Usually choose 32 or 64.
     */
    private static final int STANDARD_MIXING_COUNT = 32;

    /**
     * Replacement table.
     */
    private static final Map<String, String> REPLACEMENT_TABLE = Map.ofEntries(
            entry("0000", "1011"),
            entry("0001", "1111"),
            entry("0010", "1110"),
            entry("0011", "0100"),
            entry("0100", "1101"),
            entry("0101", "1001"),
            entry("0110", "0010"),
            entry("0111", "1010"),
            entry("1000", "0011"),
            entry("1001", "0111"),
            entry("1010", "0001"),
            entry("1011", "1100"),
            entry("1100", "0000"),
            entry("1101", "0110"),
            entry("1110", "0101"),
            entry("1111", "1000")
    );

    /**
     * Encrypt message by GOST-89.
     * @param key encryption key, must have length = 32 symbols.
     * @return encrypted message.
     */
    public String encrypt(String message, String key) {
        String binaryMessage = BinaryUtils.toBinaryText(message);
        String[] binaryBlocks = splitToBinaryBlocks(binaryMessage);
        String[] keyParts = splitKeyToParts(BinaryUtils.toBinaryText(key));


        String[] encryptedBinaryBlocks = new String[binaryBlocks.length];
        for (int i = 0; i < binaryBlocks.length; i++) {
            var keyPart = getKeyPart(keyParts, i);
            encryptedBinaryBlocks[i] = encryptBinaryBlock(binaryBlocks[i], keyPart);
        }

        String encryptedMessage = ArrayUtils.reduceArray(encryptedBinaryBlocks);
        return encryptedMessage;
    }

    /**
     * Encrypt one binary block by key part.
     * @return encrypted binary block.
     */
    private String encryptBinaryBlock(String binaryBlock, String keyPart) {
        var pair = splitBlockOnTwoBlocks(binaryBlock);
        var L = pair.L;
        var R = pair.R;
        for (int i = 0; i < STANDARD_MIXING_COUNT; i++) {
            var transformedLeftBlock = transformLeftBlock(L, keyPart);
            R = BinaryUtils.xor(transformedLeftBlock, R);
            var V = R;
            R = L;
            L = V;
        }
        return L + R;
    }

    /**
     * Decrypt message by GOST-89.
     * @param key encryption key, must have length = 32 symbols.
     * @return decrypted message.
     */
    public String decrypt(String encryptedMessage, String key) {
        String[] binaryBlocks = splitToBinaryBlocks(encryptedMessage);
        String[] keyParts = splitKeyToParts(BinaryUtils.toBinaryText(key));

        String[] decryptedBinaryBlocks = new String[binaryBlocks.length];
        for (int i = binaryBlocks.length - 1; i >= 0; i--) {
            var keyPart = getKeyPart(keyParts, i);
            decryptedBinaryBlocks[i] = decryptBinaryBlock(binaryBlocks[i], keyPart);
        }

        String decryptedText = ArrayUtils.reduceArray(decryptedBinaryBlocks);
        return BinaryUtils.fromBinaryTextToText(decryptedText);
    }

    /**
     * Decrypt one binary block by key part.
     * @return decrypted binary block.
     */
    private String decryptBinaryBlock(String binaryBlock, String keyPart) {
        var pair = splitBlockOnTwoBlocks(binaryBlock);
        var L = pair.L;
        var R = pair.R;
        for (int i = 0; i < STANDARD_MIXING_COUNT; i++) {
            var V = R;
            R = L;
            L = V;
            var transformedLeftBlock = transformLeftBlock(L, keyPart);
            R = BinaryUtils.xor(R, transformedLeftBlock);
        }
        return L + R;
    }

    /**
     * Get key part by mixing iteration.
     * @return key part.
     */
    private String getKeyPart(String[] keyParts, int iterationNumber) {
        int i = iterationNumber < 25 ? iterationNumber % 8 : (32 - (iterationNumber - 1)) % 8;
        return keyParts[i];
    }

    /**
     * Split binary text to binary blocks by standard block size.
     */
    private String[] splitToBinaryBlocks(String binaryText) {
        return ArrayUtils.splitStringToParts(binaryText, STANDARD_BLOCK_SIZE);
    }

    /**
     * Split key to part by standard key part size.
     */
    private String[] splitKeyToParts(String key) {
        return ArrayUtils.splitStringToParts(key, STANDARD_KEY_PART_SIZE);
    }

    /**
     * Split binary blocks to left and right parts with equal length.
     */
    private BlockPair splitBlockOnTwoBlocks(String binaryBlock) {
        var twoBlocks = ArrayUtils.splitStringToParts(binaryBlock, STANDARD_HALF_BLOCK_SIZE);
        return new BlockPair(twoBlocks[0], twoBlocks[1]);
    }

    /**
     * Transform left binary block: <br>
     * 1) xor left binary block and key part; <br>
     * 2) replace bits in xored left binary block by Replacement table; <br>
     * 3) shift bits in replaced left binary block to 11 in left; <br>
     */
    private String transformLeftBlock(String leftBlock, String keyPart) {
        String xoredLeftBlock = BinaryUtils.xor(leftBlock, keyPart);
        String replacedLeftBlock = replaceBitsInLeftBlock(xoredLeftBlock);
        String shiftedLeftBlock = shiftBitsInLeftBlock(new StringBuilder(replacedLeftBlock));
        return shiftedLeftBlock;
    }

    /**
     * Replace block bits by Replacement table.
     */
    private String replaceBitsInLeftBlock(String leftBlock) {
        int substringLength = 4; // <- we replace it each 4 digits.
        StringBuilder replacedLeftBlock = new StringBuilder();
        for (int i = 0; i < leftBlock.length(); i += substringLength) {
            String substring = leftBlock.substring(i, i + substringLength);
            String replacedSubstring = REPLACEMENT_TABLE.get(substring);
            replacedLeftBlock.append(replacedSubstring);
        }
        return replacedLeftBlock.toString();
    }

    /**
     * Shift 11 bits in block to left side.
     */
    private String shiftBitsInLeftBlock(StringBuilder leftBlock) {
        int shiftLength = 11; // <- we shift back 11 bits.
        String shiftBits = leftBlock.substring(0, shiftLength);
        leftBlock.delete(0, shiftLength);
        leftBlock.append(shiftBits);
        return leftBlock.toString();
    }
}
