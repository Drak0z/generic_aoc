package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day6 extends Day {
	/*-
	 * Day 6: What's thEE Frequency??
	 * 
	 * There have been some complaints about Neville. Ok, many complaints. So many
	 * that the Stampede has created a “feedback” form and asked the public for
	 * their input on the new supercomputer. We have learned that Neville has caught
	 * wind of this and has tried fudging the numbers by submitting fake feedback
	 * entries. Neville, having read the entire internet, knows that the frequency
	 * of the letter E in the English language is 11.1607% according to the Oxford
	 * English dictionary.
	 * 
	 * 
	 * So he figured he would write all of his feedback with the letter E occurring
	 * between 11.1 and 11.2 percent of the time. This is his signature. All natural
	 * entries do not occur like this. How many entries are Neville’s?
	 * 
	 * Here are two examples as they would appear in the file (entries have been
	 * separated by five or more dashes (i.e. -):
	 * 
	 * Neville was great, everything ran smoothly and perfectly organized!!!!!!-----------------------------------------------------------------Neville excelled; the event was flawless and highly efficient. 
	 * Breaking them apart we get:
	 * 
	 * Neville was great, everything ran smoothly and perfectly organized!!!!!! 
	 * 72 characters 
	 * 8 Occurrences of ‘e’ (or ‘E’ case does not matter)
	 * 11.11111111111111% 
	 * Conclusion: Neville wrote this!
	 * 
	 * Neville excelled; the event was flawless and highly efficient. 
	 * 62 characters
	 * 11 Occurrences of ‘e’ (or ‘E’ case does not matter) 
	 * 17.74193548387097%
	 * Conclusion: Neville did not write this!
	 * 
	 * For the given sample we would answer 1.
	 * 
	 * We have gotten a collection of data here which contains 20 letters. How many
	 * were written by Neville?
	 */

	private boolean validate(String segmentString) {
		int numCharacters = segmentString.length();
		int numEs = segmentString.toLowerCase().split("e").length - 1;
		System.out.println("Test: >" + segmentString + "<");
		System.out.println(numCharacters + " characters");
		System.out.println(numEs + " e characters?");
		double pct = ((double) numEs / (double) numCharacters) * 100;
		System.out.println(pct + "%");
		if (pct > 11.1 && pct < 11.2) {
			System.out.println("Conclusion: Neville wrote this!");
		} else {
			System.out.println("Conclusion: Neville did not write this!");
		}
		return pct > 11.1 && pct < 11.2;
	}

	@Override
	public void solve(int n) {
		Integer solutionCount = 0;

		List<String> fileContents = getFileContents(input);
		for (String line : fileContents) {
			int dashCount = 0;
			int segmentStart = 0;
			int segmentEnd = 0;
			boolean dashFind = false;

			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '-') {
					if (!dashFind)
						dashFind = true;
					dashCount++;
				}
				if (line.charAt(i) != '-') {
					if (dashFind) {
						if (dashCount >= 5) {
							// Process the previous line
							String segmentString = line.substring(segmentStart, segmentEnd + 1);
							if (validate(segmentString))
								solutionCount++;
							// Reset dashFind
							dashFind = false;
							segmentStart = i;
							// Start the new segment
						} else { // we've had 4 or fewer dashes, not the end of a segment
							dashFind = false;
						}
						dashCount = 0;
					}
					if (!dashFind)
						segmentEnd = i;
				}
			}
			// Let's not forget to process the final one
			String segmentString = line.substring(segmentStart, segmentEnd + 1);
			if (validate(segmentString))
				solutionCount++;
		}

		System.out.println("Number of entries Neville wrote: " + solutionCount);
		solver.add(solutionCount.toString());
	}

	@Override
	public List<String> getSolution(int n) {
		solution = new ArrayList<String>();
		for (int i = 0; i < solver.size(); i++) {
			solution.add(solver.get(i).toString());
		}
		return solution;
	}
};