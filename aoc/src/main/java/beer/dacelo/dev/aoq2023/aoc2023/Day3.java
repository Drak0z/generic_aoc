package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day3 extends Day {
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

    class GearFinder implements Comparable<GearFinder> {
	int x, y;

	public GearFinder(int x, int y) {
	    this.x = x;
	    this.y = y;
	} // GearFinder

	public String toString() {
	    return "[" + this.x + "," + this.y + "]";
	} // toString

	@Override
	public int compareTo(GearFinder o) {
	    return this.toString().compareTo(o.toString());
	} // compareTo

	public int hashCode() {
	    return this.toString().hashCode();
	} // hashCode

	public boolean equals(Object o) {
	    return getClass() == o.getClass() && this.compareTo((GearFinder) o) == 0;
	} // equals
    }

    @Override
    public void solve(int n) {
	Integer result = 0;
	List<String> engineSchematic = getFileContents(input);
	detail.add("Engine Schematic: ");
	for (String line : engineSchematic) {
	    detail.add(line);
	}
	detail.add("");

	Map<GearFinder, Integer> nrGears = new HashMap<GearFinder, Integer>();
	Map<GearFinder, Integer> gearRatio = new HashMap<GearFinder, Integer>();

	for (int y = 0; y < engineSchematic.size(); y++) {
	    String line = engineSchematic.get(y);
	    // let's start by going line-by-line, and identify the actual numbers we find
	    int nrStart = -1;
	    int nrEnd = -1;
	    StringBuilder numberSb = new StringBuilder();
	    for (int x = 0; x < line.length(); x++) {
		if (Character.isDigit(line.charAt(x))) {
		    // We have (part of) a number here
		    numberSb.append(line.charAt(x));
		    if (nrStart == -1) {
			nrStart = x;
		    }
		    nrEnd = x;
		}
		if (!Character.isDigit(line.charAt(x)) || (x == line.length() - 1)) {
		    // We do not have a number anymore, or we're at the end of the line
		    // but if we have identified a number earlier, let's see if this is valid
		    if (nrStart != -1) {
			detail.add("Number found: " + numberSb);
			Boolean validPartNr = false;
			for (int searchY = y - 1; searchY <= y + 1; searchY++) {
			    for (int searchX = nrStart - 1; searchX <= nrEnd + 1; searchX++) {
				if (searchY >= 0 && searchY < engineSchematic.size() && searchX >= 0
					&& searchX < line.length()
					&& (searchY != y || (searchX < nrStart || searchX > nrEnd))) {
				    // We're hopefully looking "around" our found number...
				    // Any symbol in this schematic is not a number nor a dot
				    if (n == 1 && !(engineSchematic.get(searchY).charAt(searchX) == '.'
					    || Character.isDigit(engineSchematic.get(searchY).charAt(searchX)))) {
					validPartNr = true;
				    }

				    if (n == 2 && engineSchematic.get(searchY).charAt(searchX) == '*') {
					// We have a number that we can associate with a gear
					GearFinder gear = new GearFinder(searchX, searchY);
					Integer _nrGears = nrGears.get(gear);
					Integer _gearRatio = gearRatio.get(gear);
					if (_nrGears == null) _nrGears = 0;
					if (_gearRatio == null) _gearRatio = 1;
					_nrGears ++;
					_gearRatio *= Integer.parseInt(numberSb.toString());
					nrGears.put(gear,  _nrGears);
					gearRatio.put(gear, _gearRatio);

					detail.add("Found gear at " + gear + ", associating number " + numberSb + " with it");
				    }
				}
			    }
			}
			
			if (n == 1 && validPartNr) {
			    result += Integer.parseInt(numberSb.toString());
			    detail.add("  " + numberSb.toString() + " is valid, adding it to the results (" + result
				    + ")");
			} else if (n == 1) {
			    detail.add("  " + numberSb.toString() + " is NOT valid. Discarding");
			}
			// And reset
			nrStart = -1;
			nrEnd = -1;
			numberSb = new StringBuilder();
		    }
		}
	    }
	}
	
	if (n == 2) {
	for (GearFinder gear : nrGears.keySet()) {
	    if (nrGears.get(gear) == 2) {
		// This gear is associated with exactly 2 numbers
		result += gearRatio.get(gear);
		detail.add(gear + " is associated with exactly 2 numbers. Adding gear ratio to total (" + result + ")");
	    }
	}
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
