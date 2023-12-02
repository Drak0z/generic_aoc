package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day0 extends Day {
    /**
     * According to Specs, the most four-eyed elf, blobs try to eat equal parts
     * sugar, sandstone, water, and disdain over a ten day period. There are nearby
     * sources of all four blob food groups separated by a day of blob-ish travel:
     * 
     * The Bakery for sugar
     * The Main Hall for sandstone 
     * The Pumphouse for water 
     * The Park for disdain (geese are made of distilled disdain) 
     * 
     * Specs has also tracked Sedrick's last nine days of consumption (in kg) - A day per row. 
     * 
     * Where will Sedrick most likely strike next? (Answer in all lower case, and leave out "The")
     */
    List<String> solver = new ArrayList<String>();

    @Override
    public void solve(int n) {
	List<String> fileContents = getFileContents(input);
	Map<String, Integer> kgsConsumed = new HashMap<String, Integer>();
	String headerLine = fileContents.remove(0);
	List<String> headers = Arrays.asList(headerLine.split(","));
	for (int i = 0; i < headers.size(); i++) {
	    headers.set(i, headers.get(i).toLowerCase().replace("the ", ""));
	    kgsConsumed.put(headers.get(i), 0);
	    detail.add("Initialising: " + kgsConsumed);
	}
	for (String line : fileContents) {
	    List<String> rowData = Arrays.asList(line.split(","));
	    for (int i = 0; i < rowData.size(); i++) {
		Integer kgs = kgsConsumed.get(headers.get(i));
		kgs += Integer.parseInt(rowData.get(i));
		kgsConsumed.put(headers.get(i), kgs);
	    }
            detail.add("line: [" + line + "] results in: " + kgsConsumed);
	}

	// There are probably cleaner ways to do this, but ... Java man, sorting HashMaps by values is... interesting
	Map<String, Integer> sortedLowestKg = kgsConsumed.entrySet().stream().sorted(Map.Entry.comparingByValue())
		.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

	detail.add("");
	detail.add("Sorted output: " + sortedLowestKg);
	String nextTarget = sortedLowestKg.entrySet().iterator().next().getKey();
	solver.add(nextTarget);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0));
	return solution;
    }
}
