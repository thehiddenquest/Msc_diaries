package gui;

public class NewJPanel extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public NewJPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        textArea1 = new java.awt.TextArea();
        buttonUploadData = new java.awt.Button();
        buttonMaximumMarks = new java.awt.Button();
        buttonAverageMarks = new java.awt.Button();
        buttonPercentage = new java.awt.Button();
        buttonResult = new java.awt.Button();
        buttonEmailSend = new java.awt.Button();
        buttonLogout = new java.awt.Button();

        buttonUploadData.setLabel("Upload Data");
        buttonUploadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUploadDataActionPerformed(evt);
            }
        });

        buttonMaximumMarks.setLabel("Maximum Marks");
        buttonMaximumMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMaximumMarksActionPerformed(evt);
            }
        });

        buttonAverageMarks.setLabel("Average Marks");
        buttonAverageMarks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAverageMarksActionPerformed(evt);
            }
        });

        buttonPercentage.setLabel("Percentage");
        buttonPercentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPercentageActionPerformed(evt);
            }
        });

        buttonResult.setLabel("Result");
        buttonResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResultActionPerformed(evt);
            }
        });

        buttonEmailSend.setLabel("Email Send");
        buttonEmailSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEmailSendActionPerformed(evt);
            }
        });

        buttonLogout.setLabel("Log Out");
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonUploadData, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMaximumMarks, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAverageMarks, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonResult, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEmailSend, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonUploadData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonMaximumMarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonAverageMarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonResult, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buttonEmailSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(buttonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>                        

    private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // Add your logout handling code here
    }                                            

    private void buttonUploadDataActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // Add your upload data handling code here
    }                                                 

    private void buttonMaximumMarksActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // Add your maximum marks handling code here
    }                                                   

    private void buttonAverageMarksActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // Add your average marks handling code here
    }                                                   

    private void buttonPercentageActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // Add your percentage handling code here
    }                                                  

    private void buttonResultActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Add your result handling code here
    }                                              

    private void buttonEmailSendActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // Add your email send handling code here
    }                                                


    // Variables declaration - do not modify                     
    private java.awt.Button buttonAverageMarks;
    private java.awt.Button buttonEmailSend;
    private java.awt.Button buttonLogout;
    private java.awt.Button buttonMaximumMarks;
    private java.awt.Button buttonPercentage;
    private java.awt.Button buttonResult;
    private java.awt.Button buttonUploadData;
    private java.awt.TextArea textArea1;
    // End of variables declaration                   
}