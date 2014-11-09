package fileManipulation;

import javax.xml.bind.DatatypeConverter;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Provides static methods to encrypt or decrypt a message.
 */
public class Encrypt {
	/**
	 * Takes a string and a pass-phrase and applies a XOR to the
	 * characters of each, then returns a Base64 formatted string.
	 * <p>
	 * Influenced by the answer found 
	 * <a href=http://stackoverflow.com/questions/13641563/xor-cipher-in-java-php-different-results>
	 * here</a>
	 * @param message the String to be en/decrypted
	 * @param cipher the pass phrase to encrypt or decrypt message with
	 * @return the encrypted message
	 */
	public static String encrypt(String message, String cipher) {
		byte[] newTextArray = new byte[message.length()];
		char[] textArray = message.toCharArray();
		char[] cipherArray = cipher.toCharArray();
		int ciphLen = cipherArray.length;

		// standard XOR encrypt
		for (int i = 0; i < textArray.length; i++) {
			int target = i % ciphLen;
			newTextArray[i] =
					(byte)(textArray[i] ^ cipherArray[target]);
		}
		
		// convert to Base64 so it's ASCII
		String newMessage =
				DatatypeConverter.printBase64Binary(newTextArray);
		return newMessage;
	}

	/**
	 * Takes a message encrypted by encrypt() and a cipher and returns
	 * the plain text.
	 * Citation same as above.
	 * @param message
	 * @param cipher
	 * @return the decrypted message
	 */
	public static String decrypt(String message, String cipher) {
		// we receive Base64, so un-Base64 the message
		byte[] tempArray = DatatypeConverter.parseBase64Binary(message);
		String tempString = new String(tempArray);
		char[] textArray = tempString.toCharArray();

		char[] cipherArray = cipher.toCharArray();
		int ciphLen = cipherArray.length;

		byte[] newTextArray = new byte[textArray.length];

		// standard XOR decrypt
		for (int i = 0; i < textArray.length; i++) {
			int target = i % ciphLen;
			newTextArray[i] =
					(byte)(textArray[i] ^ cipherArray[target]);
		}

		String newMessage = new String(newTextArray);
		return newMessage;
	}

}
