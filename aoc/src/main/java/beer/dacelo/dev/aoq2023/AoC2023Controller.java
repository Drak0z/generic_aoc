package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

public class AoC2023Controller extends GenericController {
        
    public AoC2023Controller() {
	super();
	// 1
	int d = 1;
	headers.put(d, "Day 1: Trebuchet?!");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: Consider your entire calibration document. What is the sum of all of the calibration values?"
              			, "part2", "Part 2: What is the sum of all of the calibration values?"
				))
	);
	
    }

    @Override
    String getPathPrefix() {
	return "aoc2023\\input\\";
    }

    @Override
    protected String getModule() {
	return "beer.dacelo.dev.aoq2023.aoc2023";
    }
}