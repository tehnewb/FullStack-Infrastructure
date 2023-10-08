package infrastructure.io.crypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 * This class represents an implementation of the EncryptionStrategy interface using the RSA encryption algorithm.
 * RSA (Rivest-Shamir-Adleman) is a widely used public-key cryptosystem for secure data transmission.
 */
public class RSA implements EncryptionStrategy {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    /**
     * Constructs an RSA encryption strategy with the given public and private keys.
     *
     * @param publicKey  The public key used for encryption.
     * @param privateKey The private key used for decryption.
     */
    public RSA(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

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

    /**
     * Gets the public key associated with this RSA encryption strategy.
     *
     * @return The public key used for encryption.
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Gets the private key associated with this RSA encryption strategy.
     *
     * @return The private key used for decryption.
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
