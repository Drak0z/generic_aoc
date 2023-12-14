package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day12 extends Day {
    /**
     * Day 12: Deep Ticker Tape Thoughts
     * 
     * Poor Moonshot the elf sighed. "Sedrick ate my beloved vintage ticker tape
     * machine last week." Yeah, Sedrick eats all sorts of things. And elves cite
     * Wikipedia when they're sad.
     * 
     * But on the bright side, Sedrick has somehow integrated the ticker tape
     * machine into his thoughts. We were finding lengths of tape with confusing
     * things written to them. Purely by accident, we found that folding the tape
     * back and forth vertically produced rudimentary images!
     * 
     * eEEAaattT works out to:
     * 
     * eat Eat EAT There were even decorative ones like /|\^ vvC^^hvvo^^mvvp^^ v\|/:
     * 
     * /vvvvvvv\ | Chomp | \^^^^^^^/ We think the machine has a real chance at
     * revealing Sedrick's deepest motivations. The further into his mind it goes,
     * the bigger the image. They started off simple: (3 × 3), and (9 × 3), like
     * what's shown above.
     * 
     * The latest length of tape we found was a doozy, long enough to produce a (45
     * × 27) image. As useful as the info may be, we're thoroughly grossed out at
     * the thought of handling that much ooze-covered ticker tape. What does it
     * mean? What motivates Sedrick? Answer in exactly the same case as what's in
     * the image.
     */
    List<String> solver = new ArrayList<String>();

    @Override
    public void solve(int n) {
	List<String> fileContents = getFileContents(input);
	// For ease of use, I've stored the expected width as the first line in the file
	// The second line is the actual (given) input
	Integer outputWidth = Integer.parseInt(fileContents.get(0));
	String inputLine = fileContents.get(1);
	Integer outputHeight = inputLine.length() / outputWidth;
	List<StringBuilder> sbArray = new ArrayList<StringBuilder>();
	for (int i = 0; i < outputHeight; i++) {
	    sbArray.add(new StringBuilder());
	}

	int y = 0;
	// we need to do wibbly wobbly stuff with the y!
	boolean ascending = false;
	for (int i = 0; i < inputLine.length(); i++) {
	    if (i % outputHeight == 0)
		ascending = !ascending; // wobble!

	    if (ascending)
		y = i % outputHeight;
	    else
		y = outputHeight - (i % outputHeight) - 1;

	    sbArray.get(y).append(inputLine.charAt(i));
	}

	// Now let's get this transformed to Strings properly, and sent to our
	// solver/output
	for (StringBuilder sb : sbArray) {
	    solver.add(sb.toString());
	}
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	for (int i = 0; i < solver.size(); i++) {
	    solution.add(solver.get(i).toString());
	}
	return solution;
    } // getSolution
}
