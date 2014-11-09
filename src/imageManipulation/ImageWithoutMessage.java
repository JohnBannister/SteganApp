package imageManipulation;

import java.io.File;
import java.io.IOException;

import fileManipulation.OpenOrSave;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * This is the master class to create images with messages merged
 * into them. Subclasses will extend to be either encrypted or not
 * encrypted.
 * 
 * Objects created from the child classes have methods to save the
 * merged data to a bitmap file.
 */
public abstract class ImageWithoutMessage extends Image {

	/**
	 * @param imageFile the image file
	 * @param message the message
	 */
	public ImageWithoutMessage(File imageFile, String message) {
		super(imageFile, message);
	}

	/**
	 * @see imageManipulation.Image#isEncrypted()
	 */
	public abstract boolean isEncrypted();

	/**
	 * Tells the image to save itself to a file.
	 * Passes this Image to openSave.saveImage()
	 */
	public void saveImage() {
		OpenOrSave.saveImage(this);
	}

	/**
	 * The "traffic cop" of the creation of the object. 
	 * 1. Calls openSave.openImage() to fill the array of bytes from
	 * the image file. 
	 * 2. Creates the array of chars from the message by calling
	 * breakoutMessage(). 
	 * 3. Locates the end of the header information in the image by
	 * calling getHeader(). 
	 * 4. Processes the message into imageryData by calling process()
	 * used by constructor. 
	 */
	protected void setup() {
		try {
			imageBytes = OpenOrSave.openImage(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("I do not have a valid image file;"
					+ " exiting.");
			System.exit(0);
		}

		breakoutMessage();
		getHeader();
		process();
	}

	/**
	 * Populate messageChars with the individual characters of the message.
	 * Used by setup().
	 */
	protected void breakoutMessage() {
		int theLength = message.length();
		messageChars = new char[theLength];
		for (int i = 0; i < theLength; i++) {
			messageChars[i] = message.charAt(i);
		}
	}

	/**
	 * Put the individual elements of the messageChars[] into the alpha
	 * channel of the imageBytes[], i.e. after header bytes, every fourth
	 * byte can hold a character without affecting the look of the image.
	 * Write the message length into an unused byte of the bitmap.
	 * Used by setup().
	 */
	protected void process() {
		//headerEnd + 1 is the start of the alpha pixels
		for (int i = headerEnd + 1, j = 0; j < messageChars.length;
				i += 4, j++) {
			imageBytes[i] = (byte)messageChars[j];

		}
		// 4th byte unused in bmp file. We put the length of the message
		// here. Meant to be a small message so the byte isn't overflowed.
		imageBytes[3] = (byte)messageChars.length;
	}

	/**
	 * Allows the image to be queried for its image data.
	 * @return the array of bytes that make up the image after processing
	 */
	public byte[] getImage() {
		return imageBytes;
	}
}
