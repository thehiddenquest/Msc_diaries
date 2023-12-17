package Notepad_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class eventListening implements ActionListener {
	private GUI gui;
	public eventListening(GUI gui)
	{
		this.gui = gui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String actions = e.getActionCommand();
		if (actions.equalsIgnoreCase("Exit")) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(gui, "Notepad Exited");
			System.exit(0);
		}
		if (actions.equalsIgnoreCase("New")) {
			gui.window.add(gui.sp);
			gui.validate();
		}
		if (actions.equalsIgnoreCase("Open")) {
			try {
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(gui) == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					FileReader fr = new FileReader(f);
					gui.ta.read(fr, null);
					BufferedReader br = new BufferedReader(fr);
					String line;
					while ((line = br.readLine()) != null) {
						gui.ta.append(line + "\n");
					}
					br.close();
					gui.window.add(gui.sp);
					gui.window.validate();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

}
