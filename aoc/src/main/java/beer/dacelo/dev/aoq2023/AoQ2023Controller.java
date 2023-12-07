package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AoQ2023Controller extends GenericController {

    @FXML
    Button backButton;

    public AoQ2023Controller() {
	super();
	
	// -1
	int d = -1;
	headers.put(d, "Day -1: Hello World.");
	headerParts.put(d, new HashMap<>(Map.of("part1",
		"Part 1: What is the value of \"x\" that the bot used in their program that would have produced the provided output?")));
	headerParts.get(d).putAll(genericHeaderPartsMap);

	// 0
	d++;
	headers.put(d, "Day 0: What Will He Eat Next? Where Will He Eat Next??");
	headerParts.put(d, new HashMap<>(Map.of("solve",
		"Where will Sedrick most likely strike next? (Answer in all lower case, and leave out \"The\")")));
	files.put(d, Map.of("solve", "eatenKgs.csv"));
	headerParts.get(d).putAll(genericHeaderPartsMap);

	// Day 1: Sedrick is odd... really odd.
	d++;
	headers.put(d, "Day 1: Sedrick is odd... really odd.");
	headerParts.put(d, new HashMap<>(Map.of("solve", "Find the 11111th odd odd number.")));
	

	// Day 2: Prime Feeding Time!
	d++;
	headers.put(d, "Day 2: Prime Feeding Time!");
	headerParts.put(d, new HashMap<>(Map.of("solve", "Given this pattern, how many times in a day will Sedrick eat?")));
	

	// Day 3:How Big Will He Get?!?
	d++;
	headers.put(d, "Day 3: How Big Will He Get?!?");
	headerParts.put(d, new HashMap<>(Map.of("solve", "Given this pattern, how many times in a day will Sedrick eat?")));

	// Day 4: Prime Feeding Time!
	d++;
	headers.put(d, "Day 4: Candy Genetics");
	headerParts.put(d, new HashMap<>(Map.of("solve", "How many unique and valid 6 base sequences are in Sedrick's DNA?")));
	
	// Day 5: The giant blob blast of 2023!
	d++;
	headers.put(d, "Day 4: Candy Genetics");
	headerParts.put(d, new HashMap<>(Map.of("solve", "How many unique and valid 6 base sequences are in Sedrick's DNA?")));
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