/**
 * 
 */
package imageManipulation;

import fileManipulation.*;
import java.io.File;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Objects of this type creates a new bitmap file with an encrypted
 * message merged and saves it to disk.
 */
public class ImageWithoutMessageEncrypted extends ImageWithoutMessage {

	/**
	 * Encrypts the message and calls super().
	 * @param imageFile the image file
	 * @param message the message to be encrypted and merged into the
	 * image file
	 */
	public ImageWithoutMessageEncrypted(File imageFile, String message,
			String cipher) {
		super(imageFile, Encrypt.encrypt(message, cipher));
	}

	/**
	 * @see imageManipulation.ImageWithoutMessage#isEncrypted()
	 */
	@Override
	public boolean isEncrypted() {
		return false;
	}

}
