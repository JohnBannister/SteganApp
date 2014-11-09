/**
 * 
 */
package imageManipulation;

import java.io.File;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Objects of this type create a new bitmap file with a message merged
 * and saves it to disk.
 */
public class ImageWithoutMessageUnencrypted extends ImageWithoutMessage {

	/**
	 * calls super()
	 * @param imageFile the image file
	 * @param message the message to be merged into the image file
	 */
	public ImageWithoutMessageUnencrypted(File imageFile, String message) {
		super(imageFile, message);
	}

	/**
	 * @see imageManipulation.ImageWithoutMessage#isEncrypted()
	 */
	@Override
	public boolean isEncrypted() {
		return false;
	}

}
