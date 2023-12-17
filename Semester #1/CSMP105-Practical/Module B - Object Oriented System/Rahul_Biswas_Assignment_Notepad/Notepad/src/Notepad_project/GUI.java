package Notepad_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.color.ColorSpace;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.*;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame window;
	public JTextArea ta = new JTextArea();
	public JScrollPane sp = new JScrollPane(ta);
	private Image img;
	public JMenuBar mb; // Declare Menu Bar
	/* File Option and Its inner Menu items */
	private JMenu file;
	private JMenuItem newFile, openFile, saveFile, saveasFile, exitFile;
	/* Edit Option and Its inner Menu items */
	private JMenu edit;
	private JMenuItem copyEdit, undoEdit, cutEdit, deleteEdit, redoEdit;
	/* Format Option and Its inner Menu items */
//	private JMenu format;

	private eventListening listener = new eventListening(this);

	public GUI() {
		window = new JFrame();
		window.setTitle("My Notepad");
		window.setSize(800, 600);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sp.setBorder(BorderFactory.createEmptyBorder());
		img = new ImageIcon(getClass().getResource("/Image/notepad_icon.png")).getImage();
		window.setIconImage(img);
		window.setVisible(true);
		initComponents();
	}

	public void initComponents() {
		setColors();
		createMenuBar();
		addMenuBarToFrame();
		customizeMenuIcons();
	}

	private void setColors() {
		ta.setBackground(Color.WHITE);
		window.getContentPane().setBackground(Color.WHITE);
	}

	private void createMenuBar() {
		mb = new JMenuBar();
		createFileMenu(mb);
		createEditMenu(mb);
		createFormatMenu(mb);
	}

	private void createFileMenu(JMenuBar mb) {
		file = new JMenu("File");

		newFile = new JMenuItem("New");
		openFile = new JMenuItem("Open");
		saveFile = new JMenuItem("Save");
		saveasFile = new JMenuItem("Save As");
		exitFile = new JMenuItem("Exit");

		file.add(newFile);
		newFile.addActionListener(listener);
		file.add(openFile);
		openFile.addActionListener(listener);
		file.add(saveFile);
		file.add(saveasFile);
		file.add(exitFile);
		exitFile.addActionListener(listener);

		mb.add(file);
	}

	private void createEditMenu(JMenuBar mb) {
		edit = new JMenu("Edit");

		copyEdit = new JMenuItem("Copy");
		cutEdit = new JMenuItem("Cut");
		deleteEdit = new JMenuItem("Delete");
		undoEdit = new JMenuItem("Undo");
		redoEdit = new JMenuItem("Redo");

		edit.add(copyEdit);
		edit.add(cutEdit);
		edit.add(undoEdit);
		edit.add(redoEdit);
		edit.add(deleteEdit);

		mb.add(edit);
	}

	private void createFormatMenu(JMenuBar mb) {
		JMenu format = new JMenu("Format");
		mb.add(format);
	}

	private void addMenuBarToFrame() {
		window.add(mb, BorderLayout.NORTH);
	}

	private void customizeMenuIcons() {
		// Customize menu icons
		file.setIconTextGap(5);
		edit.setIconTextGap(5);
//		format.setIconTextGap(5);
//		file.setIconTextGap(5);
//		file.setIconTextGap(5);
		newFile.setIconTextGap(10);
		openFile.setIconTextGap(10);
		saveFile.setIconTextGap(10);
		saveasFile.setIconTextGap(10);
		exitFile.setIconTextGap(10);
		copyEdit.setIconTextGap(10);
		undoEdit.setIconTextGap(10); // 
		undoEdit.setIconTextGap(10);
		cutEdit.setIconTextGap(10);
		deleteEdit.setIconTextGap(10);
		redoEdit.setIconTextGap(10);

	}
}
