package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day11 extends Day {
    /**
     * Day 11: Sedrick the Gin Rummy card shark!
     * 
     * Sedrick plays card games for cash. Lots of cash. We think he cheats. He
     * played gin rummy with one of the reindeer until he won everything the poor
     * critter owned. There is nothing sadder than than the sound of a bankrupt
     * reindeer crying. Tragic.
     * 
     * Nonetheless, Sedrick was able to persuade us to sit down and play a friendly
     * game. It went well. We only lost a thousand hands, which was our plan all
     * along, right?
     * 
     * Help us out by scoring the hands we lost so we know how far in the hole we
     * ended up.
     * 
     * Scoring For simplicity we will not consider card suits. Jack, Queen, King are
     * written as 11, 12, 13 and the ace will be 1.
     * 
     * Hands contain five cards. Points in a hand are counted from the value of each
     * card that is not part of a run.
     * 
     * A run is any sequence of three or more cards with consecutive values.
     * 
     * For example: 2,3,4 is a run 6,7,9 is not a run 2,3 is not a run 9,13 is
     * definitely not a run 7,8,9,10 is absolutely a run 4,5,6,7,8 is also a run
     * 2,3,5,6,8 is not A hand of 2,3,4,7,8 would count 15 points: 7 and 8 are not
     * part of a run, 7+8 = 15.
     * 
     * 4,6,7,8,10 counts as 14 (4+10).
     * 
     * 8,9,10,11,13 scores 13.
     * 
     * 2,10,11,12,13 scores 2.
     * 
     * On the other hand, 2,3,5,6,8 scores 24: 2+3+5+6+8 since there are no cards in
     * a run.
     * 
     * On the third hand, 9,10,11,12,13 scores 0: everything is part of a run.
     * 
     * Question Each line in this file is a hand of cards. The cards in each hand
     * are in sorted order, smallest to largest.
     * 
     * Calculate the score for each hand, and enter the total of all scores.
     */
    List<String> solver = new ArrayList<String>();

    private class GinRummyHand {
	List<Integer> hand;

	public GinRummyHand(String input) {
	    this.hand = Arrays.asList(input.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

	}

	/**
	 * 
	 * @param aceMode 0: Ace is "1" , 1 Ace is EITHER 1 OR 14 , 2 Ace is BOTH 1 AND
	 *                14
	 * @return
	 */
	public Integer getScore(int aceMode) {
	    Integer score = 0;
	    List<Integer> uniqueHandCards = new ArrayList<Integer>(new HashSet<Integer>(hand)); // remove duplicates of
												// the original hand to
												// validate set size
	    Set<Set<Integer>> runsFound = new HashSet<Set<Integer>>(); // we may find more than one run, if Ace is High
	    if (uniqueHandCards.size() >= 3) {
		Set<Integer> run = new HashSet<Integer>();
		Boolean hasLooped = false;
		Integer prev = uniqueHandCards.get(0);
		for (int i = 1; i < uniqueHandCards.size(); i++) {
		    Integer current = uniqueHandCards.get(i);
		    if ((current - prev) == 1 || (prev == 13 && current == 1) // only applicable if we're looping back
		    ) {
			// We're in a run, add our own and our previous value to the run
			// don't worry, hashset only contains unique values :)
			run.add(prev);
			run.add(current);
		    } else if (aceMode > 0 && !hasLooped && current == 13 && uniqueHandCards.get(0) == 1) {
			// If we are at the end of our hand, and we end with a King high,
			// Handle Ace as part of the run back if ace mode is "either//or" (1), or
			// "and//and" (2)
			switch (aceMode) {
			case 1:
			    run.add(current);
			    run.add(uniqueHandCards.get(0));
			    break;
			case 2:
			    i = -1;
			    hasLooped = true;
			    break;
			}
		    } else {
			// We're at the end of a run. Reset
			if (run.size() >= 3)
			    runsFound.add(run);
			run = new HashSet<Integer>();
		    }
		    prev = current;
		}
		if (run.size() >= 3)
		    runsFound.add(run);
	    }

	    Set<Integer> run = new HashSet<Integer>();
	    Set<Set<Integer>> validRuns = new HashSet<Set<Integer>>();
	    for (Set<Integer> r : runsFound) {
		if (r.size() >= 3)
		    validRuns.add(r);
	    }

	    if (validRuns.size() > 1) {
		// we have multiple runs, let's eliminate the lowest value one(s?)
		Integer maxValue = Integer.MIN_VALUE;
		for (Set<Integer> r : validRuns) {
		    Integer thisValue = 0;
		    for (Integer i : r) {
			thisValue += i;
		    }
		    if (thisValue > maxValue) {
			maxValue = thisValue;
			run = r;
		    }
		}
	    } else {
		for (Set<Integer> s : validRuns) {
		    run.addAll(s);
		}
	    }

	    for (Integer i : hand) {
		if (!run.contains(i)) {
		    score += i;
		} else {
		    run.remove(i); // let's make sure we do include duplicates in our score
		}
	    }
	    return score;
	}

	public String toString() {
	    return "Hand: " + hand.toString();
	}
    }

    @Override
    public void solve(int n) {
	Long result = 0L;
	List<Integer> scores = new ArrayList<Integer>(List.of(0, 0, 0));
	for (String line : getFileContents(input)) {
	    for (int i = 0; i <= 2; i++) {
		GinRummyHand grh = new GinRummyHand(line);
		Integer score = grh.getScore(i);
		StringBuilder outputString = new StringBuilder(grh.toString());

		outputString.append(score);
		detail.add(outputString.toString());
		scores.set(i, scores.get(i) + score);
	    }
	}
	for (int i = 0; i < scores.size(); i++) {
	    StringBuilder outputString = new StringBuilder("Ace Handling Method: ");
	    switch (i) {
	    case 0:
		outputString.append("[ Ace = 1      ], total score: ");
		break;
	    case 1:
		outputString.append("[ Ace = 1 || 14], total score: ");
		break;
	    case 2:
		outputString.append("[ Ace = 1 && 14], total score: ");
		break;
	    }

	    outputString.append(scores.get(i));
	    solver.add(outputString.toString());
	}
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	for (int i = 0; i < solver.size(); i++) {
	    solution.add(solver.get(i).toString());
	}
	return solution;
    } // getSolution
}
