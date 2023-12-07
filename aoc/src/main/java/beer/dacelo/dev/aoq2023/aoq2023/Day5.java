package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day5 extends Day {
    /**
     * Day 5: The giant blob blast of 2023!
     * 
     * Thank you recruits for your hard work the other day in determining the size
     * of Sedrick so that we could calculate the exact amount of explosive to use to
     * blow this sucker up!
     * 
     * We have, however, run into a bit of a problem. Given the data we had at the
     * time we thought Sedrick was growing at a percentage a day rate. As it turns
     * out that was not a growth chart for Sedrick but merely the weight of yo-yos
     * that the elves have been producing in preparation for Christmas. Long story
     * short, we have just spread the problem out.
     * 
     * Aerial reconnaissance has given us a heat map dispersion of Sedrick and we
     * have got an idea of the distribution of him. using the following key we can
     * work out how many parts there are now:
     * 
     * X = 0 parts 
     * S = 1 Part 
     * E = 5 Parts 
     * D = 10 Parts 
     * R = 20 Parts 
     * I = 50 Parts 
     * C = 100 Parts 
     * K = 500 Parts 
     * 
     * Given the following dispersal map:
     * 
     * X,X,X,X,X,X,X,S,X,X,X,X,X,X,X 
     * X,X,X,X,X,X,S,E,S,X,X,X,X,X,X
     * X,X,X,X,X,S,E,D,E,S,X,X,X,X,X 
     * X,X,X,X,S,E,D,R,D,E,S,X,X,X,X
     * X,X,X,S,E,D,R,I,R,D,E,S,X,X,X 
     * X,X,S,E,D,R,I,C,I,R,D,E,S,X,X
     * X,S,E,D,R,I,C,K,C,I,R,D,E,S,X 
     * S,E,D,R,I,C,K,K,K,C,I,R,D,E,S
     * X,S,E,D,R,I,C,K,C,I,R,D,E,S,X 
     * X,X,S,E,D,R,I,C,I,R,D,E,S,X,X
     * X,X,X,S,E,D,R,I,R,D,E,S,X,X,X 
     * X,X,X,X,S,E,D,R,D,E,S,X,X,X,X
     * X,X,X,X,X,S,E,D,E,S,X,X,X,X,X 
     * X,X,X,X,X,X,S,E,S,X,X,X,X,X,X
     * X,X,X,X,X,X,X,S,X,X,X,X,X,X,X
     * 
     * Sedrick would now be split into 4568 pieces.
     * 
     * Using this dispersal map and the same key, how many pieces did Sedrick blow
     * up into?
     * 
     * Houston, we have a problem!
     */
    List<Long> solver = new ArrayList<Long>();

    @Override
    public void solve(int n) {
	Map<Character, Integer> keyParts = new HashMap<Character, Integer>();
	    keyParts.put('X', 0);
	    keyParts.put('S', 1);
	    keyParts.put('E', 5);
	    keyParts.put('D', 10);
	    keyParts.put('R', 20);
	    keyParts.put('I', 50);
	    keyParts.put('C', 100);
	    keyParts.put('K', 500);
	
	Long result = 0L;
	for (String line : getFileContents(input)) {
	    for (int i = 0; i < line.length(); i++) {
		if (keyParts.containsKey(line.charAt(i))) result += keyParts.get(line.charAt(i));
	    }
	    
	} // for (String line : getFileContents)
	solver.add(result);
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
