package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day8 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private void solve1() {
	List<String> fileContents = getFileContents(input);
	String instructions = fileContents.get(0);

	Map<String, List<String>> directions = new HashMap<String, List<String>>();
	for (int i = 1; i < fileContents.size(); i++) {
	    if (fileContents.get(i).length() > 0 && fileContents.get(i).contains(" = ")) {
		String[] inst = fileContents.get(i).split(" = ");
		String origin = inst[0].trim();
		String[] leftRight = inst[1].replace("(", "").replace(")", "").split(",");
		String left = leftRight[0].trim();
		String right = leftRight[1].trim();
		List<String> destination = Arrays.asList(left, right);
		directions.put(origin, destination);
	    }
	}

	String current = "AAA";
	Long steps = 0L;
	while (!"ZZZ".equals(current)) {
	    Integer pos = Long.valueOf(steps % instructions.length()).intValue();
	    Character direction = instructions.charAt(pos);
	    switch (direction) {
	    case 'L':
		current = directions.get(current).get(0);
		break;
	    case 'R':
		current = directions.get(current).get(1);
		break;
	    }
	    steps++;
	    detail.add("After going " + direction + ", step " + steps + ": " + current);
	}
	solver.add(steps);
    }

    private void solve2() {
	List<String> fileContents = getFileContents(input);
	String instructions = fileContents.get(0);

	Map<String, List<String>> directions = new HashMap<String, List<String>>();
	for (int i = 1; i < fileContents.size(); i++) {
	    if (fileContents.get(i).length() > 0 && fileContents.get(i).contains(" = ")) {
		String[] inst = fileContents.get(i).split(" = ");
		String origin = inst[0].trim();
		String[] leftRight = inst[1].replace("(", "").replace(")", "").split(",");
		String left = leftRight[0].trim();
		String right = leftRight[1].trim();
		List<String> destination = Arrays.asList(left, right);
		directions.put(origin, destination);
	    }
	}

	List<String> startPositions = new ArrayList<String>();
	for (String s : directions.keySet()) {
	    if (s.endsWith("A")) {
		startPositions.add(s);
	    }
	}

	/*
	 * There is a better mathematical solution for this than brute-forcing... The finalSteps is the
	 * number that's divisible by all other steps... somehow? The Internet says :
	 * Use Greatest Common Denominator on a set of numbers to find the Least Common
	 * Multiple
	 */

	Long[] endPositions = new Long[startPositions.size()];
	List<String> currentPositions = new ArrayList<String>(startPositions);
	for (int i = 0; i < currentPositions.size(); i++) {
	    Long thisSteps = 0L;
	    while (!currentPositions.get(i).endsWith("Z")) {
		String current = currentPositions.get(i);
		Integer pos = Long.valueOf(thisSteps % instructions.length()).intValue();
		Character direction = instructions.charAt(pos);
		switch (direction) {
		case 'L':
		    currentPositions.set(i, directions.get(current).get(0));
		    break;
		case 'R':
		    currentPositions.set(i, directions.get(current).get(1));
		    break;
		}
		thisSteps++;
	    }
	    endPositions[i] = thisSteps;
	}

	Long gcd = Util.gcd(endPositions);
	Long lcm = Util.lcm(endPositions);
	detail.add("Start positions: " + startPositions + ", individually takes this many steps to get to a Z: "
		+ Arrays.asList(endPositions) + ", looking like this: " + currentPositions + ", GCD: " + gcd + ", LCM: "
		+ lcm);
	solver.add(lcm);
    }

    @Override
    public void solve(int n) {
	switch (n) {
	case 1:
	    solve1();
	    break;
	case 2:
	    solve2();
	    break;
	}
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
