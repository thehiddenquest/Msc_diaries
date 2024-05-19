package com.raven.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;


public class PanelLoading extends javax.swing.JPanel {
    public class PanelLoading () {
        initComponents();
        setOpaque(false);
        setFocusCycleRoot(true);
        setVisible(false);
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>  
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        
        Graphics2D q2 = (Graphics2D)grphcs;
        q2.setColor(new Color(255, 255, 255));
        q2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        q2.fillRect(0, 0, getWidth(), getHeight());
        q2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
        
    }   
    private javax.swing.JLabel jLabel1;
    
}

