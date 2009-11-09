/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wekinator;

import com.illposed.osc.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import wekinator.FeatureManager.*;
import wekinator.util.Subject;
import wekinator.util.Observer;

/**
 *
 * @author rebecca
 */
public class OscHandler implements Subject {

    public int receivePort = 0;
    public int sendPort = 0;
    OSCPortOut sender;
    public OSCPortIn receiver;
    WekaOperator w;
    HidSetup h = new HidSetup();
    String startHandshakeString = "/hello";
    String returnHandshakeString = "/hiback";
    String featureInfoString = "/featureInfo";
    String featuresString = "/features"; //used for chuck features (not osc feats)
    String extractString = "/extract";
    String stopString = "/stop";
    String classLabelString = "/classLabel";
    String classDistString = "/classDist";
    String realValueRequestString = "/realValueRequest";
    String realValueString = "/realValue";
    String realLabelString = "/realLabel";
    String hidSetupString = "/hidSetup";
    String hidSetupBegunString = "/hidSetupBegun";
    String hidSetupStopString = "/hidSetupStop";
    String hidSetupStoppedString = "/hidSetupStopped";
    String hidInitString = "/hidInit";
    String sendHidInitValuesString = "/sendHidInitValues";
    String hidInitAxesString = "/hidInitAxes";
    String hidInitHatsString = "/hidInitHats";
    String hidInitButtonsString =  "/hidInitButtons";
    String hidInitMaskString = "/hidInitMask";
    String hidSettingsRequestString = "/hidSettingsRequest";
    String hidSettingsNumsString = "/hidSettingsNums";
    String hidSettingsAxesString = "/hidSettingsAxes";
    String hidSettingsHatsString = "/hidSettingsHats";
    String hidSettingsMaskString = "/hidSettingsMask";
    String hidSettingButtonsString = "/hidSettingsButtons";
    String hidRunString = "/hidRun";
    String setUseTrackpadFeature = "/useTrackpadFeature";
    String setUseMotionFeature = "/useMotionFeature";
    String setUseOtherHidFeature = "/useOtherHidFeature";
    String setUseProcessingFeature = "/useProcessingFeature";
   // String setUseAudioFeature = "/useAudioFeature";
    String requestNumParamsString = "/requestNumParams";
    String numParamsString = "/numParams";
    String setUseAudioFeatureString = "/useAudioFeature";
    String sendFeatureMessageString = "/useFeatureMessage";
    String trackpadMessageString = "trackpad";
    String motionMessageString = "motion";
    String processingMessageString = "processing";
    String customMessageString = "custom";
    String oscCustomMessageString = "oscCustom";
    String otherHidMessageString = "otherHid";
    String sendControlMessageString = "/control";
    String helloMessageString = "hello";
    String requestNumParamsMessageString = "requestNumParams";
    String requestChuckSettingsMessageString = "requestChuckSettings";
    String extractMessageString = "extract";
    String stopMessageString = "stop";
    String stopSoundMessageString = "stopSound";
    String startSoundMessageString = "startSound";
    String realValueRequestMessageString = "realValueRequest";
    String chuckSettingsString = "/chuckSettings";
    String startGettingParamsMessageString = "startGettingParams";
    String stopGettingParamsMessageString = "stopGettingParams";
    String startPlaybackMessageString = "startPlayback";
    String stopPlaybackMessageString = "stopPlayback";
    String playAlongMessage = "/playAlongMessage";
    
    int counter = 0;

 

  
  

    public enum OscState {

        NOT_CONNECTED, CONNECTING, CONNECTED, FAIL
    };
    private ArrayList<Observer> observers;
    public OscState state = OscState.NOT_CONNECTED;
    private String myStatusMessage = "Not initialized";

    public OscHandler(WekaOperator w, int receivePort, int sendPort) throws SocketException, UnknownHostException {
        this.receivePort = receivePort;
        this.sendPort = sendPort;
        this.w = w;
        receiver = new OSCPortIn(receivePort);
      //  System.out.println("Java listening on " + receivePort);
        sender = new OSCPortOut(InetAddress.getLocalHost(), sendPort);
       // System.out.println("Java sending on " + sendPort);
        observers = new ArrayList();
        h.setOSCHandler(this);
    }

    public void setHidSetup(HidSetup h) {
        this.h = h;
        h.setOSCHandler(this);
    }

    public String statusString() {
        switch (state) {
            case NOT_CONNECTED:
                return "Not connected yet.";
            case CONNECTING:
                return "Waiting for ChucK to finish connection";
            case CONNECTED:
                return "Connected";
            case FAIL:
                return "Connection failed.";
        }
        return "Other";


    }

    public void startHandshake() throws IOException {


        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                w.receivedHandshake();
                state = OscState.CONNECTED;
                myStatusMessage = "Connected";
                notifyObservers();
            }
        };

        receiver.addListener(returnHandshakeString, listener);
        addFeatureInfoListener();
        addFeatureListener();
        addRealValueListener();
        addHidSetupBegunListener();
        addHidSetupStoppedListener();
        addSendHidInitValuesListener();
        addHidSettingsNumsListener();
        addHidSettingsAxesListener();
        addHidSettingsHatsListener();
        addHidSettingsButtonsListener();
        addHidSettingsMaskListener();
        addNumParamsListener();
        addChuckSettingsListener();
        receiver.startListening();
        sendHandshakeMessage();
        addPlayalongMessageListener();
        state = OscState.CONNECTING;
        notifyObservers();

    }

    public String getStatusMessage() {
        return myStatusMessage;
    }

    private void notifyObservers() {
        // loop through and notify each observer
        Iterator<Observer> i = observers.iterator();
        while (i.hasNext()) {
            Observer o = i.next();
            o.update(this, state, getStatusMessage());
        }
    }

    private void sendHandshakeMessage() throws IOException {
        Object[] o = new Object[2];
        o[0] = helloMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
    }

    public void stopSound() throws IOException {
        Object[] o = new Object[2];
        o[0] = stopSoundMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
    }

     public void startSound() throws IOException {
        Object[] o = new Object[2];
        o[0] = startSoundMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
    }


    void askForCurrentValue() throws IOException {
        Object[] o = new Object[2];
        o[0] = realValueRequestMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("Asked for current value");
    }

       void startGettingParams() throws IOException {
                   Object[] o = new Object[2];
        o[0] = startGettingParamsMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("Asked for start getting params");

    }

     void stopGettingParams() throws IOException {
                   Object[] o = new Object[2];
        o[0] = stopGettingParamsMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("Asked for stop getting params");

    }

    void requestHidSetup() throws IOException {
        Object[] o = new Object[1];
        o[0] = new Integer(0);
        OSCMessage msg = new OSCMessage(hidSetupString, o);
        sender.send(msg);
        System.out.println("Requested hid setup start");
    }
    
      void setUseTrackpad(boolean useTrackpad) throws IOException {
        Object[] o = new Object[2];
        o[0] = trackpadMessageString;
        o[1] = new Integer(useTrackpad? 1 : 0);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }
    
      void setUseCustom(boolean useCustom, int numCustomChuck) throws IOException {
        Object[] o = new Object[2];
        o[0] = customMessageString;
        o[1] = new Integer(useCustom? numCustomChuck : 0);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }
    
     void setUseOscCustom(boolean useOscCustom, int numCustom) throws IOException {
        Object[] o = new Object[2];
        o[0] = oscCustomMessageString;
        o[1] = new Integer(useOscCustom? numCustom : 0);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }
    
      
      
      void setUseAudio(boolean useAudio, boolean useFFT, boolean useRMS, boolean useCentroid, boolean useRolloff, boolean useFlux, int fftSize, int windowSize, WindowTypes windowType, int audioExtractionRate) throws IOException {
          System.out.println("Setting use audio: " + useAudio);
          Object[] o = new Object[10];
        o[0] = new Integer(useAudio? 1 : 0);
        o[1] = new Integer(useFFT? 1 : 0);
        o[2] = new Integer(useRMS? 1 : 0);
        o[3] = new Integer(useCentroid? 1 : 0);
        o[4] = new Integer(useRolloff? 1 : 0);
        o[5] = new Integer(useFlux? 1 : 0);
        o[6] = new Integer(fftSize);
        o[7] = new Integer(windowSize);
        if (windowType == WindowTypes.Hamming) {
            o[8] = new Integer(3);
        } else if (windowType == WindowTypes.Hann) {
            o[8] = new Integer(2);
        } else {
            o[8] = new Integer(0);
        }
        o[9] = new Integer(audioExtractionRate);
        
        OSCMessage msg = new OSCMessage(setUseAudioFeatureString, o);
        sender.send(msg);
        
        
          
    }

      
      
       void setUseMotion(boolean useMotion, int motionExtractionRate) throws IOException {
        Object[] o = new Object[2];
        o[0] = motionMessageString;
        o[1] = new Integer(useMotion? motionExtractionRate : 0);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }
    
    void setUseOtherHid(boolean useOtherHid) throws IOException {
        Object[] o = new Object[2];
        o[0] = otherHidMessageString;
        o[1] = new Integer(useOtherHid? 1 : 0);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }
    
    void setUseProcessing(boolean useProcessing, int numFeats) throws IOException {
        Object[] o = new Object[2];
        o[0] = processingMessageString;
        o[1] = new Integer(numFeats);
        OSCMessage msg = new OSCMessage(sendFeatureMessageString, o);
        sender.send(msg);
    }


    void requestHidSetupStop() throws IOException {
        Object[] o = new Object[1];
        o[0] = new Integer(0);
        OSCMessage msg = new OSCMessage(hidSetupStopString, o);
        sender.send(msg);
        System.out.println("Requested hid setup stop");
    }

    void sendHidInit(int numAxes, int numHats, int numButtons) throws IOException {
        Object[] o = new Object[3];
        o[0] = new Integer(numAxes);
        o[1] = new Integer(numHats);
        o[2] = new Integer(numButtons);
        OSCMessage msg = new OSCMessage(hidInitString, o);
        sender.send(msg);
        System.out.println("Sent first hid init string");
    }

    void sendHidSettingsRequest() throws IOException {
        Object[] o = new Object[1];
        o[0] = new Integer(0);
        OSCMessage msg = new OSCMessage(hidSettingsRequestString, o);
        sender.send(msg);
        System.out.println("Requested hid settings");

    }
    
     void startHidRun() throws IOException {
        Object[] o = new Object[1];
        o[0] = new Integer(0);
        OSCMessage msg = new OSCMessage(hidRunString, o);
        sender.send(msg);
        System.out.println("Requested hid run");   
     }

    void sendHidValues(float[] initAxes, int[] initHats, int[] initButtons, int[] axesMask, int[] hatsMask, int[] buttonsMask) throws IOException {
        Object[] o1 = new Object[initAxes.length];
        for (int i = 0; i < initAxes.length; i++) {
            o1[i] = new Float(initAxes[i]);
        }
        OSCMessage msg1 = new OSCMessage(hidInitAxesString, o1);
        sender.send(msg1);

        Object[] o2 = new Object[initHats.length];
        for (int i = 0; i < initHats.length; i++) {
            o2[i] = new Integer(initHats[i]);
        }
        OSCMessage msg2 = new OSCMessage(hidInitHatsString, o2);
        sender.send(msg2);

        Object[] o3 = new Object[initButtons.length];
        for (int i = 0; i < initButtons.length; i++) {
            o3[i] = new Integer(initButtons[i]);
        }
        OSCMessage msg3 = new OSCMessage(hidInitButtonsString, o3);
        sender.send(msg3);
        
        Object[] o4 = new Object[initAxes.length + initHats.length + initButtons.length];
        int j = 0;
        for (int i = 0; i < axesMask.length; i++) {
            o4[j] = axesMask[i];
            j++;
        }
        for (int i = 0; i < hatsMask.length; i++) {
            o4[j] = hatsMask[i];
            j++;
        }
        for (int i= 0; i < buttonsMask.length; i++) {
            o4[j] = buttonsMask[i];
            j++;
        }
        OSCMessage msg4 = new OSCMessage(hidInitMaskString, o4);
        sender.send(msg4);
        
        System.out.println("Sent 4 init strings");
    }

    public void end() {
        receiver.stopListening();
        receiver.close(); //this line causes errors!!

        sender.close();
        state = OscState.NOT_CONNECTED;
        notifyObservers();
    }

    public void initiateRecord() throws IOException {
        Object[] o = new Object[2];
        o[0] = extractMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
    }

    public void initiateClassify() throws IOException {
       initiateRecord();
    }

    public void stopTrainTest() throws IOException {
        Object[] o = new Object[2];
        o[0] = stopMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("h sent stop " + sendControlMessageString + " " + stopMessageString);
    }

    public void sendClass(int c) {
        Object[] o = new Object[1];
        o[0] = new Integer(c);

        OSCMessage msg = new OSCMessage(classLabelString, o);
        try {

            sender.send(msg);
        //System.out.println("sent label... I think");
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sendClassMulti(int[] vals) {
    /*    Object[] o = new Object[vals.length];
        for (int i = 0; i < vals.length; i++) {
            o[i] = new Integer(vals[i]);

        }

        OSCMessage msg = new OSCMessage(classLabelString, o);
        try {

            sender.send(msg);
        //System.out.println("sent label... I think");
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        } */
        
        float[] fvals = new float[vals.length];
        for (int i= 0; i < vals.length; i++) {
            fvals[i] = vals[i];
            
        }
        sendRealValueMulti(fvals);

    }

    public void sendRealValue(double r) {
        float[] vals = new float[1];
      vals[0] = (float)r;
      sendRealValueMulti(vals);
    }
    
    public void requestChuckSettings() {
        Object[] o = new Object[2];
        o[0] = requestChuckSettingsMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        try {

            sender.send(msg);
        //System.out.println("sent label... I think");
        } catch (IOException ex) {
            System.out.println("123");
            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    void sendRealValueMulti(float[] realVals) {
     //   System.out.println("sending real values:");
        Object[] o = new Object[realVals.length];

        try {

            for (int i = 0; i < realVals.length; i++) {
                o[i] = new Float(realVals[i]);
            }

            OSCMessage msg = new OSCMessage(realLabelString, o);
            sender.send(msg);
         //   System.out.println("sent labels... I think, for len=" + realVals.length);
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendClass(int c, double[] dist) {
        Object[] o = new Object[1];
        o[0] = new Integer(c);

        OSCMessage msg = new OSCMessage(classLabelString, o);
        try {
            int n = 0;
            for (int i = 0; i < dist.length; i++) {
                msg.addArgument(new Double(dist[i]));
                n++;
            }

            System.out.println("n added: " + n);


            sender.send(msg);
        //System.out.println("sent label... I think");
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //TODO: implement sending method for multi-class distribution
    public void sendDist(double[] dist) {
        OSCMessage msg = new OSCMessage(classDistString);
        try {
            int i = 0;
            for (; i < dist.length; i++) {
                msg.addArgument(new Float((float) dist[i]));
            }
            sender.send(msg);
            System.out.println("sent: " + i);
        //System.out.println("sent label... I think");
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void sendDistMulti(double[][] dists) {

        OSCMessage msg = new OSCMessage(classDistString);
        try {
            for (int j = 0; j < dists.length; j++) {

                for (int i = 0; i < dists[j].length; i++) {
                    msg.addArgument(new Float((float) dists[j][i]));
                }

            //System.out.println("sent label... I think");
            }
            sender.send(msg);
            System.out.println("sent: " + dists.length * dists[0].length);
        } catch (IOException ex) {
            System.out.println("123");

            Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addFeatureInfoListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Feature info received!");
                Object[] o = message.getArguments();
                if (o.length > 0 && (o[0] instanceof java.lang.Integer)) {
                    w.receivedFeatureInfo((Integer) o[0]);
                }
            }
        };
        receiver.addListener(featureInfoString, listener);
    }


   private void addNumParamsListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("# params received!");
                Object[] o = message.getArguments();

                w.receivedNumParams(o);

            }
        };
        receiver.addListener(numParamsString, listener);
    }

      private void addPlayalongMessageListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("playalong message received!"); //only gets here after "Play along" executed-- because xmit not ready yet!!
                Object[] o = message.getArguments();
                w.receivedPlayalongUpdate((String)o[0]);
                
            }
        };
        receiver.addListener(playAlongMessage, listener);
          System.out.println("playalong message listener added");
    }

      private void addChuckSettingsListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("settings received!");
                Object[] o = message.getArguments();
                System.out.println("class " + o[0].getClass().toString());
                
                w.receivedChuckSettings(o);

            }
        };
        receiver.addListener(chuckSettingsString, listener);
    }
    
    private void addFeatureListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
              // System.out.println("Feature received!");
                Object[] o = message.getArguments();

                w.receivedFeature(o);

            }
        };
        receiver.addListener(featuresString, listener);
    }

    private void addRealValueListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
            //    System.out.println("Real value received!");
                Object[] o = message.getArguments();
              //  try {
                    //Introduce delay here?
                   // Thread.sleep(500);
               // } catch (InterruptedException ex) {
                //    Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
               // }
                w.receivedRealValue(o);

            }
        };
        receiver.addListener(realValueString, listener);
    }

    private void addHidSetupBegunListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Hid Setup begun");
                Object[] o = message.getArguments();
                h.setupBegun();
            }
        };
        receiver.addListener(hidSetupBegunString, listener);

    }

    private void addHidSetupStoppedListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                try {
                    System.out.println("Hid Setup stopped");
                    Object[] o = message.getArguments();

                    h.setupStopped();
                } catch (IOException ex) {
                    Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        receiver.addListener(hidSetupStoppedString, listener);
    }

    private void addSendHidInitValuesListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Hid received send hid init values message");
                Object[] o = message.getArguments();
                try {
                    h.receivedSendHidInitValues();
                } catch (IOException ex) {
                    Logger.getLogger(OscHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        receiver.addListener(sendHidInitValuesString, listener);
    }

    private void addHidSettingsNumsListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Received hid settings nums");
                Object[] o = message.getArguments();
                if (o.length == 3) {
                    Integer a = (Integer) o[0];
                    Integer b = (Integer) o[1];
                    Integer c = (Integer) o[2];
                    h.receivedHidSettingsNums(a, b, c);
                } else {
                    System.out.println("Wrong number of nums received");
                }
            }
        };
        receiver.addListener(hidSettingsNumsString, listener);

    }

    private void addHidSettingsAxesListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Received hid settings axes");
                Object[] o = message.getArguments();
                float f[] = new float[o.length];
                for (int i = 0; i < o.length; i++) {
                    f[i] = ((Float) o[i]).floatValue();
                }
                h.receivedHidAxisSettings(f);
            }
        };
        receiver.addListener(hidSettingsAxesString, listener);

    }

    private void addHidSettingsHatsListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Received hid settings hats");
                Object[] o = message.getArguments();
                int f[] = new int[o.length];
                for (int i = 0; i < o.length; i++) {
                    f[i] = ((Integer) o[i]).intValue();
                }
                h.receivedHidHatsSettings(f);
            }
        };
        receiver.addListener(hidSettingsHatsString, listener);

    }

    private void addHidSettingsButtonsListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Received hid settings buttons");
                Object[] o = message.getArguments();
                int f[] = new int[o.length];
                for (int i = 0; i < o.length; i++) {
                    f[i] = ((Integer) o[i]).intValue();
                }
                h.receivedHidButtonsSettings(f);
            }
        };
        receiver.addListener(hidSettingButtonsString, listener);

    }
    
        private void addHidSettingsMaskListener() {
        OSCListener listener = new OSCListener() {

            public void acceptMessage(java.util.Date time, OSCMessage message) {
                System.out.println("Received hid settings mask");
                Object[] o = message.getArguments();
                int f[] = new int[o.length];
                for (int i = 0; i < o.length; i++) {
                    f[i] = ((Integer) o[i]).intValue();
                }
                h.receivedHidMaskSettings(f);
            }
        };
        receiver.addListener(hidSettingsMaskString, listener);

    }

        
        
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void playScore() throws IOException {
        Object[] o = new Object[2];
        o[0] = startPlaybackMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("Asked for start playback");

    }

    public void stopPlayback() throws IOException {
        Object[] o = new Object[2];
        o[0] = stopPlaybackMessageString;
        o[1] = new Integer(0);
        OSCMessage msg = new OSCMessage(sendControlMessageString, o);
        sender.send(msg);
        System.out.println("Asked for start playback");
    }
}