package userInterface;

import fileManipulation.*;
import imageManipulation.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Creates the window to recover a message from an image file and save
 * the message. Creates an object of type ImageWithMessageEnc or
 * ImageWithMessageUnEnc, depending on whether encryption is set, and
 * manipulates them to save the text file.
 */
@SuppressWarnings("serial")
public class RecoverMessage extends JFrame implements ItemListener,
ActionListener {
	/**
	 * the bitmap file that the message will be recovered from
	 */
	private File theImageFile;

	/**
	 * a flag for text decryption
	 */
	private boolean textDecrypt;

	/**
	 * the pass phrase used to decrypt a message, if applicable
	 */
	private String passPhrase;

	/**
	 * the button to save the message
	 */
	private JButton saveButton;

	/**
	 * the button to process the message
	 */
	private JButton goButton;

	/**
	 * the text box that will show the image file chosen
	 */
	private JTextField theImagePath;
	
	/**
	 * the check box that tracks the textDecrypt encryption flag
	 */
	private JCheckBox decryptText;
	
	/**
	 * the object the image is loaded into
	 */
	private ImageWithMessage theImageObject;

	/**
	 * Set up window and controls.
	 */
	public RecoverMessage() {
		reset();
		
		setLayout(new FlowLayout());

		JPanel theImageControls = openImageControls();
		add(theImageControls);

		decryptText = new JCheckBox("Decrypt Text");
		decryptText.setSelected(textDecrypt);
		decryptText.addItemListener(this);
		decryptText.setEnabled(false);
		add(decryptText);

		JPanel theButtons = createButtons();
		add(theButtons);

		validate();
		pack();

		setSize(new Dimension(500,120));
		setLocationRelativeTo(null);
	}

	/**
	 * Set up the buttons along the bottom of the window.
	 * Used by constructor.
	 * @return the JPanel containing the buttons
	 */
	private JPanel createButtons() {
		JPanel theButtons = new JPanel();

		goButton = new JButton("Go!");
		goButton.setActionCommand("Go");
		goButton.addActionListener(this);
		goButton.setEnabled(false);
		theButtons.add(goButton);

		JButton canxButton = new JButton("Cancel");
		canxButton.setActionCommand("Cancel");
		canxButton.addActionListener(this);
		theButtons.add(canxButton);

		saveButton = new JButton("Save");
		saveButton.setActionCommand("Save");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		theButtons.add(saveButton);

		return theButtons;
	}

	/**
	 * Set up the button and text box for the text file.
	 * Used by constructor.
	 * @return the JPanel containing the controls to open an image
	 */
	private JPanel openImageControls() {
		JButton theImageButton = new JButton("Choose Image");
		theImageButton.setActionCommand("pickImage");
		theImageButton.addActionListener(this);

		theImagePath = new JTextField("Choose an image file",25);
		theImagePath.setEditable(false);
		theImagePath.setSize(400, 35);

		JPanel theImageControls = new JPanel();

		theImageControls.add(theImageButton);
		theImageControls.add(theImagePath);

		return theImageControls;
	}

	/**
	 * Check if the check box has been changed.
	 * Updates textDecrypt.
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			passPhrase = OpenOrSave.getPassPhrase();
			if (passPhrase != null) {
				textDecrypt = true;
				System.out.println("Text will be decrypted");			
			} else {
				textDecrypt = false;
				decryptText.setSelected(false);
			}
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			textDecrypt = false;
			System.out.println("Text will not be decrypted");
		}
	}

	/**
	 * Check which button is pushed and calls appropriate routines.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionString = 
				((JButton) e.getSource()).getActionCommand();
		if (actionString == "Go") createText();
		else if (actionString == "Cancel") {
			fullReset();
			setVisible(false);
		}
		else if (actionString == "Save") saveText();
		else if (actionString == "pickImage") pickImage();
	}

	/**
	 * Save the recovered text to a file.
	 * Uses openSave.saveTextFile() and openSave.save() static methods.
	 * Used by the save button.
	 */
	private void saveText() {
		File theSaveFile = OpenOrSave.save();
		theImageObject.saveText(theSaveFile);
		this.setVisible(false);
	}

	/**
	 * Create an image and tell it to extract the text, optionally
	 * decrypting. Will create one of: 
	 * ImageWithMessageEnc or ImageWithMessageUnEnc depending on whether
	 * encryption is set.
	 * Used by the go button.
	 * Polls textDecrypt.
	 * Enables saveButton.
	 * Disables goButton and decryptText check box.
	 */
	private void createText() {
		if (textDecrypt == true) {
			theImageObject = new ImageWithMessageEnc(theImageFile, "", passPhrase);	
		} else {
			theImageObject = new ImageWithMessageUnEnc(theImageFile);
		}
		saveButton.setEnabled(true);
		goButton.setEnabled(false);
		decryptText.setEnabled(false);
		
	}

	/**
	 * Sets theImageFile to a file on the file system.
	 * Uses openSave.open() static method.
	 * Used by theImageButton.
	 * Updates theImagePath text value.
	 * May enable goButton.
	 */
	private void pickImage() {
		theImageFile = OpenOrSave.open();
		if (theImageFile != null) {
			String theFileName = theImageFile.getName();
			theImagePath.setText(theFileName);
			goButton.setEnabled(true);
		}
	}
	
	/**
	 * Resets objects to initial state.
	 */
	private void reset() {
		theImageFile = null;
		textDecrypt = false;
		passPhrase = null;
	}
	
	/**
	 * Calls reset() and also resets all controls to initial state.
	 * Used by cancel button and also by main window.
	 */
	public void fullReset() {
		reset();
		goButton.setEnabled(false);
		saveButton.setEnabled(false);
		decryptText.setEnabled(true);
		theImagePath.setText("Choose an image file");
	}

}
