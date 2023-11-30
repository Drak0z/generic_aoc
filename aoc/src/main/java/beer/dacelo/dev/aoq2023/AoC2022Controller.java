package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

public class AoC2022Controller extends GenericController {
        
    public AoC2022Controller() {
	Map<String, String> genericFileMap = Map.of("test","test_input.txt"
                                		  , "test2","test_input.txt"
                                		  , "part1", "input.txt"
                                  		  , "part2", "input.txt"
		  );
	
	Map<String, String> genericHeaderPartsMap = Map.of("test" , "Test Input", "test2", "Test Input 2");
	// 1
	int d = 1;
	headers.put(d, "Day 1: Calorie Counting.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?"
              			, "part2", "Part 2: Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	
	// 2
	d++;
	headers.put(d, "Day 2: Rock Paper Scisors.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: What would your total score be if everything goes exactly according to your strategy guide?"
              			, "part2", "Part 2: What would your total score be if everything goes exactly according to your strategy guide?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	
	// 3
	d++;
	headers.put(d, "Day 3: Rucksack Reorganization.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: Find the item type that appears in both compartments of each rucksack. What is the sum of the priorities of those item types?"
              			, "part2", "Part 2: Find the item type that corresponds to the badges of each three-Elf group. What is the sum of the priorities of those item types?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	

	// 4
	d++;
	headers.put(d, "Day 4: Camp Cleanup.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: In how many assignment pairs does one range fully contain the other?"
              			, "part2", "Part 2: In how many assignment pairs do the ranges overlap?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
    }

    @Override
    String getPathPrefix() {
	return "aoc2022\\input\\";
    }

    @Override
    protected String getModule() {
	return "beer.dacelo.dev.aoq2023.aoc2022";
    }
}