package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day7 extends Day {
    List<Long> solver = new ArrayList<Long>();
    public Map<Character, Integer> allCardsTwo = Map.ofEntries(Map.entry('A', 14), Map.entry('K', 13),
	    Map.entry('Q', 12), Map.entry('J', 0), Map.entry('T', 10), Map.entry('9', 9), Map.entry('8', 8),
	    Map.entry('7', 7), Map.entry('6', 6), Map.entry('5', 5), Map.entry('4', 4), Map.entry('3', 3),
	    Map.entry('2', 2));

    public Map<Character, Integer> allCards = Map.ofEntries(Map.entry('A', 14), Map.entry('K', 13), Map.entry('Q', 12),
	    Map.entry('J', 11), Map.entry('T', 10), Map.entry('9', 9), Map.entry('8', 8), Map.entry('7', 7),
	    Map.entry('6', 6), Map.entry('5', 5), Map.entry('4', 4), Map.entry('3', 3), Map.entry('2', 2));

    private class CardTwo extends Card {

	private Integer getValue() {
	    return allCardsTwo.get(super.c);
	}

	public CardTwo(Character c) {
	    super(c);
	}

	@Override
	public int compareTo(Card o) {
	    if (o instanceof CardTwo) {
		CardTwo other = (CardTwo) o;
		return this.getValue().compareTo(other.getValue());
	    }
	    return super.compareTo(o);
	}

    }

    private class Card implements Comparable<Card> {

	private Character c;

	public Card(Character c) {
	    this.c = c;
	}

	private Integer getValue() {
	    return allCards.get(c);
	}

	public String toString() {
	    return this.c.toString();
	}

	@Override
	public int compareTo(Card o) {
	    return getValue().compareTo(o.getValue());
	}

	@Override
	public boolean equals(Object o) {
	    if (this.getClass() != o.getClass())
		return false;
	    return (this.compareTo((Card) o) == 0);
	}
    }

    private class HandTwo extends Hand {
	private String cards;

	public HandTwo(String cards, Long l) {
	    super(cards, l);
	    this.cards = cards;

	    Hand originalHand = new Hand(cards, l);
	    Hand bestHand = originalHand;
	    if (cards.contains("J")) {
		// there are jokers
		for (Entry<Character, Integer> cardEntry : allCardsTwo.entrySet()) {
		    if (cardEntry.getKey() != 'J') {
			Hand testHand = new Hand(cards.replace('J', cardEntry.getKey()), l);
			if (testHand.compareTo(bestHand) > 0)
			    bestHand = testHand;
		    }
		}
	    }
	    super.setCards(bestHand.getCards());
	}

	public String getCards() {
	    return this.cards;
	}

	@Override
	public int compareTo(Hand o) {
	    HandTwo other = (HandTwo) o;
	    Boolean debug = (("T55J5".equals(cards) && "QQQJA".equals(o.cards))
		    || ("T55J5".equals(o.cards) && "QQQJA".equals(cards))
		    || ("KK677".equals(cards) || "KK677".equals(o.cards)));
	    if (debug) {
		System.out.println("Comparing!");
		System.out.println("My cards: " + cards);
		System.out.println("Other cards: " + other.cards);
		System.out.println("My type: " + getType());
		System.out.println("Other type: " + other.getType());
		System.out.println("Types CompareTo: " + getType().compareTo(other.getType()));
	    }
	    if (this.getType() == o.getType()) {
		// we have the same hand type, let's see what we can do in terms of value....
		for (int i = 0; i < cards.length(); i++) {
		    CardTwo myCard = new CardTwo(getCards().charAt(i));
		    CardTwo oCard = new CardTwo(other.getCards().charAt(i));
		    if (!myCard.equals(oCard))
			return myCard.compareTo(oCard);
		}
	    }
	    return this.getType().compareTo(other.getType());
	}

	public String toString() {
	    return this.cards + " as (" + super.getCards() + ")";
	}

    } // HandTwo

    private class Hand implements Comparable<Hand> {
	private String cards;
	Long bet;
	Map<Card, Integer> typeCount;

	public String getCards() {
	    return this.cards;
	}

	public void setCards(String cards) {
	    this.cards = cards;
	    this.typeCount = new TreeMap<Card, Integer>();
	    for (int i = 0; i < cards.length(); i++) {
		Card card = new Card(cards.charAt(i));
		int count = 0;
		if (typeCount.containsKey(card))
		    count = typeCount.get(card);
		typeCount.put(card, count + 1);
	    }
	}

	public Hand(String cards, Long l) {
	    this.bet = l;
	    setCards(cards);
	}

	public Boolean isFiveOfAKind() {
	    return typeCount.values().contains(5);
	}

	public Boolean isFourOfAKind() {
	    return typeCount.values().contains(4);
	}

	public Boolean isFullHouse() {
	    return typeCount.values().contains(3) && typeCount.values().contains(2);
	}

	public Boolean isThreeOfAKind() {
	    return typeCount.values().contains(3);
	}

	public Boolean isTwoPair() {
	    return Collections.frequency(typeCount.values(), 2) == 2;
	}

	public Boolean isOnePair() {
	    return Collections.frequency(typeCount.values(), 2) == 1;
	}

	public Boolean isHighCard() {
	    return Collections.frequency(typeCount.values(), 1) == 5;
	}

	public Integer getType() {
	    if (isFiveOfAKind())
		return 7;
	    if (isFourOfAKind())
		return 6;
	    if (isFullHouse())
		return 5;
	    if (isThreeOfAKind())
		return 4;
	    if (isTwoPair())
		return 3;
	    if (isOnePair())
		return 2;
	    if (isHighCard())
		return 1;
	    return -1;
	}

	public String toString() {
	    return this.cards;
	}

	@Override
	public int compareTo(Hand o) {
	    if (this.getType() == o.getType()) {
		// we have the same hand type, let's see what we can do in terms of value....
		for (int i = 0; i < cards.length(); i++) {
		    Card myCard = new Card(cards.charAt(i));
		    Card oCard = new Card(o.cards.charAt(i));
		    if (!myCard.equals(oCard))
			return myCard.compareTo(oCard);
		}
	    }
	    return this.getType().compareTo(o.getType());
	}

	public Long getBet() {
	    return bet;
	}
    }

    private void solve1() {
	List<String> fileContents = getFileContents(input);
	Long winnings = 0L;
	List<Hand> hands = new ArrayList<Hand>();
	for (String line : fileContents) {
	    String[] play = line.split(" ");
	    Hand h = new Hand(play[0], Long.parseLong(play[1]));
	    hands.add(h);
	}

	Collections.sort(hands);

	Long i = 0L;
	for (Hand h : hands) {
	    i++;
	    Long rank = i;
	    winnings += (rank * h.getBet());
	    System.out.println(
		    "Hand " + h + ", rank " + rank + ", bet: " + h.getBet() + ", totalWinnings (" + winnings + ")");
	}
	solver.add(winnings);
    }

    private void solve2() {
	List<String> fileContents = getFileContents(input);
	Long winnings = 0L;
	List<HandTwo> hands = new ArrayList<HandTwo>();
	for (String line : fileContents) {
	    String[] play = line.split(" ");
	    HandTwo h = new HandTwo(play[0], Long.parseLong(play[1]));
	    hands.add(h);
	}

	Collections.sort(hands);

	Long i = 0L;
	for (Hand h : hands) {
	    i++;
	    Long rank = i;
	    winnings += (rank * h.getBet());
	    System.out.println(
		    "Hand " + h + ", rank " + rank + ", bet: " + h.getBet() + ", totalWinnings (" + winnings + ")");
	}
	solver.add(winnings);

    }

    @Override
    public void solve(int n) {
	switch (n) {
	case 1:
	    solve1();
	    break;
	case 2:
	    solve2();
	    break;
	}
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
