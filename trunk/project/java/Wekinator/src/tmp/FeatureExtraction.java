/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FeatureExtraction.java
 *
 * Created on Oct 26, 2009, 1:58:54 PM
 */

package tmp;

/**
 *
 * @author rebecca
 */
public class FeatureExtraction extends javax.swing.JFrame {

    /** Creates new form FeatureExtraction */
    public FeatureExtraction() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        checkFFT = new javax.swing.JCheckBox();
        checkCentroid = new javax.swing.JCheckBox();
        checkFlux = new javax.swing.JCheckBox();
        checkRMS = new javax.swing.JCheckBox();
        checkRolloff = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        textFFTSize = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textWindowSize = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboWindowType = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        textAudioRate = new javax.swing.JTextField();
        checkFFT1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jCheckBox1.setText("Use same features for all audio channels");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        checkFFT.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkFFT.setText("FFT");
        checkFFT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFFTActionPerformed(evt);
            }
        });

        checkCentroid.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkCentroid.setText("Centroid");
        checkCentroid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCentroidActionPerformed(evt);
            }
        });

        checkFlux.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkFlux.setText("Flux");
        checkFlux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFluxActionPerformed(evt);
            }
        });

        checkRMS.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkRMS.setText("RMS");
        checkRMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRMSActionPerformed(evt);
            }
        });

        checkRolloff.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkRolloff.setText("Rolloff");
        checkRolloff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRolloffActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jLabel3.setText("FFT size");

        textFFTSize.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        textFFTSize.setText("512");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jLabel4.setText("Window size");

        textWindowSize.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        textWindowSize.setText("256");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jLabel5.setText("Window type");

        comboWindowType.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        comboWindowType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hamming", "Hann", "Rectangular" }));

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        jLabel6.setText("Rate / Hop size (ms):");

        textAudioRate.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        textAudioRate.setText("100");

        checkFFT1.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        checkFFT1.setText("External feature extractor");
        checkFFT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFFT1ActionPerformed(evt);
            }
        });

        jButton1.setText("Choose...");

        jLabel2.setText("Using external extractor feature_extractors/mfcc1.ck");
        jLabel2.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(checkFFT)
                            .add(checkCentroid)
                            .add(checkFlux))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel4Layout.createSequentialGroup()
                                .add(checkRMS)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 136, Short.MAX_VALUE)
                                .add(jLabel3))
                            .add(jPanel4Layout.createSequentialGroup()
                                .add(checkRolloff)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 100, Short.MAX_VALUE)
                                .add(jLabel4))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(textWindowSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(textFFTSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(comboWindowType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(textAudioRate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(286, Short.MAX_VALUE))))
            .add(jPanel4Layout.createSequentialGroup()
                .add(checkFFT1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addContainerGap(179, Short.MAX_VALUE))
            .add(jPanel4Layout.createSequentialGroup()
                .add(27, 27, 27)
                .add(jLabel2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(checkFFT)
                    .add(checkRMS)
                    .add(textFFTSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(checkCentroid)
                        .add(checkRolloff))
                    .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(textWindowSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel4)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(checkFlux)
                    .add(jLabel5)
                    .add(comboWindowType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(textAudioRate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(checkFFT1)
                    .add(jButton1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel2)
                .addContainerGap())
        );

        jLabel1.setText("Features for channel:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jCheckBox1))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(51, 51, 51)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox1)
                .add(19, 19, 19)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Audio", jPanel3);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 490, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 339, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Sensor", jPanel1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 490, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 339, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Video", jPanel2);

        jButton3.setText("Save this feature configuration");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Load saved feature configuration");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jButton2)
                        .addContainerGap(260, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jButton3)
                        .addContainerGap(274, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 385, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton3)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkFFTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFFTActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkFFTActionPerformed

    private void checkCentroidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCentroidActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkCentroidActionPerformed

    private void checkFluxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFluxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkFluxActionPerformed

    private void checkRMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRMSActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkRMSActionPerformed

    private void checkRolloffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRolloffActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_checkRolloffActionPerformed

    private void checkFFT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFFT1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkFFT1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FeatureExtraction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkCentroid;
    private javax.swing.JCheckBox checkFFT;
    private javax.swing.JCheckBox checkFFT1;
    private javax.swing.JCheckBox checkFlux;
    private javax.swing.JCheckBox checkRMS;
    private javax.swing.JCheckBox checkRolloff;
    private javax.swing.JComboBox comboWindowType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField textAudioRate;
    private javax.swing.JTextField textFFTSize;
    private javax.swing.JTextField textWindowSize;
    // End of variables declaration//GEN-END:variables

}
