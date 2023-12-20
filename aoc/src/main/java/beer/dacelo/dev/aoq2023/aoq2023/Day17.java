package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day17 extends Day {
    /**
     * Day 17: The Amazing Blobby Race
     * 
     * We need to find out how to defeat Sedrick. We suspect that Sedrick's
     * henchblobs know his weakness but those guys are hidden all over town, and
     * this town is a maze! We're going to have to interview them all and collect
     * clues to Sedrick's weakness.
     * 
     * The town is represented by an ASCII map like the following. In the map...
     * 
     * # indicates a wall. You can't go through walls. @ indicates where you start
     * in the map $ indicates where you must end up ##################### #@ # # # #
     * # ### # ### ### # # # # # # # # # # # # # # ##### ### # ### # # # # # # #####
     * ### # ####### # # # # # ##### # ##### ### # # # # # # # # # ##### ### ### ###
     * ### # # # # # ####### # # ####### # # # # # # # # ##### ##### ### # # # # # #
     * # # ### # # # # ##### # # # # # # # # # # # # ##### ### # # ### # # # $#
     * ##################### You need to develop an algorithm to find the shortest
     * path through the maze from the @ to the $. Here is the solution for the
     * sample maze with . indicating the shortest path.
     * 
     * ##################### #@..# # # # # ###.# ### ### # # # # # .# # # # # # #
     * #.# ##### ### # ### # #.......# # # # ##### ###.# ####### # # # ....... # #
     * ##### # #####.### # # # # # # #...# # ##### ### ### ###.### # # # ..... # #
     * ####### # #.####### # # # # #.# # # ##### #####.### # # # # . # # # # ### # #
     * # #.##### # # # # # # #.# # # # # ##### ###.# # ### # # #......$#
     * ##################### However... scattered throughout the town are Sedrick's
     * henchmen who hold the clues (in the form of lower-case letters). Most of
     * these clues are garbage and will throw you off the path. The clues you need
     * lie, in order, on the shortest path from the Start to the Finish.
     * 
     * ##################### #@ # v#t# g w # d# ###h# ### ###j#q# # # # # #i#p# h
     * #d# # # # ##### ### # ### # # e l # q# # # ##### ### # ####### # # xv w#gm l
     * o s # # ##### #t##### ### # # # #v# # # w #h# ##### ###t### ### ### # #oq j
     * #y o # # ####### # #r####### #mm # # # # # # # #####h##### ###f# # # y# s # #
     * # #n### #m#z# #l##### # # # # # # # # # p# # # ##### ### # # ### # # i j # d
     * $# ##################### In the example case, we've highlighted (in CAPS) all
     * the letters we encountered along our shortest path.
     * 
     * ##################### #@..# v#t# g w # d# ###H# ### ###j#q# # # # .# #i#p# h
     * #d# # #.# ##### ### # ### # #.E...L.# q# # # ##### ###.# ####### # # xv
     * w#gm.L...O. s # # ##### #t#####.### # # # #v# # #.W.#h# ##### ###t### ###.###
     * # #oq j #y ...O. # # ####### # #R####### #mm # # # #.# # # #####h#####.###f#
     * # # y# s. # # # #n### #m#z# #L##### # # # # # # #.# # p# # # ##### ###.# #
     * ### # # i j #.D....$# ##################### So, the solution for the above
     * maze is helloworld.
     * 
     * Naturally, the real maze is here, and it's a heck of a lot larger.
     * 
     * What is Sedrick's weakness (in lower-case letters, no spaces or other
     * punctuation)?
     */
    private class Coordinate {
	Integer x;
	Integer y;
	Character c;
	Boolean visited;

	public Coordinate(Integer x, Integer y, Character c) {
	    this.x = x;
	    this.y = y;
	    this.c = c;
	    this.visited = false;
	}

	
	Integer getX() {
	    return x;
	}

	Integer getY() {
	    return y;
	}

	Character getC() {
	    return c;
	}

	Boolean isVisited() {
	    return visited;
	}

	Boolean isWall() {
	    return c.equals('#');
	}


	public String toString() {
	    if (c.equals(' ') && isVisited())
		return ".";
	    return c.toString();
	}

	public void setVisited() {
	    this.visited = true;
	}

	public void unsetVisited() {
	    this.visited = false;
	}
    } // Coordinate

    private class Maze {
	private List<List<Coordinate>> maze;
	private Coordinate start, exit;

	public Maze() {
	    maze = new ArrayList<List<Coordinate>>();
	}

	public void addRow(String line) {
	    int y = maze.size();

	    List<Coordinate> row = new ArrayList<Coordinate>();
	    for (int x = 0; x < line.length(); x++) {
		Character c = line.charAt(x);
		Coordinate coord = new Coordinate(x, y, c);
		if (c.equals('@'))
		    start = coord;
		if (c.equals('$'))
		    exit = coord;
		row.add(coord);
	    }
	    maze.add(row);
	    // line.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
	}

	public Coordinate getStart() {
	    return start;
	}


	public boolean isExit(int x, int y) {
	    return (x == exit.getX() && y == exit.getY());
	}

	public boolean isValidLocation(int x, int y) {
	    if (maze.size() > 0 && maze.get(0).size() > 0) {
		return (x >= 0 && x < maze.get(0).size() && y >= 0 && y < maze.size());
	    }
	    return false;
	}

	public Coordinate getCoordinate(int x, int y) {
	    return maze.get(y).get(x);
	}

	public boolean isWall(int x, int y) {
	    return getCoordinate(x, y).isWall();
	}

	public boolean isVisited(int x, int y) {
	    return getCoordinate(x, y).isVisited();
	}

	public void setVisited(int x, int y) {
	    maze.get(y).get(x).setVisited();
	}

	public void unsetVisited(int x, int y) {
	    maze.get(y).get(x).unsetVisited();
	}

	public String toString() {
	    StringBuilder output = new StringBuilder();
	    for (List<Coordinate> row : maze) {
		for (Coordinate c : row) {
		    output.append(c);
		}
		output.append(System.lineSeparator());
	    }
	    return output.toString();
	}

	private Boolean inPath(int x, int y, List<Coordinate> path) {
	    for (Coordinate c : path) {
		if (c.getX() == x && c.getY() == y)
		    return true;
	    }
	    return false;
	}

	private List<Coordinate> path = null;

	public List<Coordinate> setSolved(List<Coordinate> path) {
	    this.path = path;
	    List<Coordinate> mazePath = new ArrayList<Coordinate>();
	    for (int y = 0; y < maze.size(); y++) {
		for (int x = 0; x < maze.get(y).size(); x++) {
		    if (inPath(x, y, path)) {
			mazePath.add(getCoordinate(x,y));
			setVisited(x, y);
		    }
		    else {
			unsetVisited(x, y);
		    }
		}
	    }
	    return mazePath;
	}

	public void reset() {
	    path = null;
	    for (int y = 0; y < maze.size(); y++) {
		for (int x = 0; x < maze.get(y).size(); x++) {
		    unsetVisited(x, y);
		}
	    }
	}
    }

    private static int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private boolean exploreDFS(Maze maze, int x, int y, List<Coordinate> path) {
	if (!maze.isValidLocation(x, y) || maze.isWall(x, y) || maze.isVisited(x, y))
	    return false;
	path.add(maze.getCoordinate(x, y));
	maze.setVisited(x, y);

	if (maze.isExit(x, y))
	    return true;

	for (int[] direction : DIRECTIONS) {
	    int newX = x + direction[0];
	    int newY = y + direction[1];
	    if (exploreDFS(maze, newX, newY, path))
		return true;
	}
	path.remove(path.size() - 1);
	return false;
    }

    public List<Coordinate> solveDFS(Maze maze) {
	List<Coordinate> path = new ArrayList<Coordinate>();
	if (exploreDFS(maze, maze.getStart().getX(), maze.getStart().getY(), path)) {
	    return path;
	}
	return Collections.emptyList();
    }

    @Override
    public void solve(int n) {
	StringBuilder output = new StringBuilder();
	Maze maze = new Maze();
	for (String line : getFileContents(input)) {
	    maze.addRow(line);
	}

	List<Coordinate> path;

	detail.add("Solving Depth First: ");
	path = solveDFS(maze);
	path = maze.setSolved(path);
	System.out.println(path);
	output.append("DFS: ");
	for (Coordinate step : path) {
	    Character c = step.getC();
	    switch (c) {
	    case '#': // we should never see this in our path..
	    case '@':
	    case '$':
	    case ' ':
	    case '.':
		break;
	    default:
		output.append(c);
		break;
	    }
	}

	detail.add(maze.toString());
	detail.add("");

	maze.setSolved(path);
	detail.add(maze.toString());
	solver.add(output.toString());
    } // solve
}
