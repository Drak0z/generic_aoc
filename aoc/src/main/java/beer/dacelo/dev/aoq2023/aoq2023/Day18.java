package beer.dacelo.dev.aoq2023.aoq2023;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day18 extends Day {
    /*-
    Day 18: Cookie Break
    All this Blob fighting is making the elves hungry, so in an act of charity rarely afforded to, well anyone, Robot Prime is allowing them one evening to bake their favorite snack... Cookies!!! Unfortunately, the elves have just been throwing salvaged candy (from the Sedrick-fragmentation event) into random places in the pantry so collecting the ingredients is gonna take some effort, especially since Elves are infamous for overly complicated cookie recipes!
    
    The pantry layout is described like this:
    *//*-
    	[A, B, C, D, DOOR, E, F]
    *//*
   * The Elf must enter and exit the pantry through the DOOR and move either left
   * or right to collect the ingredients. Ingredients must be collected in order!
   * We need to know the minimum number of total steps it will take to collect the
   * ingredients.
   * 
   * For example:
   *//*-
        If the required ingredients were just [D], it would take two steps (DOOR > D, D > DOOR)
        If the required ingredients were [D, C, C, B], it would take six steps (DOOR > D, D > C (pick up two ingredients here), C > B, B > C > D > DOOR)
        If the required ingredients were [F, A], it would take six steps (DOOR>E>F, F>A, A>F>E>DOOR) (NOTE: the pantry is circular so we can move directly between A and F)
    *//*
   * The actual pantry contents are as follows:
   *//*-
        Chameleon chips (color-changing chocolate)
        Yeti flakes (white chocolate shavings)
        Interstellar icing sugar
        Aurora borealis glaze
        Fairy floss fragments
        Pirate's rum raisins
        Witch's wart chocolate chips
        Dragonfruit chunks
        Wizard's beard (cotton candy pieces)
        Stardust sugar
        Quantum quinoa crunch
        Phoenix feathers (shredded coconut)
        Phoenix feathers (shredded coconut)
        Rocket raspberry ribbons
        Merlin's mix (mixed nuts and seeds)
        Miniature chocolate spaceships
        Goblin goo (green tea matcha)
        Miniature chocolate spaceships
        Yeti flakes (white chocolate shavings)
        Cactus confetti
        Quantum quinoa crunch
        Yeti flakes (white chocolate shavings)
        Merlin's mix (mixed nuts and seeds)
        Vortex vanilla swirls
        Sasquatch snacks (dried berries)
        Quantum quinoa crunch
        Jupiter jams
        Crystalized honey from Venus
        Glow-in-the-dark frosting
        Galactic grape jelly
        Meteorite morsels
        Jupiter jams
        Yeti flakes (white chocolate shavings)
        Interstellar icing sugar
        Saturn syrup swirls
        Time-traveling toffee bits
        Aurora borealis glaze
        Pirate's rum raisins
        Cactus confetti
        Time-traveling toffee bits
        Caramelized comet crumbs
        Supernova sugar crystals
        Nebula nuts
        Nebula nuts
        Rocket raspberry ribbons
        Interstellar icing sugar
        Pirate's rum raisins
        DOOR
        Dragonfruit chunks
        Pirate's rum raisins
        Zombie zest (lime peel)
    *//*
   * The recipe for Glowing Chocolate Wart Zest Crystal Cookies is:
   *//*-
        Witch's wart chocolate chips
        Glow-in-the-dark frosting
        Dragonfruit chunks
        Phoenix feathers (shredded coconut)
        Phoenix feathers (shredded coconut)
        Zombie zest (lime peel)
        Rocket raspberry ribbons
        Zombie zest (lime peel)
        Saturn syrup swirls
        Wizard's beard (cotton candy pieces)
        Wizard's beard (cotton candy pieces)
        Crystalized honey from Venus
        Glow-in-the-dark frosting
        Rocket raspberry ribbons
        Zombie zest (lime peel)
        Stardust sugar
        Caramelized comet crumbs
        Stardust sugar
        Goblin goo (green tea matcha)
        Rocket raspberry ribbons
    *//*
   * What is the shortest number of "steps" to collect all the ingredients
   * required to make these cookies (integer, no punctuation).
   */
    private class Pantry {
	List<String> layout;
	String myIngredient;
	Integer myPosition = null;

	public Pantry() {
	    layout = new ArrayList<String>();
	    myIngredient = "DOOR"; // we start at door
	} // Pantry

	public void addSpace(String space) {
	    layout.add(space);
	} // addSpace

	private Long minDistance(Long distance, int position, LinkedList<String> recipe, Long foundMinDistance) {
	    if (recipe.size() == 0) {
		// System.out.println(" *** found an end, returning distance: " + distance);
		return distance;
	    }
	    if (distance > foundMinDistance) {
		// We don't have to explore further, since we have a path that's shorter
		return Long.MAX_VALUE;
	    }

	    String ingredient = recipe.removeFirst();
	    Long minDistance = Math.min(Long.MAX_VALUE, foundMinDistance);
	    int[] positions = IntStream.range(0, layout.size()).filter(i -> layout.get(i).equals(ingredient)).toArray();
	    // System.out.println("For: " + ingredient + ": this many positions to explore:
	    // " + positions.length);

	    // For fun, I should be able to make this bit here multithreaded, getting some
	    // more use out of my 20-core system
	    // without multithreading, this takes:
	    for (int newPos : positions) {
		Long directDelta = Math.abs(0L + position - newPos);
		// We can be circular... so let's also take into account distances to the edge
		Long circularDelta = 0L + Math.min(position, newPos) + layout.size() - Math.max(position, newPos);
		Long myDistance = minDistance(distance + Math.min(directDelta, circularDelta), newPos,
			new LinkedList<String>(recipe), minDistance);
		if (myDistance < minDistance)
		    minDistance = myDistance;
	    }

	    return minDistance;
	}

	public Long findMinDistance(List<String> recipe) {
	    LinkedList<String> recipeLinkedList = new LinkedList<String>(recipe);
	    recipeLinkedList.add("DOOR"); // we start at the door, and we need to end at it too
	    int position = layout.indexOf("DOOR");
	    Long distance = 0L;
	    System.out.println("Finding the shortest path for this recipelist: " + recipeLinkedList);
	    Long result = minDistance(distance, position, recipeLinkedList, Long.MAX_VALUE);
	    return result;
	}

	/*-
	 *  This will always find the distance to the first item in the pantry, looking from the left. There are duplicates, sneaky nasty BLOBBITSES!
	public Long distanceToNextIngredient(String ingredient) {
	    Long minDistance = Long.MAX_VALUE;
	    Integer minPosition = 0;
	    if (myPosition == null)
		myPosition = layout.indexOf(myIngredient); // first time, let's make we start at the door
	
	    int newPos = layout.indexOf(ingredient);
	    Long directDelta = Math.abs(0L + myPosition - newPos);
	    // We can be circular... so let's also take into account distances to the edge
	    Long circularDelta = 0L + Math.min(myPosition, newPos) + layout.size() - Math.max(myPosition, newPos);
	    detail.add("    -> " + myIngredient + " at " + myPosition + ", next " + ingredient + " at " + newPos
		    + ", direct delta: " + directDelta + ", circular delta: " + circularDelta);
	    Long thisDistance = Math.min(directDelta, circularDelta);
	    if (thisDistance < minDistance) {
		minPosition = newPos;
		minDistance = thisDistance;
	    }
	    myPosition = minPosition;
	    myIngredient = ingredient;
	    return minDistance;
	} // distanceToNextIngredient
	*/
    } // Pantry

    @Override
    public void solve(int n) {
	StringBuilder output = new StringBuilder();
	Pantry p = new Pantry();
	for (String line : getFileContents(input)) {
	    p.addSpace(line);
	}

	Long total = 0L;
	File recipeFile = new File(getFilePath().replace("input.txt", "recipe.txt"));
	/*-
		for (String line : getFileContents(recipeFile)) {
		    detail.add("Looking for: " + line);
		    Long distance = p.distanceToNextIngredient(line);
		    total += distance;
		    detail.add("    " + distance + ", total: " + total);
		}
		// we need to get out through the door still
		Long distance = p.distanceToNextIngredient("DOOR");
		total += distance;
		detail.add(distance + ", total: " + total);
		output.append(total);
		output.append(", ");
	*/
	Util.startTimer();
	output.append(p.findMinDistance(getFileContents(recipeFile)));
	Util.endTimer();
	System.out.println("Duration: " + Util.getDuration());
	solver.add(output.toString());
    } // solve
}
