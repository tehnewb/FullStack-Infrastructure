package infrastructure.gdx.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALMusic;
import com.badlogic.gdx.utils.GdxRuntimeException;
import javazoom.jl.decoder.*;

import java.io.ByteArrayInputStream;

/**
 * The `Mp3` class is a custom extension of LibGDX's `OpenALMusic` class, designed to play MP3 audio tileIndices
 * from a byte array. It utilizes the JavaZoom JLayer library to decode the MP3 tileIndices and OpenAL for audio playback.
 * This class provides methods for initializing, reading, and resetting the MP3 audio stream.
 */
public class Mp3 extends OpenALMusic {

    private final byte[] data;        // The raw MP3 audio tileIndices in a byte array.
    private Bitstream bitstream; // Bitstream used for reading the MP3 frames.
    private OutputBuffer outputBuffer; // Output buffer for decoded audio frames.
    private MP3Decoder decoder; // MP3 decoder for decoding MP3 frames.

    /**
     * Constructs an `Mp3` object with the provided MP3 audio tileIndices in a byte array.
     *
     * @param data The raw MP3 audio tileIndices in a byte array.
     */
    public Mp3(byte[] data) {
        super((OpenALLwjgl3Audio) Gdx.audio, null); // Initialize the parent class.
        try {
            this.data = data;
            this.bitstream = new Bitstream(new ByteArrayInputStream(data));
            this.decoder = new MP3Decoder();

            // Read the header of the first MP3 frame to initialize audio parameters.
            Header header = bitstream.readFrame();
            if (header == null) throw new GdxRuntimeException("Empty MP3");
            int channels = header.mode() == Header.SINGLE_CHANNEL ? 1 : 2;
            this.outputBuffer = new OutputBuffer(channels, false);
            this.decoder.setOutputBuffer(outputBuffer);
            this.setup(channels, header.getSampleRate());
        } catch (Exception e) {
            throw new GdxRuntimeException("Error while preloading MP3", e);
        }
    }

    /**
     * Reads audio tileIndices from the MP3 byte array and fills the provided buffer.
     *
     * @param buffer The byte array buffer to fill with audio tileIndices.
     * @return The total number of bytes read into the buffer.
     */
    @Override
    public int read(byte[] buffer) {
        try {
            boolean setup = bitstream == null;
            if (setup) {
                // Reinitialize the bitstream and decoder for subsequent reads.
                this.bitstream = new Bitstream(new ByteArrayInputStream(data));
                this.decoder = new MP3Decoder();

                // Read the header of the first MP3 frame to initialize audio parameters.
                Header header = bitstream.readFrame();
                if (header == null) throw new GdxRuntimeException("Empty MP3");
                int channels = header.mode() == Header.SINGLE_CHANNEL ? 1 : 2;
                this.outputBuffer = new OutputBuffer(channels, false);
                this.decoder.setOutputBuffer(outputBuffer);
                this.setup(channels, header.getSampleRate());
            }

            int totalLength = 0;
            int minRequiredLength = buffer.length - OutputBuffer.BUFFERSIZE * 2;

            // Read and decode MP3 frames until the buffer is sufficiently filled.
            while (totalLength <= minRequiredLength) {
                Header header = bitstream.readFrame();
                if (header == null) break;
                try {
                    decoder.decodeFrame(header, bitstream);
                } catch (Exception ignored) {
                }
                bitstream.closeFrame();

                int length = outputBuffer.reset();
                System.arraycopy(outputBuffer.getBuffer(), 0, buffer, totalLength, length);
                totalLength += length;
            }
            return totalLength;
        } catch (Throwable ex) {
            reset();
            throw new GdxRuntimeException("Error reading audio tileIndices.", ex);
        }
    }

    /**
     * Resets the MP3 decoding and closes the bitstream.
     */
    @Override
    public void reset() {
        if (bitstream == null) return;
        try {
            bitstream.close();
        } catch (BitstreamException ignored) {
        }
        bitstream = null;
    }
}
