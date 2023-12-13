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
	String checksum;
	List<Character> condition;

	public ConditionRecord(String input) {
	    this.conditionRecord = input;
	    String[] rec = conditionRecord.split(" ");
	    this.checksum = rec[1];
	    condition = new ArrayList<Character>(rec[0].chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
	}
	
	private boolean isPossibleChecksum(String checksum, List<Character> condition) {
	    return false;
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
