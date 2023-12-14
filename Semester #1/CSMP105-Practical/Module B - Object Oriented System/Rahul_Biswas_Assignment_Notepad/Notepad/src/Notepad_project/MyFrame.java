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
	JTextArea ta = new JTextArea();
	JScrollPane sp = new JScrollPane(ta);

	public MyFrame() {
		setTitle("My Notepad");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
	}

	public void initComponents() {
		ta.setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);

		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");

		JMenuItem newFile = new JMenuItem("New");
		JMenuItem openFile = new JMenuItem("Open");
		JMenuItem saveFile = new JMenuItem("Save");
		JMenuItem saveasFile = new JMenuItem("Save As");
		JMenuItem exitFile = new JMenuItem("Exit");

		file.add(newFile);
		newFile.addActionListener(this);
		file.add(openFile);
		openFile.addActionListener(this);
		file.add(saveFile);
		file.add(saveasFile);
		file.add(exitFile);
		exitFile.addActionListener(this);

		JMenu edit = new JMenu("Edit");

		JMenuItem copyEdit = new JMenuItem("Copy");
		JMenuItem cutEdit = new JMenuItem("Cut");
		JMenuItem deleteEdit = new JMenuItem("Delete");

		edit.add(copyEdit);
		edit.add(cutEdit);
		edit.add(deleteEdit);

		JMenu format = new JMenu("Format");

		mb.add(file);
		mb.add(edit);
		mb.add(format);
		add(mb, BorderLayout.NORTH);

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
