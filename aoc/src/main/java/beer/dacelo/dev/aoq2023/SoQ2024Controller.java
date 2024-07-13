package beer.dacelo.dev.aoq2023;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SoQ2024Controller extends GenericController {
	@FXML
	Button backButton, part1, part2, test1, test2;

	public SoQ2024Controller() {
		super();

		int d = 0;

		// 1
		d++;
		headers.put(d, "Day 1: Your Name is In What???");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"It has chosen one for himself and is wanting to share it with all of us. It has let us know that it will only responded to any inquiries if this name is used. Feeling that we all don’t appreciate knitting and crocheting enough it has decided that the only way to give us this name is to reveal it in a long diatribe on knitting and crocheting. Also that in order for us to truly appreciate these arts that the paragraph be revealed via a typical knitting pattern.")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "MyGloriousName.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);

		// 2
		d++;
		headers.put(d, "Day 2: Hack the Water!");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"The code for the combo lock is “encrypted” using a novel rotary cipher. Imagine the letters of the alphabet (A-Z) around an old-school cereal box decoding ring. The cursor starts at the first position, and then takes turns rotating clockwise, and then counter-clockwise to reveal successive letters of the plaintext.")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "input.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);
		
		// 3
		d++;
		headers.put(d, "Day 3: A Sure Bet");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"Given the current year’s ongoing fiasco, our insurance adjusters want to make sure that the big horse race is properly… predictable.")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "Horse_DNA_Sequences.tsv"));
		headerParts.get(d).putAll(genericHeaderPartsMap);

		// 4
		d++;
		headers.put(d, "Day 4: Wheel of Fun!");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"Help the Iron Wranglers decode the full diagnostic logs and calculate the funfactor so that we can determine what needs to be done to restore the fun to the ride and keep the supercomputer happy!")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "diagnostics.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);

		// 5
		d++;
		headers.put(d, "Day 5: Life Support & Off-by-one Rotary Encryption");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"Encrypt this message and provide the result.")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "input.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);

		// 6
		d++;
		headers.put(d, "Day 6: What's thEE Frequency??");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"We have gotten a collection of data here which contains 20 letters. How many were written by Neville?")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "input.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);
		
		// 7
		d++;
		headers.put(d, "Day 7: Clean-up");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"Given the list of attendees and their planned ride schedule, we need your help calculating the expected cost of cleaning supplies given the anticipated number of “incidents”. Each incident requires two cleaning rags and 5 squirts of cleaning solution. Each rag costs $0.50 and a bottle of cleaning solution (containing exactly 250 squirts) costs $25.")));
		files.put(d, Map.of("test", "attendees_short.csv", "solve", "attendees.csv"));
		headerParts.get(d).putAll(genericHeaderPartsMap);
		
		// 8
		d++;
		headers.put(d, "Day 8: Horsetastrophe™");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"Given our actual starting population of microhorses, how many microhorses would we expect there to be after 120 days?")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "input.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);

		// 41 -- Bonus question released on day 4
		d = 41;
		headers.put(d, "Bonus Question: The Donut Oracle");
		headerParts.put(d, new HashMap<>(Map.of("solve",
				"The supercomputer protects Bill’s secret recipe and won’t reveal it easily. We need Bill’s login credentials to access the recipe!")));
		files.put(d, Map.of("test", "test_input.txt", "solve", "input.txt"));
		headerParts.get(d).putAll(genericHeaderPartsMap);
	}

	@Override
	String getPathPrefix() {
		return "soq2024\\input\\";
	}

	@Override
	protected String getModule() {
		return "beer.dacelo.dev.aoq2023.soq2024";
	}
}