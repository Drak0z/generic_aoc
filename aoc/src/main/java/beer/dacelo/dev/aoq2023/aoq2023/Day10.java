package beer.dacelo.dev.aoq2023.aoq2023;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day10 extends Day {
    /**
     * Day 10: Weighty Ideas
     * 
     * Even though he looks like a one month old pile of sucrose, sometimes Sedrick
     * starts talking like a grizzled old-timer. Today he's ranting about weighing
     * things.
     * 
     * "Kids these days with their pounds and grams, it doesn't make sense!" he
     * shouts.
     * 
     * "Back in my day we would weigh everything in sturracs, and we liked it fine!
     * You knew what you had with a sturrac of something."
     * 
     * Sedrick is really getting going now.
     * 
     * "If you needed a smaller weight, that was easier, too! Everyone knew there
     * was twenty nine ristoids in a sturrac. It just made sense. And bigger weights
     * were even better back then!" he continues, increasing in volume.
     * 
     * "These days everyone talks about tons. What even is a ton? Is it different
     * than a tonne? Who cares? When you saw a sign that says 'weight: 1 hodlich'
     * you would actually understand what it's talking about. You would know it was
     * exactly one hundred and seventeen and a half sturracs plus a ristoid for good
     * measure."
     * 
     * Sedrick sloshes away, shaking something approximating a head.
     * 
     * Question We have a file full of weights in Sedrick's crazy units, with metric
     * prefixes. (Don't tell Sedrick about the metric system, it would just raise
     * his blob pressure.)
     * 
     * Please convert all the weights to grams (1 sturrac = 40 grams) and submit the
     * total rounded to the nearest gram.
     */
    List<String> solver = new ArrayList<String>();

    private Double convertToGrams(Double d, String uom) {
	// 40 grams = 1 sturrac (s)
	// 1 sturrac = 29 ristroids (r)
	// 117.5 sturracs + 1 ristoid = 1 hodlich (h)
	Character baseUnit = uom.charAt(uom.length() - 1);
	String siPrefix = "";
	Double sturracs = 0.0, ristroids = 0.0, hodlich = 0.0;
	System.out.println(d + " " + uom);
	if (uom.length() > 1) {
	    siPrefix = uom.substring(0, uom.length() - 1);
	}

	Double multiplier = Util.getSIMultiplier(siPrefix);

	switch (baseUnit) {
	case 'h':
	    hodlich = d * multiplier;
	    break;
	case 'r':
	    ristroids = d * multiplier;
	    break;
	case 's':
	    sturracs = d * multiplier;
	    break;
	}

	if (hodlich > 0) {
	    sturracs += (hodlich * 117.5);
	    ristroids += (hodlich);
	    hodlich = 0.0;
	}
	System.out.println(hodlich + " --> Contains: " + sturracs + "s, and " + ristroids + "r");

	if (ristroids > 0) {
	    sturracs += ristroids * 1 / 29;
	    ristroids = 0.0;
	}
	System.out.println(ristroids + " --> Contains: " + sturracs + "s");

	Double grams = sturracs * 40;

	System.out.println(sturracs + " --> Contains: " + grams + "g");
	System.out.println(d + " => siPrefix: " + siPrefix + ", Base: " + baseUnit + "  == " + grams + " g");
	return grams;
    }

    @Override
    public void solve(int n) {
	System.out.println(convertToGrams(1.0, "s"));
	System.out.println(convertToGrams(1.0, "r"));
	System.out.println(convertToGrams(1.0, "h"));
	Set<String> uoms = new HashSet<String>();
	Double result = 0.0;
	for (String line : getFileContents(input)) {
	    String[] input = line.split(" ");
	    Double weight = Double.parseDouble(input[0]);
	    String uom = input[1];
	    uoms.add(uom);
	    result += convertToGrams(weight, uom);
	    System.out.println("Running result: " + result);
	}
	System.out.println(uoms);
	System.out.println(result);
	DecimalFormat df = new DecimalFormat("#");
	df.setRoundingMode(RoundingMode.HALF_UP);
	solver.add(df.format(result));
    } // solve

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    } // getSolution
}
