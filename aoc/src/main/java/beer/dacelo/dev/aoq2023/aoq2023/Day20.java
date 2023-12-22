package beer.dacelo.dev.aoq2023.aoq2023;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day20 extends Day {
    /*-
    Day 20: Network Bananalytics
    
    Sedrick continues to be strangely focused on ice cream shops, especially the business side of them. He's started his own chain of stores called "Blob and Jerry's"! Our Sneaky Network Analysis, Retrieval, and Forensics team (aka SNARF) have captured some of the network traffic between Sedrick's store's sales terminals and the head office. We need to analyze these transactions and figure out what his sales are like.
    
    You'll need to re-assemble the network traffic, figure out the protocol which the sales terminals use, and once again tell us where the most scoops of banana ice cream were sold.
    
    We know the cash registers communicate via the BlobNet protocol. It is very low level and only defines the streams of bytes going between two devices. Unfortunately we don't have any further information on what the bytes could mean...
    
    All we have figured out is that device #1 is the central accounting server, so all the others must be the sales terminals.
    
    BlobNet protocol
    BlobNet runs on a network that only transfers small (64 byte or less) packets.
    
    BlobNet provides connections between two devices.
    
    A connection could need to transfer any amount of data so BlobNet will wrap up the long streams of bytes into delicious packets.
    
    A connection consists of two parts, the request followed by the response. Each part is an unlimited length sequence of bytes.
    
    There can only be one active connection at a time between any pair of devices.
    
    Packet Structure:
    
    Packets are up to 64 bytes long
    
    Three byte header (present in all packets)
    
    1 byte: Source device ID
    1 byte: Destination device ID
    1 byte: Message type
    Valid values:
    Start connection (1)
    Data for request (2)
    End of request (3)
    Data for response (4)
    End of response (5)
    Data (e.g. following a header with Message type 2 or 4)
    1 byte: sequence number
    up to 60 bytes of data
    Only data packets are longer than 3 bytes.
    
    The network is not completely reliable, a packet may be sent multiple times before it makes it to the recipient.
    
    For a given connection, repeated identical packets should be treated as if it arrived only once.
    
    Packets are always in the correct order. "Start" and "End" messages won't occur in the wrong sequence.
    
    The sequence number is only needed to ignore repeats of the immediately previous data packet.
    
    Example Packet sequence for a connection between A and B, requested by A:
    
    startup
    A -> B Start connection (1)
    request data flow
    A -> B Data (2) sequence (0)
    A -> B Data (2) sequence (1)
    etc, as many packets as needed to send all the data for the request
    request complete
    A -> B End of request (3)
    response data flow
    B -> A Data (4) sequence (0)
    B -> A Data (4) sequence (1)
    B -> A Data (4) sequence (2)
    B -> A Data (4) sequence (3)
    etc, as many packets as needed to send the whole response
    shutdown
    B -> A End of response (5)
    Packet log file format
    The packet log file is plain text, one packet per line, in chronological order. The bytes of each packet are written as hexadecimal pairs in upper case.
    
    Sample data
    070101
    070102003F
    070103
    010704005448495320
    F0F101
    F0F102004865
    01070401495320
    010704024120
    F0F102016C6C6F3F
    010704034C454E4754485920
    F0F103
    0107040453414D504C4520
    01070405524553504F4E534520
    F0F103
    F1F00400476F6F6462796521
    010704065448415420
    010704075350414E5320
    F1F00400476F6F6462796521
    010704084F56455220
    010704094D414E5920
    F1F005
    0107040A5041434B45545320
    0107040B31323334353637383930
    010705
    This is a connection from device 240 to device 241 and at the same time a connection from 7 to 1.
    
    device 240 sends a request with bytes equivalent to "Hello?" in ascii
    device 241 sends a response "Goodbye!"
    device 7 sends the request "?"
    device 1 responds "THIS IS A LENGTHY SAMPLE RESPONSE THAT SPANS OVER MANY PACKETS 1234567890"
    Note the duplicated packets, caused by an ice cream spill onto the network cable.
    
    Question
    file download link
    
    Read the captured packets, re-assemble the connections, and process the data. Add up all the successful sales transactions, and enter the device ID which sold the largest number of scoops of banana flavored ice cream. As a decimal integer with no punctuation please.
    */

    private static class Orders {
	static Map<Integer, Integer> locationBananaScoops = new HashMap<Integer, Integer>();

	public static void parseConnection(Connection c) {
	    if (c.source != 1)
		return; // only connections between our order server and a destination are ok
	    if (c.request.indexOf("[{") == -1) {
		// weird, we don't have json?
		System.err.println(c.request);
		return;
	    }
	    // I should've really imported a library with json...
	    String json = c.request.substring(c.request.indexOf("[{")).replace("[", "").replace("]", "");
	    
	    // Do we have jackson?
	    //ObjectMapper mapper = new ObjectMapper();
	    //com.fasterxml.jackson.databind.ObjectMapper om;

	    if (!json.contains("banana"))
		return; // we don't really care for anything that doesn't have bananas at the moment...
	    System.out.println("Processing: " + json);
	    String[] orderData = json.split("},");
	    for (String order : orderData) {
		if (order.indexOf("banan") != -1) {
		    String[] detail = order.split(",");
		    for (String d : detail) {
			if (d.contains("scoops")) {
			    if (!locationBananaScoops.containsKey(c.destination)) {
				locationBananaScoops.put(c.destination, 0);
			    }
			    Integer scoops = Integer.parseInt(d.replace("}", "").split(":")[1].trim());
			    Integer totalScoops = scoops + locationBananaScoops.get(c.destination);
			    locationBananaScoops.put(c.destination, totalScoops);
			    System.out.println(
				    "  Total scoops at " + c.destination + " " + totalScoops + "(+" + scoops + ")");
			}
		    }

		}
	    }

	}
    }

    private static class Connection {
	String uniqueKey, request, response;
	Integer source, destination;

	public Connection(Integer source, Integer destination, String uniqueKey, String request, String response) {
	    this.source = source;
	    this.destination = destination;
	    this.uniqueKey = uniqueKey;
	    this.request = request;
	    this.response = response;
	}

	public String toString() {
	    return (uniqueKey + ": \r\n" + request + "\r\n" + response);
	}
    }

    private static class SNARF {
	public static String dataToString(String uniqueKey, String type) {
	    if (!communicationMap.containsKey(uniqueKey) || !communicationMap.get(uniqueKey).containsKey(type))
		return "";
	    List<byte[]> resData = communicationMap.get(uniqueKey).get(type);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    for (byte[] bArray : resData) {
		if (bArray.length > 0) {
		    for (int i = 0; i < bArray.length; i++)
			if (bArray[i] != 0) {
			    out.write(bArray[i]);
			}
		}
	    }
	    return out.toString();
	}

	static Map<String, Map<String, List<byte[]>>> communicationMap = new HashMap<String, Map<String, List<byte[]>>>();
	static List<Connection> okConnections = new ArrayList<Connection>();
	static Map<String, Integer> mode = new HashMap<String, Integer>();

	private static void process(String logLine) {
	    // every 2 characters is one "byte"?
	    /*-
	     * 1 byte: Source device ID
	     * 1 byte: Destination device ID
	     * 1 byte: Message type
	     * - Valid values:
	     * - Start connection (1)
	     * - Data for request (2)
	     * - End of request (3)
	     * - Data for response (4)
	     * - End of response (5)
	     * Data (e.g. following a header with Message type 2 or 4)
	     * - 1 byte: sequence number
	     * - up to 60 bytes of data
	     * Only data packets are longer than 3 bytes.
	     */
	    byte[] data = new byte[logLine.length() / 2 - 3];
	    int sourceDevice = 0;
	    int destinationDevice = 0;
	    int messageType = 0;
	    int sequenceNumber = 0;

	    for (int i = 0; i < logLine.length(); i += 2) {
		String hexString = "" + logLine.charAt(i) + logLine.charAt(i + 1);
		int thisInt = Integer.parseInt(hexString, 16);// bit of an ugly hack, but bytes in Java go from
		// -128 to 127, not 0 to 256 (eventhough the intvalue is 128...)
		switch (i / 2) {
		case 0:
		    sourceDevice = thisInt;
		    break;
		case 1:
		    destinationDevice = thisInt;
		    break;
		case 2:
		    messageType = thisInt;
		    break;
		case 3:
		    if (messageType != 2 && messageType != 4)
			System.out.println("Danger Will Robinson!");
		    sequenceNumber = thisInt;
		    break;
		default:
		    data[(i / 2) - 3] = (byte) thisInt;
		    break;
		}
	    }
	    // we've processed everything into bytes now... Let's see what's what?
	    String uniqueKey = null;
	    switch (messageType) {
	    case 1:
	    case 2:
	    case 3:
		uniqueKey = "[source:" + sourceDevice + ", destination:" + destinationDevice + "]";
		break;
	    case 4:
	    case 5: // this is a response (hopefully :D )
		uniqueKey = "[source:" + destinationDevice + ", destination:" + sourceDevice + "]";
		break;
	    }

	    List<byte[]> reqData, resData;
	    Map<String, List<byte[]>> comms;
	    Integer messageMode = mode.get(uniqueKey);
	    if (messageType == 1 && (messageMode == null || messageMode == 5)) {
		mode.put(uniqueKey, messageType);
		comms = new HashMap<String, List<byte[]>>();
		comms.put("req", new ArrayList<byte[]>());
		comms.put("res", new ArrayList<byte[]>());
		communicationMap.put(uniqueKey, comms);
	    }

	    comms = communicationMap.get(uniqueKey);
	    switch (messageType) {
	    case 2: // data for request
		if (messageMode == 1 || messageMode == 2) {
		    reqData = comms.get("req");
		    if (reqData.size() > sequenceNumber) {
			boolean dataMatches = (reqData.get(sequenceNumber).length == data.length);
			if (dataMatches) {
			    for (int i = 0; i < reqData.get(sequenceNumber).length; i++) {
				if (reqData.get(sequenceNumber)[i] != data[i]) {
				    dataMatches = false;
				    break;
				}
			    }
			}
			if (!dataMatches)
			    System.err.println("Data mismatch for the same sequence number");
		    }
		    while (reqData.size() <= sequenceNumber) {
			reqData.add(new byte[0]);
		    }
		    reqData.set(sequenceNumber, data);
		    comms.put("req", reqData);
		    communicationMap.put(uniqueKey, comms);
		    mode.put(uniqueKey, messageType);
		} else {
		    System.err.println("DANGER WILL ROBINSON! Previous message mode: " + messageMode
			    + ", but we're getting data for our request");
		}
		break;
	    case 3: // end of request, wait for request
		if (messageMode == 2) {
		    // System.out.println("End of Request: " + uniqueKey + "\r\n" +
		    // dataToString(uniqueKey, "req"));
		    comms.put("res", new ArrayList<byte[]>());
		    communicationMap.put(uniqueKey, comms);
		    mode.put(uniqueKey, messageType);
		} else {
		    System.err.println("DANGER WILL ROBINSON! Previous message mode: " + messageMode
			    + ", but we're getting our end of request");
		}
		break;
	    case 4:
		if (messageMode == 3 || messageMode == 4) {
		    resData = comms.get("res");
		    while (resData.size() <= sequenceNumber) {
			resData.add(new byte[0]);
		    }
		    resData.set(sequenceNumber, data);
		    comms.put("res", resData);
		    communicationMap.put(uniqueKey, comms);
		    mode.put(uniqueKey, messageType);
		}
		break;
	    case 5: // end of response
		String responseString = dataToString(uniqueKey, "res");
		if (mode.get(uniqueKey) == 4 && responseString.contains("HTTP/1.0 200 OK")) {
		    Connection c = new Connection(sourceDevice, destinationDevice, uniqueKey,
			    dataToString(uniqueKey, "req"), dataToString(uniqueKey, "res"));
		    okConnections.add(c);
		    // successfulConnections.add(comms);
		}
		// System.out.println("End of Response: " + uniqueKey + "\r\n" +
		// responseString);
		communicationMap.remove(uniqueKey); // end connection, remove all the things!
		mode.put(uniqueKey, messageType);
		break;
	    }
	}

	private static int uniqueId = 0;

	public static void writeAll(String outputFilePath, List<Connection> connections) {
	    // Let's write what we have?
	    BufferedWriter writer;
	    for (Connection c : connections) {
		uniqueId++;
		System.out.println("key: " + c.uniqueKey);
		String fileName, fileExt;
		if (c.request.contains("application/json"))
		    fileExt = ".json";
		else if (c.request.contains("image/png"))
		    fileExt = ".png";
		else
		    fileExt = ".txt";
		fileName = outputFilePath + c.uniqueKey.replace(":", "_") + "." + uniqueId + ".req" + fileExt;
		try {
		    writer = new BufferedWriter(new FileWriter(fileName));
		    writer.write(c.request);
		    writer.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		fileName = outputFilePath + c.uniqueKey.replace(":", "_") + "." + uniqueId + ".res" + fileExt;
		if (c.response.contains("application/json"))
		    fileExt = ".json";
		else if (c.response.contains("image/png"))
		    fileExt = ".png";
		else
		    fileExt = ".txt";
		try {
		    // I really want to handle that PNG....
		    writer = new BufferedWriter(new FileWriter(fileName));
		    writer.write(c.response);
		    writer.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }
	}
    }

    @Override
    public void solve(int n) {
	StringBuilder output = new StringBuilder();

	for (String line : getFileContents(input)) {
	    SNARF.process(line);
	}

	// SNARF.printAll();
	String outputFilePath = getFilePath().replace("input.txt", "").replace("test_", "").replace("input", "output");
	// SNARF.writeAll(outputFilePath);

	for (Connection c : SNARF.okConnections) {
	    Orders.parseConnection(c);
	}
	Integer maxScoops = 0;
	Integer maxLocation = 0;
	for (Integer location : Orders.locationBananaScoops.keySet()) {
	    Integer scoops = Orders.locationBananaScoops.get(location);
	    System.out.println("Location: " + location + ", Scoops: " + scoops);
	    if (scoops > maxScoops) {
		maxScoops = scoops;
		maxLocation = location;
	    }
	}
	SNARF.writeAll(outputFilePath, SNARF.okConnections);
	output.append(maxLocation);
	solver.add(output.toString());
    } // solve
}
