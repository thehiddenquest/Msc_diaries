package com.Java.Messenger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
//import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class gui implements ActionListener {

	public JFrame window;
	private JPanel send_button_panel;
	private JButton send_button;
	private JTextArea messagers;
	private final int HEIGHT = 300;
	private final int WIDTH = 350;
	private final String title = "MESSENGER";
	private int scale = 2;
	private JLabel subjectLabel;
	private JScrollPane scrollPane, scrollPaneText;
	private JTextPane subjectTextPane;
	private String[] messages = { "Text", "Email", "Buzzer" };
	private JComboBox<String> messageTyper = createComboBox(messages);
	private JTextPane messagePane;
	String senderEmailID;
	String senderPassword;
	String senderNumber;
	String senderNumberPassword;

	public gui(String senderEmailID,String senderPassword,String senderNumber,String senderNumberPassword) {
		this.senderPassword= senderPassword;
		this.senderEmailID = senderEmailID;
		this.senderNumber = senderNumber;
		this.senderNumberPassword = senderNumberPassword;
		
		initWindow();
		setSendButton();
		addContainer();
		addCenterContainer();
		messageTyper.addActionListener(this);

	}

	private void initWindow() {
		window = new JFrame();
		window.setTitle(title);
		window.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
//		window.setMinimumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
//		window.setMaximumSize(new Dimension(WIDTH * scale, HEIGHT * scale));

		Image originalImage = new ImageIcon(getClass().getResource("/img/logo.jpg")).getImage();

		// Define the new dimensions
		int newWidth = 100; // replace with your desired width
		int newHeight = 100; // replace with your desired height

		// Resize the image
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		window.setIconImage(scaledImage);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

//	private void setSendButton() {
//		send_button_panel = new JPanel();
//		send_button = new JButton("SEND");
//		send_button.addActionListener(this);
//		send_button_panel.add(send_button);
//		window.add(send_button_panel, BorderLayout.SOUTH);
//		window.validate();
//	}
	private void setSendButton() {
	    send_button_panel = new JPanel(new GridBagLayout());
	    send_button_panel.setPreferredSize(new Dimension(700, 100));
	    send_button_panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

	    send_button = new JButton("SEND");
	    send_button.addActionListener(this);

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.WEST;
	    gbc.insets = new Insets(0, 0, 15, 10); // Add some spacing between components
	    send_button_panel.add(new JLabel("Attachments"), gbc);

	    gbc.gridy++;
	    JTextPane attachMentPane = new JTextPane();
	    JScrollPane attach = new JScrollPane(attachMentPane);
	    attach.setPreferredSize(new Dimension(150, 100)); // Adjust width and height as needed
	    gbc.gridheight = 3; // Span two rows
	    gbc.weightx = 1.0; // Allow attachment pane to expand horizontally
	    gbc.fill = GridBagConstraints.BOTH; // Allow both horizontal and vertical expansion
	    send_button_panel.add(attach, gbc);

	    gbc.gridy = 0;
	    gbc.gridx++;
	    JButton add = new JButton("ADD");
	    add.setPreferredSize(new Dimension(50, 25)); // Adjust the size as needed
	    send_button_panel.add(add, gbc);

	    gbc.gridy++;
	    JButton delete = new JButton("DELETE");
	    delete.setPreferredSize(new Dimension(50, 25)); // Adjust the size as needed
	    send_button_panel.add(delete, gbc);

	    gbc.gridy = 0;
	    gbc.gridx = 2;
	    // Span two rows for the SEND button
	    send_button.setPreferredSize(new Dimension(50, 20)); // Adjust the size as needed
	    send_button_panel.add(send_button, gbc);

	    window.add(send_button_panel, BorderLayout.SOUTH);
	    window.validate();
	}
	private JComboBox<String> createComboBox(String[] items) {
		return new JComboBox<>(items);
	}

	private void addContainer() {

		JLabel toLabel = new JLabel("         To: ");
		// JTextPane with JScrollPane
		messagePane = new JTextPane();
		scrollPane = new JScrollPane(messagePane);
		scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT / 10));
		// Add components to the content pane
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(messageTyper); // ,BorderLayout.WEST
		contentPane.add(toLabel);
		contentPane.add(scrollPane);// ,BorderLayout.EAST
		window.add(contentPane, BorderLayout.NORTH);
		window.setVisible(true);
	}

	private void addCenterContainer() {
		JPanel mid = new JPanel(new BorderLayout());
		mid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adjust border size as needed
		JPanel label = new JPanel(new BorderLayout());
		label.setBorder(BorderFactory.createEmptyBorder(00, 00, 10, 00));
		JLabel Me = new JLabel(" Message: ");
		subjectLabel = new JLabel("                                             Subject: ");
		subjectTextPane = new JTextPane();
		scrollPaneText = new JScrollPane(subjectTextPane);

		scrollPaneText.setPreferredSize(new Dimension(WIDTH, HEIGHT / 10));
		label.add(subjectLabel, BorderLayout.CENTER);
		label.add(Me, BorderLayout.WEST);
		label.add(scrollPaneText, BorderLayout.EAST);
		mid.add(label, BorderLayout.NORTH);
		subjectLabel.setVisible(false);

		scrollPaneText.setVisible(false);

		messagers = new JTextArea();
		messagers.setLineWrap(true);
		messagers.setWrapStyleWord(true);
		messagers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		messagers.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));
		mid.add(messagers);
		window.add(mid);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String messageType = messageTyper.getItemAt(messageTyper.getSelectedIndex());
		String reciver = messagePane.getText();
		String message = messagers.getText();
		String subject = subjectTextPane.getText();
		String selectedValue = (String) messageTyper.getSelectedItem();

		if ("Email".equals(selectedValue)) {
			subjectLabel.setVisible(true);
			scrollPaneText.setVisible(true);
		} else {
			subjectLabel.setVisible(false);
			scrollPaneText.setVisible(false);
		}

		if (e.getActionCommand().equalsIgnoreCase("Send")) {
			if ("Email".equals(selectedValue)) {
				String Notification_Message = Notification_service.notify(senderEmailID, reciver, messageType,
						subject, message,senderPassword);
				JOptionPane.showMessageDialog(window, Notification_Message);
				messagers.setText("");
				subjectTextPane.setText("");
			} else {
				String Notification_Message = Notification_service.notify(senderNumber, reciver, message,
						messageType);
				JOptionPane.showMessageDialog(window, Notification_Message);
			}

		}

	}

}
