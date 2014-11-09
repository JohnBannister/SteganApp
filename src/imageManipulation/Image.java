package imageManipulation;

import java.io.*;


/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * This is the master Image class. From this class one of four types will
 * be derived:
 * 1. an object that takes its data from a bitmap file and merges an
 * unencrypted message
 * 2. an object that takes its data from a bitmap file and merges an
 * encrypted message
 * 3. an object that takes its data from a bitmap file that already has
 * an unencrypted message merged and extracts that message
 * 4. an object that takes its data from a bitmap file that already has
 * and encrypted message merged and extracts and decrypts that message.
 * 
 * Every object has methods to save either a text file or a processed
 * bitmap file.
 */
public abstract class Image {
	/**
	 * the text that will be processed into the image or extracted
	 */
	protected String message;
	
	/**
	 * the image file on the file system that will be processed
	 */
	protected File imageFile;
	
	/**
	 * the individual bytes of the entire image file in an array for
	 * processing
	 */
	protected byte[] imageBytes;
	
	/**
	 * the location of the last byte of the header portion of the image
	 * file
	 */
	protected int headerEnd;
	
	/**
	 * the message in an array of characters for insertion or after
	 * extraction
	 */
	protected char[] messageChars;

	public Image() {}
	/**
	 * Constructors setup the object by calling helper routine setup().
	 * @param imageFile the image to be processed
	 * @param message the message to be processed into the image
	 */
	public Image(File imageFile, String message) {
		this.message = message;
		this.imageFile = imageFile;

		setup();
	}
	/**
	 * For an object that already has a message embedded.
	 * @param imageFile the image to be processed
	 */
	public Image(File imageFile) {
		this(imageFile,"");
	}

	/**
	 * Depending on child object type will call helper methods to setup
	 * the object.
	 * Used by constructor
	 */
	protected abstract void setup();
	
	/**
	 * @return returns true if this object's message is encrypted
	 */
	public abstract boolean isEncrypted();
	

	/**
	 * Depending on object type, will either insert or extract the
	 * message from the image data.
	 * Used by setup().
	 */
	protected abstract void process();
	

	/**
	 * Reads the header of the image and updates headerEnd with
	 * the location of the last byte of the header.
	 * File format from
	 * <a href=http://en.wikipedia.org/wiki/BMP_file_format>here</a>.
	 */
	protected void getHeader() {
		headerEnd = readBytes(imageBytes, 10, 4);
	}
	
    /**
     * @param theBytes the array of bytes to read from
     * @param startPos the first byte to read
     * @param count the number of bytes to read
     * @return the bytes as an integer
     */
    private int readBytes(byte[] theBytes, int startPos, int count) {
        int result = strip(theBytes[startPos]);
        for (int i = 1; i < count; i++) {
            result = result | strip(theBytes[startPos + i]) << (8 * i);
        }
        return result;
    }

    /**
     * return the least significant 8 bits of an integer with
     * all other bits &ed out. Finding that spurious bits are
     * being inserted when promoting a byte to an int.
     */
    private int strip(int toStrip) {
        return toStrip & 0xFF;
    }
}
