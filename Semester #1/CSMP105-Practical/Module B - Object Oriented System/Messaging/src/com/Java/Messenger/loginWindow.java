package com.Java.Messenger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class loginWindow implements ActionListener {

	public JFrame window;

	private JPanel send_button_panel, label, mid;
	private JButton send_button;
	private final int HEIGHT = 180;
	private final int WIDTH = 150;
	private final String title = "MESSENGER";
	private int scale = 2;
	private JLabel loginLabel, EmailID, EmailAppPassWord, PhoneNumber;
	private JTextPane Email, Password, Phone;

	public loginWindow() {
		initWindow();
		northsideWindow();
		centersideWindow();
		southsideWindow();
		window.setVisible(true);
	}

	private void initWindow() {
		window = new JFrame();
		window.setTitle(title);
		window.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		window.setMinimumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		window.setMaximumSize(new Dimension(WIDTH * scale, HEIGHT * scale));

		Image originalImage = new ImageIcon(getClass().getResource("/img/logo.jpg")).getImage();

		// Define the new dimensions
		int newWidth = 100; // replace with your desired width
		int newHeight = 100; // replace with your desired height

		// Resize the image
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		window.setIconImage(scaledImage);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
//		window.setVisible(true);

	}

	private void northsideWindow() {
		label = new JPanel(new BorderLayout());
		label.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 00));
		loginLabel = new JLabel("LOGIN FORM");
		loginLabel.setPreferredSize(new Dimension(10, 10));
		label.add(loginLabel, BorderLayout.CENTER);
		window.add(label, BorderLayout.NORTH);
	}

	private void centersideWindow() {
		mid = new JPanel();
		mid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mid.setLayout(new GridBagLayout()); // Use GridBagLayout

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 5, 5); // Add some padding

		// Email ID Section
		JPanel emailPanel = new JPanel(new BorderLayout());
		EmailID = new JLabel("Email ID: ");
		emailPanel.add(EmailID);

		JPanel mid_emailtext = new JPanel(new BorderLayout());
		Email = new JTextPane();
		Email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// Set the maximum size for the JTextPane
		Email.setMaximumSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		// Set the preferred size to control the initial size
		Email.setPreferredSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		mid_emailtext.add(Email);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Make the first column smaller
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mid.add(emailPanel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 2.0; // Make the second column larger
		mid.add(mid_emailtext, gbc);

		// Password Section
		JPanel passwordPanel = new JPanel(new BorderLayout());
		EmailAppPassWord = new JLabel("Password: ");
		passwordPanel.add(EmailAppPassWord);

		JPanel mid_passwordtext = new JPanel(new BorderLayout());
		Password = new JTextPane();
		Password.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// Set the maximum size for the JTextPane
		Password.setMaximumSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		// Set the preferred size to control the initial size
		Password.setPreferredSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		mid_passwordtext.add(Password);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0; // Reset the weight for the first column
		mid.add(passwordPanel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 2.0; // Make the second column larger
		mid.add(mid_passwordtext, gbc);

		// Phone Section
		JPanel mid_phone_label = new JPanel(new BorderLayout());
		PhoneNumber = new JLabel("Number: ");
		mid_phone_label.add(PhoneNumber);

		JPanel mid_phonetext = new JPanel(new BorderLayout());
		Phone = new JTextPane();
		Phone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// Set the maximum size for the JTextPane
		Phone.setMaximumSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		// Set the preferred size to control the initial size
		Phone.setPreferredSize(new Dimension(WIDTH + 20, HEIGHT / 8));
		mid_phonetext.add(Phone);

		gbc.gridx = 0;
		gbc.gridy = 2; // Change the row for the phone section
		gbc.weightx = 1.0; // Reset the weight for the first column
		mid.add(mid_phone_label, gbc);

		gbc.gridx = 1;
		gbc.weightx = 2.0; // Make the second column larger
		mid.add(mid_phonetext, gbc);

		// Adjust BorderLayout constraints for mid JPanel
		window.add(mid, BorderLayout.CENTER);
	}

	private void southsideWindow() {
		send_button_panel = new JPanel();
		send_button_panel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 00));
		send_button = new JButton("LOGIN");
		send_button.addActionListener(this);
		send_button_panel.add(send_button);
		window.add(send_button_panel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String senderEmail = Email.getText();
		String senderPassword = Password.getText();
		String senderPhone = Phone.getText();

		JTextArea textArea = new JTextArea();
		textArea.setText("Create an application-specific password in your gmail account: <br>"
				+ "Gmail Account -> Security - > 2 - Step Verification - > App passwords (Bottom of Page) - <br>"
				+ "Create App Password account (Provide any name) - Save - Password will be generated.");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(5, 5, 100, 5));

		if (e.getActionCommand().equalsIgnoreCase("LOGIN")) {
			if (senderEmail == null || senderEmail.isEmpty()) {
				JOptionPane.showMessageDialog(window, "Invalid Email ID!!!");
				Email.setText("");
				Password.setText("");
			} else if (senderPassword == null || senderPassword.isEmpty()) {
				JOptionPane.showMessageDialog(window, textArea, "Instructions", JOptionPane.INFORMATION_MESSAGE);
				Email.setText("");
				Password.setText("");
			} else{
				Boolean verifiedEmail = Notification_service.verify("EMail",senderEmail,senderPassword);
				Boolean verifiedNumber= Notification_service.verify("TEXT",senderPhone,"1");
				if(verifiedEmail && verifiedNumber)
				{
					try {
						JOptionPane.showMessageDialog(window, "Authentication Successful", "Verified", JOptionPane.INFORMATION_MESSAGE);
						Thread.sleep(1000);
						window.setVisible(false);
						new gui(senderEmail,senderPassword,senderPhone,senderPhone);
					} catch (InterruptedException e1) {
						System.exit(0);
					}
				}
				else
				{
					try {
						JOptionPane.showMessageDialog(window, "Authentication Failed", "Not Verified", JOptionPane.INFORMATION_MESSAGE);
						Thread.sleep(1000);
						System.exit(0);
					} catch (InterruptedException e1) {
						System.exit(0);
					}
				}
			}
		}

	}

}
