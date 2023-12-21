package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day9 extends Day {
    /**
     * Day 9: Sedrick's Random Sequence Generator™
     * 
     * If we have learned anything, we've learned this, Sedrick is an evil genius...
     * oh yeah, and that he is primarily made of candy canes, but back to the evil
     * genius part. He has been toying with us, and has hidden many things. His
     * latest escalation: A random sequence generator. A recipe of simple math
     * operations that generates a seemingly unending sequence of random numbers.
     * 
     * Sedrick's Random Sequence Generator™ works like this:
     * 
     * Calculation Remainder Reminder This description uses mod / modulus /
     * remainder / % operator, which may look different in your programming language
     * of choice.
     * 
     * However it's spelled, the operation we need is just the remainder from
     * integer division. As Wikipedia says: "For example, the expression 5 mod 2
     * evaluates to 1, because 5 divided by 2 has a quotient of 2 and a remainder of
     * 1, while 9 mod 3 would evaluate to 0, because 9 divided by 3 has a quotient
     * of 3 and a remainder of 0. "
     * 
     * In this description we will use:
     * 
     * mod(7,3) = 1
     * 
     * or
     * 
     * 7 % 3 = 1
     * 
     * Randomizer Calculations The randomizer holds six digits.
     * 
     * To generate a random digit: Take the last digit of the current six as the
     * output, and insert a new digit at the start.
     * 
     * The new value is the final digit of the sum of:
     * 
     * mod(first digit, 6) + mod(second digit, 3) + mod(third digit, 5) + mod(fourth
     * digit, 9) + mod(fifth digit, 2) + sixth digit as is Example Current digits
     * are 1,2,3,4,5,6
     * 
     * Calculate ((1 % 6) + (2 % 3) + (3 % 5) + (4 % 9) + (5 % 2) + 6) = 17
     * 
     * get the final digit: 17 % 10 = 7
     * 
     * New digit = 7
     * 
     * Remove the 6 from the right, and place the 7 at the left:
     * 
     * Digit Swap
     * 
     * New digits = 7,1,2,3,4,5
     * 
     * Output value = 6
     * 
     * Next will be ((7 % 6) + (1 % 3) + (2 % 5) + (3 % 9) + (4 % 2) + 5) % 10 = 2
     * With an output digit of 5 and new digits: 2,7,1,2,3,4
     * 
     * and so on
     * 
     * Sample Starting with the randomizer digits 4,9,6,8,9,8 the first sixteen
     * digits in the output sequence will be: 8986942265811292
     * 
     * Question What are the first 20 generated digits if starting from 9,3,4,5,0,9
     * ?
     */
    List<String> solver = new ArrayList<String>();

    public static int calculateNew(LinkedList<Integer> digit) {
	return ((digit.get(0) % 6) + (digit.get(1) % 3) + (digit.get(2) % 5) + (digit.get(3) % 9) + (digit.get(4) % 2)
		+ digit.get(5)) % 10;
    }

    @Override
    public void solve(int n) {
	LinkedList<Integer> digit = new LinkedList<Integer>();
	String[] inputArray = getFileContents(input).get(0).split(",");
	for (String s : inputArray) {
	    digit.add(Integer.parseInt(s));
	}

	StringBuilder output = new StringBuilder();
	for (int i = 0; i < Integer.parseInt(getFileContents(input).get(1)); i++) {
	    detail.add("iteration: " + i + ", input digit: " + digit);
	    int newDigit = calculateNew(digit);
	    int outputValue = digit.removeLast(); // remove(digit.size() - 1);
	    digit.addFirst(newDigit); // .add(0, newDigit);
	    detail.add("output value: " + outputValue + ", next digit: " + newDigit + " => new digit: " + digit);
	    output.append(outputValue);
	}
	solver.add(output.toString());
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
