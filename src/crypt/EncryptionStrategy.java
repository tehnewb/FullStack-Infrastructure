package crypt;

import javax.crypto.Cipher;
import java.security.*;

/**
 * An interface that defines the contract for encryption and decryption strategies.
 *
 * @author Albert Beaupre
 */
public interface EncryptionStrategy {

    /**
     * Encrypts the given data.
     *
     * @param data The data to be encrypted.
     * @return The encrypted data as a byte array.
     */
    byte[] encrypt(byte[] data);

    /**
     * Decrypts the given data.
     *
     * @param data The data to be decrypted.
     * @return The decrypted data as a byte array.
     */
    byte[] decrypt(byte[] data);

    /**
     * An implementation of the EncryptionStrategy interface using the RSA encryption algorithm.
     */
    record RSA(PublicKey publicKey, PrivateKey privateKey) implements EncryptionStrategy {

        /**
         * Generates an RSA key pair with the specified key size.
         *
         * @param keySize The size of the RSA key pair to generate.
         * @return A KeyPair containing the generated public and private keys.
         * @throws RuntimeException if key pair generation fails.
         */
        public static KeyPair generateKeyPair(int keySize) {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(keySize);
                return keyGen.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Cannot generate RSA KeyPair", e);
            }
        }

        /**
         * Encrypts data using the RSA algorithm with the provided public key.
         *
         * @param data The data to be encrypted.
         * @return The encrypted data as a byte array.
         * @throws RuntimeException if encryption fails.
         */
        @Override
        public byte[] encrypt(byte[] data) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(data);
            } catch (Exception e) {
                throw new RuntimeException("Cannot encrypt using RSA algorithm", e);
            }
        }

        /**
         * Decrypts data using the RSA algorithm with the provided private key.
         *
         * @param data The data to be decrypted.
         * @return The decrypted data as a byte array.
         * @throws RuntimeException if decryption fails.
         */
        @Override
        public byte[] decrypt(byte[] data) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(data);
            } catch (Exception e) {
                throw new RuntimeException("Cannot decrypt using RSA algorithm", e);
            }
        }
    }


}
