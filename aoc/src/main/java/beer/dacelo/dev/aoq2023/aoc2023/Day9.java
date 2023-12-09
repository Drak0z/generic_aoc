package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day9 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private class History {
	private ArrayList<Long> baseline;

	public History(String input) {
	    String[] initial = input.split(" ");
	    ArrayList<Long> baseline = new ArrayList<Long>();
	    for (int i = 0; i < initial.length; i++) {
		baseline.add(Long.parseLong(initial[i]));
	    }
	    this.baseline = baseline;
	}

	public Long getNext() {
	    return baseline.get(baseline.size() - 1) + generateDelta(baseline, true);
	}

	public Long getPrevious() {
	    return baseline.get(0) - generateDelta(baseline, false);
	}

	private Long generateDelta(ArrayList<Long> input, boolean forward) {
	    System.out.println("Generating deltalist for : " + input);
	    detail.add("Generating deltalist for : " + input);
	    ArrayList<Long> deltaList = new ArrayList<Long>();
	    Boolean allZeroes = true;
	    for (int i = 0; i < input.size() - 1; i++) {
		Long delta = input.get(i + 1) - input.get(i);
		if (delta != 0)
		    allZeroes = false;
		deltaList.add(delta);
	    }
	    if (allZeroes)
		return 0L;
	    if (forward)
		return deltaList.get(deltaList.size() - 1) + generateDelta(deltaList, forward);
	    return deltaList.get(0) - generateDelta(deltaList, forward);
	}

    }

    @Override
    public void solve(int n) {
	Long result = 0L;
	for (String line : getFileContents(input)) {
	    // let's process the first line first
	    History h = new History(line);
	    Long value = 0L;
	    switch (n) {
	    case 1:
		value = h.getNext();
		System.out.println("Next number: " + value);
		detail.add("Next number: " + value);
		break;
	    case 2:
		value = h.getPrevious();
		System.out.println("Previous number: " + value);
		detail.add("Previous number: " + value);
		break;
	    }
	    result += value;
	}
	System.out.println("Total: " + result);
	solver.add(result);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
