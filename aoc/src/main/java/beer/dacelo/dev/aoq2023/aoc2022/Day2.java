package beer.dacelo.dev.aoq2023.aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day2 extends Day {

    List<Integer> solver = new ArrayList<Integer>();
    Map<String, Integer> scores = new HashMap<String, Integer>();

    private class Choice implements Comparable<Choice> {
	private Integer choiceValue;
	private String choiceName;
	private char choiceChar;

	Choice(char c) {
	    switch (c) {
	    case 'A':
	    case 'X':
		setChoiceChar('A');
		setChoiceName("ROCK");
		setChoiceValue(1);
		break;
	    case 'B':
	    case 'Y':
		setChoiceChar('B');
		setChoiceName("PAPER");
		setChoiceValue(2);
		break;
	    case 'C':
	    case 'Z':
		setChoiceChar('C');
		setChoiceName("SCISSORS");
		setChoiceValue(3);
		break;
	    }
	}

	public Choice getDesiredOutcome(char outcome) {
	    // A = Rock
	    // B = Paper
	    // C = Scissors
	    switch (outcome) {
	    case 'X': // X = I must LOSE
		switch (this.getChoiceName()) {
		case "ROCK":
		    return new Choice('C');
		case "PAPER":
		    return new Choice('A');
		case "SCISSORS":
		    return new Choice('B');
		}
		break;
	    case 'Y':
		return new Choice(this.getChoiceChar());
	    case 'Z': // Z = I must WIN
		switch (this.getChoiceName()) {
		case "ROCK":
		    return new Choice('B');
		case "PAPER":
		    return new Choice('C');
		case "SCISSORS":
		    return new Choice('A');
		}
		break;
	    }
	    return null;
	}

	@Override
	public int compareTo(Choice o) {
	    if (this.getChoiceName().equals(o.getChoiceName()))
		return 0;
	    if (("ROCK".equals(this.getChoiceName()) && "SCISSORS".equals(o.getChoiceName()))
		    || ("PAPER".equals(this.getChoiceName()) && "ROCK".equals(o.getChoiceName()))
		    || ("SCISSORS".equals(this.getChoiceName()) && "PAPER".equals(o.getChoiceName()))) {
		return 1;
	    }
	    return -1;
	}

	public Integer getChoiceValue() {
	    return choiceValue;
	}

	public void setChoiceValue(Integer choiceValue) {
	    this.choiceValue = choiceValue;
	}

	public String getChoiceName() {
	    return choiceName;
	}

	public void setChoiceName(String string) {
	    this.choiceName = string;
	}

	public String toString() {
	    return this.getChoiceName();
	}

	public char getChoiceChar() {
	    return choiceChar;
	}

	public void setChoiceChar(char choiceChar) {
	    this.choiceChar = choiceChar;
	}
    }

    private static int outcome(Choice myChoice, Choice opChoice) {
	int WIN = 6, DRAW = 3, LOSS = 0;

	switch (myChoice.compareTo(opChoice)) {
	case -1:
	    System.out.println(myChoice + " loses to " + opChoice);
	    return LOSS;
	case 0:
	    System.out.println(myChoice + " draws with " + opChoice);
	    return DRAW;
	case 1:
	    System.out.println(myChoice + " beats " + opChoice);
	    return WIN;
	}
	return -1;
    }

    private Map<String, Integer> parse(String line) {
	// A = op chooses Rock
	// B = op chooses Paper
	// C = op chooses Scissors

	// X = I choose Rock
	// Y = I choose Paper
	// Z = I choose Scissors

	Map<String, Integer> result = new HashMap<String, Integer>();
	int myScore = 0;
	int opScore = 0;

	Choice myChoice = new Choice(line.charAt(line.length() - 1));
	Choice opChoice = new Choice(line.charAt(0));

	System.out.println(line);
	myScore += myChoice.getChoiceValue();
	System.out.println(myChoice.getChoiceName() + ": " + myChoice.getChoiceValue());
	opScore += opChoice.getChoiceValue();

	myScore += outcome(myChoice, opChoice);
	System.out.println(outcome(myChoice, opChoice));
	opScore += 6 - outcome(myChoice, opChoice);

	result.put("me", myScore);
	result.put("op", opScore);

	return result;
    }

    private Map<String, Integer> parse2(String line) {
	// A = op chooses Rock
	// B = op chooses Paper
	// C = op chooses Scissors

	// X = I must LOSE
	// Y = I must DRAW
	// Z = I must WIN

	Map<String, Integer> result = new HashMap<String, Integer>();

	int myScore = 0;
	int opScore = 0;

	Choice opChoice = new Choice(line.charAt(0));

	char requiredOutcome = line.charAt(line.length() - 1);
	Choice myChoice = opChoice.getDesiredOutcome(requiredOutcome);

	System.out.println(line);
	myScore += myChoice.getChoiceValue();
	System.out.println(myChoice.getChoiceName() + ": " + myChoice.getChoiceValue());
	opScore += opChoice.getChoiceValue();

	myScore += outcome(myChoice, opChoice);
	System.out.println(outcome(myChoice, opChoice));
	opScore += 6 - outcome(myChoice, opChoice);

	result.put("me", myScore);
	result.put("op", opScore);

	return result;
    }

    @Override
    public void solve(int n) {
	detail = new ArrayList<String>();
	for (String line : getFileContents(input)) {
	    Map<String, Integer> roundScore = null;
	    switch (n) {
	    case 1:
		roundScore = parse(line);
		break;
	    case 2:
		roundScore = parse2(line);
		break;
	    }
	    Integer myScore = scores.get("me");
	    Integer opScore = scores.get("op");
	    if (myScore == null)
		myScore = 0;
	    if (opScore == null)
		opScore = 0;
	    myScore += roundScore.get("me");
	    opScore += roundScore.get("op");

	    detail.add(line + " (" + roundScore.get("me") + " -> " + myScore + ")");

	    scores.put("me", myScore);
	    scores.put("op", opScore);
	}
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	switch (n) {
	case 1:
	case 2:
	    solution.add(Integer.toString(scores.get("me")));
	    break;
	}
	return solution;
    }
}
