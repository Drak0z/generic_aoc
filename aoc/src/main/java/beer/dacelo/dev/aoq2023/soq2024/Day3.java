package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day3 extends Day {
	/*-
	Day 3: A Sure Bet
	
	Given the current year’s ongoing fiasco, our insurance adjusters want to make sure that the big horse race is properly… predictable.
	
	To do this, we’re going to make use of science! and examine horse DNA.
	
	As it turns out, horse DNA is kind of like normal DNA except that it’s made up of the following bases:
	
	H: Hungry
	F: Fast
	D: Distracted
	B: Bouncy
	A horse’s DNA is represented by a string of bases such as HFFDBDHHF (but, in reality, these strings are much longer).
	
	To predict the fastest horse, we’re looking for the horse with the longest string of F bases that aren’t interrupted by D. A distracted horse is never fast. The traits of bounciness B and hunger H are irrelevant when computing fastness.
	
	So, for example.
	
	Sequence	“Fastness” (metric units)
	FHFHF	3
	FHHBF	2
	FHDHF	1
	FFDHF	2
	Of the above options, the horse with the DNA FHFHF would be our fastest horse.
	
	This file contains the DNA sequences of all the horses in our race.
	*/

	private class Horse implements Comparable<Horse> {
		private String name, DNA;
		private int speed;

		public Horse(String name, String DNA) {
			this.name = name;
			this.DNA = DNA;
			processDNA();
		}

		public String getName() { return this.name; }
		public String getDNA() { return this.DNA; }
		public int getSpeed() { return this.speed; }
		public String toString() { return name + " " + DNA + ": " + speed; }

		private void processDNA() {
			speed = 0;
			int thisSpeed = 0;
			for (char c : DNA.toCharArray()) {
				switch (c) {
				case 'F':
					thisSpeed++;
					break;
				case 'D':
					if (thisSpeed > speed)
						speed = thisSpeed;
					thisSpeed = 0;
					break;
				default:
					// the other characters don't matter
					break;
				}
			}
			if (thisSpeed > speed) speed = thisSpeed;
		}

		@Override
		public int compareTo(Horse o) {
			if (this.getSpeed() == o.getSpeed()) return 0;
			if (this.getSpeed() < o.getSpeed()) return -1;
			return 1;
		}

	}

	@Override
	public void solve(int n) {
		List<Horse> horses = new ArrayList<Horse>();

		for (String line : getFileContents(input)) {
			String[] input = line.split("\\t");
			horses.add(new Horse(input[0], input[1]));
		}
		Collections.sort(horses, Collections.reverseOrder()); // Sort largest to smallest
		System.out.println(horses);
		solver.add(horses.get(0).toString());
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