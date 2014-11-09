package userInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import fileManipulation.Encrypt;

/**
 * @author John Bannister <john_bannister@my.uri.edu>
 * <p>
 * Creates the main window of the program.
 * <p>
 * Instantiates the two sub-windows but does not display them until
 * the appropriate button is pushed. Creates objects of type CreateImage
 * and RecoverMessage which are the sub-windows.
 * <p>
 * This is a program that performs two main tasks:
 * 1. Open a bitmap and a text file and merge them together so that it is
 * not obvious the bitmap has an embedded message. This is done through
 * the Image object created through CreateImage.
 * 2. Open a bitmap that has already been merged with a text file and
 * extract the message and save it to a text file. This is done through
 * the Image object created through RecoverMessage.
 * <p>
 * In either case, the message may be encrypted and not, which is set in
 * the sub-window.
 */
@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener{
	/**
	 * the window to create a new image
	 */
	private CreateImage newImage;
	/**
	 * the window to recover a text message from an image
	 */
	private RecoverMessage recoverImage;

	/**
	 * sets up windows and buttons
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new FlowLayout());

		createButtons();

		newImage = new CreateImage();
		recoverImage = new RecoverMessage();
		
		validate();
		pack();

		setVisible(true);
		setSize(new Dimension(400,75));
		setLocationRelativeTo(null);

	}

	/**
	 * Creates the main window.
	 * @param args not used
	 */
	public static void main(String[] args) {
		new Main();
	}

	/** 
	 * Check actions of button pushes.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionString = 
				((JButton) e.getSource()).getActionCommand();
		if (actionString == "Exit") System.exit(0);
		else if (actionString == "New") newImage();
		else if (actionString == "Recover") recoverImage();
	}

	/**
	 * Show the window to create a new image.
	 * Used by the createImage button.
	 */
	private void newImage() {
		newImage.fullReset();
		newImage.setVisible(true);
	}

	/**
	 * Show the window to recover a message.
	 * Used by the recoverImage button.
	 */
	private void recoverImage() {
		recoverImage.fullReset();
		recoverImage.setVisible(true);
	}

	/**
	 * Create the buttons for the main window.
	 */
	private void createButtons() {
		JButton createImage = new JButton("New Image");
		createImage.setActionCommand("New");
		createImage.addActionListener(this);
		add(createImage);

		JButton recoverMessage = new JButton("Recover Message");
		recoverMessage.setActionCommand("Recover");
		recoverMessage.addActionListener(this);
		add(recoverMessage);

		JButton exitProgram = new JButton("Exit");
		exitProgram.setActionCommand("Exit");
		exitProgram.addActionListener(this);
		add(exitProgram);
	}

}
