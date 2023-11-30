package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day0 extends Day {
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	int x = 0;
	boolean loop = true;
	detail = new ArrayList<String>();
	while (loop) {
	    List<Integer> range = IntStream.range(0, x).boxed().collect(Collectors.toList());

	    detail.add("Resulting output for x = " + x);
	    for (int i : range) {
		String line = i + " Hello, World!";
		detail.add(line);
		if (line.charAt(0) == '6') {
		    solver.add(x);
		    loop = false;
   		    detail.add("^ Found the number 6 at the start of the line! x = " + x);
		}
	    }
	    detail.add("");
	    x++;
	}

    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(Integer.toString(solver.get(0)));
	return solution;
    }
}
