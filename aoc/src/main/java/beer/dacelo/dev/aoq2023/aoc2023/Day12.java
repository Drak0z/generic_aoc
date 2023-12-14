package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day12 extends Day {
    List<Long> solver = new ArrayList<Long>();
    /*
     * private class GalaxyPair { Galaxy a, b; public GalaxyPair(Galaxy a, Galaxy b)
     * { this.a = a; this.b = b; }
     * 
     * public Integer getDistance() { return a.getDistanceTo(b); }
     * 
     * @Override public boolean equals(Object o) { if (o == null || o.getClass() !=
     * this.getClass()) return false; return ((this.a.equals(((GalaxyPair) o).a) &&
     * (this.b.equals(((GalaxyPair) o).b))) || (this.a.equals(((GalaxyPair) o).b) &&
     * (this.b.equals(((GalaxyPair) o).a))) ); } }
     */

    public class ConditionRecord {
	String conditionRecord;
	List<Integer> checksum;
	List<Character> condition;

	public ConditionRecord(String input) {
	    this.conditionRecord = input;
	    String[] rec = conditionRecord.split(" ");
	    this.checksum = List.of(rec[1].split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
	    condition = new ArrayList<Character>(rec[0].chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
	}

	private boolean isPossibleChecksum(List<Integer> checksum, List<Character> condition) {
	    boolean debug = "#.#.###".equals(condition.stream().map(String::valueOf).collect(Collectors.joining()));
	    if (debug) System.out.println("is Possible Checksum? " + checksum + ", "
		    + condition.stream().map(String::valueOf).collect(Collectors.joining()));
	    int i = 0;
	    int check = 0;
	    for (int x = 0; x < condition.size(); x++) {
		// for (Character c : condition) {
		Character c = condition.get(x);
		if (debug) System.out.println("What will we return? l43");
		if (check >= condition.size()) {
		    if (debug) System.out.println("What will we return? l45");
		    return false;
		}
		switch (c) {
		case '#':
		    if (debug) System.out.println("What will we return? # l50");
		    // we are at a damaged spring
		    if (i < checksum.get(check)) {
			i++;
		    } else {
      	                System.out.println("Nope! " + i + ", " + check + " - expecting: " + checksum.get(check));
			return false;
		    }
		    break;
		case '.':
		    if (debug) System.out.println("What will we return? . l50");
		    // we're at a good spring, let's see if we need to do anything
		    if (i == checksum.get(check)) {
			i = 0;
			check++;
		    }
		    if (i != 0) {
			if (debug) System.out.println("Nope! " + i + ", " + check + " - expecting: " + 0);
			return false;
		    }
		    break;
		case '?':
		    List<Character> copyConditionDot = new ArrayList<Character>(condition);
		    List<Character> copyConditionHash = new ArrayList<Character>(condition);
		    copyConditionDot.set(x, '.');
		    copyConditionHash.set(x, '#');
		    boolean dotPossible = isPossibleChecksum(checksum, copyConditionDot);
		    boolean hashPossible = isPossibleChecksum(checksum, copyConditionHash);

		    if (debug) System.out.println("Replace with Dot possible? " + dotPossible);
		    if (debug) System.out.println("Replace with Hash possible? " + hashPossible);

		    if (!(dotPossible || hashPossible)) {
			return false;
		    }
		    break;
		}
	    }
	    if (debug) System.out.println("Out of loop!");
	    return true;
	}

	private String arrangementToChecksum(List<Character> condition) {
	    return "";
	}

	private Long exploreArrangements(List<Character> condition, Integer index, Long count) {
	    List<Character> explorableCondition = new ArrayList<Character>(condition);
	    return 0L;
	}

	public Long getArrangements() {
	    return exploreArrangements(condition, 0, 0L);
	}
    }

    @Override
    public void solve(int n) {
	Long arrangements = 0L;
	for (String line : getFileContents(input)) {
	    ConditionRecord cr = new ConditionRecord(line);
	    arrangements += cr.getArrangements();
	    System.out.println("Checksum: " + cr.checksum + ", condition: " + cr.condition + "? "
		    + cr.isPossibleChecksum(cr.checksum, cr.condition));
	}
	solver.add(arrangements);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	for (int i = 0; i < solver.size(); i++) {
	    solution.add(solver.get(i).toString());
	}
	return solution;
    }
}
