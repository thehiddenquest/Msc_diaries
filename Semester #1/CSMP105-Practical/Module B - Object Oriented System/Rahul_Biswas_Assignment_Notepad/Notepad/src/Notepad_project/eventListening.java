package Notepad_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;

public class eventListening implements ActionListener {
	private GUI gui;
	private  FileHandler FH ;
	private formatingHandler ForH;
	public eventListening(GUI gui)
	{
		this.gui = gui;
		handleEvent();
		formating();
	}
    public void handleEvent() {
        FH = new FileHandler(gui);
    }
    public void formating() {
    	ForH = new formatingHandler(gui);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String actions = e.getActionCommand();
		
		switch(actions) {
		case "New" :FH.newFileHandler(); break;
		case "Open": FH.openFileHandler();break;
		case "Save": FH.saveFileHandler();break;
		case "Save As":FH.saveAsFileHandler();break;
		case "Exit" : FH.exitFileHandler();break;
		case "Word Wrap": ForH.wordWrapper();break;
		}
		gui.window.validate();
	}

}
