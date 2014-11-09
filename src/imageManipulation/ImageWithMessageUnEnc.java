package imageManipulation;

import java.io.File;

import fileManipulation.OpenOrSave;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Objects of this class pull merged data from a bitmap file, extract the
 * unencrypted message, then save it to a text file.
 */
public class ImageWithMessageUnEnc extends ImageWithMessage {

	public ImageWithMessageUnEnc(File imageFile) {
		super(imageFile);
	}

	/**
	 * @see imageManipulation.ImageWithMessage#isEncrypted()
	 */
	@Override
	public boolean isEncrypted() {
		return false;
	}
	
	/**
	 * Saves the text file.
	 * @see imageManipulation.ImageWithMessage#saveText(java.io.File)
	 */
	public void saveText(File saveFile){
			OpenOrSave.saveTextFile(message, saveFile);
		}


}
