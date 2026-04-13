import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecureMessageApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
        try {
                // Accepts user input
                System.out.print("Enter a message: ");
                String message = scanner.nextLine();

                // Generate a hash of original message
                String ogHash = hash(message);
                System.out.println("Original Hash: " + ogHash);

                // Generate the secret key
                SecretKey secretKey = getKey();

                // Encrypt message
                String encryptedMessage = encrypt(message, secretKey);
                System.out.println("Encrypted Message: " + encryptedMessage);

                // Decrypt message
                String decryptedMessage = decrypt(encryptedMessage, secretKey);
                System.out.println("Decrypted Message: " + decryptedMessage);

                // Hash decrypted message
                String decryptedHash = hash(decryptedMessage);
                System.out.println("Decrypted Hash: " + decryptedHash);

                if (ogHash.equals(decryptedHash)) { // if 'ogHash' is equal to 'decryptedHash'
                    System.out.println("Integrity Verified: Hashes match.");
                } else {
                    System.out.println("Integrity Check Failed: Hashes do not match.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Would you like to continue? Y/N >>> ");
            if (scanner.nextLine().equals("N")) {
                System.out.println("Closing app...");
                break;
            } else if (!"Y".equals(scanner.nextLine()) || !"N".equals(scanner.nextLine())) {
                System.out.println("Not a valid response. Resetting...");
            }
        }
    }

    // SHA-256 hashing
    public static String hash(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) { // For each 'byte' in 'hashBytes'
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    // AES key generation
    public static SecretKey getKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    // AES encryption
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES decryption
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}