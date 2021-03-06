/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wekinator;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rebecca
 */
public class OscSynthProxy {

    protected static final OscSynthProxy ref = new OscSynthProxy();
    OSCPortOut sender;

    public static void setup() throws UnknownHostException, SocketException {
        //call /OSCSynth/setup
       // InetAddress.
//        ref.sender = new OSCPortOut(InetAddress.getLocalHost(), sendPort);
              //  ref.sender = new OSCPortOut(InetAddress.getByName("Edgard.local"), sendPort);

        boolean isRemote = WekinatorInstance.getWekinatorInstance().getConfiguration().getOscSynthConfiguration().isUseRemoteHost();
        if (!isRemote) {
           ref.sender = new OSCPortOut(InetAddress.getLocalHost(),
                  WekinatorInstance.getWekinatorInstance().getConfiguration().getOscSynthConfiguration().getRemotePort());
        } else {
            ref.sender = new OSCPortOut(InetAddress.getByName(WekinatorInstance.getWekinatorInstance().getConfiguration().getOscSynthConfiguration().getRemoteHostName()),
                    WekinatorInstance.getWekinatorInstance().getConfiguration().getOscSynthConfiguration().getRemotePort());
        }

        try {
            Object[] o = {new Integer(1)};
            OSCMessage msg = new OSCMessage("/OSCSynth/setup", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }
    }

    public static void setParams(double[] params) {
        //RF: Changed this 2/26/11
     //   boolean isSep = WekinatorInstance.getWekinatorInstance().getConfiguration().getOscSynthConfiguration().getSendingSingleParameters();
     //   if (! isSep) {
        Object[] o = new Object[params.length];
        try {
            for (int i = 0; i < params.length; i++) {
                o[i] = new Float(params[i]);
            }
            OSCMessage msg = new OSCMessage("/OSCSynth/params", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }
     /*   } else {
            try {
            for (int i =0; i < params.length; i++) {

                    Object[] o = new Object[1];
                    o[0] = new Float(params[i]);
                    OSCMessage msg = new OSCMessage("/OSCSynth/param" + i, o);
                    ref.sender.send(msg);


            }
            } catch (IOException ex) {
                    Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
                }

        } */
    }

    public static void silent() {
        //call //OSCSynth/silent 1
                try {
            Object[] o = {new Integer(1)};
            OSCMessage msg = new OSCMessage("/OSCSynth/silent", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }

    }

    public static void sound() {
        //call /OSCSynth/sound i
                try {
            Object[] o = {new Integer(1)};
            OSCMessage msg = new OSCMessage("/OSCSynth/sound", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }

    }

    public static void startSendingParams() {
        //call /OSCSynth/startSendingParams s i f
        //where s is hostname, i is port, f is rate (TODO: Check this with processing synth)
       try {
            Object[] o = {"localhost", OscHandler.getOscHandler().receivePort, 100.0f};
            OSCMessage msg = new OSCMessage("/OSCSynth/startSendingParams", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }
    }

    public static void stopSendingParams() {
        //call 	/OSCSynth/stopParams i : stops getting params (i always 0) - when stopGettingParams() called - w/ playalong score.
         try {
             Object[] o = {new Integer(1)};
            OSCMessage msg = new OSCMessage("/OSCSynth/stopParams", o);
            ref.sender.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(OscSynthProxy.class.getName()).log(Level.SEVERE, null, ex);
            errorHappened(ex);
        }

    }

    private static void errorHappened(IOException ex) {
        //TODO: Action to advertise that OSC connection is having problems
        System.out.println("OSC connection problem in OscSynthProxy");
    }
}
