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
 * Creates the window to create a new image with a message merged into it
 * and save the new image. Creates an object of type
 * ImageWithoutMessageEnc or ImageWithoutMessageUnEnc, depending on
 * whether encryption is set, and manipulates them to save the new image.
 */
@SuppressWarnings("serial")
public class CreateImage extends JFrame implements ActionListener,
ItemListener {
	/**
	 * a flag for text encryption
	 */
	private boolean textEncrypt;

	/**
	 * the check box to track textEncrypt
	 */
	private JCheckBox encryptText;

	/**
	 * the image file that will be processed
	 */
	private File theImageFile;

	/**
	 * the text file that contains the message to be processed
	 */
	private File theTextFile;

	/**
	 * the message that will be processed as a String object
	 */
	private String theMessage;

	/**
	 * the button to save the image after processing
	 */
	private JButton saveButton;

	/**
	 * the button to process the image and text file
	 */
	private JButton goButton;

	/**
	 * the text box that shows the image file chosen for processing
	 */
	private JTextField theImagePath;

	/**
	 * the text box that shows the text file chosen for processing
	 */
	private JTextField theTextPath;

	/**
	 * the pass phrase used to encrypt a message,if applicable
	 */
	private String passPhrase;

	/**
	 * the object that will hold the processed data
	 */
	ImageWithoutMessage theImageObject;


	/**
	 * Initialize variables and set up window and controls.
	 */
	public CreateImage() {
		reset();

		setLayout(new FlowLayout());

		JPanel theImageControls = openImageControls();
		add(theImageControls);

		JPanel theTextControls = openTextControls();
		add(theTextControls);

		encryptText = new JCheckBox("Encrypt Text");
		encryptText.setSelected(textEncrypt);
		encryptText.addItemListener(this);
		add(encryptText);

		JPanel theButtons = createButtons();
		add(theButtons);

		validate();
		pack();

		setSize(new Dimension(500,165));
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
	 * @return JPanel containing the controls to open a text file
	 */
	private JPanel openTextControls() {
		JButton theTextButton = new JButton("Choose Text");
		theTextButton.setActionCommand("pickText");
		theTextButton.addActionListener(this);

		theTextPath = new JTextField("Choose a text file",25);
		theTextPath.setEditable(false);
		theTextPath.setSize(400, 35);

		JPanel theTextControls = new JPanel();

		theTextControls.add(theTextButton);
		theTextControls.add(theTextPath);

		return theTextControls;
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
	 * Check which button is pushed and calls appropriate routines.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionString = 
				((JButton) e.getSource()).getActionCommand();
		if (actionString == "Go") createNewImage();
		else if (actionString == "Cancel") {
			fullReset();
			setVisible(false);
		}
		else if (actionString == "Save") saveImage();
		else if (actionString == "pickImage") pickImage();
		else if (actionString == "pickText") pickText();
	}

	/**
	 * Check if the check box has been changed.
	 * Updates textEncrypt.
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			passPhrase = OpenOrSave.getPassPhrase();
			if (passPhrase != null) {
				textEncrypt = true;
				System.out.println("Text will be decrypted");
			} else {
				textEncrypt = false;
				encryptText.setSelected(false);
			}			
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			textEncrypt = false;
			System.out.println("Text will not be encrypted");
		}
	}

	/**
	 * Create an ImageWithoutMessage object and tell it to process the
	 * bitmap and the text file, optionally encrypting.
	 * Used by the go button.
	 * Enables saveButton.
	 * Disables goButton and encryptText check box.
	 */
	private void createNewImage() {
		if (theImageFile != null || theTextFile != null) {
			if (textEncrypt) {
				theImageObject = new ImageWithoutMessageEncrypted(
						theImageFile, theMessage, passPhrase);
				saveButton.setEnabled(true);
				goButton.setEnabled(false);
				encryptText.setEnabled(false);
			} else {
				theImageObject = new ImageWithoutMessageUnencrypted(
						theImageFile, theMessage);
				saveButton.setEnabled(true);
				goButton.setEnabled(false);
				encryptText.setEnabled(false);
			}
		}
	}

	/**
	 * Save the new image file to a bitmap on the file system.
	 * Used by the save button.
	 */
	private void saveImage() {
		theImageObject.saveImage();
		this.setVisible(false);
	}

	/**
	 * Choose an image file from the file system
	 * Uses openSave.open() static method
	 * Used by theImageButton
	 * Updates theImagePath text value
	 * May enable goButton
	 */
	private void pickImage() {
		theImageFile = OpenOrSave.open();
		if (theImageFile != null)
			theImagePath.setText(theImageFile.getName());
		checkGoButton();
	}

	/**
	 * Choose a text file from the file system.
	 * Uses openSave.open() static method.
	 * Used by theTextButton.
	 * Updates theTextPath text value.
	 * May enable goButton.
	 */
	private void pickText() {
		theTextFile = OpenOrSave.open();
		if (theTextFile != null) {
			theTextPath.setText(theTextFile.getName());
			theMessage = OpenOrSave.readText(theTextFile);
		}
		checkGoButton();
	}

	/**
	 * Determines if the go button should be enabled.
	 * Used by pickImage() and pickText().
	 * Updates goButton enabled status.
	 */
	private void checkGoButton() {
		if (theImageFile != null || theTextFile != null) {
			goButton.setEnabled(true);
		}
	}
	
	/**
	 * resets objects to the initial state
	 */
	private void reset() {
		theImageFile = null;
		theTextFile = null;
		textEncrypt = false;
		passPhrase = null;
	}
	
	/**
	 * calls reset() and also resets controls to the initial state
	 * Used by cancel button and also by main window
	 */
	public void fullReset() {
		reset();
		goButton.setEnabled(false);
		saveButton.setEnabled(false);
		encryptText.setEnabled(true);
		theImagePath.setText("Choose an image file");
		theTextPath.setText("Choose a text file");
	}
}
