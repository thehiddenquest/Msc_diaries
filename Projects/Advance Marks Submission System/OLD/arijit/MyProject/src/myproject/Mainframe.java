package myproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Mainframe extends JFrame implements ActionListener{
	
	public JPanel panel = new JPanel();
	public JPanel subjectpanel = new JPanel();
	
	public String messageType[] = {"Message", "Mail", "Sound"};
	public JTextArea area = new JTextArea();
	public JLabel jl = new JLabel("To");
	public JLabel subject = new JLabel("Subject");
	public JTextArea messagearea = new JTextArea();
	public JTextArea subjectarea = new JTextArea();
	public JComboBox cb = new JComboBox(messageType);
	public JButton send = new JButton("Send");
	
	public Mainframe() {
		init();
	}
	
	public void init() {
		this.setSize(600, 400);
		this.setBackground(Color.WHITE);
		this.getDefaultCloseOperation();
		
		area.setPreferredSize(new Dimension(200,30));
		subjectarea.setPreferredSize(new Dimension(150,30));
		messagearea.setPreferredSize(new Dimension(200,200));
		
		this.add(send,BorderLayout.SOUTH);
		send.addActionListener(this);
		
		panel.add(cb,BorderLayout.NORTH);
		panel.add(jl);
		panel.add(area,BorderLayout.NORTH);
		panel.add(send,BorderLayout.NORTH);
		
		subjectpanel.add(subject,BorderLayout.NORTH);
		subjectpanel.add(subjectarea,BorderLayout.NORTH);
		
		this.add(panel,BorderLayout.NORTH);
		this.add(messagearea,BorderLayout.CENTER);
		
	}
	
	
	public static void main(String[] args) {
		Mainframe f = new Mainframe();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		String messageType = (String) cb.getItemAt(cb.getSelectedIndex());
		String reciver = area.getText();
		String message = messagearea.getText();
		
		if(cmd.equalsIgnoreCase("Send")) {
			Notification_service.notify("someone", reciver, message, messageType);
		}
	}

}
