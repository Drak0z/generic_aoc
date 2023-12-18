package beer.dacelo.dev.aoq2023.aoq2023;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day16 extends Day {
    /**
     * Day 16: Unicode Unicorns
     * 
     * We have gotten access, so we are told, to Sedrick's favorite number. He's
     * gone and hidden it in the number of lines matching the pattern below. Why is
     * this important? Maybe it's the key to his bank account, a passcode to his
     * online profile, a glimpse into his deep dark soul or just plain worthless. We
     * don't know but orders have come from the top! We need this number! The
     * problem? It's really hidden! How you ask? He hasn't made it easy... Here are
     * his notes that we have found:
     * 
     * Facts about sequences: Sequences are made up of only these characters: Group
     * 1: ✿✲✴✶✧✹✺✣✫ and Group 2: ★●✦ Sequences have at least one character from each
     * group. Sequences do not start with multiple consecutive Group 2 characters. ●
     * does not appear next to Group 1 characters or itself. Sequences do not
     * contain more than 3 Group 2 characters in a row. These characters: ✲✴✶✹✺
     * never appear next a Group 1 character. In other words none of ✿✲✴✶✧✹✺✣✫ may
     * be next to ✲✴✶✹✺. ✿ never immediately follows a Group 1 character. ✧ and ✣
     * only appear next to ✿. Sequences do not end with ✶ or ✫. Question Count how
     * many lines in this file are valid sequences according to these rules and we
     * will have Sedrick's favorite number!
     * 
     * (the file is in UTF-8 encoding)
     */
    private enum Characters {
	FLOWER(10047), SIX_SPOKE(10034), SQUARED_STAR(10036), SIX_POINT_STAR(10038), OPEN_FOUR_POINT_STAR(10023),
	ELEVEN_POINT_STAR(10041), MANY_RAY_SUN(10042), CROSS(10019), OPEN_FIVE_POINT_STAR(10027), FIVE_POINT_STAR(9733),
	CIRCLE(9679), FOUR_POINT_STAR(10022);

	private final int value;

	private Characters(int value) {
	    this.value = value;
	}

	public Character getCharacter() {
	    return (char) this.value;
	}
    }

    private List<Characters> groupOne = List.of(Characters.FLOWER, Characters.SIX_SPOKE, Characters.SQUARED_STAR,
	    Characters.SIX_POINT_STAR, Characters.OPEN_FOUR_POINT_STAR, Characters.ELEVEN_POINT_STAR,
	    Characters.MANY_RAY_SUN, Characters.CROSS, Characters.OPEN_FIVE_POINT_STAR);
    private List<Characters> groupTwo = List.of(Characters.FIVE_POINT_STAR, Characters.CIRCLE,
	    Characters.FOUR_POINT_STAR);

    private boolean partOfGroup(Character c, List<Characters> group) {
	for (Characters x : group) {
	    if (c.equals(x.getCharacter()))
		return true;
	}
	return false;
    }

    private boolean partOfGroupOne(Character c) {
	return partOfGroup(c, groupOne);
    }

    private boolean partOfGroupTwo(Character c) {
	return partOfGroup(c, groupTwo);
    }

    private boolean hasInvalidNeighbour(Integer i, List<Characters> disallowed, String line) {
	Character left = null, right = null;
	if (i > 0) {
	    left = line.charAt(i - 1);
	    if (partOfGroup(left, disallowed))
		return true;
	}
	if (i < line.length() - 1) {
	    right = line.charAt(i + 1);
	    if (partOfGroup(right, disallowed))
		return true;
	}
	return false;
    }

    // Sequences are made up of only these characters (Group 1: ✿✲✴✶✧✹✺✣✫), (Group
    // 2: ★●✦)
    private boolean factOne(String line) {
	for (Character c : line.toCharArray()) {
	    if (!(partOfGroupOne(c) || partOfGroupTwo(c)))
		return false;
	}
	return true;
    } // factOne

    // Sequences are made up of only these characters (Group 1: ✿✲✴✶✧✹✺✣✫), (Group
    // 2: ★●✦)
    private String factOneToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    if (partOfGroupOne(line.charAt(i)) || partOfGroupTwo(line.charAt(i))) {
		output.append("Y");
	    } else {
		output.append("N");
	    }
	}
	return output.toString();
    } // factOneToString

    // Sequences have at least one character from each group
    private boolean factTwo(String line) {
	Integer groupOneCount = 0, groupTwoCount = 0;
	for (Character c : line.toCharArray()) {
	    if (partOfGroupOne(c))
		groupOneCount++;
	    if (partOfGroupTwo(c))
		groupTwoCount++;
	}
	return (groupOneCount > 0 && groupTwoCount > 0);
    } // factTwo

    // Sequences have at least one character from each group
    private String factTwoToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    if (partOfGroupOne(line.charAt(i))) {
		output.append("1");
	    } else if (partOfGroupTwo(line.charAt(i))) {
		output.append("2");
	    } else {
		output.append(".");
	    }
	}
	return output.toString();
    } // factTwoToString

    // Sequences do not start with multiple consecutive Group 2 characters
    private boolean factThree(String line) {
	if (line.length() < 2)
	    return false;
	return !(partOfGroupTwo(line.charAt(0)) && partOfGroupTwo(line.charAt(1)));
    } // factThree

    // Sequences do not start with multiple consecutive Group 2 characters
    private String factThreeToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    if (i < 2)
		if (partOfGroupTwo(line.charAt(i))) {
		    output.append("2");
		} else if (partOfGroupOne(line.charAt(i))) {
		    output.append("1");
		} else {
		    output.append(".");
		}
	}
	return output.toString();
    } // factThreeToString

    // ● does not appear next to Group 1 characters or itself
    private boolean factFour(String line) {
	List<Characters> disallowed = new ArrayList<Characters>(groupOne);
	disallowed.add(Characters.CIRCLE);
	for (int i = 0; i < line.length(); i++) {
	    if (Characters.CIRCLE.getCharacter().equals(line.charAt(i)) && hasInvalidNeighbour(i, disallowed, line))
		return false;
	}
	return true; // no circle is next to another circle or a group 1 character
    } // factFour

    // ● does not appear next to Group 1 characters or itself
    private String factFourToString(String line) {
	List<Characters> disallowed = new ArrayList<Characters>(groupOne);
	disallowed.add(Characters.CIRCLE);
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    output.append(".");
	}
	for (int i = 0; i < line.length(); i++) {
	    if (Characters.CIRCLE.getCharacter().equals(line.charAt(i))) {
		output.replace(i, i + 1, "" + Characters.CIRCLE.getCharacter());
		if (i > 0 && partOfGroup(line.charAt(i - 1), disallowed))
		    output.replace(i - 1, i, "!");
		if (i < line.length() - 1 && partOfGroup(line.charAt(i + 1), disallowed))
		    output.replace(i + 1, i + 2, "!");
	    }
	}
	return output.toString();
    } // factFourtoString

    // Sequences do not contain more than 3 Group 2 characters in a row
    private boolean factFive(String line) {
	Integer sequentialGroupTwo = 0;
	for (Character c : line.toCharArray()) {
	    if (partOfGroupTwo(c)) {
		sequentialGroupTwo++;
		if (sequentialGroupTwo > 3)
		    return false;
	    } else {
		sequentialGroupTwo = 0;
	    }
	}
	return true;
    } // factFive

    // Sequences do not contain more than 3 Group 2 characters in a row
    private String factFiveToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    output.append(".");
	}
	Integer sequentialGroupTwo = 0;
	for (Integer i = 0; i < line.length(); i++) {
	    Character c = line.charAt(i);
	    if (partOfGroupTwo(c)) {
		sequentialGroupTwo++;
		output.replace(i, i + 1, sequentialGroupTwo.toString());
		if (sequentialGroupTwo > 3) {
		    output.replace(i, i + 1, "!");
		    return output.toString();
		}
	    } else {
		sequentialGroupTwo = 0;
	    }
	}
	return output.toString();
    } // factFive

    // ✲✴✶✹✺ may never be next to group one
    private boolean factSix(String line) {
	List<Characters> searchFor = List.of(Characters.SIX_SPOKE, Characters.SQUARED_STAR, Characters.SIX_POINT_STAR,
		Characters.ELEVEN_POINT_STAR, Characters.MANY_RAY_SUN);
	for (int i = 0; i < line.length(); i++) {
	    if (partOfGroup(line.charAt(i), searchFor) && hasInvalidNeighbour(i, groupOne, line))
		return false;
	}
	return true;
    } // factSix

    // ✲✴✶✹✺ may never be next to group one
    private String factSixToString(String line) {
	List<Characters> searchFor = List.of(Characters.SIX_SPOKE, Characters.SQUARED_STAR, Characters.SIX_POINT_STAR,
		Characters.ELEVEN_POINT_STAR, Characters.MANY_RAY_SUN);
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    output.append(".");
	}
	for (int i = 0; i < line.length(); i++) {
	    if (partOfGroup(line.charAt(i), searchFor)) {
		output.replace(i, i + 1, "" + line.charAt(i));
		if (i > 0 && partOfGroup(line.charAt(i - 1), groupOne))
		    output.replace(i - 1, i, "!");
		if (i < line.length() - 1 && partOfGroup(line.charAt(i + 1), groupOne))
		    output.replace(i + 1, i + 2, "!");
	    }
	}
	return output.toString();
    } // factSixToString

    // A flower may not follow a group one character
    private boolean factSeven(String line) {
	for (int i = 1; i < line.length(); i++) {
	    if (Characters.FLOWER.getCharacter().equals(line.charAt(i)) && (partOfGroup(line.charAt(i - 1), groupOne)))
		return false;
	}
	return true;
    } // factSeven

    // A flower may not follow a group one character
    private String factSevenToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    Character c = line.charAt(i);
	    if (c.equals(Characters.FLOWER.getCharacter())) {
		output.append(Characters.FLOWER.getCharacter() + "");
		if (i > 0 && partOfGroup(line.charAt(i - 1), groupOne)) {
		    output.replace(i - 1, i, "!");
		}
	    } else {
		output.append(".");
	    }
	}
	return output.toString();
    } // factSevenToString

    // ✧ and ✣ only appear next to ✿
    private boolean factEight(String line) {
	for (int i = 0; i < line.length(); i++) {
	    Character c = line.charAt(i);
	    if (c.equals(Characters.OPEN_FOUR_POINT_STAR.getCharacter()) || c.equals(Characters.CROSS.getCharacter())) {
		if (!((i > 0 && Characters.FLOWER.getCharacter().equals(line.charAt(i - 1)))
			|| (i < line.length() - 1 && Characters.FLOWER.getCharacter().equals(line.charAt(i + 1)))))
		    return false;
	    }
	}
	return true;
    } // factEight

    // ✧ and ✣ only appear next to ✿
    private String factEightToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    output.append(".");
	}

	for (int i = 0; i < line.length(); i++) {
	    Character c = line.charAt(i);
	    if (c.equals(Characters.OPEN_FOUR_POINT_STAR.getCharacter()) || c.equals(Characters.CROSS.getCharacter())) {
		output.replace(i, i + 1, c.toString());
		if (i > 0 && !Characters.FLOWER.getCharacter().equals(line.charAt(i - 1))) {
		    output.replace(i - 1, i, "!");
		}
		if (i < line.length() - 1 && !Characters.FLOWER.getCharacter().equals(line.charAt(i + 1))) {
		    output.replace(i + 1, i + 2, "!");
		}
	    }
	}
	return output.toString();
    } // factEight

    // Sequences do not end with ✶ or ✫
    private boolean factNine(String line) {
	List<Characters> disallowed = List.of(Characters.SIX_POINT_STAR, Characters.OPEN_FIVE_POINT_STAR);
	if (partOfGroup(line.charAt(line.length() - 1), disallowed))
	    return false;
	return true;
    } // factNine

    // Sequences do not end with ✶ or ✫
    private String factNineToString(String line) {
	StringBuilder output = new StringBuilder();
	for (int i = 0; i < line.length(); i++) {
	    output.append(".");
	}

	List<Characters> disallowed = List.of(Characters.SIX_POINT_STAR, Characters.OPEN_FIVE_POINT_STAR);
	if (partOfGroup(line.charAt(line.length() - 1), disallowed))
	    output.replace(line.length() - 1, line.length(), "!");
	return output.toString();
    } // factNine

    @Override
    public void solve(int n) {
	Long countValidSequences = 0L;
	for (String line : getFileContents(input)) {
	    detail.add("Analysing sequence: [" + line + "]");
	    detail.add("  Fact 1, Sequences are made up of only these characters (Group 1: ✿✲✴✶✧✹✺✣✫), (Group 2: ★●✦): "
		    + factOne(line) + " [" + factOneToString(line) + "]");
	    detail.add("  Fact 2, Sequences have at least one character from each group: " + factTwo(line) + " ["
		    + factTwoToString(line) + "]");
	    detail.add("  Fact 3, Sequences do not start with multiple consecutive Group 2 characters: "
		    + factThree(line) + " [" + factThreeToString(line) + "]");
	    detail.add("  Fact 4, ● does not appear next to Group 1 characters or itself:" + factFour(line) + " ["
		    + factFourToString(line) + "]");
	    detail.add("  Fact 5, Sequences do not contain more than 3 Group 2 characters in a row: " + factFive(line)
		    + " [" + factFiveToString(line) + "]");
	    detail.add("  Fact 6, These characters: ✲✴✶✹✺ never appear next a Group 1 character: " + factSix(line)
		    + " [" + factSixToString(line) + "]");
	    detail.add("  Fact 7, ✿ never immediately follows a Group 1 character: " + factSeven(line) + " ["
		    + factSevenToString(line) + "]");
	    detail.add("  Fact 8, ✧ and ✣ only appear next to ✿: " + factEight(line) + " [" + factEightToString(line)
		    + "]");
	    detail.add("  Fact 9, Sequences do not end with ✶ or ✫: " + factNine(line) + " [" + factNineToString(line)
		    + "]");
	    if (factOne(line) && factTwo(line) && factThree(line) && factFour(line) && factFive(line)
		    && factSix(line) && factSeven(line) && factEight(line) && factNine(line)) {
		countValidSequences++;
		detail.add("  ✅ : This is a valid sequence! [Count: " + countValidSequences + "]"
			+ System.lineSeparator());

	    } else {
		detail.add("  ❌ : This is NOT a valid sequence" + System.lineSeparator());
	    }
	}
	solver.add(countValidSequences.toString());
    } // solve
}
