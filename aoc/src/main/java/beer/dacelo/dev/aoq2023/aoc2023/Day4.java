package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day4 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private class ScratchCard {
	String name;
	String numbers;
	private Long value = 0L;
	private int matches = 0;

	public ScratchCard(String line) {
	    String[] input = line.split(":");
	    this.name = input[0].trim();
	    process(input[1]);
	}

	private void process(String line) {
	    line = line.trim();

	    this.numbers = line;
	    String[] input = line.split("\\|");

	    List<Integer> winningNrs = new ArrayList<Integer>();
	    for (String s : (input[0].trim()).split(" ")) {
		if (s.length() > 0) {
		    winningNrs.add(Integer.parseInt(s));
		}

	    }

	    List<Integer> nrsWeHave = new ArrayList<Integer>();
	    for (String s : (input[1].trim()).split(" ")) {
		if (s.length() > 0) {
		    nrsWeHave.add(Integer.parseInt(s));
		}
	    }

	    for (Integer nr : nrsWeHave) {
		if (winningNrs.contains(nr)) {
		    this.matches++;
		    if (this.value == 0)
			this.value = 1L;
		    else
			this.value *= 2;
		}
	    }
	}

	public String toString() {
	    return this.name + ": " + this.numbers + " value: (" + this.value + ")";
	}

	public Long getValue() {
	    return this.value;
	}

	public Integer getMatches() {
	    return this.matches;
	}
    }

    @Override
    public void solve(int n) {
	Long result = 0L;
	List<ScratchCard> scratchcards = new ArrayList<ScratchCard>();
	for (String line : getFileContents(input)) {
	    ScratchCard sc = new ScratchCard(line);
	    detail.add("Processed ScratchCard: " + sc.toString());
	    if (n == 1) {
		result += sc.getValue();
	    }
	    if (n == 2) {
		scratchcards.add(sc);
	    }
	}
	List<Integer> nrCards = new ArrayList<Integer>(Collections.nCopies(scratchcards.size(), 1));

	if (n == 2) {

	    /**
	     * --- Part Two --- Just as you're about to report your findings to the Elf, one
	     * of you realizes that the rules have actually been printed on the back of
	     * every card this whole time.
	     * 
	     * There's no such thing as "points". Instead, scratchcards only cause you to
	     * win more scratchcards equal to the number of winning numbers you have.
	     * 
	     * Specifically, you win copies of the scratchcards below the winning card equal
	     * to the number of matches. So, if card 10 were to have 5 matching numbers, you
	     * would win one copy each of cards 11, 12, 13, 14, and 15.
	     * 
	     * Copies of scratchcards are scored like normal scratchcards and have the same
	     * card number as the card they copied. So, if you win a copy of card 10 and it
	     * has 5 matching numbers, it would then win a copy of the same cards that the
	     * original card 10 won: cards 11, 12, 13, 14, and 15. This process repeats
	     * until none of the copies cause you to win any more cards. (Cards will never
	     * make you copy a card past the end of the table.)
	     * 
	     * This time, the above example goes differently:
	     * 
	     * Card 1 has four matching numbers, so you win one copy each of the next four
	     * cards: cards 2, 3, 4, and 5. Your original card 2 has two matching numbers,
	     * so you win one copy each of cards 3 and 4. Your copy of card 2 also wins one
	     * copy each of cards 3 and 4. Your four instances of card 3 (one original and
	     * three copies) have two matching numbers, so you win four copies each of cards
	     * 4 and 5. Your eight instances of card 4 (one original and seven copies) have
	     * one matching number, so you win eight copies of card 5. Your fourteen
	     * instances of card 5 (one original and thirteen copies) have no matching
	     * numbers and win no more cards. Your one instance of card 6 (one original) has
	     * no matching numbers and wins no more cards. Once all of the originals and
	     * copies have been processed, you end up with 1 instance of card 1, 2 instances
	     * of card 2, 4 instances of card 3, 8 instances of card 4, 14 instances of card
	     * 5, and 1 instance of card 6. In total, this example pile of scratchcards
	     * causes you to ultimately have 30 scratchcards!
	     * 
	     * Process all of the original and copied scratchcards until no more
	     * scratchcards are won. Including the original set of scratchcards, how many
	     * total scratchcards do you end up with?
	     */
	    System.out.println(scratchcards);
	    System.out.println(nrCards);
	    for (int i = 0; i < scratchcards.size(); i++) {
		ScratchCard sc = scratchcards.get(i);
		for (int j = i + 1; j <= i + sc.getMatches() && j < nrCards.size(); j++) {
		    nrCards.set(j, nrCards.get(j) + nrCards.get(i));
		}
	    }

	    for (Integer i : nrCards) {
		result += i;
	    }
	}
	solver.add(result);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
