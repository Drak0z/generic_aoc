package beer.dacelo.dev.aoq2023.aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day1 extends Day {
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	detail = new ArrayList<String>();
	    Integer calories = 0;
	for (String line : getFileContents(input)) {
		if (line.length() == 0) {
		    detail.add("Total for this Elf: " + calories);
		    detail.add("");
		    solver.add(calories);
		    calories = 0;
		} else {
		    calories += Integer.parseInt(line);
		    detail.add(line + " (" + calories + ")");
		}
	    }
	    // Let's not forget the last line
	    solver.add(calories);
	    detail.add("Total for this Elf: " + calories);
	    detail.add("");
	    Collections.sort(solver);
    }

    @Override
    public List<String> getSolution(int n) {
	System.out.println("Getting solution for " + this.getClass() + " part: " + n);
	solution = new ArrayList<String>();
	switch (n) {
	case 1:
	    solution.add(Integer.toString(Collections.max(solver)));
	    break;
	case 2:
	    int x = 0;
	    int s = solver.size();
	    for (int i = 1; i <= 3; i++) {
		x += solver.get(s - i);
	    }
	    solution.add(Integer.toString(x));
	    break;
	}
	return solution;
    }
}
