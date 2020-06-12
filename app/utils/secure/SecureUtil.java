package utils.secure;

import org.jasypt.commons.CommonUtils;
import org.jasypt.digest.StandardStringDigester;

import javax.inject.Singleton;

/**
 * Util class for secure operations
 */

@Singleton
public class SecureUtil {

    /**
     * Ecrypts the user password and return the digest after applying algorithm(SHA-512), salt and iteration.
     *
     * @param username string to encrypt username
     * @param credential string to encrypt credential
     * @return String digest
     */
    public String encrypt(String username, String credential) {

        StandardStringDigester digester = new StandardStringDigester();
        digester.setAlgorithm("SHA-512");
        digester.setSaltSizeBytes(0);
        digester.setIterations(1);
        digester.setStringOutputType(CommonUtils.STRING_OUTPUT_TYPE_HEXADECIMAL);
        return digester.digest(username + "&" + credential).toLowerCase();

    }
}
