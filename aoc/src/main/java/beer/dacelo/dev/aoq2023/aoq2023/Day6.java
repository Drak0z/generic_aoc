package beer.dacelo.dev.aoq2023.aoq2023;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day6 extends Day {
    /**
     * Day 6: We Were Wrong. Maybe Doubly Wrong?? 23 hours 12 minutes 56 seconds
     * remaining If it were a 1950's movie
     * 
     * With just a little more research, it appears we may have been really wrong
     * about his weight gain. But our crack team of scientists and mathematicians
     * have come up with what they think is an infallible algorithm for his growth
     * rate. We think he is now doubling in size every day. Doing the math, as mathy
     * people do, they have realized that this is troubling for a number of reasons.
     * First and foremost is that with exponential growth, he will eventually get
     * bigger than the planet. Our boffins suggest this is bad.
     * 
     * So for an example, if on day 1, Sedrick weighed 1 kg, on which day will he
     * weigh more than 100 kg?
     * 
     * Day 1: 1 kg Day 2: 2 kg Day 3: 4 kg Day 4: 8 kg Day 5: 16 kg Day 6: 32 kg Day
     * 7: 64 kg Day 8: 128 kg In this case, the answer would be day 8 (i.e. his
     * weight surpasses 100kg on the 8th day).
     * 
     * Based on our technicians' calculations, the earth weighs roughly
     * 5,974,000,000,000,000,000,000,000 kilograms and Sedrick currently weighs
     * 12021 kg (a palindrome!! But more on that on another day!).
     * 
     * If he doubles in weight every day, on which day will his weight surpass that
     * of the earth?
     */
    List<Long> solver = new ArrayList<Long>();

    @Override
    public void solve(int n) {
	String[] parts = getFileContents(input).get(0).split(" ");

	BigInteger startWeight = new BigInteger(parts[0]);
	BigInteger destinationWeight = new BigInteger(parts[1].replace(",", ""));
	detail.add("Start Weight: " + startWeight + ", destinationWeight " + destinationWeight);
	Long result = 1L;
	BigInteger currentWeight = startWeight;
	while (currentWeight.compareTo(destinationWeight) < 0) {
	    currentWeight = currentWeight.multiply(BigInteger.TWO);
	    result++;
	    detail.add("Day " + result + ": " + currentWeight);
	}
	solver.add(result);
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
