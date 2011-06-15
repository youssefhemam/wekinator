/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wekinator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import wekinator.ChuckRunner.ChuckRunnerState;
import wekinator.Plog.Msg;
import wekinator.util.*;

/**
 * Ideally, captures current state of the system, including all active component objects
 *
 * @author rebecca
 */
public class WekinatorInstance {

    protected EventListenerList oscFeatureNamesListenerList = new EventListenerList();
    protected EventListenerList featureParameterSetupDoneListenerList = new EventListenerList();
    protected EventListenerList autorunBegunListenerList = new EventListenerList();

    protected ChuckConfiguration configuration = null;
    private WekinatorSettings settings = null;
    protected HidSetup currentHidSetup;
    private static final String settingsSaveFile = "wekinator.usersettings";
    public static final String PROP_CURRENTHIDSETUP = "currentHidSetup";
    private LinkedList<Handler> handlers;
    private static final String chuckConfigSaveFile = "lastChuckConfig";
    protected FeatureConfiguration featureConfiguration = null;
    public static final String PROP_FEATURECONFIGURATION = "featureConfiguration";
    protected LearningSystem learningSystem = null;
    public static final String PROP_LEARNINGSYSTEM = "learningSystem";
    protected PlayalongScore playalongScore = null;
    public static final String PROP_PLAYALONGSCORE = "playalongScore";
    public static final String PROP_NUMPARAMS = "numParams";
    protected String[] customOscFeatureNames = new String[0];
    protected boolean hasCustomOscFeatureNames = false;
    private static WekinatorInstance ref = null;
    private ChangeEvent oscFeatureNameChangeEvent = null;
    private ChangeEvent featureParameterSetupDoneEvent = null;
    private ChangeEvent autorunBegunEvent = null;
    public static int recvPort = 6448;
    public static int sendPort = 6453;

    public boolean hasCustomOscFeatureNames() {
        return hasCustomOscFeatureNames;
    }

    public void setCustomOscFeatureNames(String[] n) {
        customOscFeatureNames = n;
        hasCustomOscFeatureNames = (n != null && n.length != 0);
        fireOscFeatureNamesChanged();
    }

    public String[] getCustomOscFeatureNames() {
        return customOscFeatureNames;
    }
    protected int numParams = -1;

    /**
     * Get the value of numParams
     *
     * @return the value of numParams
     */
    public int getNumParams() {
        return numParams;
    }

    /**
     * Set the value of numParams
     *
     * @param numParams new value of numParams
     */
    public void setNumParams(int numParams) {
        int oldNum = this.numParams;
        this.numParams = numParams;

        PlayalongScore score = new PlayalongScore(numParams);
        setPlayalongScore(score);
        propertyChangeSupport.firePropertyChange(PROP_NUMPARAMS, oldNum, numParams);

    }

    private void chuckSystemUpdated(PropertyChangeEvent evt) {
        ChuckSystem cs = ChuckSystem.getChuckSystem();
        if (evt.getPropertyName().equals(ChuckSystem.PROP_STATE)) {
            if (evt.getOldValue() != ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID && evt.getNewValue() == ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID) {
                this.setNumParams(SynthProxy.getNumParams());

                //This should inform all GUI listeners that we know what the new learning system will look like.                
                fireFeatureParameterSetupDone();

                //Invalidate prior learning system if it's incompatible with this #, type of features & params
                //Otherwise leave it unchanged
                if (learningSystem != null) {
                    if (learningSystem.getNumParams() != SynthProxy.getNumParams()) {
                        setLearningSystem(null);
                    } else {
                        if (learningSystem.getDataset() != null) {
                            for (int i = 0; i < learningSystem.getNumParams(); i++) {
                                if (learningSystem.getDataset().isParameterDiscrete(i) != SynthProxy.isParamDiscrete(i)
                                        || (SynthProxy.isParamDiscrete(i) && SynthProxy.paramMaxValue(i) != learningSystem.getDataset().maxLegalDiscreteParamValue(i))) {


                                    boolean a = learningSystem.getDataset().isParameterDiscrete(i);
                                    boolean b= SynthProxy.isParamDiscrete(i);
                                    int c = learningSystem.getDataset().maxLegalDiscreteParamValue(i);
                                    int d =SynthProxy.paramMaxValue(i);


                                    setLearningSystem(null);
                                    break;
                                }
                            }
                        } else {
                            setLearningSystem(null); //hack: don't want to do this; better to store in learning system cont/disc params
                        }
                    }
                }
            }


            //Moved from MainGUI:
            if (evt.getOldValue() != ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID && evt.getNewValue() == ChuckSystem.ChuckSystemState.CONNECTED_AND_VALID) {
                //TODO: Test that this doesn't break when feature configuration changes.

                if (WekinatorInstance.getWekinatorInstance().getLearningSystem() == null) {
                    if (WekinatorRunner.getLearningSystemFile() != null) {
                        try {
                            LearningSystem ls = LearningSystem.readFromFile(WekinatorRunner.getLearningSystemFile());
                            if (canUse(ls)) {
                                setLearningSystem(ls);
                                //X : Add listener to learning system panel: learningSystemConfigurationPanel.setLearningSystem(ls);
                                //X : doublecheck have listener to panelMainTabs/mainGUI: panelMainTabs.setSelectedComponent(trainRunPanel1);
                                if (WekinatorRunner.runAutomatically) {
                                    if (ls != null && ls.isIsRunnable()) {
                                        startAutoRun();
                                      //  WekinatorLearningManager.getInstance().startRunning();
                                    } else {
                                        System.out.println("Cannot run automatically: learning system not ready");
                                    }

                                    //TODO: Test that MainGUI minimizes correctly.
                                }
                            } else {
                                //TODO: more info
                                System.out.println("This learning system is not configured correctly");
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, "Could not load learning system from file");
                            Logger.getLogger(WekinatorInstance.class.getName()).log(Level.WARNING, null, ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the value of playalongScore
     *
     * @return the value of playalongScore
     */
    public PlayalongScore getPlayalongScore() {
        return playalongScore;
    }

    /**
     * Set the value of playalongScore
     *
     * @param playalongScore new value of playalongScore
     */
    public void setPlayalongScore(PlayalongScore playalongScore) {
        PlayalongScore oldPlayalongScore = this.playalongScore;
        this.playalongScore = playalongScore;
        propertyChangeSupport.firePropertyChange(PROP_PLAYALONGSCORE, oldPlayalongScore, playalongScore);
    }

    /**
     * Get the value of learningSystem
     *
     * @return the value of learningSystem
     */
    public LearningSystem getLearningSystem() {
        return learningSystem;
    }

    /**
     * Set the value of learningSystem
     *
     * @param learningSystem new value of learningSystem
     */
    public synchronized void setLearningSystem(LearningSystem learningSystem) {

        if (WekinatorLearningManager.getInstance().getMode() == WekinatorLearningManager.Mode.RUNNING) {
            WekinatorLearningManager.getInstance().stopRunning();
            OscHandler.getOscHandler().stopSound();
        } else if (WekinatorLearningManager.getInstance().getMode() == WekinatorLearningManager.Mode.EVALUATING) {
            WekinatorLearningManager.getInstance().stopEvaluating();
        } else if (WekinatorLearningManager.getInstance().getMode() == WekinatorLearningManager.Mode.TRAINING) {
            WekinatorLearningManager.getInstance().stopTraining();
        }

        LearningSystem oldLearningSystem = this.learningSystem;
        this.learningSystem = learningSystem;
        propertyChangeSupport.firePropertyChange(PROP_LEARNINGSYSTEM, oldLearningSystem, learningSystem);
    }

    /**
     * Get the value of featureConfiguration
     *
     * @return the value of featureConfiguration
     */
    public FeatureConfiguration getFeatureConfiguration() {
        return featureConfiguration;
    }

    /**
     * Set the value of featureConfiguration
     *
     * @param featureConfiguration new value of featureConfiguration
     */
    public void setFeatureConfiguration(FeatureConfiguration featureConfiguration) {
        FeatureConfiguration oldFeatureConfiguration = this.featureConfiguration;
        this.featureConfiguration = featureConfiguration;
        propertyChangeSupport.firePropertyChange(PROP_FEATURECONFIGURATION, oldFeatureConfiguration, featureConfiguration);

        //New: invalidate learning configuration
        //Do this before telling chuck system to wait for new settings.
        if (!FeatureConfiguration.equal(featureConfiguration, oldFeatureConfiguration)) {
            //Problem: Multiple threads may try to update LearningSYstemConfigurationPanel -- set to null here, set to non-null in LearningSystemConfigurationPanel
            // when OSC receives chuck settings update.
            setLearningSystem(null);
        }

        if (featureConfiguration == null) {
            // setForOscState(); //TODO: check on this -- not sensible?
        } else {
            ChuckSystem.getChuckSystem().waitForNewSettings();
            try {
                OscHandler.getOscHandler().sendFeatureConfiguration(featureConfiguration);
            } catch (IOException ex) {
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
            OscHandler.getOscHandler().requestChuckSettingsArray();
            //  if (state == State.OSC_CONNECTION_MADE) {
            //      setState(State.FEATURE_SETUP_DONE);
            //  }
        }
    }

    boolean canUse(LearningSystem ls) {
        if (ls == null || featureConfiguration == null) {
            System.out.print("Cannot use this learning system: ");
            if (ls == null)
                System.out.println("Learning system is null.");
            else
                System.out.println("Feature conifguration is null.");

            return false;
        }

        SimpleDataset sd = ls.getDataset();
        if (sd != null) {
            int sF = sd.getNumFeatures();
            int tF = featureConfiguration.getNumFeaturesEnabled();
            int sP = sd.getNumParameters();


            if (sd.getNumFeatures() != featureConfiguration.getNumFeaturesEnabled()
                    || sd.getNumParameters() != SynthProxy.getNumParams()) {
                System.out.println("Cannot use this learning system: feature/param mismatch");
                System.out.println("Loaded " + sF + "/" + tF + ", expecting " + sP + "/" + SynthProxy.getNumParams());
                return false;
            }

            for (int i =0; i < SynthProxy.getNumParams(); i++) {
                if (SynthProxy.isParamDiscrete(i) != sd.isParameterDiscrete(i)) {
                    System.out.print("Cannot use learning system: Parameter " + i + " discrete is ");
                    System.out.println(sd.isParameterDiscrete(i) + ", expected " + SynthProxy.isParamDiscrete(i));
                    return false;
                }
                if (SynthProxy.isParamDiscrete(i)) {
                    if (SynthProxy.paramMaxValue(i) < sd.getMaxLegalDiscreteParamValues()[i]) {
                        System.out.print("Cannot use learning system: Parameter " + i + " requires max value of ");
                        System.out.println(SynthProxy.paramMaxValue(i)
                                + " or less, learning system has max value of " + sd.getMaxLegalDiscreteParamValues()[i]);
                        return false;
                    }
                }
            }

            return true;

        }

        return false;

    }

    /* public enum State {

    INIT,
    OSC_CONNECTION_MADE,
    FEATURE_SETUP_DONE,
    MODELS_SETUP_DONE
    }; */
    //protected State state = State.INIT;
    // public static final String PROP_STATE = "state";
    /**
     * Get the value of state
     *
     * @return the value of state
     */
    /*public State getState() {
    return state;
    } */
    /**
     * Set the value of state
     *
     * @param state new value of state
     */
    /* public void setState(State state) {
    State oldState = this.state;
    this.state = state;
    propertyChangeSupport.firePropertyChange(PROP_STATE, oldState, state);
    } */
    /**
     * Get the value of featureManager
     *
     * @return the value of featureManager
     */
    /* public FeatureManager getFeatureManager() {
    return featureManager;
    } */
    /**
     * Set the value of featureManager
     *
     * @param featureManager new value of featureManager
     */
    /*  public void setFeatureManager(FeatureManager featureManager) {
    FeatureManager oldFeatureManager = this.featureManager;
    this.featureManager = featureManager;
    propertyChangeSupport.firePropertyChange(PROP_FEATUREMANAGER, oldFeatureManager, featureManager);
    } */
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Get the value of currentHidSetup
     *
     * @return the value of currentHidSetup
     */
    public HidSetup getCurrentHidSetup() {
        return currentHidSetup;
    }

    /**
     * Set the value of currentHidSetup
     *
     * @param currentHidSetup new value of currentHidSetup
     */
    public void setCurrentHidSetup(HidSetup currentHidSetup) {
        try {
            HidSetup oldCurrentHidSetup = this.currentHidSetup;
            this.currentHidSetup = currentHidSetup;
            if (currentHidSetup != null) {
                currentHidSetup.startHidRun();
                currentHidSetup.startHidInit();
            }
            propertyChangeSupport.firePropertyChange(PROP_CURRENTHIDSETUP, oldCurrentHidSetup, currentHidSetup);
        } catch (IOException ex) {
            Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of configuration
     *
     * @return the value of configuration
     */
    public ChuckConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Set the value of configuration
     *
     * @param configuration new value of configuration
     */
    private void setConfiguration(ChuckConfiguration configuration) {
        this.configuration = configuration;
    }

    public static void setupPlog() {
        String currentDir = Util.getCanonicalPath(new File(""));
        if (currentDir != null) {
            try {
                // String currentDir = Util.getCanonicalPath(new File(""));
                File tmpf = new File(currentDir);
                if (tmpf != null && tmpf.getParentFile() != null && tmpf.getParentFile().getParentFile() != null) {

                    File projectDir = (new File(currentDir)).getParentFile().getParentFile().getParentFile();
                    Plog.setup(Util.getCanonicalPath(projectDir) + File.separator + "logging");
                    Plog.log(Msg.LOAD);
                }
            } catch (IOException ex) {
                try {
                    //   String currentDir = Util.getCanonicalPath(new File(""));
                    File projectDir = (new File(currentDir)).getParentFile().getParentFile();
                    Plog.setup(Util.getCanonicalPath(projectDir) + File.separator + "logging");
                    Plog.log(Msg.LOAD);

                } catch (Exception ex2) {
                    System.out.println("Couldn't log." + ex.getMessage());
                    System.exit(0);
                }
            }
        }
    }

    //TODO: Modify so that can be created from Java, not command line.
    private WekinatorInstance() {
        FileInputStream fin = null;


        boolean useChuckFromCL = (WekinatorRunner.chuckFile != null);
        String currentDir = Util.getCanonicalPath(new File(""));

        try {
            // fin = new FileInputStream(settingsSaveFile);
            // ObjectInputStream sin = new ObjectInputStream(fin);
            settings = WekinatorSettings.readFromFile(new File(settingsSaveFile));

            settings.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    settingsPropertyChange(evt);
                }
            });

            if (!useChuckFromCL) {
                String cLoc = settings.getDefaultSettingsDirectory() + File.separator + ChuckConfiguration.getDefaultLocation() + File.separator + chuckConfigSaveFile + "." + ChuckConfiguration.getFileExtension();
                configuration = ChuckConfiguration.readFromFile(new File(cLoc));
                System.out.println("read chuck config from " + cLoc);
            }

            System.out.println("Loaded user settings");
        } catch (Exception ex) {
            System.out.println("No user settings found");
            settings = new WekinatorSettings();
            //Save 1st settings now.
            FileOutputStream fout = null;
            boolean fail = false;
            try {
                settings.writeToFile(new File(settingsSaveFile));
                System.out.println("Wrote to settings file");
            } catch (IOException ex1) {
                fail = true;
                System.out.println("Failed to write to settings file: " + ex1.getMessage());
                ex1.printStackTrace();
            } finally {
                try {
                    if (fout != null) {
                        fout.close();
                    }
                } catch (IOException ex2) {
                    Logger.getLogger(WekinatorSettings.class.getName()).log(Level.INFO, null, ex2);
                }
            }
            configuration = new ChuckConfiguration();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.INFO, null, ex);
            }
        }

        if (useChuckFromCL) {
            try {
                configuration = ChuckConfiguration.readFromFile(WekinatorRunner.getChuckConfigFile());
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.INFO, null, "Loaded chuck configuration file successfully");
            } catch (Exception ex) {
                configuration = new ChuckConfiguration();
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, "Could not load chuck configuration from specified file");
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ChuckRunner.setConfiguration(configuration);

        // featureManager = new FeatureManager();

        handlers = new LinkedList<Handler>();
        try {
            //Give this a try...
            FileHandler h = new FileHandler(settings.getLogFile());
            h.setFormatter(new SimpleFormatter());
            handlers.add(h);
            Logger.getLogger(WekinatorInstance.class.getPackage().getName()).addHandler(h);
        } catch (Exception ex) {
            System.out.println("Couldn't create log file");
        }

        currentHidSetup = new HidSetup();


        //add state listeners
        OscHandler.getOscHandler().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                oscPropertyChanged(evt);
            }
        });

        ChuckSystem.getChuckSystem().addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                chuckSystemUpdated(evt);
            }
        });

        ChuckRunner.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                chuckRunnerUpdated(pce);
            }
        });



        // TODO RAF add check for valid model state
    }

    //
    public static void start() throws IOException {
        if (WekinatorRunner.isLogging()) {
            setupPlog();
        }
        setupOsc();
        runOscIfNeeded();
        runChuckIfNeeded();
    }

    private static void setupOsc() throws IOException {
        OscHandler.getOscHandler().setupOsc();
    }

    private static void runOscIfNeeded() throws IOException {
        if (WekinatorRunner.chuckFile == null
                && WekinatorRunner.connectAutomatically
                && OscHandler.getOscHandler().getConnectionState() == OscHandler.ConnectionState.NOT_CONNECTED) {

            OscHandler.getOscHandler().startHandshake();
            //  connectOSC();
        }
    }

    private static void runChuckIfNeeded() {
        if (WekinatorRunner.chuckFile != null
                && ChuckRunner.getConfiguration() != null
                && ChuckRunner.getConfiguration().isUsable()
                && ChuckRunner.getRunnerState() == ChuckRunnerState.NOT_RUNNING) {
            try {
                ChuckRunner.run();
            } catch (IOException ex) {
                Logger.getLogger(WekinatorRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setWekinatorLearningManager(WekinatorLearningManager m) {
        m.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                learningManagerPropertyChanged(evt);
            }
        });
    }

    private void oscPropertyChanged(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OscHandler.PROP_CONNECTIONSTATE)) {
            OscHandler.ConnectionState n = (OscHandler.ConnectionState) evt.getNewValue();
            if (n == OscHandler.ConnectionState.CONNECTED) {
                if (WekinatorRunner.getFeatureFile() != null) {
                    try {
                        //Maybe should do this earlier? i.e. on start?
                        FeatureConfiguration fc = FeatureConfiguration.readFromFile(WekinatorRunner.getFeatureFile());
                        WekinatorInstance.getWekinatorInstance().setFeatureConfiguration(fc); //Make sure triggers feat config panel update
                    } catch (Exception ex) {
                        Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, "Unable to load feature configuration from file");
                        Logger.getLogger(WekinatorInstance.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
            }
        }
    }

    public void chuckRunnerUpdated(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(ChuckRunner.PROP_RUNNERSTATE)) {
            ChuckRunnerState cs = (ChuckRunnerState) pce.getNewValue();
            if (cs == ChuckRunnerState.RUNNING) {
                useConfigurationNextSession();
                //also connect!
                //connectOSC(); //changed: We'll wait to hear from chuck.

            } else {
                try {
                    OscHandler.getOscHandler().end();
                    OscHandler.getOscHandler().setupOsc();
                } catch (IOException ex) {
                    Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    /*  private void setForOscState() {
    if (OscHandler.getOscHandler().getConnectionState() != OscHandler.ConnectionState.CONNECTED) {
    setState(State.INIT);
    } else if (state == State.INIT && OscHandler.getOscHandler().getConnectionState() == OscHandler.ConnectionState.CONNECTED) {
    setState(State.OSC_CONNECTION_MADE);
    }
    } */
    private void learningManagerPropertyChanged(PropertyChangeEvent evt) {
        /*   if (evt.getPropertyName().equals(WekinatorLearningManager.PROP_FEATURECONFIGURATION)) {
        if (WekinatorLearningManager.getInstance().getFeatureConfiguration() == null) {
        setForOscState();
        } else {
        if (state == State.OSC_CONNECTION_MADE) {
        setState(State.FEATURE_SETUP_DONE);
        }

        }

        } */
    }

    public static void saveCurrentSettings() {
        FileOutputStream fout = null;
        boolean fail = false;
        try {
            getWekinatorInstance().settings.writeToFile(new File(settingsSaveFile));

            System.out.println("Wrote to settings file");
        } catch (IOException ex1) {
            fail = true;
            System.out.println("Failed to write to settings file: " + ex1.getMessage());
            ex1.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ex2) {
                Logger.getLogger(WekinatorSettings.class.getName()).log(Level.INFO, null, ex2);
            }
        }
    }

    public static synchronized WekinatorInstance getWekinatorInstance() {
        if (ref == null) {
            ref = new WekinatorInstance();
        }
        return ref;
    }

    public void useConfigurationNextSession() {
        try {
            //   settings.saveConfiguration(configuration);
            String cLoc = WekinatorInstance.getWekinatorInstance().getSettings().getDefaultSettingsDirectory() + File.separator + ChuckConfiguration.getDefaultLocation() + File.separator + chuckConfigSaveFile + "." + ChuckConfiguration.getFileExtension();
            configuration.writeToFile(new File(cLoc));

        } catch (IOException ex) {
            System.out.println("Could not save configuration to use next session");
            Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private void settingsPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(WekinatorSettings.PROP_LOGLEVEL)) {
            for (Handler h : handlers) {
                h.setLevel(settings.getLogLevel());
            }
        }
    }

    public void addLoggingHandler(Handler h) {
        if (!handlers.contains(h)) {
            Logger.getLogger(WekinatorInstance.class.getPackage().getName()).addHandler(h);
            handlers.add(h);
        }
    }

    void removeLoggingHandler(Handler h) {
        handlers.remove(h);
    }

    public WekinatorSettings getSettings() {
        return settings;
    }

    public void addOscFeatureNamesChangeListener(ChangeListener l) {
        oscFeatureNamesListenerList.add(ChangeListener.class, l);
    }

    public void removeOscFeatureNamesChangeListener(ChangeListener l) {
        oscFeatureNamesListenerList.remove(ChangeListener.class, l);
    }



    protected void fireOscFeatureNamesChanged() {
        Object[] listeners = oscFeatureNamesListenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (oscFeatureNameChangeEvent == null) {
                    oscFeatureNameChangeEvent = new ChangeEvent(this);

                }
                ((ChangeListener) listeners[i + 1]).stateChanged(oscFeatureNameChangeEvent);
            }
        }
    }

    public void addFeatureParameterSetupDoneListener(ChangeListener l) {
        featureParameterSetupDoneListenerList.add(ChangeListener.class, l);
    }

    public void removeFeatureParameterSetupDoneListener(ChangeListener l) {
        featureParameterSetupDoneListenerList.remove(ChangeListener.class, l);
    }

    protected void fireFeatureParameterSetupDone() {
        Object[] listeners = featureParameterSetupDoneListenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (featureParameterSetupDoneEvent == null) {
                    featureParameterSetupDoneEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i + 1]).stateChanged(featureParameterSetupDoneEvent);
            }
        }
    }

        public void addAutorunBegunListener(ChangeListener l) {
        autorunBegunListenerList.add(ChangeListener.class, l);
    }

    public void removeAutorunBegunListener(ChangeListener l) {
        autorunBegunListenerList.remove(ChangeListener.class, l);
    }

    protected void fireAutorunBegun() {
        Object[] listeners = autorunBegunListenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (autorunBegunEvent == null) {
                    autorunBegunEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i + 1]).stateChanged(autorunBegunEvent);
            }
        }
    }

    public static void resetOscConnection() throws IOException {
        if (FeatureExtractionController.isExtracting()) {
            FeatureExtractionController.stopExtracting();
        }
        OscHandler.getOscHandler().end();
        OscHandler.getOscHandler().setupOsc();
    }

    public static void startOscConnection() throws IOException {
        OscHandler.getOscHandler().connectOSC();
    }

    public static void shutdown() {
        if (FeatureExtractionController.isExtracting()) {
            FeatureExtractionController.stopExtracting();
        }

        OscHandler.getOscHandler().end();

        if (ChuckRunner.getRunnerState() == ChuckRunner.ChuckRunnerState.RUNNING) {
            try {
                ChuckRunner.stop();
            } catch (IOException ex) {
                Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Want to save settings here!
        saveCurrentSettings();

        try {
            if (WekinatorRunner.isLogging()) {
                Plog.log(Msg.CLOSE);
                Plog.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(WekinatorInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void startAutoRun() {
        WekinatorLearningManager.getInstance().startRunning();
        OscHandler.getOscHandler().startSound();
        getWekinatorInstance().fireAutorunBegun();
    }
}
