package CodeConversionController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CodeConversionModel.BinaryCodeConvertor;
import CodeConversionModel.DecimalNumberSubject;
import CodeConversionModel.HexaDecimalCodeConvertor;
import CodeConversionModel.OctalCodeConvertor;

public class ActionController implements ActionListener, ItemListener {

	private int subjectValue;
	private JTextField decimalInputTextField;
	private JCheckBox hexCheckBox, binaryCheckBox, octCheckBox;
	private JLabel hexLabel, binaryLabel, octLabel;
	private DecimalNumberSubject dec = new DecimalNumberSubject();
	private HexaDecimalCodeConvertor hex = new HexaDecimalCodeConvertor();
	private OctalCodeConvertor oct = new OctalCodeConvertor();
	private BinaryCodeConvertor bin = new BinaryCodeConvertor();

	public void getaction(JButton jb, JTextField decimalInputTextField) {
		jb.addActionListener(this);
		this.decimalInputTextField = decimalInputTextField;

	}

	public void setCheckBox(JCheckBox hexCheckBox, JCheckBox binaryCheckBox, JCheckBox octCheckBox) {
		this.hexCheckBox = hexCheckBox;
		this.binaryCheckBox = binaryCheckBox;
		this.octCheckBox = octCheckBox;
        hexCheckBox.addItemListener(this);
        binaryCheckBox.addItemListener(this);
        octCheckBox.addItemListener(this);
	}

	public void setOutputLabel(JLabel hexLabel, JLabel binaryLabel, JLabel octLabel) {
		this.hexLabel = hexLabel;
		this.binaryLabel = binaryLabel;
		this.octLabel = octLabel;
	}

	private int setSubjectValue() {
		int intChecker = 0;
		String temp = decimalInputTextField.getText();
		String regex = "-?\\d+";

		// Check if the input matches the regular expression
		if (Pattern.matches(regex, temp)) {
			intChecker = 1;
			this.subjectValue = Integer.parseInt(temp);
		}
		return intChecker;
	}

	private int isAttached()
	{
		int checker = 0;
		if (hexCheckBox.isSelected()) {
            hexLabel.setText(hex.getHexCode());
            checker =1;
        } else {
            hexLabel.setText("");
        }

        if (binaryCheckBox.isSelected()) {
            binaryLabel.setText(bin.getBinCode());
            checker =1;
        } else {
            binaryLabel.setText("");
        }

        if (octCheckBox.isSelected()) {
            octLabel.setText(oct.getOctCode());
            checker =1;
        } else {
            octLabel.setText("");
        }
        return checker;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "Convert":
			int check = setSubjectValue();
			if (check == 1) {
				dec.setState(subjectValue);
			} else {
				JOptionPane.showMessageDialog(null, "Please Enter Integer Value");
				decimalInputTextField.setText("");
				break;
			}
			dec.notify_observer();
			int check2 = isAttached();
			if(check2 == 0)
			{
				JOptionPane.showMessageDialog(null, "Please Select a Code to Convert The Value");
			}
			break;
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox checkBox = (JCheckBox) e.getItem();

        if (checkBox == hexCheckBox) {
            if (checkBox.isSelected()) {
				dec.attached(hex);
			} else {
				dec.datached(hex);
                hexLabel.setText("");
            }
        } else if (checkBox == binaryCheckBox) {
            if (checkBox.isSelected()) {
				dec.attached(bin);
			} else {
				dec.datached(bin);
                binaryLabel.setText("");
            }
        } else if (checkBox == octCheckBox) {
            if (checkBox.isSelected()) {
				dec.attached(oct);
			} else {
				dec.datached(oct);
                octLabel.setText("");
            }
        }
    }

}
