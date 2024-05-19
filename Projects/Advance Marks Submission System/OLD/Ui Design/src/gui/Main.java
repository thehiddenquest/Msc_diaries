package gui;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of NewJPanel
        NewJPanel panel = new NewJPanel();
        
        // Add the panel to the frame
        frame.getContentPane().add(panel);
        
        // Set frame size and make it visible
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}