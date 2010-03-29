/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RunPanel.java
 *
 * Created on Dec 6, 2009, 9:55:25 PM
 */
package wekinator;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wekinator.WekinatorLearningManager.Mode;

/**
 *
 * @author rebecca
 */
public class RunPanel extends javax.swing.JPanel {
    DecimalFormat dd = new DecimalFormat("#.##");

    int numParams = 0;
    ParameterMiniViewer[] paramPanels = null;
    boolean[] takesDist = null;
    int[] maxClass = null;
    
    boolean isRunning = false;
    LearningSystem learningSystem = null;

    /** Creates new form RunPanel */
    public RunPanel() {
        initComponents();
        WekinatorLearningManager.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                learningManagerChange(evt);
            }
        });
        WekinatorLearningManager.getInstance().addOutputChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                outputChanged();
            }

        });
    }

    void setLearningSystem(LearningSystem ls) {
        if (learningSystem == ls) {
            return;
        }

        //Configure params pane
        if (ls == null) {
            return;
        }

        this.learningSystem = ls;
        numParams = ls.getNumParams();
        panelOutputs.removeAll();
        paramPanels = new ParameterMiniViewer[numParams];
        SimpleDataset d = learningSystem.getDataset();
        for (int i = 0; i < numParams; i++) {
            if (d != null) {
                paramPanels[i] = new ParameterMiniViewer(d.getParameterName(i), 0.0);
            } else {
                paramPanels[i] = new ParameterMiniViewer("Param" + i, 0.0);
            }
            paramPanels[i].setPreferredSize(new Dimension(409, 28));
            panelOutputs.add(paramPanels[i]);
        }

        takesDist = new boolean[numParams];
        maxClass = new int[numParams];

        for (int i = 0; i < numParams; i++) {
            takesDist[i] = (learningSystem.isParamUsingDistribution(i));
            if (takesDist[i]) {
                maxClass[i] = learningSystem.getNumMaxValsForParameter()[i];
            } else {
                maxClass[i] = 0;
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        buttonAddToClipboard = new javax.swing.JButton();
        scrollOutputPanel = new javax.swing.JScrollPane();
        panelOutputs = new javax.swing.JPanel();
        buttonRun = new javax.swing.JButton();

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Generated parameters"));

        buttonAddToClipboard.setText("add to clipboard");
        buttonAddToClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddToClipboardActionPerformed(evt);
            }
        });

        panelOutputs.setLayout(new javax.swing.BoxLayout(panelOutputs, javax.swing.BoxLayout.Y_AXIS));
        scrollOutputPanel.setViewportView(panelOutputs);

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(buttonAddToClipboard)
                .addContainerGap(153, Short.MAX_VALUE))
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, scrollOutputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .add(buttonAddToClipboard))
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                    .add(scrollOutputPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 246, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );

        buttonRun.setText("Run");
        buttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonRun, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
            .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(buttonRun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAddToClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddToClipboardActionPerformed
        if (WekinatorInstance.getWekinatorInstance().getPlayalongScore() == null) {
            WekinatorInstance.getWekinatorInstance().setPlayalongScore(new PlayalongScore(WekinatorInstance.getWekinatorInstance().getLearningSystem().getNumParams()));
        }
        WekinatorInstance.getWekinatorInstance().getPlayalongScore().addParams(WekinatorLearningManager.getInstance().getOutputs(), 1.0);
        
}//GEN-LAST:event_buttonAddToClipboardActionPerformed

    private void buttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunActionPerformed
                if (!isRunning) {
            startRunning();
        } else {
            stopRunning();
        }
}//GEN-LAST:event_buttonRunActionPerformed

    private void learningManagerChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equals(WekinatorLearningManager.PROP_MODE)) {
            WekinatorLearningManager.Mode m = WekinatorLearningManager.getInstance().getMode();
            setButtonRunningIsRunning(m == Mode.RUNNING);
        }
    }

     private void setButtonRunningIsRunning(boolean y) {
        if (y) {
            buttonRun.setText("Stop");
           // buttonRecord.setBackground(Color.RED); : TODO: overide LAF
            isRunning = true;
        } else {
            buttonRun.setText("Run");
            isRunning = false;
        }
    }

    private void setParams(double[] p) {
        //TODO TODO TODO will break using dist\
        int next = 0;
        for (int i = 0; i < paramPanels.length; i++) {
            if (takesDist[i]) {
                String s = "";
                for (int j = 0; j < maxClass[i]; j++) {
                    
                    s += dd.format(p[next++]);
                    if (j != maxClass[i] -1) {
                        s += "|";
                    }
                    
                }
                paramPanels[i].setValue(s);

            } else {
                paramPanels[i].setValue(p[next++]);
            } 
        }


        for (int i = 0; i < paramPanels.length; i++) {
            if (takesDist[i]) {
            } else {
              paramPanels[i].setValue(p[i]);

            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddToClipboard;
    private javax.swing.JButton buttonRun;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel panelOutputs;
    private javax.swing.JScrollPane scrollOutputPanel;
    // End of variables declaration//GEN-END:variables

    private void startRunning() {
        WekinatorLearningManager.getInstance().startRunning();
        OscHandler.getOscHandler().startSound();
    }

    private void stopRunning() {
        WekinatorLearningManager.getInstance().stopRunning();
                OscHandler.getOscHandler().stopSound();

    }


   private void outputChanged() {
        setParams(WekinatorLearningManager.getInstance().getOutputs());
   }
}
