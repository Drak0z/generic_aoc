package beer.dacelo.dev.aoq2023.aoq2023;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day8 extends Day {
    /**
     * Day 8: Muddy Earnings & Losses
     * 
     * As we know from Sedrick's profile on the Lonely Hearts Club dating site, he
     * likes to do his own taxes. Recently we received some of the raw data for this
     * year's tax return. The problem we have is that the elves, not knowing what it
     * was have used it as a doormat. Turns out it's great at cleaning those crazy
     * curly shoes of theirs. This is bad for us as we now have dirty taxes to
     * recover (probably literally and figuratively).
     * 
     * One thing we do have going for us is that Sedrick is meticulous, and created
     * a basic checksum. He uses the sum of the number's digits. ex: The checksum
     * for 213 is 6.
     * 
     * Another thing we have going for us: luck. The checksum was on the bottom and
     * stayed clean. No dirt on it whatsoever!
     * 
     * Sedrick kept duplicates of his records, but they don't match now after the
     * mud and our cleaning. Your job is to recover the real figures in this data:
     * 
     * Records1 Records2 Checksums Using only the values that match the checksum,
     * calculate Sedrick's net earnings or loss. If the final amount is a loss,
     * prefix your answer with a - sign. This data and your answer are in thousands.
     * Sedrick is a ridiculous high-roller.
     * 
     * Example:
     * 
     * Sheet 1 Gains Sheet 1 Losses Sheet 2 Gains Sheet 2 Losses Gains Checksum
     * Losses Checksum 219 815 219 615 12 14 585 55 565 55 16 10 80 528 80 528 8 15
     * 446 44 440 42 8 8
     * 
     * True Gains True Losses 219 815 565 55 80 528 440 44
     * 
     * Total Gains: 1304 Total Losses: 1442 Net: -138
     */
    List<String> solver = new ArrayList<String>();

    private Long calculateChecksum(String s) {
	Long checksum = 0L;
	for (int i = 0; i < s.length(); i++) {
	    checksum += Long.parseLong(s.charAt(i) + "");
	}
	return checksum;	
    }
    
    @Override
    public void solve(int n) {
	// We'll have to fix this in post :)
	String filePath = this.getFilePath();
	File recordsOneFile = new File(filePath.replace("input.txt", "earnings1.csv"));
	File recordsTwoFile = new File(filePath.replace("input.txt", "earnings2.csv"));
	File recordsChecksumFile = new File(filePath.replace("input.txt", "checksums.csv"));

	List<String> recordsOne = getFileContents(recordsOneFile);
	List<String> recordsTwo = getFileContents(recordsTwoFile);
	List<String> recordsChecksum = getFileContents(recordsChecksumFile);

	List<List<Long>> gainsLosses = new ArrayList<List<Long>>();
	for (int i = 0; i < recordsOne.size(); i++) {
	    String[] rOne = recordsOne.get(i).split(",");
	    String[] rTwo = recordsTwo.get(i).split(",");
	    String[] rChecksum = recordsChecksum.get(i).split(",");
	    if (rOne[0].matches("-?\\d+(\\.\\d+)?") && rOne[1].matches("-?\\d+(\\.\\d+)?")
		    && rTwo[0].matches("-?\\d+(\\.\\d+)?") && rTwo[1].matches("-?\\d+(\\.\\d+)?")
		    && rChecksum[0].matches("-?\\d+(\\.\\d+)?") && rChecksum[1].matches("-?\\d+(\\.\\d+)?")) {
		// All the inputs are numbers, let's compare!
		Long gainsChecksum = Long.parseLong(rChecksum[0]);
		Long gainsOneChecksum = calculateChecksum(rOne[0]);
		Long gainsTwoChecksum = calculateChecksum(rTwo[0]);		
		Long gains = 0L;
		if (gainsChecksum == gainsOneChecksum) gains = Long.parseLong(rOne[0]);
		if (gainsChecksum == gainsTwoChecksum) gains = Long.parseLong(rTwo[0]);
		
		
		Long lossesChecksum = Long.parseLong(rChecksum[1]);
		Long lossesOneChecksum = calculateChecksum(rOne[1]);
		Long lossesTwoChecksum = calculateChecksum(rTwo[1]);		
		Long losses = 0L;
		if (lossesChecksum == lossesOneChecksum) losses = Long.parseLong(rOne[1]);
		if (lossesChecksum == lossesTwoChecksum) losses = Long.parseLong(rTwo[1]);
		
		gainsLosses.add(List.of(gains, losses));
	    }
	}
	
	Long totalGains = 0L;
	Long totalLosses = 0L;
	
	for (List<Long> gl : gainsLosses) {
	    totalGains += gl.get(0);
	    totalLosses += gl.get(1);
	}

	detail.add("Total Gains: " + totalGains);
	detail.add("Total Losses: " + totalLosses);
	detail.add("Net: " + (totalGains - totalLosses));

	solver.add((totalGains - totalLosses) + "");
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
