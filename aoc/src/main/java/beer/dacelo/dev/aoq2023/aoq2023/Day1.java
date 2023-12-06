package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day1 extends Day {
    /**
     * Sedrick. as it turns out, likes odd numbers. Specifically odd odd numbers. He
     * tinkers around on his vintage adding machine, one of the few things he hasn't
     * eaten, playing with these numbers.
     * 
     * That's Odd!
     * 
     * What are odd odd numbers? Let's look at the following numbers:
     * 
     * 1, 2, 3, 4, 5, 6, 7, 8, 9 The odd numbers are:
     * 
     * 1, 3, 5, 7, 9 
     * 
     * And the odd odd numbers are 1, 5, 9 as they were the first,
     * third and fifth numbers in the series (the odd ones).
     * 
     * So if we asked for the third odd odd number the answer would be 9.
     * 
     * If we asked for the 9th odd odd number, the answer would be 33.
     * 
     * Our intel (we have captured an elf and got information from him... this is
     * probably all you need or want to know) leads us to believe that there is an
     * important number hidden in this series.
     * 
     * Find the 11111th odd odd number.
     * 
     * Note: Please answer with no leading or trailing spaces and no commas in the
     * number.
     */
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	List<String> fileContents = getFileContents(input);
	Integer nthNumberToFind = Integer.parseInt(fileContents.get(0));
	String suffix = "th";
	switch (Integer.toString(nthNumberToFind).charAt(Integer.toString(nthNumberToFind).length() - 1)) {
	case '1':
	    suffix = "st";
	    break;
	case '2':
	    suffix = "nd";
	    break;
	case '3':
	    suffix = "rd";
	    break;
	} // switch

	detail.add("We're trying to find the " + (nthNumberToFind) + suffix + " odd odd number");
	Integer oddFound = 0;
	Integer oddoddFound = 0;
	Integer number = 0;
	while (oddoddFound < nthNumberToFind) {
	    number++;
	    if (number % 2 == 1)
		oddFound++;
	    if (number % 2 == 1 && oddFound % 2 == 1) {
		oddoddFound++;
		detail.add("Number: " + number + ", n%2: " + (number % 2) + ", oddFound: " + oddFound
			+ ", oddoddFound: " + oddoddFound);
	    } // if
	} // while

	detail.add("Number: " + number + " is the " + nthNumberToFind + suffix + " odd odd number!");
	solver.add(number);
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(Integer.toString(solver.get(0)));
	return solution;
    } // getSolution
}
