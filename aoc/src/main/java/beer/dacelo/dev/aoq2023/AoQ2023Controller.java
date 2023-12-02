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
	// -1
	int d = -1;
	headers.put(d, "Day -1: Hello World.");
	headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: What is the value of \"x\" that the bot used in their program that would have produced the provided output?"
				))
	);
	files.put(d, genericFileMap);
	headerParts.get(d).putAll(genericHeaderPartsMap);
	
	// 0	
	d++;
	headers.put(d, "Day 0: What Will He Eat Next? Where Will He Eat Next??");
	headerParts.put(d, new HashMap<>(Map.of("solve", "Where will Sedrick most likely strike next? (Answer in all lower case, and leave out \"The\")"))
	);
	files.put(d, Map.of("solve", "eatenKgs.csv"));
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