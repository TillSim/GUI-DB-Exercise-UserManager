package helpers;

import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class EncryptionHandler {

    /**
     * creates Hash from password String
     * @param password String
     * @return String
     */
    public static String bcryptHash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * compares password String with hashed String
     * @param password String
     * @param hash String
     * @return boolean
     */
    public static boolean comparePassword(String password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }
}
