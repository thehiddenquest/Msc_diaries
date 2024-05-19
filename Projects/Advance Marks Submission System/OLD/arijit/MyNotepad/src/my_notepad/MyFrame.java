package my_notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

public class MyFrame extends JFrame implements ActionListener {
	public JTextArea area = new JTextArea();
	UndoManager manager = new UndoManager();
	
	public MyFrame() {
		setTitle("Notepad");
		setSize(600, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
	}

	public void initComponents() {

		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu format = new JMenu("Format");
		JMenu view = new JMenu("View");
		JMenu help = new JMenu("Help");

		JMenuItem newit = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");

		newit.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);

		JMenuItem selectAll = new JMenuItem("Select All");
		JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		JMenuItem cut = new JMenuItem("Cut");
	    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		JMenuItem delete = new JMenuItem("Delete");
		
		undo.addActionListener(this);
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		selectAll.addActionListener(this);
		delete.addActionListener(this);
		
	    JMenu fontfamily = new JMenu("Font");
	    
	    JMenuItem SAN_SERIF = new JMenuItem("SAN_SERIF, Font.PLAIN, 20");
	    
	    SAN_SERIF.addActionListener(this);
	    
		file.add(newit);
		file.add(open);
		file.add(save);
		file.add(exit);

		edit.add(selectAll);
		edit.add(undo);
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(delete);
		
		format.add(fontfamily);
		
		fontfamily.add(SAN_SERIF);

		mb.add(file);
		mb.add(edit);
		mb.add(format);
		mb.add(view);
		mb.add(help);

		add(mb, BorderLayout.NORTH);

	}

	public static void main(String[] args) {
		MyFrame f = new MyFrame();
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else if (cmd.equalsIgnoreCase("New")) {
			area.setBackground(Color.BLACK);
			area.setForeground(Color.WHITE);
			JScrollPane sp = new JScrollPane(area);
			add(sp);
			validate();
		} else if (cmd.equalsIgnoreCase("Open")) {
			try {
				JFileChooser fc = new JFileChooser();
				area.setBackground(Color.BLACK);
				area.setForeground(Color.WHITE);
				if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					FileReader fr = new FileReader(f.getAbsoluteFile());
					area.read(fr, null);
					JScrollPane sp = new JScrollPane(area);
					add(sp);
					validate();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (cmd.equalsIgnoreCase("Save")) {
			try {
				JFileChooser fc = new JFileChooser();
				if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					File f = new File(fc.getSelectedFile()+"");
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					area.write(bw);
					validate();
					f.exists();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (cmd.equalsIgnoreCase("Select All")) {
			area.selectAll();
		}
		else if (cmd.equalsIgnoreCase("Undo")) {
			area.getDocument().addUndoableEditListener(manager);
			validate();
		}
		else if (cmd.equalsIgnoreCase("Copy")) {
			area.copy();
		} else if (cmd.equalsIgnoreCase("Paste")) {
			area.paste();
		} else if (cmd.equalsIgnoreCase("Cut")) {
			area.cut();
		} else if (cmd.equalsIgnoreCase("Delete")) {
			area.replaceSelection("");
		}
        else if (cmd.equalsIgnoreCase("SAN_SERIF, Font.PLAIN, 20")) {
        	 area.setFont(new Font("SAN_SERIF", Font.PLAIN, 30));
        }
	}
}
