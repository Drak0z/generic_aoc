package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day5 extends Day {
	/*-
	Day 5: Life Support & Off-by-one Rotary Encryption
	
	After finally quenching the city’s thirst on day one, you’re now faced with another challenge. The life support subsystem at the Stampede grounds needs to be restarted, but there’s a twist.
	
	The city, in its infinite wisdom, decided to protect the server by encoding commands using the same rotary cipher you cracked previously. But, because nothing can ever be straightforward, all clockwise rotations on this cipher wheel have an off-by-one error (asymmetric encryption the wrong way).
	
	Your task is to encode this message to the server using the defective rotary cipher. Remember, counter-clockwise rotations are normal, but clockwise rotations have the off-by-one error.
	
	For example:
	
	If we were at position D and moving two stops counter-clockwise, we’d move to B
	However, if we were at position D and moving 2 clockwise, we’d expect to move to F but, due to this error, we actually need to move to G. If we were moving from F to F in a clockwise direction, we would move 1 stop (because of the error) vs. 0 stops in a counter-clockwise direction!
	For example, our same plaintext HELLO would encrypt to 8/24/19/1/23
	
	Start at H (position 8)
	Now move clockwise to E (because of our off-by-one error in this direction, we move 24 spaces)
	Move counter-clockwise to L (19 spaces)
	Move clockwise to L (despite already being there, this off by one error means we’re moving 1 space)
	Finally move counter-clockwise to O (23 spaces)
	The command to restart the life support system is:
	
	SYSTEMINITIALIZESTARTUPROUTINEPRONTOPLEASEPRETTYPLEASE
	Encrypt this message and provide the result.
	*/
	@Override
	public void solve(int n) {
		StringBuilder rotarySb = new StringBuilder();

		List<String> fileContents = getFileContents(input);
		for (String line : fileContents) {
			int pos = (int) line.charAt(0) - 64;

			boolean clockwise = true;
			rotarySb.append(pos);
			for (int i = 1; i < line.length(); i++) {
				int newPos = (int) line.charAt(i) - 64;
				int delta = 0;
				if (clockwise)
					delta = newPos - pos;
				else
					delta = pos - newPos;

				/*
				 * The off by 1 generates an interesting conundrum If we need to go
				 * "one letter back" in a clockwise position. "ABA"
				 * 
				 * Do we move 25 + 1, and thus apply the offset after the delta Or do we not
				 * move at all, applying the offset to the delta
				 * 
				 * The main question is: Does the rotary dial "miss" a click, Or does the system
				 * see the following character as "one lower"?
				 * 
				 * Since this would technically mean rotating the wheel counter clock- wise once
				 * and then clockwise, this feels against the spirit of the problem statement.
				 * As such, for this solution I assume the former - move 25+1.
				 */

				// if (clockwise) delta++; // <-- rotary reads wrong
				while (delta < 0)
					delta += 26;
				if (clockwise)
					delta++; // <-- rotary misses a click

				rotarySb.append("/" + delta);
				pos = newPos;
				clockwise = !clockwise;
			}
		}
		solver.add(rotarySb.toString());
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