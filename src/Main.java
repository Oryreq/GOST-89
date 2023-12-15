import app.EncryptorService;
import utils.BinaryUtils;

/**
 * This program was written for discipline "Information Security" in UdSU.
 * @author Vsevolod Ashihmin.
 * @Date: 14.12.2023.
 */
public class Main {

    private static final EncryptorService encryptor = new EncryptorService();

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * Test 1.
     */
    private static void test1() {
        String text = "Это проверочный текст with english alphabet and some digits 0123456789 and super symbols {}.,:-";
        String key = "в ключе обязательно 32 символа .";// <- длиной 32 символа

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("Исходный текст: " + text);

        System.out.println("-----------------------------------------------------------------------------------------");

        var encryptedText = encryptor.encrypt(text, key);
        System.out.println("Зашифрованный текст: " + BinaryUtils.fromBinaryTextToText(encryptedText));

        System.out.println("-----------------------------------------------------------------------------------------");

        var decryptedText = encryptor.decrypt(encryptedText, key);
        System.out.println("Расшифрованный текст: " + decryptedText);

        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Test 2.
     */
    private static void test2() {
        String text = """
                Хотел бы я знать, зачем звезды светятся.
                Наверно, затем, чтобы рано или поздно каждый мог вновь отыскать свою.""";

        String key = "ключ, впервые пришедший в голову"; // <- длиной 32 символа

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("Исходный текст: " + text);

        System.out.println("-----------------------------------------------------------------------------------------");

        var encryptedText = encryptor.encrypt(text, key);
        System.out.println("Зашифрованный текст: " + BinaryUtils.fromBinaryTextToText(encryptedText));

        System.out.println("-----------------------------------------------------------------------------------------");

        var decryptedText = encryptor.decrypt(encryptedText, key);
        System.out.println("Расшифрованный текст: " + decryptedText);

        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
