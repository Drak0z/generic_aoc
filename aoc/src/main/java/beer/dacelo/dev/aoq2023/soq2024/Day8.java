package beer.dacelo.dev.aoq2023.soq2024;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day8 extends Day {
	/*-
	Day 8: Horsetastrophe™
		
	The Stampede organizers are always looking for new ideas to modernize their image, attract crowds, aggravate PETA, and help maintain their image as the Greatest Outdoor Show on Earth.
	
	Having learned nothing from the catastrophe on Isla Nublar, the Stampede geneticists have developed a new adorable pocket-sized breed of microhorses and will unleash them on attendees over the coming days. Legal counsel, however, requires a detailed simulation to understand the likely population growth prior to greenlighting this venture.
	
	 We wanted to give attendees a bit of Stampede they could bring home in their pocket. Just take it out before throwing your jeans in the wash! 
	
	It turns out that microhorses have some unique breeding properties:
	
	They breed very consistently and very quickly.
	A newly born microhorse will breed after just 9 days!
	After breeding, a microhorse will breed again after 6 days.
	They breed asexually. Organizers felt that an “enthusiastically” breeding population of microhorses might just be a little too “natural” for the attendees.
	When asked for a comment, Dr. Ian Malcolm responded with:
	
	 Yeah, but your scientists were so preoccupied with whether or not they could that they didn’t stop to think if they should. 
	
	Given the consistency in microhorse breeding, it is practical to maintain a countdown to breeding timer for each microhorse for modeling purposes.
	
	So, suppose you have a microhorse with a breeding timer value of 3:
	
	After one day, its breeding timer would become 2.
	After another day, its breeding timer would become 1.
	After another day, its breeding timer would become 0.
	After another day, its breeding timer would reset to 6, and it would create a new microhorse with a breeding timer of 9.
	After another day, the first microhorse would have a breeding timer of 5, and the second microhorse would have a breeding timer of 8.
	Given a population of microhorses, we would expect to see different microhorses at different stages. To keep things simple, we could describe a starting population as an array of breeding timers.
	
	Each day, each microhorse in the population moves one day closer to breeding time!
	
	For example, consider the following initial breeding timers:
	
	5,4,2,1
	These timers evolve daily as follows:
	
	=========== After Day 1 [4 microhorses] ===========
	[4, 3, 1, 0]
	=========== After Day 2 [5 microhorses] ===========
	[3, 2, 0, 6, 9]
	=========== After Day 3 [6 microhorses] ===========
	[2, 1, 6, 5, 8, 9]
	=========== After Day 4 [6 microhorses] ===========
	[1, 0, 5, 4, 7, 8]
	=========== After Day 5 [7 microhorses] ===========
	[0, 6, 4, 3, 6, 7, 9]
	=========== After Day 6 [8 microhorses] ===========
	[6, 5, 3, 2, 5, 6, 8, 9]
	=========== After Day 7 [8 microhorses] ===========
	[5, 4, 2, 1, 4, 5, 7, 8]
	=========== After Day 8 [8 microhorses] ===========
	[4, 3, 1, 0, 3, 4, 6, 7]
	=========== After Day 9 [9 microhorses] ===========
	[3, 2, 0, 6, 2, 3, 5, 6, 9]
	=========== After Day 10 [10 microhorses] ===========
	[2, 1, 6, 5, 1, 2, 4, 5, 8, 9]
	=========== After Day 11 [10 microhorses] ===========
	[1, 0, 5, 4, 0, 1, 3, 4, 7, 8]
	=========== After Day 12 [12 microhorses] ===========
	[0, 6, 4, 3, 6, 0, 2, 3, 6, 7, 9, 9]
	=========== After Day 13 [14 microhorses] ===========
	[6, 5, 3, 2, 5, 6, 1, 2, 5, 6, 8, 8, 9, 9]
	=========== After Day 14 [14 microhorses] ===========
	[5, 4, 2, 1, 4, 5, 0, 1, 4, 5, 7, 7, 8, 8]
	=========== After Day 15 [15 microhorses] ===========
	[4, 3, 1, 0, 3, 4, 6, 0, 3, 4, 6, 6, 7, 7, 9]
	=========== After Day 16 [17 microhorses] ===========
	[3, 2, 0, 6, 2, 3, 5, 6, 2, 3, 5, 5, 6, 6, 8, 9, 9]
	=========== After Day 17 [18 microhorses] ===========
	[2, 1, 6, 5, 1, 2, 4, 5, 1, 2, 4, 4, 5, 5, 7, 8, 8, 9]
	=========== After Day 18 [18 microhorses] ===========
	[1, 0, 5, 4, 0, 1, 3, 4, 0, 1, 3, 3, 4, 4, 6, 7, 7, 8]
	=========== After Day 19 [21 microhorses] ===========
	[0, 6, 4, 3, 6, 0, 2, 3, 6, 0, 2, 2, 3, 3, 5, 6, 6, 7, 9, 9, 9]
	=========== After Day 20 [24 microhorses] ===========
	[6, 5, 3, 2, 5, 6, 1, 2, 5, 6, 1, 1, 2, 2, 4, 5, 5, 6, 8, 8, 8, 9, 9, 9]
	As you can see, they breed quickly. With our small starting population, we have 8 microhorses at the end of the 8th day, 21 microhorses at the end of the 19th day, and 3188 microhorses after 80 days!
	
	ACTUAL STARTING POPULATION
	3, 6, 1, 2, 1, 6, 5, 4, 3, 2, 1, 3, 4, 6, 
	5, 3, 2, 1, 4, 5, 6, 2, 1, 5, 6, 4, 3, 2, 
	5, 4, 1, 6, 3, 2, 4, 5, 6, 1, 3, 2, 5, 4, 
	6, 1, 3, 2, 6, 5, 4, 1, 3
	Given our actual starting population of microhorses, how many microhorses would we expect there to be after 120 days?
	 */

	@Override
	public void solve(int n) {
		List<Integer> horseys = new ArrayList<Integer>();
		StringBuilder inputSb = new StringBuilder();
		for (String line : getFileContents(input)) {
			inputSb.append(line.trim());
		}
		
		String[] inputArray = inputSb.toString().split(",");
		for (int i = 0; i < inputArray.length; i++) {
			horseys.add(Integer.parseInt(inputArray[i].trim()));
		}

		int days = 0;
		while (days < 120) {
			horseys = evolve(horseys);
			days++;
			
			detail.add("=========== After Day " + days + " [" + horseys.size() + " microhorses] ===========");
			detail.add(horseys.toString());
		}
		
		solver.add(""+horseys.size());
	}

	private List<Integer> evolve(List<Integer> horseys) {
		List<Integer> evolvedHorseys = new ArrayList<>(horseys);
		for (int i = 0; i < horseys.size(); i++) {
			int x = horseys.get(i);
			x--;
			if (x < 0) {
				evolvedHorseys.add(9);
				x = 6;
			}
			evolvedHorseys.set(i, x);
		}
		return evolvedHorseys;
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