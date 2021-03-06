/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChuckRunnerPanel.java
 *
 * Created on Nov 15, 2009, 4:13:51 PM
 */
package wekinator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//TODO: add setting of whether or not to prompt user in case of chuck error.
/**
 *
 * @author rebecca
 */
public class ChuckRunnerPanel extends javax.swing.JPanel {
 

    /** Creates new form ChuckRunnerPanel */
    public ChuckRunnerPanel() {
        initComponents();
        ChuckRunner.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                runnerPropertyChange(evt);
            }
        }); 
        updateConfigurationListeners(null, ChuckRunner.getConfiguration(), configurationChangeListener); 
        if (ChuckRunner.getConfiguration() != null) {
            updateGUIForConfiguration(ChuckRunner.getConfiguration().isUsable());
        } else {
                        updateGUIForConfiguration(false);

        }
        updateRunnerState(ChuckRunner.getRunnerState());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelAboutConfiguration = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        buttonRun = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();
        buttonStop = new javax.swing.JButton();
        labelAboutConfiguration1 = new javax.swing.JLabel();
        labelAboutConfiguration2 = new javax.swing.JLabel();
        labelAboutConfiguration4 = new javax.swing.JLabel();
        labelSynth = new javax.swing.JLabel();
        labelFeatures = new javax.swing.JLabel();
        labelPlayalong = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        labelAboutConfiguration.setText("Edit");

        jButton1.setText("Edit ChucK configuration...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonRun.setText("Run");
        buttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunActionPerformed(evt);
            }
        });

        labelStatus.setText("Chuck status: ");

        buttonStop.setText("Stop");
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });

        labelAboutConfiguration1.setText("Synth:");

        labelAboutConfiguration2.setText("Custom features:");

        labelAboutConfiguration4.setText("Score player:");

        labelSynth.setText("jLabel2");
        labelSynth.setMaximumSize(new java.awt.Dimension(200, 16));

        labelFeatures.setText("10 features from custom_centroid.ck. 5 features from OSC.");
        labelFeatures.setMaximumSize(new java.awt.Dimension(200, 16));

        labelPlayalong.setText("a_long_score_player_name.ck.");

        jLabel1.setText("Current configuration:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1)
                    .add(jLabel1)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(23, 23, 23)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(labelAboutConfiguration1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(labelSynth, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                            .add(labelAboutConfiguration)
                            .add(layout.createSequentialGroup()
                                .add(labelAboutConfiguration2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(labelFeatures, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(labelAboutConfiguration4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(labelPlayalong, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(buttonRun)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(buttonStop))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(labelStatus)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labelAboutConfiguration)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelAboutConfiguration1)
                    .add(labelSynth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelAboutConfiguration2)
                    .add(labelFeatures, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelAboutConfiguration4)
                    .add(labelPlayalong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 43, Short.MAX_VALUE)
                .add(labelStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonRun)
                    .add(buttonStop)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ChuckConfigurationForm p = new ChuckConfigurationForm(ChuckRunner.getConfiguration());
        p.setVisible(true);
        Logger.getLogger(ChuckRunnerPanel.class.getName()).warning("TEST");
}//GEN-LAST:event_jButton1ActionPerformed

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunActionPerformed
        try {
            ChuckRunner.run();
        } catch (IOException ex) {
            Logger.getLogger(ChuckRunnerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_buttonRunActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        try {
            ChuckRunner.stop();
        } catch (IOException ex) {
            Logger.getLogger(ChuckRunnerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_buttonStopActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRun;
    private javax.swing.JButton buttonStop;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelAboutConfiguration;
    private javax.swing.JLabel labelAboutConfiguration1;
    private javax.swing.JLabel labelAboutConfiguration2;
    private javax.swing.JLabel labelAboutConfiguration4;
    private javax.swing.JLabel labelFeatures;
    private javax.swing.JLabel labelPlayalong;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelSynth;
    // End of variables declaration//GEN-END:variables

       protected PropertyChangeListener configurationChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                configurationPropertyChange(evt);
            }
        };

    private void runnerPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ChuckRunner.PROP_RUNNERSTATE)) {
            updateRunnerState(ChuckRunner.getRunnerState());
        } else if (evt.getPropertyName().equals(ChuckRunner.PROP_CONFIGURATION)) {
            ChuckConfiguration o = (ChuckConfiguration)evt.getOldValue();
            ChuckConfiguration n = (ChuckConfiguration)evt.getNewValue();
            updateConfigurationListeners(o, n, configurationChangeListener);
            updateGUIForConfiguration(n.isUsable());
        }
    }

    private void configurationPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ChuckConfiguration.PROP_USABLE)) {
            updateGUIForConfiguration(ChuckRunner.getConfiguration().isUsable());
        }

    }

    private void updateConfigurationListeners(ChuckConfiguration o, ChuckConfiguration n, PropertyChangeListener configurationChangeListener) {
        if (o != null) {
            o.removePropertyChangeListener(configurationChangeListener);
        }
        if (n != null) {
            n.addPropertyChangeListener(configurationChangeListener);
        }
    }

    private void updateRunnerState(ChuckRunner.ChuckRunnerState state) {
        if (state == ChuckRunner.ChuckRunnerState.RUNNING) {

            buttonRun.setEnabled(false);
            buttonStop.setEnabled(true);
            labelStatus.setText("Status: Chuck running successfully.");
        } else if (state == ChuckRunner.ChuckRunnerState.NOT_RUNNING) {
            labelStatus.setText("Status: Chuck not running.");
            buttonRun.setEnabled(true);
            buttonStop.setEnabled(false);

        } else if (state == ChuckRunner.ChuckRunnerState.TRYING_TO_RUN) {
            labelStatus.setText("Status: Chuck encountered error");
            buttonRun.setEnabled(true);
            buttonStop.setEnabled(false);
            int lResponse = JOptionPane.showConfirmDialog(this,
                    "Chuck encountered errors:\n" + ChuckRunner.getLastErrorMessages() + "\n Do you want to try to proceed anyway?", "",
                    JOptionPane.YES_NO_OPTION);

            if (lResponse != JOptionPane.YES_OPTION) {
                ChuckRunner.ignoreRunErrors(false);
            } else {
                ChuckRunner.ignoreRunErrors(true);
            }
        }

    }

    private void updateGUIForConfiguration(boolean usable) {
        buttonRun.setEnabled(usable);
        if (!usable) {
            labelAboutConfiguration.setText("No valid configuration");
            setAboutConfigurationVisible(false);
        } else {
            labelAboutConfiguration.setText("Configuration is usable and ready to run");
            populateConfigurationInfo();
            setAboutConfigurationVisible(true);
        }
    }

    private void populateConfigurationInfo() {
        String s = "";
        ChuckConfiguration c = ChuckRunner.getConfiguration();
        if (c.isCustomChuckFeatureExtractorEnabled()) {
            String fname = getLastPart(c.getCustomChuckFeatureExtractorFilename());
            s = "Custom ChucK features from " + fname + ". ";
        
       /* if (c.isOscFeatureExtractorEnabled()) {
            s += c.getNumOSCFeaturesExtracted() + " features from OSC.";
        } */
        } else {
            s = "No custom ChucK feature extractor.";
        }
        labelFeatures.setText(s);

        if (c.isUseChuckSynthClass()) {
            s = "Using chuck synth " + getLastPart(c.getChuckSynthFilename()) + ".";
        } else {
           // s = "Using OSC synth: ";
            s = c.getOscSynthConfiguration().getDescription();
            /*int n = c.getNumOscSynthParams();
            s += Integer.toString(n);
            if (n > 0) {
                if (c.getIsOscSynthParamDiscrete()[0]) {
                    s += " integer params, max " + c.getNumOscSynthMaxParamVals() + " values per param";
                    if (c.getOscUseDistribution()[0]) {
                        s += ", using distribution";
                    }
                } else {
                    s += " real-valued params";
                }
            } */
        }
        labelSynth.setText(s);


        if (c.isIsPlayalongLearningEnabled()) {
            s = " using " + getLastPart(c.getPlayalongLearningFile());
        } else {
            s = " not enabled ";
        }
        labelPlayalong.setText(s);
        
    }

    private String getLastPart(String filename) {
        File f = new File(filename);
        return f.getName();
    }

    private void setAboutConfigurationVisible(boolean visible) {
        labelFeatures.setVisible(visible);
        labelPlayalong.setVisible(visible);
        labelSynth.setVisible(visible);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                // panel.setVisible(true);
                JFrame frame = new JFrame();
                ChuckRunnerPanel panel = new ChuckRunnerPanel();

                frame.add(panel);
                frame.setVisible(true);

                Console c = Console.getInstance();

                c.setVisible(true);
            }
        });
    }
}
