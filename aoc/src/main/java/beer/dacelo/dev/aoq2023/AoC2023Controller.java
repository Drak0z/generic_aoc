package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

public class AoC2023Controller extends GenericController {

    public AoC2023Controller() {
	super();
	// 1
	int d = 1;
	headers.put(d, "Day 1: Trebuchet?!");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: Consider your entire calibration document. What is the sum of all of the calibration values?",
		"part2", "Part 2: What is the sum of all of the calibration values?")));

	d++;
	headers.put(d, "Day 2: Cube Conundrum");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?",
		"part2",
		"Part 2: For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?")));

	d++;
	headers.put(d, "Day 3: Gear Ratios");
	headerParts.put(d,
		new HashMap<>(Map.of("part1",
			"Part 1: What is the sum of all of the part numbers in the engine schematic?", "part2",
			"Part 2: What is the sum of all of the gear ratios in your engine schematic?")));

	d++;
	headers.put(d, "Day 4: Scratchcards");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: Take a seat in the large pile of colorful cards. How many points are they worth in total?",
		"part2",
		"Part 2: Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, how many total scratchcards do you end up with?")));

	d++;
	headers.put(d, "Day 5: If You Give A Seed A Fertilizer");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: What is the lowest location number that corresponds to any of the initial seed numbers?",
		"part2",
		"Part 2: Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, how many total scratchcards do you end up with?")));

	d++;
	headers.put(d, "Day 6: Wait For It");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: Determine the number of ways you could beat the record in each race. What do you get if you multiply these numbers together?",
		"part2", "Part 2: How many ways can you beat the record in this one much longer race?")));

	d++;
	headers.put(d, "Day 7: Camel Cards");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: Find the rank of every hand in your set. What are the total winnings?", "part2",
		"Part 2: Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, how many total scratchcards do you end up with?")));
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