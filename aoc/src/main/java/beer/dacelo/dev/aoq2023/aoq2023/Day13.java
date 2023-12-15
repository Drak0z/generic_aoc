package beer.dacelo.dev.aoq2023.aoq2023;

import java.math.BigInteger;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day13 extends Day {
    /**
     * Day 13: The Sedronacci Sequence!!
     * 
     * It's becoming quite clear that Sedrick thinks a lot of himself. I wouldn't
     * say he's a Narcissist, until now! He has created a version of the Fibonacci
     * Sequence and named it after himself! We know he likes palindromes and
     * apparently he has decided to start off the Sedronacci Sequence with those
     * instead (don't worry they don't last too long). We have intel that he is
     * going to be using one of the large numbers as a seed in some future
     * encryption scheme! Diabolical! Our job, recruits, is to crack the Sedronacci
     * Sequence™ and find the 414th number (414 is also a palindrome - don not
     * worry, the 414th number is not, but wouldn't it be nice if it was!). This is
     * what we know so far. Here are the first 7 numbers in the series:
     * 
     * 1. 11 
     * 2. 11 
     * 3. 22 
     * 4. 33 
     * 5. 55 
     * 6. 88 
     * 7. 143 
     * What is the 414th number in the series? It is big!
     */

    // To make my life easier, just convert integers to longs and call the other
    // function
    public BigInteger getNthSedonacci(Integer n) {
	return getNthSedonacci(Long.valueOf(n));
    }

    public BigInteger getNthSedonacci(Long n) {
	BigInteger sedOne, sedTwo;
	sedOne = BigInteger.valueOf(11L);
	sedTwo = BigInteger.valueOf(11L);
	if (n <= 0)
	    return null;
	for (Long i = 2L; i <= n; i++) {
	    BigInteger sedThree = sedOne.add(sedTwo);
	    sedOne = sedTwo;
	    sedTwo = sedThree;
	}
	return sedOne;
    }

    @Override
    public void solve(int n) {
	Integer x = Integer.parseInt(getFileContents(input).get(0));
	for (int i = 1; i <= 7; i++) {
	    System.out.println(i + ". " + getNthSedonacci(i));
	}
	BigInteger solution = getNthSedonacci(x);
	solver.add(x + ". " + solution);
	System.out.println(x + ". " + solution);
    } // solve
}
