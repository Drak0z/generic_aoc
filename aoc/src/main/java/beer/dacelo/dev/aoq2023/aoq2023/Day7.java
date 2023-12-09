package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day7 extends Day {
    /**
     * Day 7: This just in, Sedrick Likes Palindromes
     * 
     * Fred the elf, you may remember him from previous years, was one of the first
     * elves to find Sedrick and, of course, befriended him. Fred took to feeding
     * Sedrick whatever he liked. Their friendship took off and Fred soon took him
     * everywhere. When it was time to wrap presents, he would bring him along and
     * disguise him as a jar of Play Doh. At dinner time he was instructed to act as
     * if he was a bowl of Jello. And at an elves-only darts nights, he even
     * pretended to be a Margarita!
     * 
     * What we are getting at here is that Fred has put the time in. He has shared
     * many of his interests with Sedrick. This being said, he has got Sedrick
     * interested in many things. This includes palindromes. Sedrick loves them,
     * maybe more than Fred! So much is the case that he has left us a note using
     * only palindromes. Here is what we know about the note. It is a list of
     * palindromes. Each represents a space or a letter. The cypher works in a cycle
     * where the first palindrome is a space and the next is A and the next is B
     * etc. The pattern resets at Z, and starts over at space. The first cycle of
     * palindromes is as follows:
     * 
     * 0 - Space 1 - A 2 - B 3 - C 4 - D 5 - E 6 - F 7 - G 8 - H 9 - I 11 - J 22 - K
     * 33 - L 44 - M 55 - N 66 - O 77 - P 88 - Q 99 - R 101 - S 111 - T 121 - U 131
     * - V 141 - W 151 - X 161 - Y 171 - Z 181 - Space 191 - A 202 - B
     * 
     * If Sedrick wrote HI THERE, its ciphered text could look like this:
     * 
     * 8, 9, 181, 111, 8, 5, 99, 5 or like this 8, 9, 0, 111, 8, 5, 99, 5
     * 
     * Sedrick has opted for very large palindromes. It appears he has a sense of
     * humor. Tell us what he wrote. Ensure there are only CAPITAL letters and
     * spaces in your answer.
     * 
     * Please decipher the following:
     * 
     * 592295, 393393, 615516, 243342, 570075, 541145, 311113, 351153, 155551,
     * 558855, 268862, 540045, 317713, 528825, 297792, 505505, 560065, 474474,
     * 259952, 351153, 607706, 464464, 324423, 252252, 216612, 131131, 177771,
     * 507705, 417714, 436634, 135531, 255552, 225522, 605506, 491194, 324423,
     * 209902, 177771, 513315, 370073, 545545, 221122, 432234, 430034, 447744,
     * 291192, 216612, 236632, 315513, 241142, 621126, 171171, 351153, 163361,
     * 229922, 189981, 379973, 216612, 250052, 198891, 379973, 257752, 182281,
     * 270072, 245542, 309903, 339933, 434434, 297792, 271172, 176671, 112211,
     * 243342, 522225, 405504, 401104, 441144, 390093, 147741, 324423, 149941,
     * 545545, 427724, 572275, 396693, 513315, 193391, 225522, 167761, 162261,
     * 234432, 190091, 316613, 448844, 137731, 194491, 126621, 423324, 430034,
     * 378873, 527725, 393393, 468864, 208802, 518815
     */
    List<String> solver = new ArrayList<String>();

    private Boolean isPalindrome(Long l) {
	String checkString = l.toString();
	for (int i = 0; i < checkString.length() / 2; i++) {
	    if (checkString.charAt(i) != checkString.charAt(checkString.length() - i - 1))
		return false;
	}
	return true;
    } // isPalindrome

    private Map<Long, Character> buildMap(Long minValue, Long maxValue, List<Character> cypher) {
	Map<Long, Character> map = new HashMap<Long, Character>();
	Long l = 0L;
	int i = 0;
	while (l <= maxValue) {
	    if (isPalindrome(l)) {
		if (l >= minValue) map.put(l, cypher.get(i));
		i = (i + 1) % cypher.size();
	    }
	    l++;
	}
	return map;
    } // buildMap

    private String decode(List<Long> message, Map<Long, Character> palindromeMap) {
	StringBuilder decoderSb = new StringBuilder();
	for (Long l : message) {
	    decoderSb.append(palindromeMap.get(l));
	}
	return decoderSb.toString();
    } // decode

    @Override
    public void solve(int n) {
	Map<Long, Character> palindromeMap = null;
	List<Character> cypher = Arrays.asList(' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

	String[] fullInput = ((String.join("", getFileContents(input))).replace(" ", "")).split(",");

	List<Long> message = new ArrayList<Long>();
	for (String s : fullInput)
	    message.add(Long.parseLong(s));

	// We only have to build our palindrome map between min and max
	Long minValue = Collections.min(message);
	Long maxValue = Collections.max(message); 

	palindromeMap = buildMap(minValue, maxValue, cypher);
	String output = decode(message, palindromeMap);

	solver.add(output);
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
