package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day2 extends Day {
    /**
     * Day 3: How Big Will He Get?!?
     * 
     * He's big now. I mean really big. Think of something really big. He's bigger.
     * Unless you thought of like a planet. He's not that big. But if you thought of
     * a building, he's bigger. With this growth, his appetite has grown too. How
     * much?? That, recruits, is what we must work out today.
     * 
     * Our recon team has found some notes from the elves. It seems they were
     * worried about the rate at which he was growing. They started doing a daily
     * weight check. Sedrick didn't take to kindly to it as there are only a few
     * days of data. This is what we have:
     * 
     * Daily check in
     * 
     * Aug 20, 2023 = 400 kg Aug 21, 2023 = 420 kg Aug 22, 2023 = 441 kg
     * 
     * Not to give away any spoilers here, but we think the best strategy is to blow
     * him up. To calculate the minimum amount of explosive, we need to know his
     * mass.
     * 
     * Given the data above, and assuming he continues to grow at the same rate,
     * calculate his mass at the end of Dec 2. Enter the number answer to a
     * precision of 2 decimal places.
     * 
     * For example, on Sep 5, the correctly rounded answer would be 873.15.
     */
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	Integer result = 0;
	for (String line : getFileContents(input)) {
	    String[] gameLine = line.split(":");
	    int maxRed = 12, maxGreen = 13, maxBlue = 14;
	    Integer gameNr = Integer.parseInt(gameLine[0].split(" ")[1].trim());
	    String[] reveals = gameLine[1].split(";");
	    Boolean possible = true;
	    for (String reveal : reveals) {
		int red = 0, green = 0, blue = 0;
		String[] pulls = (reveal.trim()).split(",");
		for (String pull : pulls) {
		    String[] singlePull = (pull.trim()).split(" ");
		    Integer value = Integer.parseInt(singlePull[0]);
		    switch (singlePull[1]) {
		    case "red":
			red += value;
			break;
		    case "green":
			green += value;
			break;
		    case "blue":
			blue += value;
			break;
		    }
		    if (red > maxRed || green > maxGreen || blue > maxBlue) {
			possible = false;
			break;
		    }
		}
		if (!possible)
		    break;
	    }
	    if (possible)
		result += gameNr;

	}
	solver.add(result);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
