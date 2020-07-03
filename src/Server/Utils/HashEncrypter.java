package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashEncrypter {
    public String encryptString (String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashtext = new StringBuilder(no.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
