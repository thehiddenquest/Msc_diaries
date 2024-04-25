package GraphicalUserInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TempFrame extends JFrame{
	public TempFrame()
	{
		setTitle("Login");
		setPreferredSize(new Dimension(500,500));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        JLabel label = new JLabel();

        // Load the image from file
        ImageIcon icon = new ImageIcon("../EmployeeManagementSystem/image/blankuser.jpg"); // Replace "path/to/your/image.jpg" with the actual path to your image file
        Image image = icon.getImage();

        // Resize the image to fit the label
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the image icon to the label
        label.setIcon(scaledIcon);

        // Create a panel with FlowLayout to center the label horizontally
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);

        // Add the panel to the frame
        add(panel, BorderLayout.NORTH);
		
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	public static void main(String[] args) {
		new TempFrame();

	}

}
