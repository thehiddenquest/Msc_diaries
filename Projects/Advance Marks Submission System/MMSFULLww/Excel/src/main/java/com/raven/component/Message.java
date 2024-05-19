package com.raven.component;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Color;


public class Message extends javax.swing.JPanel {

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
    private MessageType message = MessageType.SUCCESS;
    private boolean show;
    
    public Message() {
        
        initComponents();
        setOpaque(false);
        setVisible(false);
    }
    
    public void showMesssage(MessageType messageType, String message) {
        this.messageType = messageType;
        IbMessage.setText(message);
        if(messageType==MessageType.SUCCESS) {
            IbMessage.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/success.png")));
            
        }
        else {
            IbMessage.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/error.png")));
            
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IbMessage = new javax.swing.JLabel();

        IbMessage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IbMessage.setText("Message");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    protected void PaintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D)grphcs;
        if(messageType==MessageType.SUCCESS) {
            g2.setColor(new Color(15, 174, 37));
        }
        else {
            g2.setColor(new Color(240, 52, 53));
            
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(new Color(245, 245, 245));
        g2.drawRect(0, 0, getWidth()-1,getHeight()-1);
        super.paintComponent(grphcs);
    }
    public static enum MessageType {
        SUCCESS, ERROR
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IbMessage;
    // End of variables declaration//GEN-END:variables
}
