package ems_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
  

public class UserRegistration extends JFrame {

	public UserRegistration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setName("Registration Window");
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.CYAN);

		JLabel userId = new JLabel("User ID");
		userId.setForeground(Color.CYAN);
		JLabel Password = new JLabel("Password");
		Password.setForeground(Color.CYAN);
		JLabel emailId = new JLabel("Email ID");
		emailId.setForeground(Color.CYAN);

		JTextArea useridArea = new JTextArea(1, 10);
		JPasswordField passwordArea = new JPasswordField();
		passwordArea.setPreferredSize(new Dimension(200, 20));
		JTextArea emailidArea = new JTextArea(1, 20);

		JButton register = new JButton("Register");
		JButton home = new JButton("Home");

		JPanel registrationPanel = new JPanel();
		registrationPanel.setBackground(Color.GRAY);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.GRAY);

		registrationPanel.add(userId);
		registrationPanel.add(useridArea);
		registrationPanel.add(Password);
		registrationPanel.add(passwordArea);
		registrationPanel.add(emailId);
		registrationPanel.add(emailidArea);
		buttonPanel.add(register);
		buttonPanel.add(home);

		add(registrationPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);

		register.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = useridArea.getText();
				String password;
				String email = emailidArea.getText();
				try {

					MessageDigest m = MessageDigest.getInstance("MD5");

					m.update(String.valueOf(passwordArea.getPassword()).getBytes());

					byte[] bytes = m.digest();

					StringBuilder s = new StringBuilder();
					for (int i = 0; i < bytes.length; i++) {
						s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
					}

					password = s.toString();
					User_info u = new User(username,password,email);
					u.addUser(u);
					User_Repository fileRepositroy = new User_Repository (new FileRepository());
				    fileRepositroy.save(u);
					System.out.println(username);
					System.out.println(password);
					System.out.println(email);
				} catch (NoSuchAlgorithmException ex) {
					ex.printStackTrace();
				}
			}

		});

		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});
	}
}
