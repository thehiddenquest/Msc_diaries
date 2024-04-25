package ems_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

public class EmployeeManageGUI extends JFrame{
	public EmployeeManageGUI() {
		setSize(new Dimension(1280,786));
		setName("Employee Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel keypanel = new JPanel();
		keypanel.setBackground(Color.GRAY);
		keypanel.setPreferredSize(new Dimension(400,450));
		
		JButton add = new JButton("ADD");
		JButton remove = new JButton("REMOVE");
		JButton save = new JButton("SAVE");
		JButton load = new JButton("LOAD");
		
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
		
		JLabel Opp = new JLabel("Operations : ");
		Opp.setForeground(Color.CYAN);
		
		JTextArea usernameArea = new JTextArea(1, 15);
		JPasswordField passwordArea = new JPasswordField();
		passwordArea.setPreferredSize(new Dimension(150,20));
		usernameArea.setBackground(Color.WHITE);
		passwordArea.setBackground(Color.WHITE);
		
		EmployeeDB.add(EmployeeDetails,BorderLayout.NORTH);
		
		displayPanel.add(display,BorderLayout.NORTH);
		
		keypanel.add(Opp,BorderLayout.NORTH);
		keypanel.add(add);
		keypanel.add(remove);
		keypanel.add(save);
		keypanel.add(load);
	
		add(keypanel,BorderLayout.WEST);
		add(displayPanel,BorderLayout.EAST);
		add(EmployeeDB,BorderLayout.CENTER);
		
		this.setVisible(true);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel fisrt_name = new JLabel("First Name");
				JLabel second_name = new JLabel("Second Name");
				JLabel dept_name = new JLabel("Department Name");
				JLabel designation = new JLabel("Designation");
				JLabel salary = new JLabel("salary");
				
				JTextArea f_name = new JTextArea(1,20);
				JTextArea s_name = new JTextArea(1,20);
				JTextArea dept = new JTextArea(1,20);
				JTextArea desig = new JTextArea(1,20);
				JTextArea sal = new JTextArea(1,20);
			
				
				keypanel.add(fisrt_name,BorderLayout.SOUTH);
				keypanel.add(f_name,BorderLayout.SOUTH);
				keypanel.add(second_name,BorderLayout.SOUTH);
				keypanel.add(s_name,BorderLayout.SOUTH);
				keypanel.add(dept_name,BorderLayout.SOUTH);
				keypanel.add(dept,BorderLayout.SOUTH);
				keypanel.add(designation,BorderLayout.SOUTH);
				keypanel.add(desig,BorderLayout.SOUTH);
				keypanel.add(salary,BorderLayout.SOUTH);
				keypanel.add(sal,BorderLayout.SOUTH);
				
				validate();
			}
		});
	}
}
