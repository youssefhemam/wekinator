/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* TODO this class:
 *
 *  Fix problem waiting for chuck --loop to initialize VM -- can I use waitFor there?
 *  Send chuck output to stdout at least for now, to help with debugging
 *  Grab chuck error output also and send somewhere
 *  Ultimately display in console.
 *
 * */
package wekinator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: kill listener threads when chuck runner stops!
/**
 *
 * @author rebecca
 */
public class ChuckRunner {

    protected static final ChuckRunner ref = new ChuckRunner();
    protected static ChuckConfiguration configuration;
    protected static String lastErrorMessages = "";
    protected static Logger logger = Logger.getLogger(ChuckRunner.class.getName());

    private ChuckRunner() {
    }

    public enum ChuckRunnerState {

        NOT_RUNNING,
        TRYING_TO_RUN,
        RUNNING
    }
    protected ChuckRunnerState runnerState = ChuckRunnerState.NOT_RUNNING;
    public static final String PROP_RUNNERSTATE = "runnerState";
    public static final String PROP_CONFIGURATION = "configuration";
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        ref.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        ref.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of runnerState
     *
     * @return the value of runnerState
     */
    public static ChuckRunnerState getRunnerState() {
        return ref.runnerState;
    }

    /**
     * Set the value of runnerState
     *
     * @param runnerState new value of runnerState
     */
    protected static void setRunnerState(ChuckRunnerState runnerState) {
        ChuckRunnerState oldRunnerState = ref.runnerState;
        ref.runnerState = runnerState;
        ref.propertyChangeSupport.firePropertyChange(PROP_RUNNERSTATE, oldRunnerState, ref.runnerState);
    }

    public static String getLastErrorMessages() {
        return lastErrorMessages;
    }

    public static ChuckConfiguration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(ChuckConfiguration c) {
        ChuckConfiguration oldConfiguration = configuration;
        if (ref.runnerState != ChuckRunnerState.NOT_RUNNING) {
            try {
                stop();
            } catch (IOException ex) {
                ChuckRunner.logger.log(Level.SEVERE, null, ex);
            }
        }
        configuration = c;
        ref.propertyChangeSupport.firePropertyChange(PROP_CONFIGURATION, oldConfiguration, configuration);
    }

    public static void run() throws IOException {
        stop();
        lastErrorMessages = "";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ChuckRunner.logger.log(Level.SEVERE, null, ex);
        }

        LinkedList<String[]> cmds = new LinkedList<String[]>();
        String[] s;
        s = new String[2];
        s[0] = configuration.getChuckExecutable();
        s[1] = "--loop";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "TrackpadFeatureExtractor.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "MotionFeatureExtractor.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "AudioFeatureExtractor.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "HidDiscoverer.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "CustomOSCFeatureExtractor.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "ProcessingFeatureExtractor.ck";
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        if (configuration.isCustomChuckFeatureExtractorEnabled()) {
            s[2] = configuration.getCustomChuckFeatureExtractorFilename();

        } else {
            s[2] = configuration.getChuckDir() + File.separator + "feature_extractors" + File.separator + "keyboard_rowcol.ck";
        }
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        if (configuration.isUseOscSynth()) {
            s[2] = configuration.getChuckDir() + File.separator + "synths" + File.separator + "OSC_synth_proxy.ck";
        } else {
            s[2] = configuration.getChuckSynthFilename();
        }
        cmds.add(s);

        s = new String[3];
        s[0] = configuration.getChuckExecutable();
        s[1] = "+";
        if (configuration.isIsPlayalongLearningEnabled()) {
            s[2] = configuration.getPlayalongLearningFile();
        } else {
            s[2] = configuration.getChuckDir() + File.separator + "score_players" + File.separator + "no_score.ck";
        }
        cmds.add(s);

        if (configuration.isUseOscSynth()) {
            s = new String[3];
            s[0] = configuration.getChuckExecutable();
            s[1] = "+";
            String args = ":synthNumParams:" + configuration.getNumOscSynthParams();

            args += ":synthIsDiscrete:" + (configuration.getIsOscSynthParamDiscrete()[0] ? "1" : "0");
            args += ":synthUsingDistribution:" + (configuration.getOscUseDistribution()[0] ? "1" : "0");
            args += ":synthNumClasses:" + configuration.getNumOscSynthMaxParamVals();
            args += ":synthPort:" + configuration.getOscSynthReceivePort();
            s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "main_chuck_new.ck" + args;
            //s[3] = args;
            cmds.add(s);
        } else {
            s = new String[3];
            s[0] = configuration.getChuckExecutable();
            s[1] = "+";
            s[2] = configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "main_chuck.ck";
            cmds.add(s);
        }

        //Now we want to execute these commands.
        int numErrLines = 0;

        for (int i = 0; i < cmds.size(); i++) {
            System.out.print("Executing: ");
            String c[] = cmds.get(i);
            for (int j = 0; j < c.length; j++) {
                System.out.print(c[j] + " ");
            }
            System.out.println("");

            try {
                String line, output;
                output = "";
                Process child = Runtime.getRuntime().exec(cmds.get(i));
                //Runtime.getRuntime().exec

                if (i == 0) {
                    //Special! Fork a thread that listens to the output of this process,
                    //and log lines using logger
                    new LoggerThread(child.getErrorStream());
                    new LoggerThread(child.getInputStream());
                }
                if (i != 0) {
                    try {

                        child.waitFor();
                    } catch (InterruptedException ex) {
                        System.out.println("Couldn't wait");
                        logger.log(Level.SEVERE, null, ex);
                    }

                    BufferedReader input = new BufferedReader(new InputStreamReader(child.getErrorStream()));

                    while ((line = input.readLine()) != null) {
                        numErrLines++;
                        output += "In executing command " + cmds.get(i) + " received error:\n";
                        output += (line + '\n');
                        System.out.println("**" + output);
                        lastErrorMessages += line + "\n";
                    }
                    input.close();
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }

            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        if (numErrLines != 0) {
            logger.log(Level.SEVERE, "Errors encountered running chuck: " + lastErrorMessages);
            setRunnerState(ChuckRunnerState.TRYING_TO_RUN);
            if (WekinatorRunner.isLogging()) {
                Plog.log(Plog.Msg.ERROR, "Error running chuck: " + lastErrorMessages);
            }
        } else {
            System.out.println("A miracle! Chuck runs.");
            setRunnerState(ChuckRunnerState.RUNNING);
            if (WekinatorRunner.isLogging()) {
                Plog.chuckRunSuccessful(configuration);
            }
        }
    }

    public static void ignoreRunErrors(boolean ignore) {
        if (ref.runnerState == ChuckRunnerState.TRYING_TO_RUN) {
            if (ignore) {
                setRunnerState(ChuckRunnerState.RUNNING);
            } else {
                try {
                    stop();
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void stop() throws IOException {
        String[] s = new String[2];
        s[0] = configuration.getChuckExecutable();
        s[1] = "--kill";
        Process child = Runtime.getRuntime().exec(s);

        String cmd2 = "killall chuck";
        Process child2 = Runtime.getRuntime().exec(cmd2);

        logger.log(Level.INFO, "Attempted to kill chuck");
        setRunnerState(ChuckRunnerState.NOT_RUNNING);
        ChuckSystem.getChuckSystem().waitForNewSettings();
    }

    public void restart() throws IOException {
        stop();
        run();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static void exportConfigurationToChuckFile(ChuckConfiguration configuration, File file) throws IOException {
        //Open output stream
        BufferedWriter w = null;
        w = new BufferedWriter(new FileWriter(file));

        w.write("//Automatically generated machine.add file\n");
        w.write("//Created " + (new Date()).toString() + "\n\n");
        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "TrackpadFeatureExtractor.ck" + "\");\n");
        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "MotionFeatureExtractor.ck" + "\");\n");
        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "AudioFeatureExtractor.ck" + "\");\n");
        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "HidDiscoverer.ck" + "\");\n");
        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "CustomOSCFeatureExtractor.ck" + "\");\n");

        w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "ProcessingFeatureExtractor.ck" + "\");\n");
        if (configuration.isCustomChuckFeatureExtractorEnabled()) {
            w.write("Machine.add(\"" + configuration.getCustomChuckFeatureExtractorFilename() + "\");\n");

        } else {
            w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "feature_extractors" + File.separator + "keyboard_rowcol.ck" + "\");\n");
        }

        if (configuration.isUseOscSynth()) {
            w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "synths" + File.separator + "OSC_synth_proxy.ck" + "\");\n");
        } else {
            w.write("Machine.add(\"" + configuration.getChuckSynthFilename() + "\");\n");
        }

        if (configuration.isIsPlayalongLearningEnabled()) {
            w.write("Machine.add(\"" + configuration.getPlayalongLearningFile() + "\");\n");
        } else {
            w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "score_players" + File.separator + "no_score.ck" + "\");\n");
        }

        if (configuration.isUseOscSynth()) {
            String args = ":synthNumParams:" + configuration.getNumOscSynthParams();

            args += ":synthIsDiscrete:" + (configuration.getIsOscSynthParamDiscrete()[0] ? "1" : "0");
            args += ":synthUsingDistribution:" + (configuration.getOscUseDistribution()[0] ? "1" : "0");
            args += ":synthNumClasses:" + configuration.getNumOscSynthMaxParamVals();
            args += ":synthPort:" + configuration.getOscSynthReceivePort();
            w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "main_chuck_new.ck" + args + "\");\n");
        } else {
            w.write("Machine.add(\"" + configuration.getChuckDir() + File.separator + "core_chuck" + File.separator + "main_chuck.ck" + "\");\n");
        }

        w.close();
    }
}

class LoggerThread implements Runnable {

    Thread t;
    BufferedReader input;

    LoggerThread(InputStream is) {
        input = new BufferedReader(new InputStreamReader(is));
        t = new Thread(this, "my thread");
        t.start();
    }

    public void run() {
        boolean stop = false;
        while (!stop) {
            try {
                ///byte[] byteArray = new byte[2];
                int b = input.read();
                // input.read

                if (b == -1) {
                    stop = true;
                // System.out.println("made it to end of stream");
                } else {
                    //TODO: send to console in reasonable way
                    System.out.print((char) b);
                    //String s = String.
                    Console.getInstance().log(String.valueOf((char) b));
                }
            } catch (IOException ex) {
                Logger.getLogger(LoggerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}