package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AoQ2023Controller extends GenericController {
    

    @FXML Button backButton;

    public AoQ2023Controller() {
	Map<String, String> genericFileMap = Map.of("test","test_input.txt"
                                		  , "test2","test_input.txt"
                                		  , "part1", "input.txt"
                                  		  , "part2", "input.txt"
		  );
	
	Map<String, String> genericHeaderPartsMap = Map.of("test" , "Test Input", "test2", "Test Input 2");
	// 0
	int d = 0;
	headers.put(d, "Day 0: Hello World.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: What is the value of \"x\" that the bot used in their program that would have produced the provided output?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	
	// 1
	d++;
	headers.put(d, "Day 2: Rock Paper Scisors.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: What would your total score be if everything goes exactly according to your strategy guide?"
              			, "part2", "Part 2: What would your total score be if everything goes exactly according to your strategy guide?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	
	// 2
	d++;
	headers.put(d, "Day 3: Rucksack Reorganization.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: Find the item type that appears in both compartments of each rucksack. What is the sum of the priorities of those item types?"
              			, "part2", "Part 2: ..."
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
    }

    @Override
    String getPathPrefix() {
	return "aoq2023\\input\\";
    }

    @Override
    protected String getModule() {
	return "beer.dacelo.dev.aoq2023.aoq2023";
    }
}