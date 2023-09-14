package crypt;

/**
 * ISAACCipher is a Java implementation of the ISAAC (Indirection, Shift, Accumulate, Add, and Count) cipher,
 * which is a cryptographically secure pseudorandom number generator (PRNG) and can be used for encryption purposes.
 * <p>
 * The ISAAC algorithm is designed to produce a stream of pseudorandom integers. This class allows you to initialize
 * the cipher with a byte array key and then use it to encrypt data. It uses a combination of bitwise operations and
 * mixing functions to generate random numbers and perform data encryption.
 * <p>
 * Usage:
 *
 * <pre>
 * // Initialize the cipher with a 16-byte key
 * byte[] key = new byte[16];
 * ISAACCipher cipher = new ISAACCipher(key);
 * <p>
 * // Encrypt data using the cipher
 * byte[] data = ...; // Data to be encrypted
 * cipher.encrypt(data);
 * </pre>
 *
 * @author Albert Beaupre
 */
public class ISAACCipher {
    private static final int SIZE = 256;
    private int[] rands;
    private int randA;
    private int randB;
    private int randC;

    /**
     * Initializes an ISAAC cipher with the given key.
     *
     * @param key The key used for encryption.
     * @throws IllegalArgumentException If the key is null or has a length > 256 bytes
     */
    public ISAACCipher(byte[] key) {
        if (key == null || key.length > 256) {
            throw new IllegalArgumentException("Key must be <= 256 bytes long");
        }

        rands = new int[SIZE];

        // Initialize the random state array with zeros.
        for (int i = 0; i < SIZE; i++) {
            rands[i] = 0;
        }

        int[] initState = new int[8];

        // Convert the key into an array of 32-bit integers.
        for (int i = 0; i < 8; i++) {
            initState[i] = byteArrayToInt(key, i * 4);
        }

        // Initialize the cipher with the provided key.
        initialize(initState);
    }

    /**
     * Initializes the ISAAC cipher with the given state.
     *
     * @param initState The initial state of the cipher.
     */
    private void initialize(int[] initState) {
        randA = 0;
        randB = 0;
        randC = 0;
        int[] mm = new int[8];
        int x, y, z, w, v;

        // Copy the initial state to the mm array.
        for (int i = 0; i < 8; i++) {
            mm[i] = initState[i];
        }

        int[] randPool = new int[256];

        // Initialize the randPool array with values 0x1000000 to 0x10000FF.
        for (int i = 0; i < 256; i++) {
            randPool[i] = 0x1000000 + i;
        }

        // Mix the values in randPool.
        for (int i = 0; i < 4; i++) {
            mix(randPool);
        }

        // Initialize the rands array with values from mm.
        for (int i = 0; i < SIZE; i += 8) {
            for (int j = 0; j < 8; j++) {
                mm[j] += randPool[i + j];
            }
            mix(mm);

            for (int j = 0; j < 8; j++) {
                rands[i + j] = mm[j];
            }
        }

        // Mix the values in rands array.
        for (int i = 0; i < 256; i += 8) {
            for (int j = 0; j < 8; j++) {
                mm[j] += rands[i + j];
            }
            mix(mm);

            for (int j = 0; j < 8; j++) {
                rands[i + j] = mm[j];
            }
        }

        advance(); // Advance the internal state.
    }

    /**
     * Advances the internal state of the ISAAC cipher.
     */
    private void advance() {
        randB += (++randC);

        for (int i = 0; i < SIZE; i++) {
            int x = rands[i];
            switch (i & 3) {
                case 0:
                    randA ^= randA << 13;
                    break;
                case 1:
                    randA ^= randA >>> 6;
                    break;
                case 2:
                    randA ^= randA << 2;
                    break;
                case 3:
                    randA ^= randA >>> 16;
                    break;
            }
            randA += rands[(i + 128) & 0xFF];
            int y = rands[i] = rands[(x >> 2) & 0xFF] + randA + randB;
        }
    }

    /**
     * Encrypts the given data using the ISAAC cipher.
     *
     * @param data The data to be encrypted in place.
     */
    public void encrypt(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (i % 4 == 0) {
                advance();
            }
            data[i] ^= (byte) (rands[i % 4 + 252] >> 24);
        }
    }

    /**
     * Converts a byte array to a 32-bit integer starting from the specified offset.
     *
     * @param bytes  The byte array.
     * @param offset The starting offset.
     * @return The 32-bit integer.
     */
    private int byteArrayToInt(byte[] bytes, int offset) {
        if (offset < 0 || offset + 3 >= bytes.length) {
            throw new IllegalArgumentException("Invalid offset or insufficient bytes in the array");
        }
        return (bytes[offset] << 24) | ((bytes[offset + 1] & 0xFF) << 16) | ((bytes[offset + 2] & 0xFF) << 8) | (bytes[offset + 3] & 0xFF);
    }

    /**
     * Mixes the values in the provided array.
     *
     * @param mm The array to be mixed.
     */
    private void mix(int[] mm) {
        randA ^= randA << 13;
        randA += mm[(randC) & 7];
        randC += 1;
        randB ^= randB >>> 6;
        randB += mm[(randA >> 2) & 7];
        randA += mm[(randB) & 7];
        randB ^= randB << 2;
        randB += mm[(randA >> 16) & 7];
        randA ^= randA >>> 16;
        randA += mm[(randB >> 8) & 7];
        randB ^= randB >>> 8;
        randB += mm[(randA) & 7];

        for (int i = 0; i < 8; i++) {
            mm[i] = mm[i] + mm[(i + 1) & 7] + randA;
        }
    }
}
