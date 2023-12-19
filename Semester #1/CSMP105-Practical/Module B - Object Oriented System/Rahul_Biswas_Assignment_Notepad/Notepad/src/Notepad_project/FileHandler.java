//package Notepad_project;
//
//import java.awt.FileDialog;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//
//import javax.swing.JOptionPane;
//
//public class fileHandler {
//	private GUI gui;
//	private String fileName, fileAddress;
//
//	public fileHandler(GUI gui) {
//		this.gui = gui;
//	}
//
//	public void newFileHandler() {
//		gui.window.setTitle("New Document - MY NOTEPAD");
//		gui.ta.setText("");
//		fileAddress =null;
//		fileName= null;
//		gui.window.add(gui.sp);
//	}
//
//	public void openFileHandler() {
//		FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
//		fd.setVisible(true);
//		if (fd.getFile() != null) {
//			fileName = fd.getFile();
//			fileAddress = fd.getDirectory();
//			gui.window.setTitle(fileName);
//		}
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
//			gui.ta.setText("");
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				gui.ta.append(line + "\n");
//			}
//			br.close();
//			gui.window.add(gui.sp);
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(gui, "File Not Opened");
//		}
//	}
//
//	public void saveFileHandler() {
//		if (fileName == null) {
//			saveAsFileHandler();
//		} else {
//			try {
//				FileWriter fw = new FileWriter(fileAddress + fileName);
//				fw.write(gui.ta.getText());
//				fw.close();
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(gui, "File Not Saved");
//			}
//		}
//	}
//
//	public void saveAsFileHandler() {
//		FileDialog fd = new FileDialog(gui.window, "Save As", FileDialog.SAVE);
//		fd.setVisible(true);
//		if (fd.getFile() != null) {
//			fileName = fd.getFile();
//			fileAddress = fd.getDirectory();
//			gui.window.setTitle(fileName);
//		}
//		try {
//			FileWriter fw = new FileWriter(fileAddress + fileName);
//			fw.write(gui.ta.getText());
//			fw.close();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(gui, "File Not Saved");
//		}
//
//	}
//	public void exitFileHandler()
//	{
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		JOptionPane.showMessageDialog(gui, "Notepad Exited");
//		System.exit(0);
//	}
//
//	public void copyFileHandler() {
//		JOptionPane.showMessageDialog(gui, "Under Development");
//	}
//
//}
package Notepad_project;

import java.awt.FileDialog;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileHandler extends Thread {

	private final GUI gui;
	private String fileName, fileAddress;
	private boolean changesMade = false;
	private boolean fileopen = false;

	public FileHandler(GUI gui) {
		this.gui = gui;
//		SwingUtilities.invokeLater(this::setTextAreaDocumentListener);
		start();
	}

	@Override
	public void run() {
		setTextAreaDocumentListener();
	}

	private synchronized void setChangesMade(boolean value) {
		changesMade = value;
	}

	private synchronized boolean getChangesMade() {
		return changesMade;
	}

	private void setTextAreaDocumentListener() {
		gui.ta.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (fileopen == false) {
					updateTitle();
					changesMade = true;
				}
				fileopen = false; // Reset flag after processing initial text
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (fileopen == false) {
					updateTitle();
					changesMade = true;
				}
				fileopen = false; // Reset flag after processing initial text
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (fileopen == false) {
					updateTitle();
					changesMade = true;
				}
				fileopen = false; // Reset flag after processing initial text
			}
		});
	}

	public void newFileHandler() {
		gui.window.setTitle("New Document - MY NOTEPAD");
		gui.ta.setText("");
		fileName = null;
		fileAddress = null;
		gui.window.add(gui.sp);
	}

	public void openFileHandler() {
		FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
		fd.setVisible(true);
		if (fd.getFile() != null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			setChangesMade(false);
			fileopen = true;
			updateTitle();
			gui.window.add(gui.sp);
			try {
				readFile(fileAddress + fileName);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(gui, "File Not Opened: " + e.getMessage());
			}
		}
	}

	public void saveFileHandler() {
		if (fileName == null) {
			saveAsFileHandler();
		} else {
			try {
				writeFile(fileAddress + fileName, gui.ta.getText());
				setChangesMade(false);
				updateTitle();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(gui, "File Not Saved: " + e.getMessage());
			}
		}
	}

	public void saveAsFileHandler() {
		FileDialog fd = new FileDialog(gui.window, "Save As", FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getFile() != null) {
			fileName = fd.getFile();
			fileAddress = fd.getDirectory();
			gui.window.setTitle(fileName);
			try {
				writeFile(fileAddress + fileName, gui.ta.getText());
				setChangesMade(false);
				updateTitle();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(gui, "File Not Saved: " + e.getMessage());
			}
		}
	}

	public void exitFileHandler() {
		JOptionPane.showMessageDialog(gui, "Notepad Exited");
		System.exit(0);
	}

	public void copyFileHandler() {
		JOptionPane.showMessageDialog(gui, "Under Development");
	}

	private void updateTitle() {
		if (fileName == null) {
			gui.window.setTitle("New Document - MY NOTEPAD - UNSAVED");
		} else {
			if (getChangesMade()) {
				gui.window.setTitle("*" + fileName + "-UNSAVED");
			} else {
				gui.window.setTitle(fileName);
			}
		}
	}

	private void readFile(String filePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			gui.ta.setText("");
			String line;
			while ((line = br.readLine()) != null) {
				gui.ta.append(line + "\n");
			}
		}
	}

	private void writeFile(String filePath, String content) throws IOException {
		try (FileWriter fw = new FileWriter(filePath)) {
			fw.write(content);
		}
	}
}