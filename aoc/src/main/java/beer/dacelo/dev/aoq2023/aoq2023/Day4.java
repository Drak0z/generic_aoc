package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day4 extends Day {
    /**
     * Day 4: Candy Genetics
     * 
     * Our scientists obtained a "blood" sample from Sedrick and have sequenced his
     * DNA. The hope is that our teams can create a virus that will modify Sedrick's
     * DNA and slow his growth to buy us more time.
     * 
     * Instead of the usual bases (adenine, thymine, guanine, and cytosine - A, T,
     * G, and C), Sedrick's genetic code is composed of the following bases
     * (caneosine, aspartine, nectardine, dextradrine, and yumodine - C, A, N, D,
     * and Y). Weird! Science!
     * 
     * The first step in understanding Sedrick's DNA is to find how many unique
     * sequences of 6 bases exist in Sedrick's sequenced DNA where no base is
     * present more than twice. Does this sound random and implausible? Are you a
     * genetic biologist? If not, you'll have to trust us that this is totally
     * legit! If yes... sorry!
     * 
     * For example, if Sedrick's sequenced DNA was (it's not.... it's more complex):
     * 
     * CANDYCACACANDYCA We can break it apart into 10 possible 6 base sequences:
     * 
     * Position 0 - CANDYC is valid 
     * Position 1 - ANDYCA is valid 
     * Position 2 - NDYCAC is valid 
     * Position 3 - DYCACA is valid 
     * Position 4 - YCACAC is NOT valid
     * Position 5 - CACACA is NOT valid 
     * Position 6 - ACACAN is NOT valid 
     * Position 7 - CACAND is valid 
     * Position 8 - ACANDY is valid 
     * Position 9 - CANDYC is valid
     * Position 10 - ANDYCA is valid 
     * 
     * Note that position 4 (YCACAC) is invalid
     * because C is repeated 3 times. CACACA is invalid because both C and A are
     * repeated more than twice, and ACACAN has one too many As to be valid.
     * 
     * This leaves 8 valid sequences. However, CANDYC and ANDYCA are duplicated,
     * which means we are left with 6 unique (but valid) 6 base sequences.
     * 
     * However, as mentioned, Sedrick's DNA is much more complex. Here it is!
     * 
     * NYNNYDYYYDDNCDDNNDNNACYYDNCDCCYDNAYACYNYNANNACCCNNNYAAADCANNDNCADCYNYCCYNDCDCDDADYCNCYNCNDNNNNNNYNACNDNCNNNNACNCNNDCNYYAAYYDAYNDNNNNACYCDCDNCCNYYAADACNCANCNCNANDNAAYYYDCCDDDAYYNCCNNDDDDDCCANNANNYNNCNN
     * How many unique and valid 6 base sequences are in Sedrick's DNA?
     */
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	Set<String> uniqueSequence = new HashSet<String>();
	for (String line : getFileContents(input)) {	
	    detail.add(line);
	    for (int i = 0; i+5 < line.length(); i++) {
		Boolean valid = true;
		String sequence = line.substring(i, i+6);
		Map<Character, Integer> dna = new HashMap<Character, Integer>(Map.of('C', 0, 'A', 0, 'N', 0, 'D', 0, 'Y', 0));
		for (Character c : sequence.toCharArray()) {
		    Integer num = dna.get(c)+1;
		    if (num > 2) valid = false;
		    dna.put(c, num);
		}
		detail.add("Position " + i + " - " + sequence + " is" + (!valid ? " NOT " : " ") + "valid");
		if (valid) {
		    detail.add("Have we stored this yet? " + uniqueSequence.contains(sequence));
		    uniqueSequence.add(sequence);
		} // if
	    } // for (int i = 0; i+5 < line.length(); i++)
	} // for (String line : getFileContents)
	detail.add("Unique sequences identified: " + uniqueSequence.toString());
	solver.add(uniqueSequence.size());
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
