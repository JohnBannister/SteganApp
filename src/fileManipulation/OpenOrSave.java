package fileManipulation;

import imageManipulation.*;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Provides static methods to open and save image files, open, save and
 * read text files and get a pass-phrase.
 * Used by recoverImage and createImage and the Image objects.
 */
public class OpenOrSave {
	/**
	 * a file chooser for open and save methods
	 */
	private static JFileChooser chooseFile = new JFileChooser();;

	/**
	 * Saves a string to a text file.
	 * Used by recoverImage.saveFile().
	 * @param message The string to be saved
	 * @param fileName The File to save the string to
	 */
	public static void saveTextFile(String message, File fileName) {
		FileWriter theFileWriter;
		try {
			theFileWriter = new FileWriter(fileName);
			theFileWriter.write(message);
			theFileWriter.close();
		} catch (IOException e) {
			System.out.println("File not chosen.");
		}
	}

	/**
	 * Uses a file opener dialog to return an opened file.
	 * @return the file to be opened
	 */
	public static File open() {
		File theOpenedFile = null;
		int returnedStatus = 0;
		returnedStatus = chooseFile.showOpenDialog(null);

		if (returnedStatus == JFileChooser.APPROVE_OPTION) {
			theOpenedFile = chooseFile.getSelectedFile();
		}

		return theOpenedFile;
	}

	/**
	 * Uses a file save dialog to return a file to save.
	 * @return a file handle to the file that will be saved
	 */
	public static File save() {
		File theSaveFile = null;
		int returnedStatus = chooseFile.showSaveDialog(null);
		if (returnedStatus == JFileChooser.APPROVE_OPTION) {
			theSaveFile = chooseFile.getSelectedFile();
		}

		return theSaveFile;
	}

	/**
	 * Saves an image file to disk from an array of bytes.
	 * Borrowed liberally from
	 * <a href=http://stackoverflow.com/questions/2138913/how-to-store-a-byte-array-as-an-image-file-on-disk>here</a>
	 * @param theImage the image object that is saved to the file
	 */
	public static void saveImage(ImageWithoutMessage theImage) {
		File saveTo = save();
		byte[] theImageryData = theImage.getImage();

		try {
			OutputStream outputStream = new FileOutputStream(saveTo);
			outputStream.write(theImageryData);
			outputStream.flush();
			outputStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error writing to file. Aborting!");
			System.exit(0);
		}
	}

	/**
	 * Reads an image file into an array of bytes.
	 * Borrows liberally from
	 * <a href=http://docs.oracle.com/javase/6/docs/api/java/io/DataInputStream.html>here</a>
	 * 
	 * @param fileName the bitmap file to open
	 * @return each binary byte in the image as an array
	 * @throws IOException
	 */
	public static byte[] openImage(File fileName) throws IOException {
		long sizeOfFile = fileName.length();

		byte[] theByteArray = new byte[(int)sizeOfFile]; //could overflow

		// this messy expression is to accommodate Java SE6
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream(fileName)));
		
		for (int j = 0; j < sizeOfFile; j++) {
			theByteArray[j] = in.readByte();
		}
		in.close();
		return theByteArray;
	}

	/**
	 * Reads a line from a text file and returns it as a String.
	 * Borrows liberally from
	 * <a href=http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file>here</a>
	 * 
	 * @param theTextFile the file to read from
	 * @return the first line of the file
	 */
	public static String readText(File theTextFile) {
		BufferedReader reader;
		StringBuilder builder = new StringBuilder();
		String theText = "";

		try {
			reader = new BufferedReader(
					new FileReader(theTextFile));
			theText = reader.readLine();
			builder.append(theText);
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("No such file.");
		} catch (IOException e) {
			System.out.println("Bad file.");
		} 
		
		return theText;
	}

	/**
	 * Opens a dialog box to enter the pass-phrase.
	 * @return the pass-phrase
	 */
	public static String getPassPhrase() {
		String inputValue = JOptionPane.showInputDialog(
				"Enter the pass-phrase");
		return inputValue;
	}
}
