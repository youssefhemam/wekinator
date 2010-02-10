/*
 * Bigger1.java
 *
 * Created on December 2, 2008, 11:10 AM
 */
//Update.
package wekinator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wekinator.ChuckRunner.ChuckRunnerState;

/**
 *
 * @author  rebecca
 */
public class MainGUI extends javax.swing.JFrame {

    WekinatorInstance wek = WekinatorInstance.getWekinatorInstance();
    boolean isConnected = false;

    PropertyChangeListener hidSetupChangeListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            hidSetupPropertyChange(evt);
        }
    };

    /** Creates new form Bigger1 */
    public MainGUI() {
        initComponents();
        //Anywhere we add a listener, also update to current property.

        FeatureManager fm = wek.getFeatureManager();
        ChuckSystem.getChuckSystem().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                chuckSystemPropertyChange(evt);
            }
        });
        updateGUIforChuckSystem();

        Logger.getLogger(MainGUI.class.getName()).log(Level.INFO, "Here's some info");
        OscHandler.getOscHandler().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                oscHandlerPropertyChange(evt);
            }
        });
        updateGUIforOscStatus();
        fm.hidSetup = wek.getCurrentHidSetup(); //TODO: put in fm
        wek.getCurrentHidSetup().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                hidSetupPropertyChange(evt);
            }
        });
        wek.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                wekinatorInstancePropertyChangeEvent(evt);
            }
        });

        WekinatorLearningManager.getInstance().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                learningManagerPropertyChange(evt);
            }
        });
        ChuckRunner.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                runnerPropertyChange(evt);
            }
        });

    }

    private void runnerPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ChuckRunner.PROP_RUNNERSTATE)) {
            updateRunnerState(ChuckRunner.getRunnerState());
        }

    }

    private void updateRunnerState(ChuckRunner.ChuckRunnerState state) {
        if (state == ChuckRunnerState.RUNNING) {
            wek.useConfigurationNextSession();
        } else { 
            OscHandler.getOscHandler().end();
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

        buttonGroupClassifierSource = new javax.swing.ButtonGroup();
        buttonGroupSettingsSource = new javax.swing.ButtonGroup();
        buttonGroupProcessingSource = new javax.swing.ButtonGroup();
        buttonQuit = new javax.swing.JButton();
        panelMainTabs = new javax.swing.JTabbedPane();
        panelOSC = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        labelOscStatus1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textOscReceive = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textOscSend = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        buttonOscConnect = new javax.swing.JButton();
        buttonOscDisconnect = new javax.swing.JButton();
        labelOscStatus = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        chuckRunnerPanel1 = new wekinator.ChuckRunnerPanel();
        panelTabFeatureConfiguration = new javax.swing.JPanel();
        featureConfigurationPanel1 = new wekinator.FeatureConfigurationPanel();
        panelTabLearningSystemConfiguration = new javax.swing.JPanel();
        learningSystemConfigurationPanel = new wekinator.LearningSystemConfigurationPanel();
        trainRunPanel1 = new wekinator.TrainRunPanel();
        labelGlobalStatus = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        wekMenu = new javax.swing.JMenu();
        preferencesMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        fileMenu = new javax.swing.JMenu();
        menuSaveSystem = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        menuItemViewConsole = new javax.swing.JMenuItem();
        menuItemViewFeatures = new javax.swing.JMenuItem();
        menuItemViewDataset = new javax.swing.JMenuItem();
        menuItemOtfScore = new javax.swing.JMenuItem();
        helpMenu1 = new javax.swing.JMenu();
        contentsMenuItem1 = new javax.swing.JMenuItem();
        aboutMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Wekinator");

        buttonQuit.setText("Quit");
        buttonQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitActionPerformed(evt);
            }
        });

        panelMainTabs.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelMainTabsComponentShown(evt);
            }
        });
        panelMainTabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panelMainTabsStateChanged(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("OSC"));

        labelOscStatus1.setText("Set the ports to begin.");

        jLabel2.setText("Recv Port");

        textOscReceive.setText("6448");

        jLabel9.setText("(Send port used in ChucK)");

        jLabel8.setText("(Receive port used in ChucK)");

        textOscSend.setText("6453");

        jLabel1.setText("Send Port");

        buttonOscConnect.setText("Connect");
        buttonOscConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOscConnectActionPerformed(evt);
            }
        });

        buttonOscDisconnect.setText("Disconnect");
        buttonOscDisconnect.setEnabled(false);
        buttonOscDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOscDisconnectActionPerformed(evt);
            }
        });

        labelOscStatus.setText("OSC Status: Not connected yet.");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(buttonOscConnect)
                        .add(227, 227, 227)
                        .add(buttonOscDisconnect))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                        .add(23, 23, 23)
                        .add(labelOscStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jPanel5Layout.createSequentialGroup()
                                .add(jLabel1)
                                .add(10, 10, 10)
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel5Layout.createSequentialGroup()
                                        .add(textOscReceive, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel9))
                                    .add(jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(textOscSend, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel8))))
                            .add(labelOscStatus1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 151, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .add(labelOscStatus1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(textOscReceive, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(textOscSend, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 44, Short.MAX_VALUE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonOscDisconnect)
                    .add(buttonOscConnect))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labelOscStatus)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("ChucK (experimental & optional!)"));

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(chuckRunnerPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(chuckRunnerPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout panelOSCLayout = new org.jdesktop.layout.GroupLayout(panelOSC);
        panelOSC.setLayout(panelOSCLayout);
        panelOSCLayout.setHorizontalGroup(
            panelOSCLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelOSCLayout.setVerticalGroup(
            panelOSCLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelOSCLayout.createSequentialGroup()
                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        panelMainTabs.addTab("Chuck & OSC Setup", panelOSC);

        org.jdesktop.layout.GroupLayout panelTabFeatureConfigurationLayout = new org.jdesktop.layout.GroupLayout(panelTabFeatureConfiguration);
        panelTabFeatureConfiguration.setLayout(panelTabFeatureConfigurationLayout);
        panelTabFeatureConfigurationLayout.setHorizontalGroup(
            panelTabFeatureConfigurationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTabFeatureConfigurationLayout.createSequentialGroup()
                .add(featureConfigurationPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(261, Short.MAX_VALUE))
        );
        panelTabFeatureConfigurationLayout.setVerticalGroup(
            panelTabFeatureConfigurationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTabFeatureConfigurationLayout.createSequentialGroup()
                .add(featureConfigurationPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        panelMainTabs.addTab("Features Setup", panelTabFeatureConfiguration);

        panelTabLearningSystemConfiguration.setEnabled(false);

        org.jdesktop.layout.GroupLayout panelTabLearningSystemConfigurationLayout = new org.jdesktop.layout.GroupLayout(panelTabLearningSystemConfiguration);
        panelTabLearningSystemConfiguration.setLayout(panelTabLearningSystemConfigurationLayout);
        panelTabLearningSystemConfigurationLayout.setHorizontalGroup(
            panelTabLearningSystemConfigurationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, learningSystemConfigurationPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        panelTabLearningSystemConfigurationLayout.setVerticalGroup(
            panelTabLearningSystemConfigurationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTabLearningSystemConfigurationLayout.createSequentialGroup()
                .add(learningSystemConfigurationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 551, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        panelMainTabs.addTab("Learning Setup", panelTabLearningSystemConfiguration);
        panelMainTabs.addTab("Use it!", trainRunPanel1);

        wekMenu.setText("Wekinator");

        preferencesMenuItem.setText("Preferences");
        wekMenu.add(preferencesMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        wekMenu.add(exitMenuItem);

        menuBar.add(wekMenu);

        fileMenu.setText("File");

        menuSaveSystem.setText("Save learning system");
        menuSaveSystem.setEnabled(false);
        fileMenu.add(menuSaveSystem);

        jMenuItem2.setText("Save model(s)");
        jMenuItem2.setEnabled(false);
        fileMenu.add(jMenuItem2);

        jMenuItem3.setText("Save dataset");
        jMenuItem3.setEnabled(false);
        fileMenu.add(jMenuItem3);

        jMenuItem4.setText("Load global configuration");
        jMenuItem4.setEnabled(false);
        fileMenu.add(jMenuItem4);

        jMenuItem1.setText("Save global configuration");
        jMenuItem1.setEnabled(false);
        fileMenu.add(jMenuItem1);

        menuBar.add(fileMenu);

        viewMenu.setText("View");

        menuItemViewConsole.setText("Console");
        menuItemViewConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewConsoleActionPerformed(evt);
            }
        });
        viewMenu.add(menuItemViewConsole);

        menuItemViewFeatures.setText("Feature viewer");
        menuItemViewFeatures.setEnabled(false);
        menuItemViewFeatures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewFeaturesActionPerformed(evt);
            }
        });
        viewMenu.add(menuItemViewFeatures);

        menuItemViewDataset.setText("Examples (dataset)");
        menuItemViewDataset.setEnabled(false);
        menuItemViewDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemViewDatasetActionPerformed(evt);
            }
        });
        viewMenu.add(menuItemViewDataset);

        menuItemOtfScore.setText("Parameter clipboard");
        menuItemOtfScore.setEnabled(false);
        menuItemOtfScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOtfScoreActionPerformed(evt);
            }
        });
        viewMenu.add(menuItemOtfScore);

        menuBar.add(viewMenu);

        helpMenu1.setText("Help");

        contentsMenuItem1.setText("Contents");
        helpMenu1.add(contentsMenuItem1);

        aboutMenuItem1.setText("About");
        aboutMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItem1ActionPerformed(evt);
            }
        });
        helpMenu1.add(aboutMenuItem1);

        menuBar.add(helpMenu1);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(buttonQuit)
                            .add(labelGlobalStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 493, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(panelMainTabs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 839, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .add(panelMainTabs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 668, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonQuit)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labelGlobalStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void buttonQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitActionPerformed
    if (FeatureExtractionController.isExtracting()) {
        FeatureExtractionController.stopExtracting();
    }

    OscHandler.getOscHandler().end();

    if (ChuckRunner.getRunnerState() == ChuckRunner.ChuckRunnerState.RUNNING) {
        try {
            ChuckRunner.stop();
        } catch (IOException ex) {
        }
    }
    //Want to save settings here!
    wek.saveCurrentSettings();
    System.exit(0);
}//GEN-LAST:event_buttonQuitActionPerformed

private void buttonOscConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOscConnectActionPerformed
    try {
        sendPort = Integer.parseInt(textOscSend.getText());
        receivePort = Integer.parseInt(textOscReceive.getText());
        OscHandler.getOscHandler().startHandshake(receivePort, sendPort);
    } catch (IOException ex) {
        labelGlobalStatus.setText("Error setting up: " + ex.getMessage());
    }
}//GEN-LAST:event_buttonOscConnectActionPerformed

private void buttonOscDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOscDisconnectActionPerformed
    if (FeatureExtractionController.isExtracting()) {
        FeatureExtractionController.stopExtracting();
    }
    OscHandler.getOscHandler().end();
}//GEN-LAST:event_buttonOscDisconnectActionPerformed
 

private void panelMainTabsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelMainTabsComponentShown
    System.out.println("Component shown");
}//GEN-LAST:event_panelMainTabsComponentShown

private void panelMainTabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panelMainTabsStateChanged

}//GEN-LAST:event_panelMainTabsStateChanged

private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
    System.exit(0);
}//GEN-LAST:event_exitMenuItemActionPerformed

private void menuItemViewConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewConsoleActionPerformed
    Console c = Console.getInstance();
    if (c.isVisible()) {
        c.toFront();
    } else {
        c.setVisible(true);
    }
}//GEN-LAST:event_menuItemViewConsoleActionPerformed

private void aboutMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItem1ActionPerformed
    //SHow something about wekinator TODO
}//GEN-LAST:event_aboutMenuItem1ActionPerformed

private void menuItemViewDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewDatasetActionPerformed
        WekinatorLearningManager.getInstance().getLearningSystem().getDataset().showViewer();
}//GEN-LAST:event_menuItemViewDatasetActionPerformed

private void menuItemOtfScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOtfScoreActionPerformed
    LearningSystem learningSystem = WekinatorLearningManager.getInstance().getLearningSystem();
    if (learningSystem != null && learningSystem.getScore() != null) {
            learningSystem.getScore().view();
        }

}//GEN-LAST:event_menuItemOtfScoreActionPerformed

private void menuItemViewFeaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemViewFeaturesActionPerformed
    FeatureExtractionController.showFeatureViewer();
}//GEN-LAST:event_menuItemViewFeaturesActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem1;
    private javax.swing.ButtonGroup buttonGroupClassifierSource;
    private javax.swing.ButtonGroup buttonGroupProcessingSource;
    private javax.swing.ButtonGroup buttonGroupSettingsSource;
    private javax.swing.JButton buttonOscConnect;
    private javax.swing.JButton buttonOscDisconnect;
    private javax.swing.JButton buttonQuit;
    private wekinator.ChuckRunnerPanel chuckRunnerPanel1;
    private javax.swing.JMenuItem contentsMenuItem1;
    private javax.swing.JMenuItem exitMenuItem;
    private wekinator.FeatureConfigurationPanel featureConfigurationPanel1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel labelGlobalStatus;
    private javax.swing.JLabel labelOscStatus;
    private javax.swing.JLabel labelOscStatus1;
    private wekinator.LearningSystemConfigurationPanel learningSystemConfigurationPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemOtfScore;
    private javax.swing.JMenuItem menuItemViewConsole;
    private javax.swing.JMenuItem menuItemViewDataset;
    private javax.swing.JMenuItem menuItemViewFeatures;
    private javax.swing.JMenuItem menuSaveSystem;
    private javax.swing.JTabbedPane panelMainTabs;
    private javax.swing.JPanel panelOSC;
    private javax.swing.JPanel panelTabFeatureConfiguration;
    private javax.swing.JPanel panelTabLearningSystemConfiguration;
    private javax.swing.JMenuItem preferencesMenuItem;
    private javax.swing.JTextField textOscReceive;
    private javax.swing.JTextField textOscSend;
    private wekinator.TrainRunPanel trainRunPanel1;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JMenu wekMenu;
    // End of variables declaration//GEN-END:variables



    private void oscHandlerPropertyChange(PropertyChangeEvent evt) {
        updateGUIforOscStatus();
    }

    protected void updateGUIforOscStatus() {
        OscHandler h = OscHandler.getOscHandler();
        labelOscStatus.setText("OSC status: " + h.getStatusMessage());
        if (h.getConnectionState() == OscHandler.ConnectionState.CONNECTED ||
            h.getConnectionState() == OscHandler.ConnectionState.CONNECTING) {
            buttonOscDisconnect.setEnabled(true);
            buttonOscConnect.setEnabled(false);
            if (h.getConnectionState() == OscHandler.ConnectionState.CONNECTED) {
                isConnected = true;
            } else {
                isConnected = false;
            }
        } else {
            isConnected = false;
            buttonOscDisconnect.setEnabled(false);
            buttonOscConnect.setEnabled(true);
        }

        setFeatureConfigurationPanelEnabled(isConnected);
        if (!isConnected) {
            setLearningSystemConfigurationPanelEnabled(false);
        }

    }

    //TODO: get rid of this.
    private void hidSetupPropertyChange(PropertyChangeEvent evt) {
        System.out.println("GUI RECVD HID SETUP change w/ name: " + evt.getPropertyName());
    }

    private void wekinatorInstancePropertyChangeEvent(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(WekinatorInstance.PROP_CURRENTHIDSETUP)) {

            //TODO: handle null here!
            ((HidSetup) evt.getOldValue()).removePropertyChangeListener(hidSetupChangeListener);
            ((HidSetup) evt.getNewValue()).addPropertyChangeListener(hidSetupChangeListener);
        } else if (evt.getPropertyName().equals(WekinatorInstance.PROP_FEATURECONFIGURATION)) {
            boolean e = (WekinatorInstance.getWekinatorInstance().getFeatureConfiguration() != null);
            System.out.println("enabling feat " + e);
            menuItemViewFeatures.setEnabled(e);
        }
    }

    /** To keep */
    private void chuckSystemPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ChuckSystem.PROP_STATE)) {
            ChuckSystem cs = ChuckSystem.getChuckSystem();
            updateGUIforChuckSystem();
            // panelTabLearningSystemConfiguration.setEnabled(cs.getState() == ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID);
            if (evt.getOldValue() != ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID && evt.getNewValue() == ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID) {
                learningSystemConfigurationPanel.configure(cs.getNumParams(), cs.getParamNames(), cs.isIsParamDiscrete(), WekinatorInstance.getWekinatorInstance().getFeatureConfiguration());
                panelMainTabs.setSelectedComponent(panelTabLearningSystemConfiguration);
            }
        }

    }

    private void updateGUIforChuckSystem() {
        setLearningSystemConfigurationPanelEnabled(ChuckSystem.getChuckSystem().getState() == ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID);
    }

    private void setFeatureConfigurationPanelEnabled(boolean enabled) {
        panelMainTabs.setEnabledAt(panelMainTabs.indexOfComponent(panelTabFeatureConfiguration), enabled);
    }

    private void setLearningSystemConfigurationPanelEnabled(boolean enabled) {
        panelMainTabs.setEnabledAt(panelMainTabs.indexOfComponent(panelTabLearningSystemConfiguration), enabled);
    }

    private void learningManagerPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(WekinatorLearningManager.PROP_LEARNINGSYSTEM)) {
            boolean e = (WekinatorLearningManager.getInstance().getLearningSystem() != null);
            System.out.println("enabling otf data " + e);
            menuItemOtfScore.setEnabled(e);
            menuItemViewDataset.setEnabled(e);

        }

    }
       // private WekaOperator w;
    int sendPort, receivePort;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainGUI b = new MainGUI();
                b.setVisible(true);
            }
        });
    }
}


