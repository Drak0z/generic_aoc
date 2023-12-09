package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AoQ2023Controller extends GenericController {
    @FXML Button backButton, part1, part2, test1, test2;

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
	headerParts.put(d,
		new HashMap<>(Map.of("solve", "Given this pattern, how many times in a day will Sedrick eat?")));

	// Day 3:How Big Will He Get?!?
	d++;
	headers.put(d, "Day 3: How Big Will He Get?!?");
	headerParts.put(d,
		new HashMap<>(Map.of("solve", "Given this pattern, how many times in a day will Sedrick eat?")));

	// Day 4: Prime Feeding Time!
	d++;
	headers.put(d, "Day 4: Candy Genetics");
	headerParts.put(d,
		new HashMap<>(Map.of("solve", "How many unique and valid 6 base sequences are in Sedrick's DNA?")));

	// Day 5: The giant blob blast of 2023!
	d++;
	headers.put(d, "Day 5: The giant blob blast of 2023!");
	headerParts.put(d, new HashMap<>(Map.of("solve",
		"Using this dispersal map and the same key, how many pieces did Sedrick blow up into?")));

	// Day 6: We Were Wrong. Maybe Doubly Wrong??
	d++;
	headers.put(d, "Day 6: We Were Wrong. Maybe Doubly Wrong??");
	headerParts.put(d, new HashMap<>(Map.of("solve",
		"If he doubles in weight every day, on which day will his weight surpass that of the earth?")));

	// Day 6: We Were Wrong. Maybe Doubly Wrong??
	d++;
	headers.put(d, "Day 7: This just in, Sedrick Likes Palindromes");
	headerParts.put(d, new HashMap<>(Map.of("solve",
		"Please decipher the following")));

	// Day 6: We Were Wrong. Maybe Doubly Wrong??
	d++;
	headers.put(d, "Day 8: Muddy Earnings & Losses");
	headerParts.put(d, new HashMap<>(Map.of("solve",
		"Using only the values that match the checksum, calculate Sedrick's net earnings or loss. If the final amount is a loss, prefix your answer with a - sign. This data and your answer are in thousands. Sedrick is a ridiculous high-roller.")));
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