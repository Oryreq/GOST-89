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
        String text = "��� ����������� ����� with english alphabet and some digits 0123456789 and super symbols {}.,:-";
        String key = "� ����� ����������� 32 ������� .";// <- ������ 32 �������

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("�������� �����: " + text);

        System.out.println("-----------------------------------------------------------------------------------------");

        var encryptedText = encryptor.encrypt(text, key);
        System.out.println("������������� �����: " + BinaryUtils.fromBinaryTextToText(encryptedText));

        System.out.println("-----------------------------------------------------------------------------------------");

        var decryptedText = encryptor.decrypt(encryptedText, key);
        System.out.println("�������������� �����: " + decryptedText);

        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Test 2.
     */
    private static void test2() {
        String text = """
                ����� �� � �����, ����� ������ ��������.
                �������, �����, ����� ���� ��� ������ ������ ��� ����� �������� ����.""";

        String key = "����, ������� ��������� � ������"; // <- ������ 32 �������

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("�������� �����: " + text);

        System.out.println("-----------------------------------------------------------------------------------------");

        var encryptedText = encryptor.encrypt(text, key);
        System.out.println("������������� �����: " + BinaryUtils.fromBinaryTextToText(encryptedText));

        System.out.println("-----------------------------------------------------------------------------------------");

        var decryptedText = encryptor.decrypt(encryptedText, key);
        System.out.println("�������������� �����: " + decryptedText);

        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
