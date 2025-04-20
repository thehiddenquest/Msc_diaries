package View.component;


import View.swing.Button;
import View.swing.MyPasswordField;
import View.swing.MyTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import View.component.Message;
import View.model.userDetails;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import Model.DAO.UserDAO;
import Model.main_package.Email;
import Model.main_package.encryptPassword;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

	public userDetails loginInfo() {
		return userIn;
	}

	public  boolean registerInfo() {

		return flag;
	}

	private boolean flag = false;
	private UserDAO user = new UserDAO();
	private userDetails userIn= new userDetails();
	private final encryptPassword epHashed = new encryptPassword();
	private encryptPassword.HashedPasswordWithSalt hashedPasswordWithSalt = null;

	public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
		initComponents();
		initRegister(eventRegister);
		initLogin(eventLogin);
		login.setVisible(false);
		register.setVisible(true);
	}

	private void initRegister(ActionListener eventRegister) {
		register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
		JLabel label = new JLabel("Create Account");
		label.setFont(new Font("sansserif", 1, 30));
		label.setForeground(new Color(7, 164, 121));
		register.add(label);
		final MyTextField txtUser = new MyTextField();
		txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/View/icon/user.png")));
		txtUser.setHint("User Name");
		register.add(txtUser, "w 60%");
		final MyTextField txtEmail = new MyTextField();
		txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/View/icon/mail.png")));
		txtEmail.setHint("Email");
		register.add(txtEmail, "w 60%");
		final MyPasswordField txtPass = new MyPasswordField();
		txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/View/icon/pass.png")));
		txtPass.setHint("Password");
		register.add(txtPass, "w 60%");
		Button cmd = new Button();
		cmd.setBackground(new Color(7, 164, 121));
		cmd.setForeground(new Color(250, 250, 250));
		cmd.addActionListener(eventRegister);
		cmd.setText("SIGN UP");
		register.add(cmd, "w 40%, h 40");
		cmd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String userName = txtUser.getText().trim();
				String email = txtEmail.getText().trim();
				String password = String.valueOf(txtPass.getPassword());
				hashedPasswordWithSalt = epHashed.hashPassword(password);
				String hashedPassword = hashedPasswordWithSalt.getHashedPassword();
				String salt = hashedPasswordWithSalt.getSalt();
				flag = emailValidate(email);
				if(flag == true) {
					int ch = 1;
					flag = user.createNewUser(userName, hashedPassword, salt, email, ch);
				}
			}
		});
	}

	private void initLogin(ActionListener eventLogin) {
		login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
		JLabel label = new JLabel("Sign In");
		label.setFont(new Font("sansserif", 1, 30));
		label.setForeground(new Color(7, 164, 121));
		login.add(label);
		final MyTextField txtEmail = new MyTextField();
		txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/View/icon/user.png")));
		txtEmail.setHint("User Name");
		login.add(txtEmail, "w 60%");
		final MyPasswordField txtPass = new MyPasswordField();
		txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/View/icon/pass.png")));
		txtPass.setHint("Password");
		login.add(txtPass, "w 60%");
		JButton cmdForget = new JButton("Forgot your password ?");
		cmdForget.setForeground(new Color(100, 100, 100));
		cmdForget.setFont(new Font("sansserif", 1, 12));
		cmdForget.setContentAreaFilled(false);
		cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
		login.add(cmdForget);
		cmdForget.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String name = JOptionPane.showInputDialog(null, "Please enter your name.");
				if (!name.equals(null)) {
					int ch = 4;
					String[] emailIDS = user.authenticateUser(name, null, ch);
					String emailID = emailIDS[0];
					Email mail = new Email(emailID);
					final String passcode = mail.sendMail();
					String OTP = JOptionPane.showInputDialog(null, "Please enter OTP");
					flag = epHashed.authenticate(passcode, OTP);
					if (flag) {
						String password = JOptionPane.showInputDialog(null, "Please enter new password");
						hashedPasswordWithSalt = epHashed.hashPassword(password);
						String hashedPassword = hashedPasswordWithSalt.getHashedPassword();
						String salt = hashedPasswordWithSalt.getSalt();
						flag = user.createNewUser(name, hashedPassword, salt, emailID, ch);

					} else
						JOptionPane.showMessageDialog(null, "Wrong OTP inserted","Apocalyptic message", JOptionPane.WARNING_MESSAGE);

				}
				}catch(Exception ed) {
					
				}
			}
		});
		Button cmd = new Button();
		cmd.setBackground(new Color(7, 164, 121));
		cmd.setForeground(new Color(250, 250, 250));
		cmd.addActionListener(eventLogin);
		cmd.setText("SIGN IN");
		login.add(cmd, "w 40%, h 40");
		cmd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String userName = txtEmail.getText().trim();
				String password = String.valueOf(txtPass.getPassword());
				int ch = 2;
				String[] userDetails = user.authenticateUser(userName, password, ch);
				if (userDetails != null) {
					flag = epHashed.authenticate(userDetails[0], password, userDetails[1]);
					userIn.setFlag(flag);
					userIn.setUserName(userDetails[3]);
					userIn.setEmailID(userDetails[2]);
				} else {
					userIn.setFlag(flag);
					userIn.setUserName(null);
					userIn.setEmailID(null);
				}

			}
		});
	}

	public void showRegister(boolean show) {
		if (show) {
			register.setVisible(true);
			login.setVisible(false);
		} else {
			register.setVisible(false);
			login.setVisible(true);
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		login = new javax.swing.JPanel();
		register = new javax.swing.JPanel();

		setLayout(new java.awt.CardLayout());

		login.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
		login.setLayout(loginLayout);
		loginLayout.setHorizontalGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 327, Short.MAX_VALUE));
		loginLayout.setVerticalGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE));

		add(login, "card3");

		register.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
		register.setLayout(registerLayout);
		registerLayout.setHorizontalGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 327, Short.MAX_VALUE));
		registerLayout.setVerticalGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE));

		add(register, "card2");
	}// </editor-fold>//GEN-END:initComponents
	private Boolean emailValidate(String emailID) {
		if (emailID == null || emailID.isEmpty()) {
			return false;
		}
		final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-])+(\\.[a-zA-Z]{2,7})+(\\.[a-zA-Z]{2,8})?$";
		Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		if (!pattern.matcher(emailID).matches()) {
			return false;
		}
		return true;
	}
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel login;
	private javax.swing.JPanel register;
	// End of variables declaration//GEN-END:variables
}
