package Notepad_project;

import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.FlowLayout;
import java.awt.Image;
//import java.awt.TextArea;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.awt.color.ColorSpace;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.text.*;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public JFrame window;
	public boolean wordWrapOn = false;
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
	private JMenu format, formatFont, formatFontSize;
	public JMenuItem formatwrap;
	private JMenuItem formatFontArial, formatFontCSMS, formatFontTNR, formatFontSize10, formatFontSize12, formatFontSize16,
	formatFontSize20, formatFontSize24;

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
		saveFile.addActionListener(listener);
		file.add(saveasFile);
		saveasFile.addActionListener(listener);
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
		copyEdit.addActionListener(listener);
		edit.add(cutEdit);
		edit.add(undoEdit);
		edit.add(redoEdit);
		edit.add(deleteEdit);

		mb.add(edit);
	}

	private void createFormatMenu(JMenuBar mb) {
		format = new JMenu("Format");
		formatwrap = new JMenuItem("Word Wrap: OFF");
		formatFont = new JMenu("Font");
		formatFontSize = new JMenu("Font Size");
		formatFontArial = new JMenuItem("Arial");
		formatFontCSMS = new JMenuItem("Comic Sans MS");
		formatFontTNR = new JMenuItem("Times New Roman");
		formatFontSize10 = new JMenuItem("10");
		formatFontSize12 = new JMenuItem("12");
		formatFontSize16 = new JMenuItem("16");
		formatFontSize20 = new JMenuItem("20");
		formatFontSize24 = new JMenuItem("24");
		
		mb.add(format);
		
		format.add(formatwrap);
		formatwrap.addActionListener(listener);
		formatwrap.setActionCommand("Word Wrap");
		
		format.add(formatFont);
		formatFont.add(formatFontArial);
		formatFontArial.addActionListener(listener);
		formatFont.add(formatFontCSMS);
		formatFontCSMS.addActionListener(listener);
		formatFont.add(formatFontTNR);
		formatFontTNR.addActionListener(listener);
		
		format.add(formatFontSize);
		formatFontSize.add(formatFontSize10);
		formatFontSize10.addActionListener(listener);
		formatFontSize.add(formatFontSize12);
		formatFontSize12.addActionListener(listener);
		formatFontSize.add(formatFontSize16);
		formatFontSize16.addActionListener(listener);
		formatFontSize.add(formatFontSize20);
		formatFontSize20.addActionListener(listener);
		formatFontSize.add(formatFontSize24);
		formatFontSize24.addActionListener(listener);
	}

	private void addMenuBarToFrame() {
		window.add(mb, BorderLayout.NORTH);
	}

	private void customizeMenuIcons() {
		// Customize menu icons
		file.setIconTextGap(5);
		edit.setIconTextGap(5);
		format.setIconTextGap(5);
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
