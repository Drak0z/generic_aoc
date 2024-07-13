package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day2 extends Day {
	/*-
	Day 2: Hack the Water!

	After day 1, everyone is parched, so it’s time to turn on the water fountains on the Stampede grounds. Unfortunately, the city put a giant weird combo lock on the water main for the Stampede grounds (due to the city’s ongoing water crisis).
	
	The code for the combo lock is “encrypted” using a novel rotary cipher. Imagine the letters of the alphabet (A-Z) around an old-school cereal box decoding ring. The cursor starts at the first position, and then takes turns rotating clockwise, and then counter-clockwise to reveal successive letters of the plaintext.
	
	For example, a ciphertext of 8/23/19/0/23 would be decoded as follows:
	
	The first letter is H (8th letter of the alphabet)
	Now rotate clockwise 23 spots, which now has us pointing at E
	Now counter-clockwise 19 spots, leaving us pointing at L
	Now a brisk clockwise rotation of 0. Yup, we’re still at L
	Finally counter-clockwise 23 leaving as O.
	The decrypted word is HELLO.
	Write a function that decodes the full encrypted code.
	
	20/14/3/24/14/24/11/7/21/13/7/15/20/25/5/10/4/25/9/15/23/18/21/14/16/13/0/1/17/6/3/14/23/13/17/14/10/15
	*/

	@Override
	public void solve(int n) {
		StringBuilder solution = new StringBuilder();
		for (String line : getFileContents(input)) {
			int[] intArray = Arrays.stream(line.split("/"))
				    .mapToInt(Integer::parseInt)
				    .toArray();
			int pos = 0;
			boolean clockwise = true;
			for (int i = 0; i < intArray.length; i++) {
				if (clockwise) pos += intArray[i];
				else pos -= intArray[i];

				System.out.print("Test: " + intArray[i] + ": " + pos);
				while (pos > 26) pos -= 26;
				while (pos < 1) pos += 26;
				if (i > 0) clockwise = !clockwise;
				
				System.out.println(", new pos: " + pos + " " + Util.getCharForNumber(pos));
				solution.append(Util.getCharForNumber(pos));				
			}
			solver.add(solution.toString());
		}
		System.out.println(solution.toString());
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