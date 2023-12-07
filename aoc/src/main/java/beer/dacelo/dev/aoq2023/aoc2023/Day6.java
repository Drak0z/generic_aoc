package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day6 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private Long getWins(Long time, Long distanceToBeat) {
	Long wins = 0L;
	for (Long l = 0L; l <= time; l++) {
	    Long timeRemaining = time - l;
	    Long speedMmSec = l;
	    Long totalDistance = speedMmSec * timeRemaining;
	    if (totalDistance > distanceToBeat)
		wins++;
	}
	return wins;
    }

    private void solve1() {
	List<String> fileContents = getFileContents(input);
	String timeLine = fileContents.get(0);
	String distanceLine = fileContents.get(1);
	while (timeLine.contains("  ")) {
	    timeLine = timeLine.replace("  ", " ");
	}
	while (distanceLine.contains("  ")) {
	    distanceLine = distanceLine.replace("  ", " ");
	}

	String[] raceTimeInfo = timeLine.split(" ");
	String[] raceDistanceInfo = distanceLine.split(" ");
	Long marginOfError = 1L;
	for (int i = 1; i < raceTimeInfo.length; i++) {
	    Long time = Long.parseLong(raceTimeInfo[i]);
	    Long distanceToBeat = Long.parseLong(raceDistanceInfo[i]);
	    marginOfError *= getWins(time, distanceToBeat);
	}

	solver.add(marginOfError);

    }

    private void solve2() {
	List<String> fileContents = getFileContents(input);
	String timeLine = fileContents.get(0).replace("Time:", "").replace(" ", "");
	String distanceLine = fileContents.get(1).replace("Distance:", "").replace(" ", "");

	Long marginOfError = 1L;
	Long time = Long.parseLong(timeLine);
	Long distanceToBeat = Long.parseLong(distanceLine);

	marginOfError *= getWins(time, distanceToBeat);
	solver.add(marginOfError);

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
