package beer.dacelo.dev.aoq2023.aoc2022;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day3 extends Day {

    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	String priority = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	detail = new ArrayList<String>();
	List<String> fileContents = getFileContents(input);
	int total = 0;
	if (n == 1) {
	    for (String line : fileContents) {
		int half = line.length() / 2;
		String leftCompartiment = line.substring(0, half);
		String rightCompartiment = line.substring(half);
		detail.add(line + ":: [" + leftCompartiment + "||" + rightCompartiment + "]");
		System.out.println("Left: " + leftCompartiment + ", right: " + rightCompartiment);
		char duplicate = 0;
		for (char c : leftCompartiment.toCharArray()) {
		    if (rightCompartiment.indexOf(c) != -1) {
			detail.add(" (found) Duplicate in left and right: " + c);
			duplicate = c;
		    }
		}
		total += (priority.indexOf(duplicate) + 1);
		detail.add("Duplicate: " + duplicate + ", value: " + (priority.indexOf(duplicate) + 1) + ", total: "
			+ total);
	    }
	} else if (n == 2) {
	    List<String> elfGroup1 = new ArrayList<String>();
	    List<String> elfGroup2 = new ArrayList<String>();
	    for (String line : fileContents) {
		if (elfGroup1.size() < 3) {
		    elfGroup1.add(line);
		} else if (elfGroup2.size() < 3) {
		    elfGroup2.add(line);
		}

		if (elfGroup1.size() == 3 && elfGroup2.size() == 3) {
		    detail.add("Both groups have 3 now: " + elfGroup1 + ", " + elfGroup2);
		    char badgeGroup1 = 0;
		    for (char c : elfGroup1.get(0).toCharArray()) {
			if (elfGroup1.get(1).indexOf(c) != -1 && elfGroup1.get(2).indexOf(c) != -1) {
			    badgeGroup1 = c;
			}
		    }

		    char badgeGroup2 = 0;
		    for (char c : elfGroup2.get(0).toCharArray()) {
			if (elfGroup2.get(1).indexOf(c) != -1 && elfGroup2.get(2).indexOf(c) != -1) {
			    badgeGroup2 = c;
			}
		    }

		    total += (priority.indexOf(badgeGroup1) + 1) + (priority.indexOf(badgeGroup2) + 1);
		    detail.add("Badges for Group 1: " + badgeGroup1 + "(" + (priority.indexOf(badgeGroup1) + 1)
			    + "), Group 2: " + badgeGroup2 + "(" + (priority.indexOf(badgeGroup2) + 1) + ") => ("
			    + total + ")");

		    elfGroup1 = new ArrayList<String>();
		    elfGroup2 = new ArrayList<String>();
		}
	    }
	}
	solver.add(total);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	switch (n) {
	case 1:
	    solution.add(Integer.toString(solver.get(0)));
	    break;
	case 2:
	    solution.add(Integer.toString(solver.get(0)));
	    break;
	}
	return solution;
    }
}
