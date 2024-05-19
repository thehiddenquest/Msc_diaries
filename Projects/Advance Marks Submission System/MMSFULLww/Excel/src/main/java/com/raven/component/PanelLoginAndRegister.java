package com.raven.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.raven.model.ModelUser;
import com.raven.swing.Button;
import com.raven.swing.MyPasswordField;
import com.raven.swing.MyTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    public ModelUser getUser() {
        return user;
    }
    
    private ModelUser user;
   
    public PanelLoginAndRegister(ActionListener eventRegister) {
        initComponents();
        initRegister(eventRegister);
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }
    private void initRegister(ActionListener eventRegister) {
        register.setLayout(new MigLayout("wrap", "push[center]push","push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Create Account");
        Label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);
        MyTextField txtUser = Icon prefixicon MytextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtUser.setHint("Name");
        register.add(txtUser,"w 600%");
        MyTextField txtEmail = Icon prefixicon MytextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtEmail.setHint("Email");
        register.add(txtEmail,"w 600%");
        MyPasswordField txtPass = Icon prefixicon MyPasswordFill();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtPass.setHint("Password");
        register.add(txtPass,"w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(7,164,121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd setText("SIGN UP");
        register.add(cmd, "w 40%, h 40"); 
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               String userName = txtUser.getText().trim();
               String email = txtEmail.getText().trim();
               String password = String.valueOf(txtPass.getPassword());
               user = new ModelUser(0, userName, email, password);
            }
        });
    }
    
    private void initLogin() {
      login.setLayout(new MigLayout("wrap", "push[center]push","push[]25[]10[]10[]25[]push")); 
       JLabel label = new JLabel("SIGN IN");
       Label.setFont(new Font("sansserif", 1, 30));
       label.setForeground(new Color(7, 164, 121));
       login.add(label);
        MyTextField txtUser = Icon prefixicon MytextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtUser.setHint("Name");
        login.add(txtUser,"w 600%");
        MyTextField txtEmail = Icon prefixicon MytextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtEmail.setHint("Email");
        login.add(txtEmail,"w 600%");
        MyPasswordField txtPass = Icon prefixicon MyPasswordFill();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtPass.setHint("Password");
        login.add(txtPass,"w 600%");
        JButton cmdForget = new JButton("Forgot your password?");
        cmdForgot.setForeground(new color(100, 100, 100));
        cmdForgot.setFont(new Font("sansserif", 1, 12));
        cmdForgot.setConten Cursor cursor Led(False);
        cmdForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForgot);
        Button cmd = new Color color();
        cmd.setBackground(new color(7,164,121));
        cmd.setForeground(new color(250, 250, 250));
        cmd setText("SIGN IN");
        login.add(cmd," w 40%, h 40");  
    }
    
    public void showRegister(booleanshow) {
        if(show) {
            register.setVisible(true);
            login.setVisible(false);
            
        }
        else {
             register.setVisible(false);
            login.setVisible(true);
            
        }
    }
    
    private void init() {
        title = new JLabel("Welcome Back!");
        title.setFont(new Font("sansseris", 1,30));
        title.setForeground(new color(245, 245, 245));
        add(title);
        description = new JLabel("to keep connected with us please");
        description.setForeground(new color(245, 245, 245));
        add(description);
        description = new JLabel("login with your personal info");
        description.setForeground(new color(245, 245, 245));  
        add(description);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        jPanel1.add(jPanel2);

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
