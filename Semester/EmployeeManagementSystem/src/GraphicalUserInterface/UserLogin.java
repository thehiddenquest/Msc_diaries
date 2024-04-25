package GraphicalUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class UserLogin implements ActionListener{
	//Basic requirements
	private JFrame window = new JFrame();
	private String title = "Login";
	private final int height = 250;
	private final int width = 200;
	private int scale = 2;
	
	//North Panel
	private JPanel north = new JPanel();
	JLabel login = new JLabel("Login Form");
	
	//Center Panel
	private JPanel center = new JPanel();
	private JLabel usernameLabel = new JLabel("Username :");
	private JLabel passwordLabel = new JLabel("Password :");
	private JTextField usernameTextField = new JTextField(25);
	private JTextField passwordTextField = new JTextField(25);
	private JButton loginButton = new JButton("login");
	
	
	//South Panel
	private JPanel south = new JPanel();
	private JButton resetButton = new JButton("Reset Password");
	private JButton signupButton = new JButton("Sign Up");
	public UserLogin()
	{
		northPanel();
		centerPanel();
		southPanel();
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

	}
	private void northPanel()
	{
	    // Create the north panel
	    north.setPreferredSize(new Dimension(width * scale, height / 2));
	    north.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
	    north.setLayout(new BorderLayout());

	    // Create a panel to contain the login label and the image label
	    JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 

	    // Create the login label
	    JLabel loginLabel = new JLabel("Login Form");
	    loginLabel.setFont(new Font(null, Font.BOLD, 30)); 

	    // Add the login label to the login panel
	    loginPanel.add(loginLabel);

	    // Load the image from file
	    ImageIcon icon = new ImageIcon("../EmployeeManagementSystem/image/blankuser.jpg"); 
	    Image image = icon.getImage();

	    // Resize the image to fit the label
	    Image scaledImage = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH); 
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);

	    // Create a label for the image
	    JLabel imageLabel = new JLabel(scaledIcon);
	    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

	 // Set the border for the label
	    imageLabel.setBorder(border);

	    // Add the image label to the login panel
	    loginPanel.add(imageLabel);

	    // Add the login panel to the north panel
	    north.add(loginPanel, BorderLayout.CENTER);

	    // Add the north panel to the window's NORTH position
	    window.add(north, BorderLayout.NORTH);
	}
	private void centerPanel()
	{
		center.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(05, 0, 10, 05); // Add some spacing between components
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		usernameLabel.setFont(new Font(null,Font.BOLD,15));
		center.add(usernameLabel,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		passwordLabel.setFont(new Font(null,Font.BOLD,15));
		center.add(passwordLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		center.add(usernameTextField,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		center.add(passwordTextField,gbc);
		
		gbc.gridwidth = 3;
		gbc.ipadx = 20;
		gbc.gridx = 0;
		gbc.gridy = 2;
		loginButton.addActionListener(this);
		center.add(loginButton,gbc);
		
	//	center.add(loginButton,BorderLayout.SOUTH);
		center.setPreferredSize(new Dimension(width*scale,height/2));
		center.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.BLACK)));
		window.add(center,BorderLayout.CENTER);
	}
	private void southPanel()
	{
		south.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(05, 0, 10, 05); // Add some spacing between components
		
		gbc.gridwidth = 2;
		gbc.ipadx = 20;
		gbc.gridx = 0;
		gbc.gridy = 0;
		resetButton.addActionListener(this);
		south.add(resetButton,gbc);
		
		gbc.gridwidth = 2;
		gbc.ipadx = 20;
		gbc.gridx = 0;
		gbc.gridy = 1;
		signupButton.addActionListener(this);
		south.add(signupButton,gbc);
		
		south.setPreferredSize(new Dimension(width*scale,height/2));
		//south.setBackground(Color.BLUE);
		window.add(south,BorderLayout.SOUTH);
	}
    public void makeVisible() {
        window.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command)
		{
			case "login" : System.out.println("Logged in");break;
			case "Reset Password" : System.out.println("Reset Password");break;
			case "Sign Up" : 	new UserSignUp(this);
								window.setVisible(false);
								break;
		}
		
	}
}
