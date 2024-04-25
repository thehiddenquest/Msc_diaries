package GraphicalUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserSignUp implements ActionListener{
	//Basic requirements
	private JFrame window = new JFrame();
	private String title = "Sign Up";
	private final int height = 250;
	private final int width = 200;
	private int scale = 2;
	
	//North Panel
	private JPanel north = new JPanel();
	JLabel login = new JLabel("Sign Up Form");
	
	//Center Panel
	private JPanel center = new JPanel();
	private JLabel usernameLabel = new JLabel("Username :");
	private JLabel passwordLabel = new JLabel("Password :");
	private JTextField usernameTextField = new JTextField(25);
	private JTextField passwordTextField = new JTextField(25);
	private JLabel emailIdLabel = new JLabel("Email ID :");
	private JTextField emailIdTextField = new JTextField(25);
	private JButton signupButton = new JButton("Sign up");
	
	
	private UserLogin userLoginWindow; 
	public UserSignUp(UserLogin userLoginWindow)
	{
		northPanel();
		centerPanel();
	//	southPanel();
		this.userLoginWindow = userLoginWindow;
		initWindow();
		window.setVisible(true);
	}
	private void initWindow()
	{
		window.setTitle(title);
		window.setSize(new Dimension(width*scale,height*scale));
		window.setPreferredSize(new Dimension(width*scale,height*scale));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	//	window.setLayout(null);;
		window.pack();
		window.setLocationRelativeTo(null);
		signupButton.addActionListener(this);
		window.add(signupButton,BorderLayout.SOUTH);
	}
	private void northPanel()
	{
		north.setPreferredSize(new Dimension(width*scale,height/2));
//		login.setBounds(100, 50, 10, 10);
		north.setBorder(BorderFactory.createEmptyBorder(30, 00, 00, 00));
		login.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		north.add(login);
		window.add(north,BorderLayout.NORTH);
	}
	private void centerPanel()
	{
		center.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 0, 00, 05); // Add some spacing between components
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weighty = 1.0; // Fill vertical space
		gbc.gridx = 0;
		gbc.gridy = 0;
		//gbc.gridheight = 2;
		usernameLabel.setFont(new Font(null,Font.BOLD,15));
		center.add(usernameLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		center.add(usernameTextField,gbc);
		
		gbc.insets = new Insets(00, 0, 00, 05); // Add some spacing between components
		gbc.weighty = 3; // Fill vertical space
		gbc.gridx = 0;
		gbc.gridy = 1;
		passwordLabel.setFont(new Font(null,Font.BOLD,15));
		center.add(passwordLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		center.add(passwordTextField,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 27; // Fill vertical space
		emailIdLabel.setFont(new Font(null,Font.BOLD,15));
		center.add(emailIdLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		center.add(emailIdTextField,gbc);
		
	//	center.add(loginButton,BorderLayout.SOUTH);
		center.setPreferredSize(new Dimension(width*scale,height/2));
		center.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5), BorderFactory.createLineBorder(Color.BLACK)));
		window.add(center);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("Sign up"))
		{
			System.out.println("Signup");
			userLoginWindow.makeVisible();
			window.dispose();

	
		}
		
	}
}
