package com.Java.Messenger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	private final int  HEIGHT = 250;
	private final int WIDTH = 300;
	private final String title = "MESSENGER";
	private int scale = 2;
	private JLabel subjectLabel;
	private JScrollPane scrollPane, scrollPaneText;
    private JTextPane subjectTextPane;
	private String[] messages = {"Text", "Email", "Buzzer"};
	private JComboBox<String> messageTyper = createComboBox(messages);
	private JTextPane messagePane;
	public gui()
	{
		initWindow();
		setSendButton();
		addContainer();
		addCenterContainer();
		messageTyper.addActionListener(this);
		
	}
	private void initWindow()
	{
		window = new JFrame();
		window.setTitle(title);
		window.setPreferredSize(new Dimension(WIDTH*scale, HEIGHT*scale));
		window.setMinimumSize(new Dimension(WIDTH*scale, HEIGHT*scale));
		window.setMaximumSize(new Dimension(WIDTH*scale, HEIGHT*scale));
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	private void setSendButton()
	{
		send_button_panel = new JPanel();
		send_button = new JButton("SEND");
//		send_button_panel.setLayout(new BorderLayout());
		send_button.addActionListener(this);
		send_button_panel.add(send_button);
		window.add(send_button_panel,BorderLayout.SOUTH);
		window.validate();
	}
    private JComboBox<String> createComboBox(String[] items) {
        return new JComboBox<>(items);
    }
	private void addContainer()
	{

		JLabel toLabel = new JLabel("         To: ");
        // JTextPane with JScrollPane
        messagePane = new JTextPane();
        scrollPane = new JScrollPane(messagePane);
        scrollPane.setPreferredSize(new Dimension(WIDTH,HEIGHT/10));
        // Add components to the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(messageTyper); //,BorderLayout.WEST
        contentPane.add(toLabel);
        contentPane.add(scrollPane);//,BorderLayout.EAST
        window.add(contentPane,BorderLayout.NORTH);
        window.setVisible(true);
	}
	private void addCenterContainer()
	{
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
	    label.add(scrollPaneText,  BorderLayout.EAST);
	    mid.add(label, BorderLayout.NORTH);
        subjectLabel.setVisible(false);

        scrollPaneText.setVisible(false);
        
	    messagers = new JTextArea();
	    messagers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    messagers.setPreferredSize(new Dimension(WIDTH,HEIGHT/2));
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
        String selectedValue = (String)messageTyper.getSelectedItem();

        if ("Email".equals(selectedValue)) {
            subjectLabel.setVisible(true);
            scrollPaneText.setVisible(true);
        } else {
            subjectLabel.setVisible(false);
            scrollPaneText.setVisible(false);
        }
		
		if(e.getActionCommand().equalsIgnoreCase("Send")) {
			if ("Email".equals(selectedValue))
			{
				String Notification_Message = Notification_service.notify("codehub5565@gmail.com", reciver, messageType , subject, message);
				JOptionPane.showMessageDialog(window, Notification_Message);  
			}
			else
			{
				String Notification_Message = Notification_service.notify("codehub5565@gmail.com", reciver, message, messageType);
				JOptionPane.showMessageDialog(window, Notification_Message);  
			}

		}
//		if(e.getActionCommand().equalsIgnoreCase("SEND"))
//		{
//			JOptionPane.showMessageDialog(window, + " Send to " + );  
//		}
		
	}

	
	
	
}
