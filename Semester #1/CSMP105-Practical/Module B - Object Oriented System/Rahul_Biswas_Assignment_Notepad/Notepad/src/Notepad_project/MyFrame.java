package Notepad_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.color.ColorSpace;

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

public class MyFrame extends JFrame implements ActionListener {
	private JTextArea ta = new JTextArea();
	private JScrollPane sp = new JScrollPane(ta);
	private JMenuBar mb; // Declare Menu Bar
	/* File Option and Its inner Menu items */
	private JMenu file;
	private JMenuItem newFile;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveasFile;
	private JMenuItem exitFile;
	/* Edit Option and Its inner Menu items */
	private JMenu edit;
	private JMenuItem copyEdit;
	private JMenuItem cutEdit;
	private JMenuItem deleteEdit;
	/* Format Option and Its inner Menu items */
	private JMenu format;

	public MyFrame() {
		setTitle("My Notepad");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		getContentPane().setBackground(Color.WHITE);
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
		newFile.addActionListener(this);
		file.add(openFile);
		openFile.addActionListener(this);
		file.add(saveFile);
		file.add(saveasFile);
		file.add(exitFile);
		exitFile.addActionListener(this);

		mb.add(file);
	}

	private void createEditMenu(JMenuBar mb) {
		edit = new JMenu("Edit");

		copyEdit = new JMenuItem("Copy");
		cutEdit = new JMenuItem("Cut");
		deleteEdit = new JMenuItem("Delete");

		edit.add(copyEdit);
		edit.add(cutEdit);
		edit.add(deleteEdit);

		mb.add(edit);
	}

	private void createFormatMenu(JMenuBar mb) {
		JMenu format = new JMenu("Format");
		mb.add(format);
	}

	private void addMenuBarToFrame() {
		add(mb, BorderLayout.NORTH);
	}

	private void customizeMenuIcons() {
		// Customize menu icons
		file.setIconTextGap(20);
		newFile.setIconTextGap(20);
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
			JOptionPane.showMessageDialog(this, "Notepad Exited");
			System.exit(0);
		}
		if (actions.equalsIgnoreCase("New")) {
			add(sp);
			validate();
		}
		if (actions.equalsIgnoreCase("Open")) {
			try {
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					FileReader fr = new FileReader(f);
					ta.read(fr, null);
					BufferedReader br = new BufferedReader(fr);
					String line;
					while ((line = br.readLine()) != null) {
						ta.append(line + "\n");
					}
					br.close();
					add(sp);
					validate();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
}
