package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day1 extends Day {
    List<Integer> solver = new ArrayList<Integer>();

    @Override
    public void solve(int n) {
	List<Integer> calibrationInputs = new ArrayList<Integer>();
	List<String> numbers = new ArrayList<String>(
		Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));

	for (String line : getFileContents(input)) {
	    System.out.println(line);
	    detail.add(line);
	    Integer firstDigit = 0, lastDigit = 0;
	    StringBuilder lineSb = new StringBuilder(line);
	    for (int i = 0; i < lineSb.length(); i++) {
		Character ch = lineSb.charAt(i);
		switch (n) {

		case 1: // part 1
		    if (Character.isDigit(ch)) {
			if (firstDigit == 0)
			    firstDigit = Character.getNumericValue(ch);
			lastDigit = Character.getNumericValue(ch);
		    }
		    break;
		case 2:
		    detail.add(lineSb + " {" + i + "}");
		    if (Character.isDigit(ch)) {
			if (firstDigit == 0)
			    firstDigit = Character.getNumericValue(ch);
			lastDigit = Character.getNumericValue(ch);
		    } else {
			// the first few characters will be a number
			Boolean numberFound = false;
			for (int nr = 0; nr < numbers.size() && !numberFound; nr++) {
			    String number = numbers.get(nr);
			    if (!numberFound && (i + number.length()) <= lineSb.length()) {
				if (number.equals(lineSb.substring(i, i + number.length()))) {
				    numberFound = true;
				    if (firstDigit == 0)
					firstDigit = nr;
				    lastDigit = nr;
				}
			    }
			}

		    }
		    break;
		}
	    }

	    detail.add("" + firstDigit + "" + lastDigit);
	    Integer calibrationInput = Integer.parseInt(firstDigit + "" + lastDigit);
	    calibrationInputs.add(calibrationInput);
	}
	detail.add(calibrationInputs.toString());

	Integer result = 0;
	for (Integer i : calibrationInputs) {
	    result += i;
	}
	detail.add(result.toString());
	solver.add(result);
    }

    @Override
    public List<String> getSolution(int n) {
	System.out.println("Getting solution for " + this.getClass() + " part: " + n);
	solution = new ArrayList<String>();
	switch (n) {
	case 1:
	case 2:
	    solution.add(Integer.toString(solver.get(0)));
	    break;
	}
	return solution;
    }
}
