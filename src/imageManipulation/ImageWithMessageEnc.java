package imageManipulation;

import java.io.File;

import fileManipulation.Encrypt;
import fileManipulation.OpenOrSave;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Objects of this class pull merged data from a bitmap file, extract the
 * encrypted message, then decrypt and save it to a text file.
 */
public class ImageWithMessageEnc extends ImageWithMessage {
	/**
	 * the pass phrase used to unlock the message
	 */
	private String cipher;

	public ImageWithMessageEnc(File imageFile, String message,
			String cipher) {
		this.message = message;
		this.imageFile = imageFile;
		this.cipher = cipher;
		System.out.println("New ImageWithMessageEnc");
		setup();
		process();
	}

	/**
	 * @see imageManipulation.ImageWithMessage#isEncrypted()
	 */
	@Override
	public boolean isEncrypted() {
		return false;
	}
	
	/**
	 * @see imageManipulation.ImageWithMessage#saveText(java.io.File)
	 */
	public void saveText(File saveFile){
		OpenOrSave.saveTextFile(message, saveFile);
	}

	/**
	 * Extracts the text from the image data and puts it in message;
	 * message needs to be decrypted.
	 * @see imageManipulation.Image#process()
	 */
	@Override
	protected void process() {
		super.process();
		message = Encrypt.decrypt(message, cipher);
	}
	
}


