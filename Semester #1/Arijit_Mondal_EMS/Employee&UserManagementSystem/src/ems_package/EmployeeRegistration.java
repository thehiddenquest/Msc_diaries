package ems_package;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;


public class EmployeeRegistration extends JFrame{
	JLabel name;
	TextArea empname;
	JLabel Desg;
	TextArea empDesg;
	JLabel Dept;
	TextArea empDept;
	JLabel Salary;
	TextArea empSalary;
	JLabel message;
	static HashMap <String,Integer> count=new HashMap();
	static Employee leader1=new leader("n","n","n",1,1);
	public EmployeeRegistration (){
		setSize(500,500);
		setLayout(null);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		setName("Registration Window");
		name=new JLabel("Enter Employe name: ");
		name.setBounds(20,30,160,40);
		empname=new TextArea();
		empname.setBounds(225, 30, 200, 40);
		Desg=new JLabel("Enter Employe's designation ");
		Desg.setBounds(20, 100, 200, 40);
		empDesg=new TextArea();
		empDesg.setBounds(225,100,200,40);
		Dept=new JLabel("Enter Employe's department ");
		Dept.setBounds(20, 170, 200, 40);
		empDept=new TextArea();
		empDept.setBounds(225,170,200,40);
		Salary=new JLabel("Enter Employee's salary: ");
		Salary.setBounds(20,240,200,40);
		empSalary=new TextArea();
		empSalary.setBounds(275, 240, 100, 40);
		add(name);
		add(empname);
		add(Desg);
		add(empDesg);
		add(Dept);
		add(empDept);
		add(Salary);
		add(empSalary);
		JButton add=new JButton("Add");
		add.setBounds(120, 300, 250, 40);
		add(add);
		message = new JLabel("Enter values to register");
		message.setBounds(150, 350, 500, 30);
		add(message);
		registerAction(add);
		JButton home = new JButton("Go Back to Home");
		home.setBounds(140, 400, 200, 50);
		add(home);
		homeAction(home);
		setVisible(true);
		
	}
	public void homeAction(JButton Home) {
		Home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new EmployeeSystemGui(leader1);
			}
			
		});
	}
	public void registerAction(JButton add) {
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(count.get(empDept.getText())==null) {
						count.put(empDept.getText(), 0);
				}
				if(empDesg.getText().equalsIgnoreCase("worker")){
					count.put(empDept.getText(), count.get(empDept.getText())+1);
					int sal=Integer.parseInt(empSalary.getText());
					System.out.println("act:"+sal);
					Employee worker=new Worker(empname.getText(),empDept.getText(),empDesg.getText(),count.get(empDept.getText()),sal);
					
					leader1.addEmployee(worker);
					message.setText("Registration successfull for worker");
					
				}
				else {
						count.put(empDept.getText(), count.get(empDept.getText())+1);
					Employee worker=new leader(empname.getText(),empDept.getText(),empDesg.getText(),count.get(empDept.getText()),Integer.parseInt(empSalary.getText()));
					leader1.addEmployee(worker);
					message.setText("Registration successfull for manager");
				}
			}
			
		});
	}
	public static void main(String[] args) {
		new EmployeeRegistration ();
	}
}
