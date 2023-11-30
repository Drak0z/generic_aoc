package beer.dacelo.dev.aoq2023.aoc2022;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day4 extends Day {
    List<Integer> solver = new ArrayList<Integer>();

    private class Assignment {
	private Integer min;
	private Integer max;

	public void setMin(Integer min) {
	    this.min = min;
	}

	public void setMax(Integer max) {
	    this.max = max;
	}

	public Integer getMin() {
	    return min;
	}

	public Integer getMax() {
	    return max;
	}

	public Assignment(String input) {
	    String[] minMax = input.split("-");
	    setMin(Integer.parseInt(minMax[0]));
	    setMax(Integer.parseInt(minMax[1]));
	}

	public String getContentString() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 100; i++) {
		if (i < getMin())
		    sb.append(".");
		else if (i >= getMin() && i <= getMax())
		    sb.append(i);
		else
		    sb.append(".");
	    }
	    return sb.toString();
	}

	public String toString() {
	    return getContentString() + "   " + getMin() + "-" + getMax();
	}

	public boolean hasFullOverlap(Assignment other) {
	    return (this.getMin() >= other.getMin() && this.getMax() <= other.getMax())
		    || (other.getMin() >= this.getMin() && other.getMax() <= this.getMax());
	}

	public boolean hasOverlap(Assignment other) {
	    return     (this.getMin() >= other.getMin() && this.getMin() <= other.getMax() )
		    || (this.getMax() >= other.getMin() && this.getMax() <= other.getMax())
		    || (other.getMin() >= this.getMin() && other.getMin() <= this.getMax() )
		    || (other.getMax() >= this.getMin() && other.getMax() <= this.getMax());
	}
    }

    @Override
    public void solve(int n) {
	int total = 0;
	List<String> fileContent = getFileContents(input);
	for (String line : fileContent) {
	    String[] assignments = line.split(",");
	    Assignment a1 = new Assignment(assignments[0]);
	    Assignment a2 = new Assignment(assignments[1]);
	    detail.add(a1.toString());
	    detail.add(a2.toString());
	    switch (n) {
	    case 1:
		if (a1.hasFullOverlap(a2)) {
		    total++;
		    detail.add("These two pairs fully overlap! (" + total + ")");
		}
		break;
	    case 2:
		if (a1.hasOverlap(a2)) {
		    total++;
		    detail.add("These two pairs overlap! (" + total + ")");
		}
		break;
	    }
	    detail.add("");

	}
	solver.add(total);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
        solution.add(Integer.toString(solver.get(0)));
	return solution;
    }
}
