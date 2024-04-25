package CodeConversionView;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import CodeConversionController.ActionController;

public class NewJFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
    private JLabel label1;
    private JTextField jTextField1;
    private JButton jButton1;
    private JPanel jPanel2;
    private JCheckBox jCheckBox1;
    private JLabel jLabel2;
    private JCheckBox jCheckBox2;
    private JLabel jLabel3;
    private JCheckBox jCheckBox3;
    private JLabel jLabel4;
    private ActionController actionCommand = new ActionController();

    public NewJFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Number Code Converter");
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());

        initComponentsPanel1();
        initComponentsPanel2();

        pack();
        setVisible(true);
    }

    private void initComponentsPanel1() {
        jPanel1 = new JPanel();
        jPanel1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new GridBagLayout());

        label1 = new JLabel();
        label1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        label1.setName("decimalNumber");
        label1.setText("Enter the decimal Number :");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 136;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(1, 11, 0, 109);
        jPanel1.add(label1, gridBagConstraints);

        jTextField1 = new JTextField();
        jTextField1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        jTextField1.setAutoscrolls(false);
        jTextField1.setBorder(new LineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.setCursor(new Cursor(java.awt.Cursor.TEXT_CURSOR));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 197;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 38, 0, 0);
        jPanel1.add(jTextField1, gridBagConstraints);
        

        jButton1 = new JButton();
        jButton1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        jButton1.setText("Convert");
      //  jButton1.addActionListener(actionCommand);
        actionCommand.getaction(jButton1,jTextField1);

            // Set the subject value after adding ActionListener
 //           actionCommand.setSubjectValue(jTextField1.getText());
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(12, 101, 61, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 98;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(24, 27, 0, 32);
        getContentPane().add(jPanel1, gridBagConstraints);
    }

     private void initComponentsPanel2() {
        jPanel2 = new JPanel();
        jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Converted Number", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI Black", 3, 14)));
        jPanel2.setLayout(new GridBagLayout());

        jCheckBox1 = new JCheckBox();
        jCheckBox1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jCheckBox1.setText("Hex code");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(28, 11, 0, 0);
        jPanel2.add(jCheckBox1, gridBagConstraints);

        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        jLabel2.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 32, 39, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        jCheckBox2 = new JCheckBox();
        jCheckBox2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jCheckBox2.setText("Binary Code");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(28, 62, 0, 0);
        jPanel2.add(jCheckBox2, gridBagConstraints);

        jLabel3 = new JLabel();
        jLabel3.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        jLabel3.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 83, 39, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        jCheckBox3 = new JCheckBox();
        jCheckBox3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jCheckBox3.setText("Octal code");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(28, 66, 0, 32);
        jPanel2.add(jCheckBox3, gridBagConstraints);

        jLabel4 = new JLabel();
        jLabel4.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        jLabel4.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 87, 39, 0);
        jPanel2.add(jLabel4, gridBagConstraints);
        actionCommand.setCheckBox(jCheckBox1, jCheckBox2, jCheckBox3);
        actionCommand.setOutputLabel(jLabel2, jLabel3, jLabel4);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 28;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 41, 23, 0);
        getContentPane().add(jPanel2, gridBagConstraints);
    }

}
