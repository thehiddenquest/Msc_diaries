package com.raven.main;

import com.raven.component.PanelCover;
import com.raven.component.Message;
import com.raven.component.PanelLoading;
import com.raven.component.PanelLoginAndRegister;
import com.raven.component.PanelVerifyCode;
import com.raven.model.ModelUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLayeredPane;
import static javax.swing.text.StyleConstants.Size;
import net.miginfocom.swing.MigLayout;
import org.jdekstop.animation.timing.Animator;
import org.jdekstop.animation.timing.TimingTarget;
import org.jdekstop.animation.timing.TimingTargetAdapter;


public class Main extends javax.swing.JFrame {

    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    
    public Main() {
        initComponents();
        init();
    }
    
    private void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        
        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                register();      
            }
        };
        loginAndRegister = new PanelLoginAndRegister(eventRegister);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if(fraction<=0.5f) {
                    size+ = fraction * addSize;
                }
                else {
                    size+ = addSize - fraction * addSize;
                }
                if(isLogin) {
                    
                    fractionCover = 1f-fraction;
                    fractionLogin = fraction; 
                    if(fraction>=0.5f) {
                    cover.registerRight(fractionCover * 100);
                    }
                    else {
                        cover.loginRight(fractionLogin * 100);
                        
                    }
                }
                else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if(fraction<=0.5f) {
                        cover.registerLeft(fraction * 100);
                    }
                    else {
                        cover.loginLeft((1f - fraction) * 100);
                        
                    }
                    
                }
                
                if (fraction>=0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueof(df.format(fractionCover));
                fractionLogin = Double.valueof(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width" + Size + "%,pos" + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(LoginAndRegister, "width" + loginSize + "%,pos" + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }
            public void begin() {
                super.begin();    
            }
            @Override
            public void end() {
                isLogin = !isLogin;
            }
                super.end();  
        }
        Animator animator = new Animator(800,target);
        animator.setAcceleration(0.5f);
        animator.setDeclaration(0.5f);
        animator.setResolution(0);
        bg.setLayout(layout);
        bg.setLayer(loading,JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode,JLayeredPane.POPUP_LAYER);
        bg.add(loading,"pos 0 0 100% 100%");
        bg.add(verifyCode,"pos 0 0 100% 100%");
        bg.add(cover, "width "+ coverSize + "$, pos 0al 0 n 100 ");
        bg.add(loginAndRegister,"width" + loginSize + "%,pos 0al 0 n 100%");
        cover.addEvent(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
            if(!animator.isRunning()) {
                animator.start();   
                new Thread(new Runnable() {
                    
                 @Override
                 public void run() {
                     try {
                         Thread.sleep(2000);
                         animator.start();
                         
                     }
                     catch (InterruptedException e) {
                         System.err.println(e);
                     }
                 }
                }).start();
                
            }    
           }
        });
    }
    private void register(){
        ModelUser user = loginAndRegister.getUser();
        // loading.setVisible(true);
        // System.out.println(user.getEmail());
        //verifyCode.setVisible(true);   
        showMessage(Message.MessageType.ERROR, "Test Message");
        
    }
    private void showMessage(Message.MessageType messageType,String message) {
        Message ms= new Message();
        ms.showMesssage(messageType, message);
        TimingTarget target = new TimingTargerAdapter() {
            @Override
            public void begin() {
                f(!ms.isShow()){
                    bg.add(ms,"pos 0.5al -30", 0 );
                    ms.setVisible(true);
                    bg.repaint();
                    
                }
            } 
            @Override
            public void timingEvent(float fraction) {
                float f;
                if(ms.isShow()) {
                    f=40 * (1f - fraction);
                }else {
                    f=40*fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int)(f-30));
                bg.repaint();
                bg.revalidate();
            }
            @Override
            public void end() {
                if(ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                }
                else {
                    ms.setShow(true);
                }
            }
        }
        Animator animator = new Animator(300,target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
       
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
