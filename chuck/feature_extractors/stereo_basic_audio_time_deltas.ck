/* Stereo audio features, with time deltas, for Cameron's drum hit stuff

	NOT DONE YET!

 Wekinator version 0.2
 Copyright 2009 Rebecca Fiebrink
 http://wekinator.cs.princeton.edu
*/

public class CustomFeatureExtractor {
	0 => int isDebug;

	//Keep this part: Keeps track of setup and run state, also
	//stores features in a nicely named features[] array
	0 => int isExtracting;
	1 => int isOK;
	12 => int numNowFeats;
	3 => int numWindows;
	numNowFeats * numWindows => int numFeats; //4 features x 2 channels, + deltas
	new float[numFeats] @=> float features[]; //store computed features in this array
	new float[numNowFeats] @=> float nowFeatures[];
	new float[nomNowFeats] @=> float lastFeatures[];
	
	//TODO: Optionally change the rate at which features are extracted
	//This may not correspond to rate at which they are polled.
	100::ms => dur defaultRate => dur rate;

	//TODO: Try setting these in different ways
	512 => int FFT_SIZE;
	256 => int WINDOW_SIZE;
	

	//TODO: create any custom objects here:
	//assume adc.channels() == 2 for now

	FFT fft[2];
	RMS rms[2];
	Centroid centroid[2];
 	Flux flux[2];
	RollOff rolloff[2];
	FeatureCollector fc[2];
	UAnaBlob b[2];
	adc.chan(0) => fft[0] => blackhole;
	Noise n => fft[1] => blackhole;

	for (0 => int i; i < 2; i++) {
//		adc.chan(i) => fft[i] => blackhole;
		
		FFT_SIZE => fft[i].size;
		Windowing.hamming(WINDOW_SIZE) => fft[i].window;
		fft[i] =^ centroid[i] =^ fc[i];
		fft[i] =^ rms[i] =^ fc[i];
		fft[i] =^ rolloff[i] =^ fc[i];
		fft[i] =^ flux[i] =^ fc[i];
	}
	
	//TODO: Fill in function for computing features
	fun void computeFeatures() {

		//features are chan0|chan1|[chan0-chan1]
		fc[0].upchuck() @=> b[0];
		fc[1].upchuck() @=> b[1];

		0 => int i;
		for (0 => i; i < b[0].fvals().size(); i++) {
			b[0].fval(i) => nowFeatures[i];
		}
		0 => int j;
		for (0 => j; j < b[1].fvals().size(); j++) {
			b[1].fval(j) => nowFeatures[i + j];
		}
		i + j => i;
		for (0 => j; j < b[1].fvals().size(); j++) {
			b[0].fval(j) - b[1].fval(j) => nowFeatures[i + j];
		}

		//Now do deltas


		if (isDebug) {
			//Print out
			<<< "FEATURES">>>;
			for (0 => i; i < features.size(); i++) {
				<<< i , features[i] >>>;
			}
		}
	}

	//TODO: Any necessary setup work here
	//would set 0 => isOK if any problems happen.
	fun void setup() {
		0 => isExtracting;
		1 => isOK;
		new float[numFeats] @=> float features[];
		defaultRate => rate;
	}
	
/*** Shouldn't have to edit anything beyond this point **/

	//Calls setup, also checks that we agree on # featuress
	fun void setup(int n) {
		setup();
		if (n != numFeats) {
			0 => isOK;
			<<< "Error: we don't agree on the number of features!">>>;
		}
	}

	//Return the features
	fun float[] getFeatures() {
		return features;
	}

	fun int numFeatures() {
		return numFeats;
	}

	//Extraction loop, given user-specified functions above
	fun void extract() {
		if (! isExtracting) {
		  1 => isExtracting;
			while (isExtracting) {
				computeFeatures();			
				rate => now;
		 }
	   }
	}			

	//Stop extracting
	fun void stop() {
		0 => isExtracting;
	}
}