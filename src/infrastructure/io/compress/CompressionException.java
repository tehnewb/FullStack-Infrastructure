package infrastructure.io.compress;

/**
 * CompressionException is a custom exception class that represents errors
 * related to compression and decompression operations.
 *
 * @author Albert Beaupre
 */
public class CompressionException extends RuntimeException {

    /**
     * Constructs a new CompressionException with no detail message.
     */
    public CompressionException() {
        super();
    }

    /**
     * Constructs a new CompressionException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public CompressionException(String message) {
        super(message);
    }

    /**
     * Constructs a new CompressionException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CompressionException with the specified cause and no detail message.
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public CompressionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new CompressionException with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or
     * disabled.
     *
     * @param message            The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause              The cause (which is saved for later retrieval by the getCause() method).
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable.
     */
    public CompressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

