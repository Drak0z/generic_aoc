package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day11 extends Day {
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

    private class Galaxy implements Comparable<Galaxy> {
	public Long id, x, y;

	public Galaxy(Integer id, Integer x, Integer y) {
	    this.id = Long.valueOf(id);
	    this.x = Long.valueOf(x);
	    this.y = Long.valueOf(y);
	}

	public String toString() {
	    return id.toString();
	}

	@Override
	public int compareTo(Galaxy o) {
	    return this.id.compareTo(o.id);
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null || this.getClass() != o.getClass())
		return false;
	    return this.compareTo((Galaxy) o) == 0;
	}

	public Long getDistanceTo(Galaxy o) {
	    return (Math.abs(this.x - o.x) + Math.abs(this.y - o.y));
	}
    }

    private class Universe {
	List<List<Character>> map;
	List<Galaxy> knownGalaxies;

	public Universe() {
	    map = new ArrayList<List<Character>>();
	    knownGalaxies = new ArrayList<Galaxy>();
	}

	public void addRow(String input) {
	    List<Character> row = new ArrayList<Character>();
	    for (Character c : input.toCharArray()) {
		row.add(c);
	    }
	    map.add(row);
	}

	public void expand(Long n) {
	    // expand empty spaces n times
	    // We don't really need a map
	    // We need to keep track of the galaxies, and add n to each Y coordinate which
	    // is "below" our identified empty row
	    // And we need to add n to each X coordinate which is "after" our identified
	    // column
	    knownGalaxies = getKnownGalaxies();
	    Long delta = n - 1;

	    for (int y = map.size() - 1; y >= 0; y--) {
		List<Character> r = map.get(y);
		Boolean isEmptyRow = true;
		for (Character c : r) {
		    if (c != '.') {
			isEmptyRow = false;
			break;
		    }
		}
		if (isEmptyRow) {
		    // Expand the universe vertically first, because that's easiest
		    for (Galaxy g : knownGalaxies) {
			if (g.y > y)
			    g.y += delta;
		    }
		}
	    }
	    for (int x = map.get(0).size() - 1; x >= 0; x--) {
		Boolean isEmptyCol = true;
		for (List<Character> r : map) {
		    if (r.get(x) != '.') {
			isEmptyCol = false;
			break;
		    }
		}
		if (isEmptyCol) {
		    for (Galaxy g : knownGalaxies) {
			if (g.x > x)
			    g.x += delta;
		    }
		}
	    }
	}

	public String toString() {
	    Integer nGalaxy = 1;
	    StringBuilder outputSb = new StringBuilder();
	    for (int y = 0; y < map.size(); y++) {
		for (int x = 0; x < map.get(y).size(); x++) {
		    if (map.get(y).get(x) == '.')
			outputSb.append('.');
		    else {
			outputSb.append(nGalaxy);
			nGalaxy++;
		    }
		}
		outputSb.append(System.lineSeparator());
	    }
	    return outputSb.toString();
	}

	public List<Galaxy> getKnownGalaxies() {
	    if (knownGalaxies.size() > 0)
		return knownGalaxies;
	    knownGalaxies = new ArrayList<Galaxy>();
	    for (int y = 0; y < map.size(); y++) {
		for (int x = 0; x < map.get(y).size(); x++) {
		    if (map.get(y).get(x) == '#') {
			Integer id = (knownGalaxies.size() + 1);
			knownGalaxies.add(new Galaxy(id, x, y));
		    }
		}
	    }
	    return knownGalaxies;
	}

	public Long calculateDistance() {
	    knownGalaxies = getKnownGalaxies();
	    Long totalDistance = 0L;
	    // we now have a list of galaxies with ids, x and y coordinates.
	    // the distance/shortest path between two is simply delta x + delta y
	    // Galaxy has a function to return its distance to another galaxy
	    for (int a = 0; a < knownGalaxies.size(); a++) {
		Galaxy from = knownGalaxies.get(a);
		for (int b = a + 1; b < knownGalaxies.size(); b++) {
		    Galaxy to = knownGalaxies.get(b);
		    Long distance = from.getDistanceTo(to);
		    totalDistance += distance;
		    detail.add("Distance between: " + from + " and " + to + " is: " + distance + " [" + totalDistance
			    + "]");
		}
	    }
	    return totalDistance;
	}

    }

    @Override
    public void solve(int n) {
	Universe universe = new Universe();
	for (String line : getFileContents(input)) {
	    universe.addRow(line);
	}
	System.out.println("Starting universe: ");
	System.out.println(universe);

	Long totalDistance = 0L;
	switch (n) {
	case 1:
	    universe.expand(2L);
	    System.out.println("Expanded universe: ");
	    System.out.println(universe);
	    System.out.println("Calculating distances: ");
	    totalDistance = universe.calculateDistance();
	    System.out.println(totalDistance);
	    solver.add(totalDistance);
	    break;
	case 2:
	    universe.expand(1000000L);
	    System.out.println("Expanded universe: ");
	    System.out.println(universe);
	    System.out.println("Calculating distances 2: ");
	    totalDistance = universe.calculateDistance();
	    System.out.println(totalDistance);
	    solver.add(totalDistance);
	    break;
	}
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
