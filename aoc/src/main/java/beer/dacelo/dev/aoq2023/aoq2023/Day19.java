package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day19 extends Day {
    /*-
    Day 19: Secrets And Blobs
    
    Sedrick has not only been growing in size but also in brain power! I know we have blown him up (#blowHimUp), tried beating him at cards, figuring what he is made of, and decoded messages and favorite numbers based on palindromes and variants of the Fibonacci series but now he really has stepped up his game. He has found an old book on encryption. Apparently he swallowed a library and was just sifting through some of the undigested contents. He has learned a great deal...
    
    Now he wants to be able to send messages that no one but the intended recipient can read. He's worked out that you can do this by having a secret piece of information, the "key", that only the sender and recipient know.
    
    He converts the message (just letters and spaces, no digits or punctuation) to numbers in the usual way: [space] = 0, A = 1, B = 2, up to Z = 26.
    
    Then to make the secret message he generates a secret sequence of random values and adds one to each number.
    
    Anyone looking at the message won't know what it says unless they also have the same sequence of random numbers to subtract.
    
    Message: HELLO
    Numbers: 8, 5, 12, 12, 15
    Random: 224, 723, 471, 126, 449
    Secret Message: 232, 728, 483, 138, 464
    Evil Eavesdropper: "What are these numbers?  I don't understand!"
    Knowledgeable Recipient: "I know the first secret random number is 224, and 232-224 is 8, so the first letter of the message is 'H'"
    Not all messages are the same length, so it would be super helpful to have a short value that represents a long sequence of random values. Conveniently, the SRSG™ random sequence generator already exists.
    
    Sedrick will use the six digits from the SRSG™ as the secret key value, and take 4 digits at a time from its output sequence to make each random number that is needed to encrypt or decrypt the message.
    
    Using the SRSG™ example of 496898 and its output of 8986942265... and so on, the message of "TESTING" (20,5,19,20,9,14,7) will encrypt like this
    
    Create groups of four digits:
    8986 9422 6581 1292 4482 1270 2938
    
    8986+20=9006
    9422+5=9427
    6581+19=6600
    1292+20=1312
    4482+9=4491
    1270+14=1284
    2938+7=2945
    So the secret message would be 9006, 9427, 6600, 1312, 4491, 1284, 2945
    
    The recipient who knows the secret key is 496898 will generate the same sequence and subtract them from the secret message to decrypt it.
    
    9006-8986=20
    9247-9422=5
    etc.
    For a longer example: 6563, 2180, 1974, 7726, 7568, 683, 2982, 6576, 4408, 6174, 3441, 8550, 6364, 5535, 4335, 9700, 6078, 7538, 1950, 4698, 4792, 8076, 2915, 6775 decrypts to "THIS IS A SECRET MESSAGE" using key 123456.
    
    Question: what key was used to encrypt the following secret message?
    
    6766, 4601, 1078, 2000, 8977, 7453, 2406, 2308, 6794, 3217, 4455, 8811, 6021, 9544, 6999, 6364, 5388, 5799, 9332, 2158, 8161, 7677, 5422, 116, 4864, 3020, 3288, 9605, 4760, 7858, 887, 1741, 7821, 6125, 3850, 3291, 1519, 8308, 3834, 622, 7162, 2901, 1040, 1310, 4240, 2331, 9260, 3786, 1044, 9287, 6290, 4301, 7907, 1570, 5989, 7380, 9668, 903, 4174, 7765, 8780, 9840, 3372, 2470, 2020, 2548, 3920, 5407, 5782, 2084, 5072, 8879, 2291, 3590, 6142, 9165, 8217, 6624, 712, 3235, 896, 1950, 9304, 1445, 4529, 780, 6113, 8373, 6437, 8584, 7265, 9588, 8969, 3063, 692, 6715, 8019, 120, 4894, 154, 4576, 1394, 4269, 4636, 3185
    
    The answer should be exactly six digits and nothing else, please.
    */


    private String decode(Integer character, Integer key) {
	List<String> alphabet = List.of(" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
		"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
	Integer pos = character - key;
	if (pos >= 0 && pos < alphabet.size())
	    return alphabet.get(pos);
	return null;
    }
    
    @Override
    public void solve(int n) {
	StringBuilder output = new StringBuilder();
	List<String> encryptedMessage = List.of(getFileContents(input).get(0).split(", "));

	Integer testKey = 100000;
	while (testKey < 1000000) {
	    int[] ploof = Integer.toString(testKey).chars().map(c -> c - '0').toArray();
	    LinkedList<Integer> decryptKey = new LinkedList<Integer>(Arrays.stream(ploof).boxed().toList());
	    StringBuilder decryptedMessage = new StringBuilder();
	    Boolean possible = true;
	    for (String s : encryptedMessage) {
		StringBuilder fourDigit = new StringBuilder();
		while (fourDigit.length() < 4) {
		    int newDigit = Day9.calculateNew(decryptKey);
		    int outputValue = decryptKey.removeLast(); // remove(digit.size() - 1);
		    decryptKey.addFirst(newDigit); // .add(0, newDigit);
		    fourDigit.append(outputValue);
		}
		String next = decode(Integer.parseInt(s), Integer.parseInt(fourDigit.toString()));
		if (next == null) {
		    possible = false;
		    break;
		}
		decryptedMessage.append(next);

	    }
	    if (possible) {
		System.out.println("Decoded message with key ["+testKey+"]: " + decryptedMessage);
		solver.add(testKey + ": " + decryptedMessage);
	    }
	    testKey++;
	}
	solver.add(output.toString());
    } // solve
}
