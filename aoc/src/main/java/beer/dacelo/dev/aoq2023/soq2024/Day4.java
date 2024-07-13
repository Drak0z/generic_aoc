package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day4 extends Day {
	/*-
	Day 4: Wheel of Fun!
	
	Bad news! CSAT scores for the Tilt o’ Whirl are dangerously low, and we need to get it resolved quickly!
	
	The Calgary Stampede’s new supercomputer has developed quite the personality after being exposed to every AI enhancement available and a slight radioactive leak. While it mostly dislikes human activities, it has agreed to help with the Tilt o’ Whirl ride diagnostics—if we can frame the task in terms it enjoys.
	
	The Tilt o’ Whirl’s funfactor is determined by two essential features: spin-e-ness and bounc-e-ness. Our challenge is to knit together these values from the ride’s diagnostics log.
	
	The logs consist of raw binary data for each ride subsystem and look like this:
	
	101100
	011001
	111000
	000101
	101010
	110101
	001100
	110010
	101111
	To compute the ride’s spin-e-ness, we need to “knit” a new binary number by finding the most common bit in each position across all entries. For instance, looking at position 1 in the sample data, there are six 1s and three 0s, so the first digit in our knitted number is 1. Repeat this for all positions to get your final binary number, which in this example is 101100 (or 44 in decimal).
	
	For the bounc-e-ness, knit another binary number by finding the least common bit in each position. Using the same sample:
	
	Position 1: least common bit is 0
	Position 2: least common bit is 1
	Etc.
	This results in 010011 (or 19 in decimal).
	
	Finally, the overall funfactor of the ride is calculated by multiplying the spin-e-ness and bounc-e-ness. So, for our example data:
	
	19 (bounc-e-ness) x 44 (spin-e-ness) = 836
	
	SCIENCE!
	
	Help the Iron Wranglers decode the full diagnostic logs and calculate the funfactor so that we can determine what needs to be done to restore the fun to the ride and keep the supercomputer happy!
	*/

	@Override
	public void solve(int n) {
		
		// Go through "all " lines, and 
		List<String> fileContents = getFileContents(input);
		int nrLines = fileContents.size();
		int[] diagnosticsData = new int[8];
		for (String line : fileContents) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '1') diagnosticsData[i+2]++;
			}
		}
		
		StringBuilder spinSb = new StringBuilder("00");
		StringBuilder bounceSb = new StringBuilder("00");
		
		for (int i = 2; i < diagnosticsData.length; i++) {
			if (diagnosticsData[i] <= (nrLines/2)) { // 1 is the least common bit
				spinSb.append('0');
				bounceSb.append('1');
			} else {
				spinSb.append('1');
				bounceSb.append('0');				
			}
		}
		
		int spin = Integer.parseInt(spinSb.toString(), 2);
		int bounce = Integer.parseInt(bounceSb.toString(), 2);
		int fun = spin * bounce;
		
		System.out.println("spin: " + spin + " (" + spinSb.toString() + "), bounce: " + bounce + " (" + bounceSb.toString() + ") = fun! " + fun);
		solver.add(fun + "");
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