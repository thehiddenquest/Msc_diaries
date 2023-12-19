package Notepad_project;

//import javax.swing.JOptionPane;

public class formatingHandler {
	GUI gui;

	public formatingHandler(GUI gui) {
		this.gui = gui;
	}

	public void wordWrapper() {
		if (gui.wordWrapOn == false) {
			gui.wordWrapOn = true;
			gui.ta.setLineWrap(true);
			gui.ta.setWrapStyleWord(true);
			gui.formatwrap.setText("Word Wrap: ON");
		} else if (gui.wordWrapOn == true) {
			gui.wordWrapOn = false;
			gui.ta.setLineWrap(false);
			gui.ta.setWrapStyleWord(false);
			gui.formatwrap.setText("Word Wrap: OFF");
		}
	}
}
