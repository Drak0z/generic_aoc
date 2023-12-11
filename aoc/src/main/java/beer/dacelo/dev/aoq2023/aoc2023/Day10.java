package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day10 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private class PipeMaze {
	private class Coordinate implements Comparable<Coordinate> {
	    Integer x, y;
	    Character c;
	    Long distance;
	    Long visits = 0L;

	    public Coordinate(Integer x, Integer y, Character c, Long distance) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.distance = distance;
	    }

	    @Override
	    public int compareTo(Coordinate o) {
		if (this.y == o.y) {
		    return (this.x).compareTo(o.x);
		}
		return (this.y).compareTo(o.y);
	    }

	    public String toString() {
		return "[x: " + this.x + ", y: " + this.y + "] " + this.c + ", distance: " + this.distance;
	    }

	    @Override
	    public boolean equals(Object o) {
		if (this.getClass() != o.getClass())
		    return false;
		return (this.compareTo((Coordinate) o) == 0);
	    }

	    public boolean hasUp() {
		/*
		 * return (this.y > 0 && "S|LJ".indexOf(this.c) != -1 &&
		 * "S|7F".indexOf(map.get(this.y - 1).get(this.x)) != -1);
		 */

		return (this.y > 0 && "S|LJ".indexOf(this.c) != -1 && "S|7F".indexOf(mapC[y - 1][x].c) != -1);
	    }

	    public boolean hasDown() {
		/*
		 * return (this.y < (map.size() - 1) && "S|7F".indexOf(this.c) != -1 &&
		 * "S|LJ".indexOf(map.get(this.y + 1).get(this.x)) != -1);
		 */
		return (this.y < mapC.length - 1 && "S|7F".indexOf(this.c) != -1
			&& "S|LJ".indexOf(mapC[y + 1][x].c) != -1);
	    }

	    public boolean hasLeft() {
		/*
		 * return (this.x > 0 && "S-7J".indexOf(this.c) != -1 &&
		 * "S-LF".indexOf(map.get(this.y).get(this.x - 1)) != -1);
		 */
		return (this.x > 0 && "S-7J".indexOf(this.c) != -1 && "S-LF".indexOf(mapC[y][x - 1].c) != -1);
	    }

	    public boolean hasRight() {
		/*
		 * return (this.x < map.get(this.y).size() - 1 && "S-LF".indexOf(this.c) != -1
		 * && "S-J7".indexOf(map.get(this.y).get(this.x + 1)) != -1);
		 */
		return (this.x < mapC[y].length - 1 && "S-LF".indexOf(this.c) != -1
			&& "S-J7".indexOf(mapC[y][x + 1].c) != -1);
	    }

	    private Coordinate getCoordinate(int newX, int newY) {
		return new Coordinate(newX, newY, mapC[newY][newX].c,
			Math.min(this.distance + 1, mapC[newY][newX].distance));
	    }

	    public Coordinate newUp() {
		return this.getCoordinate(x, y - 1);
	    }

	    public Coordinate newDown() {
		return this.getCoordinate(x, y + 1);
	    }

	    public Coordinate newLeft() {
		return this.getCoordinate(x - 1, y);
	    }

	    public Coordinate newRight() {
		return this.getCoordinate(x + 1, y);
	    }
	}

	// List<List<Character>> map;
	Coordinate[][] mapC;
	List<Coordinate> validLocations;
	Coordinate start;

	public PipeMaze(List<String> input) {
	    /*
	     * this.map = new ArrayList<List<Character>>();
	     * 
	     * for (int y = 0; y < input.size(); y++) { String row = input.get(y); if
	     * (row.contains("S")) { start = new Coordinate(row.indexOf('S'), y, 'S', 0L); }
	     * List<Character> mapRow = row.chars().mapToObj(c -> (char)
	     * c).collect(Collectors.toList()); map.add(mapRow); }
	     */
	    mapC = new Coordinate[input.size()][input.get(0).length()];
	    for (int y = 0; y < input.size(); y++) {
		for (int x = 0; x < input.get(y).length(); x++) {
		    Coordinate coord = new Coordinate(x, y, input.get(y).charAt(x), Long.MAX_VALUE);
		    // System.out.println("Parsing: [" + x + "," + y + "] => " +
		    // input.get(y).charAt(x));
		    if (coord.c == 'S') {
			coord.distance = 0L;
			start = coord;
		    }
		    mapC[y][x] = coord;
		}
	    }
	} // constructor PipeMaze

	public void cleanup() {
	    // Finds all dead pipes in the map and replaces them with a .
	    /*
	     * for (int y = 0; y < map.size(); y++) { for (int x = 0; x < map.get(y).size();
	     * x++) { Coordinate check = new Coordinate(x, y, map.get(y).get(x), 0L); if
	     * (!validLocations.contains(check)) { map.get(y).set(x, '.'); } } }
	     */
	    for (int y = 0; y < mapC.length; y++) {
		for (int x = 0; x < mapC[y].length; x++) {
		    Coordinate check = mapC[y][x];
		    if (check.distance == Long.MAX_VALUE || !validLocations.contains(check)) {
			Coordinate dead = new Coordinate(x, y, '.', Long.MAX_VALUE);
			mapC[y][x] = dead;
		    }
		}
	    }

	}

	private void exploreNewLocation(Coordinate newCoordinate) {
	    int index = validLocations.indexOf(newCoordinate);
	    if (index != -1) {
		validLocations.set(index, newCoordinate);
		mapC[newCoordinate.y][newCoordinate.x] = newCoordinate;
	    }
	    if (index == -1) {
		explore.add(newCoordinate);
		validLocations.add(newCoordinate);
		mapC[newCoordinate.y][newCoordinate.x] = newCoordinate;
	    }
	}

	LinkedList<Coordinate> explore;

	public void buildLoop(boolean debug) {
	    explore = new LinkedList<Coordinate>();
	    validLocations = new ArrayList<Coordinate>();
	    explore.add(start);
	    validLocations.add(start);
	    int i = 0;
	    while (!explore.isEmpty()) {
		i++;
		Coordinate c = explore.removeFirst();
		// Check if we can explore in a direction and if the target can receive our
		// explore
		// We still have issues where valid locations around the S may not be part of
		// the pipe...

		if (c.hasUp())
		    exploreNewLocation(c.newUp());
		if (c.hasDown())
		    exploreNewLocation(c.newDown());
		if (c.hasLeft())
		    exploreNewLocation(c.newLeft());
		if (c.hasRight())
		    exploreNewLocation(c.newRight());
		if (debug) {
		    // System.out.println(explore);
		    Coordinate[][] tempMap = Arrays.stream(mapC).map(Coordinate[]::clone).toArray(Coordinate[][]::new);
		    cleanup();
		    for (int cls = 0; cls < 1000; cls++) {
			System.out.println();
		    }
		    System.out.println(this.toString());
		    mapC = tempMap;
		    try {
			Thread.sleep(500);
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}

	    } // explore
	      // Now we still need to figure out whether or not every segment is part of a
	      // pipe loop...
	} // buildLoop

	private final String pipeVert = "|", pipeHoriz = "-", pipeCnrTL = "F", pipeCnrTR = "7", pipeCnrBL = "L",
		pipeCnrBR = "J";

	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    /*
	     * for (List<Character> row : map) { String rowString = (row.stream().map(e ->
	     * e.toString()).collect(Collectors.joining())); // " ═║╔╗╚╝ " rowString =
	     * rowString.replace("-", pipeHoriz).replace("|", pipeVert).replace("F",
	     * pipeCnrTL) .replace("7", pipeCnrTR).replace("L", pipeCnrBL).replace("J",
	     * pipeCnrBR); sb.append(rowString + System.lineSeparator()); }
	     */
	    for (int y = 0; y < mapC.length; y++) {
		for (int x = 0; x < mapC[y].length; x++) {
		    sb.append(mapC[y][x].c);
		}
		sb.append(System.lineSeparator());
	    }
	    return sb.toString();
	}

	public String getDistanceToCSV() {
	    StringBuilder sb = new StringBuilder();
	    for (int y = 0; y < mapC.length; y++) {
		for (int x = 0; x < mapC[y].length; x++) {
		    if (mapC[y][x].distance != Long.MAX_VALUE)
			sb.append(mapC[y][x].distance);
		    else
			sb.append(-1L);
		    sb.append(";");
		}
		sb.append(System.lineSeparator());
	    }
	    return sb.toString();
	}

	public Long getMaxDistance() {
	    // shortcut!
	    // System.out.println("Number of valid locations: " + validLocations.size());
	    Long count = 0L;
	    /*
	     * for (List<Character> l : map) { for (Character c : l) { if (c != '.')
	     * count++; } }
	     */

	    for (int y = 0; y < mapC.length; y++) {
		for (int x = 0; x < mapC[y].length; x++) {
		    if (mapC[y][x].c != '.')
			count++;
		}
	    }
	    System.out.println("Number of non-dot characters: " + count);
	    Long max = 0L;
	    for (Coordinate c : validLocations) {
		max = Math.max(max, c.distance);
	    }
	    System.out.println("Maximum distance: " + max);
	    return Integer.valueOf(validLocations.size() / 2).longValue();
	}
    } // PipeMaze

    @Override
    public void solve(int n) {
	PipeMaze pm = new PipeMaze(getFileContents(input));
	// System.out.println("Raw input: ");
	// System.out.println(pm);

	pm.buildLoop(false);
	pm.cleanup();
	System.out.println("After 1 iteration: ");
	System.out.println(pm);
	solver.add(pm.getMaxDistance());
	//System.out.println(pm.getDistanceToCSV());

	pm.buildLoop(false);
	pm.cleanup();
	// System.out.println("After 2 iterations: ");
	// System.out.println(pm);
	solver.add(pm.getMaxDistance());
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
