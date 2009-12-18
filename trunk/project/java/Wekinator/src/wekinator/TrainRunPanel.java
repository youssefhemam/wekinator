/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TrainRunPanel.java
 *
 * Created on Dec 4, 2009, 7:40:57 PM
 */
package wekinator;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import wekinator.LearningAlgorithms.*;
import wekinator.LearningSystem.*;
import wekinator.util.OverwritePromptingFileChooser;
import wekinator.util.SerializedFileUtil;
import wekinator.util.Util;
/**
 *
 * @author rebecca
 */
public class TrainRunPanel extends javax.swing.JPanel {

    LearningSystem ls = null;
    PropertyChangeListener learningSystemChangeListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            learningSystemChange(evt);
        }
    };

    /** Creates new form TrainRunPanel */
    public TrainRunPanel() {
        initComponents();

          WekinatorLearningManager.getInstance().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(WekinatorLearningManager.PROP_LEARNINGSYSTEM)) {
                    setLearningSystem(WekinatorLearningManager.getInstance().getLearningSystem());
                }
            }
        });

    }

    public void setLearningSystem(LearningSystem ls) {
        System.out.println("setting learning system in train run pane");
        if (this.ls != null) {
            this.ls.removePropertyChangeListener(learningSystemChangeListener);
        }
        this.ls = ls;
        if (this.ls != null) {
            ls.addPropertyChangeListener(learningSystemChangeListener);
            setButtonsEnabled();
        } else {
            //TOOD: disable all
        }
        buildPanel.setLearningSystem(ls);
        trainPanel.setLearningSystem(ls);
        runPanel.setLearningSystem(ls);
        editPanel.setLearningSystem(ls);
        CardLayout c = (CardLayout) layoutPanel.getLayout();
        c.show(layoutPanel, "cardBuild");
    }



    private void setButtonsEnabled() {
        LearningSystemTrainingState t = ls.getSystemTrainingState();
        EvaluationState e = ls.getEvaluationState();
        DatasetState d = ls.getDatasetState();
        LearningAlgorithmsInitializationState i = ls.getInitializationState();

        boolean enableRun = (t == LearningSystemTrainingState.TRAINED
                 && e != EvaluationState.EVALUTATING
                 && i == LearningAlgorithmsInitializationState.ALL_INITIALIZED );

        boolean enableTrain = (d == DatasetState.HAS_DATA
                 && i == LearningAlgorithmsInitializationState.ALL_INITIALIZED);

        boolean enableCollect = (i == LearningAlgorithmsInitializationState.ALL_INITIALIZED);
        boolean enableConfigure = (i == LearningAlgorithmsInitializationState.ALL_INITIALIZED);

        buttonRun.setEnabled(enableRun);
        buttonTrain.setEnabled(enableTrain);
        buttonCollect.setEnabled(enableCollect);
        buttonConfigure.setEnabled(enableConfigure);

        //TODO: Also go back to another pane if current is bogus?
    }

    private void learningSystemChange(PropertyChangeEvent evt) {
        setButtonsEnabled();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        layoutPanel = new javax.swing.JPanel();
        buildPanel = new wekinator.BuildPanel();
        trainPanel = new wekinator.TrainPanel();
        runPanel = new wekinator.RunPanel();
        editPanel = new wekinator.EditPanel();
        menuPanel = new javax.swing.JPanel();
        buttonCollect = new javax.swing.JButton();
        buttonTrain = new javax.swing.JButton();
        buttonRun = new javax.swing.JButton();
        buttonConfigure = new javax.swing.JButton();
        buttonShh = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        layoutPanel.setLayout(new java.awt.CardLayout());
        layoutPanel.add(buildPanel, "cardBuild");
        layoutPanel.add(trainPanel, "cardTrain");
        layoutPanel.add(runPanel, "cardRun");
        layoutPanel.add(editPanel, "cardEdit");

        buttonCollect.setText("COLLECT DATA");
        buttonCollect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCollectActionPerformed(evt);
            }
        });

        buttonTrain.setText("TRAIN");
        buttonTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTrainActionPerformed(evt);
            }
        });

        buttonRun.setText("RUN");
        buttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunActionPerformed(evt);
            }
        });

        buttonConfigure.setText("CONFIGURE & EVALUATE");
        buttonConfigure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConfigureActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout menuPanelLayout = new org.jdesktop.layout.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonTrain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(buttonRun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(buttonConfigure, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(buttonCollect, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );

        menuPanelLayout.linkSize(new java.awt.Component[] {buttonConfigure, buttonRun, buttonTrain}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(menuPanelLayout.createSequentialGroup()
                .add(buttonCollect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonTrain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(buttonRun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(buttonConfigure, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        menuPanelLayout.linkSize(new java.awt.Component[] {buttonConfigure, buttonRun, buttonTrain}, org.jdesktop.layout.GroupLayout.VERTICAL);

        buttonShh.setText("audio off");
        buttonShh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShhActionPerformed(evt);
            }
        });

        buttonSave.setText("Save model file");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(buttonShh)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(menuPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(buttonSave))
                        .add(26, 26, 26)
                        .add(layoutPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layoutPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(menuPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonShh)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonSave)))
                .add(114, 114, 114))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonShhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShhActionPerformed
        OscHandler.getOscHandler().stopSound();
}//GEN-LAST:event_buttonShhActionPerformed

    private void buttonConfigureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConfigureActionPerformed
        CardLayout c = (CardLayout) layoutPanel.getLayout();
        c.show(layoutPanel, "cardEdit");
}//GEN-LAST:event_buttonConfigureActionPerformed

    private void buttonCollectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCollectActionPerformed
        CardLayout c = (CardLayout) layoutPanel.getLayout();
        c.show(layoutPanel, "cardBuild");
}//GEN-LAST:event_buttonCollectActionPerformed

    private void buttonTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTrainActionPerformed
        CardLayout c = (CardLayout) layoutPanel.getLayout();
        c.show(layoutPanel, "cardTrain");
}//GEN-LAST:event_buttonTrainActionPerformed

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunActionPerformed
        CardLayout c = (CardLayout) layoutPanel.getLayout();
        c.show(layoutPanel, "cardRun");
}//GEN-LAST:event_buttonRunActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
    try {
            File file = findLearningSystemFileToSave();
            if (file != null) {
                ls.writeToFile(file);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid attempt to write to file", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_buttonSaveActionPerformed

        private File findLearningSystemFileToSave() {
        JFileChooser fc = new OverwritePromptingFileChooser();
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String location = WekinatorInstance.getWekinatorInstance().getSettings().getLastFeatureFileLocation();
        if (location == null || location.equals("")) {
            location = WekinatorInstance.getWekinatorInstance().getSettings().getDefaultFeatureFileLocation();
        }
        fc.setCurrentDirectory(new File(location));

        File file = null;

        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            file = fc.getSelectedFile();
            WekinatorInstance.getWekinatorInstance().getSettings().setLastClassifierFileLocation(Util.getCanonicalPath(file));

        }
        return file;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private wekinator.BuildPanel buildPanel;
    private javax.swing.JButton buttonCollect;
    private javax.swing.JButton buttonConfigure;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton buttonRun;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonShh;
    private javax.swing.JButton buttonTrain;
    private wekinator.EditPanel editPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel layoutPanel;
    private javax.swing.JPanel menuPanel;
    private wekinator.RunPanel runPanel;
    private wekinator.TrainPanel trainPanel;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        JFrame f = new JFrame();
        TrainRunPanel p = new TrainRunPanel();
        f.add(p);
        f.setVisible(true);
    }
}
