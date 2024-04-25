package package_number_conversation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Mainframe extends JFrame {
	public Subject sub = new Decimal();
	public Binary b = new Binary();
	public Hexadecimal h = new Hexadecimal();
	public Octadecimal o = new Octadecimal();
	
	JTextField hexField;
	JTextField binaryField;
	JTextField octalField;

	public Mainframe() {
		setTitle("Number Conversation Application");
		setSize(new Dimension(700, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel dec = new JLabel("DECIMAL");
		JLabel hex = new JLabel("HEXADECIMAL");
		JLabel binary = new JLabel("BINARY");
		JLabel octal = new JLabel("OCTAL");

		JTextField decField = new JTextField();
		decField.setPreferredSize(new Dimension(100, 20));

		hexField = new JTextField();
		hexField.setPreferredSize(new Dimension(100, 20));
		hexField.setFocusable(false);
		JCheckBox hexCheck = new JCheckBox();

		binaryField = new JTextField();
		binaryField.setPreferredSize(new Dimension(100, 20));
		binaryField.setFocusable(false);
		JCheckBox binaryCheck = new JCheckBox();

		octalField = new JTextField();
		octalField.setPreferredSize(new Dimension(100, 20));
		octalField.setFocusable(false);
		JCheckBox octalCheck = new JCheckBox();

		JButton convert = new JButton("CONVERT");

		JPanel compPanel = new JPanel();

		compPanel.add(dec);
		compPanel.add(decField);
		compPanel.add(convert);
		compPanel.add(hex);
		compPanel.add(hexField);
		compPanel.add(hexCheck);
		compPanel.add(binary);
		compPanel.add(binaryField);
		compPanel.add(binaryCheck);
		compPanel.add(octal);
		compPanel.add(octalField);
		compPanel.add(octalCheck);

		add(compPanel);

//		hexCheck.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				if(hexCheck.isSelected()) { 
//					sub.attached(h);
//				}
//				else
//					sub.datached(h);
//					
//			}
//		});
//
//		binaryCheck.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				
//				sub.attached(b);
//			}
//		});
//
//		octalCheck.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				sub.attached(o);
//			}
//		});
		isCheck(hexCheck, binaryCheck, octalCheck);

		convert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.print("we are here");
				int temp = Integer.parseInt(decField.getText());
				sub.setState(temp);
				sub.notify_observer();
				if(binaryCheck.isSelected())
					binaryField.setText(b.getBinary());
				if(hexCheck.isSelected())
					hexField.setText(h.getHexa());
				if(octalCheck.isSelected())
					octalField.setText(o.getOcta());
				validate();

			}
		});
	}

	void isCheck(JCheckBox hex, JCheckBox binary, JCheckBox oct) {
		hex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (hex.isSelected()) {
					sub.attached(h);
				} else {
					sub.datached(h);
					hexField.setText("");
				}

			}
		});

		binary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (binary.isSelected()) {
					sub.attached(b);
				} else {
					sub.datached(b);
					binaryField.setText("");
				}

			}
		});

		oct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (oct.isSelected()) {
					sub.attached(o);
				} else {
					sub.datached(o);
					octalField.setText(" ");
				}

			}
		});
	}

	public static void main(String[] args) {
		Mainframe m = new Mainframe();
		m.setVisible(true);
	}

}
