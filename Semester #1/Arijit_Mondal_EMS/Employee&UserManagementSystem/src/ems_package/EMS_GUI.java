package ems_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.login.LoginContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Popup;


public class EMS_GUI extends JFrame{
	
	public EMS_GUI() {
		setSize(new Dimension(500,500));
		setName("Employee Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel LOGIN = new JPanel();
		LOGIN.setPreferredSize(new Dimension(500,100));
		LOGIN.setBackground(Color.GRAY);
		
		JPanel forgetPassPanel = new JPanel();
		
		JPanel RegisterPanel = new JPanel();
		
		JPanel EmployeeDB = new JPanel();
		EmployeeDB.setBackground(Color.GRAY);
		EmployeeDB.setPreferredSize(new Dimension(300,450));
		
		JPanel displayPanel = new JPanel();
		displayPanel.setBackground(Color.BLACK);
		displayPanel.setPreferredSize(new Dimension(400,450));
	
		JLabel username = new JLabel("Username ");
		JLabel password = new JLabel("Password ");
		username.setForeground(Color.CYAN);
		password.setForeground(Color.CYAN);
		
		JLabel EmployeeDetails = new JLabel("Employee's Details");
		EmployeeDetails.setForeground(Color.CYAN);
		
		JLabel display = new JLabel("Display Panel");
		display.setForeground(Color.GREEN);
		
		JTextArea usernameArea = new JTextArea(1, 15);
		JPasswordField passwordArea = new JPasswordField();
		passwordArea.setPreferredSize(new Dimension(150,20));
		usernameArea.setBackground(Color.WHITE);
		passwordArea.setBackground(Color.WHITE);
		
		JButton Login = new JButton("Login");
		JButton forgetPass = new JButton("Forget Password");
		JButton Register = new JButton("Register");
	
		LOGIN.add(username,BorderLayout.NORTH);
		LOGIN.add(usernameArea,BorderLayout.NORTH);
		LOGIN.add(password,BorderLayout.NORTH);
		LOGIN.add(passwordArea,BorderLayout.NORTH);
		LOGIN.add(Login,BorderLayout.SOUTH);
		forgetPassPanel.add(forgetPass);
		RegisterPanel.add(Register);
		LOGIN.add(forgetPass,BorderLayout.SOUTH);
		LOGIN.add(Register,BorderLayout.SOUTH);
		
		EmployeeDB.add(EmployeeDetails,BorderLayout.NORTH);
		
		displayPanel.add(display,BorderLayout.NORTH);
	
		add(LOGIN,BorderLayout.WEST);
		add(displayPanel,BorderLayout.EAST);
		add(EmployeeDB,BorderLayout.CENTER);
		
		Login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameArea.getText();
				String password = String.valueOf(passwordArea.getPassword());
				User_info user = new User(username,password,null);
				user.UserAuthenticate(user);
			}
			
		});
		
		forgetPass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userid = JOptionPane.showInputDialog("Enter UserID");
				User_info user = new User(userid,null,null);
				user.ResetPass(user);
			}
			
		});
		
		Register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegistration();
			}
			
		});
		
	}
	
	
	
	public static void main(String[] args) {
		EMS_GUI EMS = new EMS_GUI();
		EMS.setVisible(true);
	}
}

