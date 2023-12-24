package beer.dacelo.dev.aoq2023.aoq2023;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day22 extends Day {
    /*-
    Day 22: Mysterious Giant Spinning Disk
    
    You may remember a while back we blew up Sedrick. The splattered pieces of him, we believed, had coalesced into a single entity. We were wrong. As it turns out, all of this blowing up has created a second problem for us. Sedrick's twin blob! Let's also call him Sedrick as presumably the two will meet up and become one again. In the meantime, they realized they needed a way to communicate as they do not share a brain... or seem to have one we can see. The science on blobs is surprisingly thin at this time.
    
    They have set up a Mysterious Giant Spinning Disk and are using it to communicate by varying the rotation speed faster and slower. We sent out our best team of clipboard-and-stopwatch operators to record the motion of the MGSD™ so we can find out what the Sedricks are saying.
    
    It turns out that there's not a lot of detail in the information you can convey with a giant spinning disk. All that seems to be happening is that they are using two speeds ("faster" and "slower" (apologies for the technical terms)) to indicate a single bit of data. Since the messages they are sending contain more than one bit, they use an encoding scheme to delimit each bit.
    
    Encoding
    Bits are sent at a fixed rate.
    A pulse of higher rotation speed represents a bit.
    The pulse for a 1 bit is longer than the pulse for a 0.
    A time period with no pulse marks the end of each byte.
    
    Measurement data
    The files contain the rotational position of the MGSD™ (in degrees), sampled at a constant interval. The measurements do have a small amount of noise and imprecision. It's hard to be accurate while hiding from a pair of evil blobs.
    
    The messages are plain ascii text once you string all the bits together.
    
    The demo data encodes the text "DEMO" using the same rotation speeds as the real message.
    
    Demo data
    
    Message to decode
    
    Enter the exact text of the message.
    
    Your Response   
    */
    private class MGSD {
	List<Double> rotateData;
	String message;

	public MGSD(List<Double> rotateData) {
	    this.rotateData = rotateData;
	}

	private Integer pulsesToBit(List<Integer> pulses) {
	    int low = Collections.frequency(pulses, 0);
	    int high = Collections.frequency(pulses, 1);
	    if (low > high)
		return 0;
	    return 1;
	}

	private byte nineBitsToByte(List<Integer> pulses) {
	    StringBuilder byteSb = new StringBuilder();
	    for (int i = 0; i < 8; i++) {
		byteSb.append(pulses.get(i));
	    }
	    return (byte) (int) Integer.valueOf(byteSb.toString(), 2);
	}

	public String interpretPulses(Double speedThreshold) {
	    List<Byte> data = new ArrayList<Byte>();
	    // My hypothesis was wrong and I couldn't figure it out so, bruteforce!
	    // for the test input, we need: 3 or 4 stops (not sure if the last one is a
	    // stop), 4 bytes, 4x8=32 bits
	    // We have 7596 lines of test data that need to be encoded to 32 bits + 4
	    // stop bits... That means timePeriod = 7596/36 chunks...
	    // Also, no idea why my logic can't figure out the stop bit, but since it's
	    // 8 bits + 1 stop bit, I will simply convert "9" bits to a byte and ignore the
	    // stop bit
	    Double previousDegree = 0.0;
	    Double timePeriod = 7596.0 / 36.0;
	    List<Integer> speedPulses = new ArrayList<Integer>();
	    List<Integer> speedBits = new ArrayList<Integer>();
	    List<Integer> allSpeedBits = new ArrayList<Integer>();
	    for (Double thisDegree : rotateData) {
		Double delta = (thisDegree - previousDegree + 360) % 360;
		if (delta > speedThreshold) {
		    speedPulses.add(1);
		} else if (delta < speedThreshold) {
		    speedPulses.add(0);
		} else {
		    System.err.println("This is unexpected, speed low == high");
		}

		if (speedPulses.size() >= timePeriod) {
		    speedBits.add(pulsesToBit(speedPulses));
		    speedPulses = new ArrayList<Integer>();
		    if (speedBits.size() == 9) {
			// we have 8 bits and a 0, convert this to a byte?
			data.add(nineBitsToByte(speedBits));
			allSpeedBits.addAll(speedBits);
			speedBits = new ArrayList<Integer>();
		    }
		}

		previousDegree = thisDegree;
	    }
	    return dataToString(data);
	}

	public void brrrr() {
	    Double speedThreshold = 0.0;
	    /*-
	    while (speedThreshold < 300) {
	    String output = interpretPulses(speedThreshold);
	    if (output.length() > 0)
	        System.out.println(speedThreshold + " results in : " + output);
	    // Anywhere between 31.8 and 48.0 results in DEMO (but it does not equal string "DEMO"?!)
	    speedThreshold+=0.1;
	    } */
	    speedThreshold = (31.8 + 48.0) / 2;
	    message = interpretPulses(speedThreshold);
	} // brrrr

	public String dataToString(List<Byte> data) {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    for (Byte b : data) {
		if (b != 0) {
		    out.write(b);
		}
	    }
	    return out.toString();
	}
    }

    @Override
    public void solve(int n) {
	List<Double> rotateData = new ArrayList<Double>();
	for (String line : getFileContents(input)) {
	    rotateData.add(Double.parseDouble(line));
	}

	MGSD mgsd = new MGSD(rotateData);
	mgsd.brrrr();

	solver.add(mgsd.message);
    } // solve
}
