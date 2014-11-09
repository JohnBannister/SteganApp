/**
 * 
 */
package imageManipulation;

import java.io.File;
import java.io.IOException;

import fileManipulation.OpenOrSave;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * This is the master class for images that already have messages merged
 * into them. Subclasses will extend to be either encrypted or not
 * encrypted.
 * 
 * Objects created from the child classes have methods to save the
 * embedded message to a text file.
 */
public abstract class ImageWithMessage extends Image {

	public ImageWithMessage() {}
	/**
	 * @param imageFile the image to be processed
	 */
	public ImageWithMessage(File imageFile) {
		super(imageFile);
	}

	/**
	 * The "traffic cop" of the creation of the object. 
	 * 1. Calls openSave.openImage() to fill the array of bytes from
	 * the image file. 
	 * 2. Locates the end of the header information in the image by
	 * calling getHeader(). 
	 * 3. Calls process to extract the message. 
	 *
	 * @see imageManipulation.Image#setup()
	 * 
	 */
	@Override
	protected void setup() {
		try {
			imageBytes = OpenOrSave.openImage(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("I do not have a valid image file;"
					+ " exiting.");
			System.exit(0);
		}
		getHeader();
		process();
	}

	/**
	 * @see imageManipulation.Image#isEncrypted()
	 */
	public abstract boolean isEncrypted();

	/**
	 * Tells the object to save message to a text file.
	 * @param saveFile the file to save the text to
	 */
	public abstract void saveText(File saveFile);
	
	/**
	 * Extracts the text from the image data and puts it in message.
	 * @see imageManipulation.Image#process()
	 */
	@Override
	protected void process() {
		int messageLength = imageBytes[3]; // 3rd byte holds the size
		messageChars = new char[messageLength];

		for (int i = headerEnd + 1, j = 0; j < messageLength;
				i += 4, j++) {
			messageChars[j] = (char)imageBytes[i];
		}
		message = String.valueOf(messageChars);
	}
}
