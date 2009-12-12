/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AdaboostM1SettingsPanel.java
 *
 * Created on Dec 11, 2009, 1:33:23 PM
 */

package wekinator.LearningAlgorithms;

import javax.swing.JPanel;

/**
 *
 * @author rebecca
 */
public class AdaboostM1SettingsPanel extends javax.swing.JPanel implements LearningAlgorithmSettingsPanel {

    AdaboostM1LearningAlgorithm la = null;

    /** Creates new form AdaboostM1SettingsPanel */
    public AdaboostM1SettingsPanel() {
        initComponents();
    }

    public AdaboostM1SettingsPanel(AdaboostM1LearningAlgorithm la) {
        initComponents();
        setLearningAlgorithm(la);
    }

    public void setLearningAlgorithm(AdaboostM1LearningAlgorithm la) {
        this.la = la;
        textNumRounds.setText(la.getClassifier().getNumIterations() + ""); //TODO: Might classifier be null?
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textNumRounds = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        textNumRounds.setText("100");

        jLabel1.setText("Number of training rounds");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(textNumRounds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(textNumRounds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel1))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void applySettings() throws Exception {
        boolean valid = validateSettings();
        if (valid) {
            int i = Integer.parseInt(textNumRounds.getText());
            la.getClassifier().setNumIterations(i);
        } else {
            throw new Exception("Invalid settings");
        }
    }

    public boolean validateSettings() {
        try {
            int i = Integer.parseInt(textNumRounds.getText());
            if (i > 0)
                return true;
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    public void reset() {
        textNumRounds.setText(la.getClassifier().getNumIterations()+ "");
    }

    public JPanel getPanel() {
        return this;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField textNumRounds;
    // End of variables declaration//GEN-END:variables

}
