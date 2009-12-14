/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wekinator;

import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Controls wekinator instance (state)
 *
 * @author rebecca
 */
public class WekinatorLearningManager {


    private ChangeEvent changeEvent = null;
    protected FeatureViewer featureViewer = null;


    private static WekinatorLearningManager ref = null;
    protected double[] params;
    protected boolean[] mask;
    protected LearningSystem learningSystem = null;
    public static final String PROP_LEARNINGSYSTEM = "learningSystem";

    protected FeatureConfiguration featureConfiguration = null;
    public static final String PROP_FEATURECONFIGURATION = "featureConfiguration";

    public static final String PROP_PARAMS = "params";

    public void showFeatureViewer() {
        if (featureViewer == null) {
            featureViewer = new FeatureViewer();
            featureViewer.setNames(featureConfiguration.getFeatureNames());
        }
        featureViewer.setVisible(true);
        featureViewer.toFront();
    }

    protected double[] outputs = null;

       protected EventListenerList listenerList = new EventListenerList();
       public void addOutputChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    public void removeOutputChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Get the value of outputs
     *
     * @return the value of outputs
     */
    public double[] getOutputs() {
        return outputs;
    }

    /**
     * Set the value of outputs
     *
     * @param outputs new value of outputs
     */
    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
       fireOutputChanged();
    }

    /**
     * Get the value of outputs at specified index
     *
     * @param index
     * @return the value of outputs at specified index
     */
    public double getOutputs(int index) {
        return this.outputs[index];
    }

    /**
     * Set the value of outputs at specified index.
     *
     * @param index
     * @param newOutputs new value of outputs at specified index
     */
    public void setOutputs(int index, double newOutputs) {
        this.outputs[index] = newOutputs;
        fireOutputChanged();
    }


    /**
     * Set the value of p2
     *
     * @param p2 new value of p2
     */
    public void setParams(double[] params) {
        double[] oldparams = this.params;
        this.params = params;
        propertyChangeSupport.firePropertyChange(PROP_PARAMS, oldparams, params);
    }

    protected PropertyChangeListener learningSystemPropertyChange = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            learningSystemPropertyChanged(evt);
        }
    };



    public enum InitializationState {
        NOT_INITIALIZED,
        INITIALIZED
    };
    protected InitializationState initState = InitializationState.NOT_INITIALIZED;
    public static final String PROP_INITSTATE = "initState";

    public enum Mode {
        DATASET_CREATION,
        TRAINING,
        RUNNING,
        NONE
    };

    protected Mode mode = Mode.NONE;
    public static final String PROP_MODE = "mode";
  //  protected double[] outputs = null;
  //  public static final String PROP_OUTPUTS = "outputs";


    /**
     * Get the value of params
     *
     * @return the value of params
     */
    public double[] getParams() {
        return params;
    }

    public void setParamsAndMask(double[] params, boolean[] mask) {
        setParams(params);
        learningSystem.setParamMask(mask);
        this.mask = mask;
    }
    /**
     * Get the value of params at specified index
     *
     * @param index
     * @return the value of params at specified index
     */
    public double getParams(int index) {
        return this.params[index];
    }


    /**
     * Get the value of outputs
     *
     * @return the value of outputs
     */
    /*public double[] getOutputs() {
        return outputs;
    } */

    /**
     * Set the value of outputs
     *
     * @param outputs new value of outputs
     */
/*    public void setOutputs(double[] outputs) {
        synchronized(this) {
            double[] oldparams = this.outputs;
            this.outputs = outputs;
            for (int i = 0; i < outputs.length; i++) {
                System.out.print(outputs[i] + " ");
            }
            System.out.println("");
            double[] newValue = outputs;
            System.out.println("Test: " + (oldparams != null && newValue != null && oldparams.equals(newValue)));
            propertyChangeSupport.firePropertyChange(PROP_OUTPUTS, oldparams, outputs);
          //  propertyChangeSupport.fire
        }
    } */

    /**
     * Get the value of outputs at specified index
     *
     * @param index
     * @return the value of outputs at specified index
     */
  /*  public double getOutputs(int index) {
        return this.outputs[index];
    } */

    /**
     * Set the value of outputs at specified index.
     *
     * @param index
     * @param newOutputs new value of outputs at specified index
     */
  /*  protected void setOutputs(int index, double newOutputs) {
        double oldOutputs = this.outputs[index];
        this.outputs[index] = newOutputs;
        propertyChangeSupport.fireIndexedPropertyChange(PROP_OUTPUTS, index, oldOutputs, newOutputs);
    } */

    /**
     * Get the value of mode
     *
     * @return the value of mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Set the value of mode
     *
     * @param mode new value of mode
     */
    protected void setMode(Mode mode) {
        Mode oldMode = this.mode;
        this.mode = mode;
        propertyChangeSupport.firePropertyChange(PROP_MODE, oldMode, mode);
    }

    public void startDatasetCreation() {
        if (initState == InitializationState.INITIALIZED) {
        if (mode == Mode.DATASET_CREATION)
            return;

        if (mode == Mode.TRAINING) {
            stopTraining();
        }
        //if mode = running, don't stop extracting features, just change what I do with them

        if (params == null) {
            params = new double[learningSystem.getNumParams()];
        }

        OscHandler.getOscHandler().initiateRecord();
        setMode(Mode.DATASET_CREATION);
        }
    }

    public void stopRunning() {
        OscHandler.getOscHandler().stopExtractingFeatures();
        setMode(Mode.NONE);
    }

    public void startRunning()  {
        if (mode == Mode.TRAINING) {
            return;
        }
        if (mode == Mode.DATASET_CREATION) {
            stopDatasetCreation();
        }
        OscHandler.getOscHandler().initiateRecord();
        setMode(Mode.RUNNING);
    }

    public void stopDatasetCreation()  {
        if (mode != Mode.DATASET_CREATION)
            return;

        OscHandler.getOscHandler().stopExtractingFeatures();
        setMode(Mode.NONE);
    }

    public void stopTraining() {
        //TODO: cancel training here
        setMode(Mode.NONE);
    }

    public void startTraining() {
        setMode(Mode.TRAINING);
        learningSystem.trainInBackground();
    }

    public void startTraining(int paramNum) {
        setMode(Mode.TRAINING);
        learningSystem.trainInBackground(paramNum);
    }


 //   public void stopEvaluating()


    public void updateFeatures(double[] features) {
        System.out.println("java received feature");
        if (mode == Mode.DATASET_CREATION) {
           // learningSystem.addToTraining(features, params);
              double[] fs = featureConfiguration.process(features);
              learningSystem.addToTraining(fs, params);
               if (featureViewer != null) {
                    featureViewer.updateFeatures(fs);
               }

        } else if (mode == Mode.RUNNING) {
            System.out.println("in run mode");
            try {
                //classify these features
                double[] fs = featureConfiguration.process(features);
               setOutputs(learningSystem.classify(fs));
               if (featureViewer != null) {
                    featureViewer.updateFeatures(fs);
               }
                //TODO RAF important TODO TODO TODO: issue of displaying output for dist features
               OscHandler.getOscHandler().sendParamsToSynth(outputs);
                
            } catch (Exception ex) {
                Logger.getLogger(WekinatorLearningManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Get the value of initState
     *
     * @return the value of initState
     */
    public InitializationState getInitState() {
        return initState;
    }

    /**
     * Set the value of initState
     *
     * @param initState new value of initState
     */
    protected void setInitState(InitializationState initState) {
        InitializationState oldInitState = this.initState;
        this.initState = initState;
        propertyChangeSupport.firePropertyChange(PROP_INITSTATE, oldInitState, initState);
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
        updateMyInitState();
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
    public void setLearningSystem(LearningSystem learningSystem) {

        LearningSystem oldLearningSystem = this.learningSystem;
        if (oldLearningSystem != null) {
            oldLearningSystem.removePropertyChangeListener(learningSystemPropertyChange);
        }
        this.learningSystem = learningSystem;
        if (learningSystem != null) {
            learningSystem.addPropertyChangeListener(learningSystemPropertyChange);
            
        }
        updateMyInitState();
        propertyChangeSupport.firePropertyChange(PROP_LEARNINGSYSTEM, oldLearningSystem, learningSystem);
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

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


    public static synchronized WekinatorLearningManager getInstance() {
        if (ref == null) {
            ref = new WekinatorLearningManager();
        }
        return ref;
    }

    private WekinatorLearningManager() {
        KeyEventPostProcessor processor = new KeyEventPostProcessor() {
            public boolean postProcessKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                        //Start recording
                        System.out.println("pg down start");
                        startDatasetCreation();
                    } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                         //TODO in future: integrate playalong here.

                    }
                } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                                                System.out.println("pg down stop");

                       stopDatasetCreation();
                    }
                }
                return true;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(processor);
    }

    static Object getWekinatorManager() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private void learningSystemPropertyChanged(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(LearningSystem.PROP_INITIALIZATIONSTATE)) {
            updateMyInitState();
        } else if (evt.getPropertyName().equals(LearningSystem.PROP_SYSTEMTRAININGSTATE)) {
                   LearningSystem.LearningSystemTrainingState ts = learningSystem.getSystemTrainingState();
            if (mode == Mode.TRAINING && ts != LearningSystem.LearningSystemTrainingState.TRAINING) {
                setMode(Mode.NONE);
            }
        }

    }

    protected void updateMyInitState() {
        if (learningSystem != null && featureConfiguration != null
                && learningSystem.getInitializationState() != LearningSystem.LearningAlgorithmsInitializationState.ALL_INITIALIZED) {
            setInitState(InitializationState.NOT_INITIALIZED);
        } else {
            setInitState(InitializationState.INITIALIZED);
        }
                
    }

 
    protected void fireOutputChanged() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

}