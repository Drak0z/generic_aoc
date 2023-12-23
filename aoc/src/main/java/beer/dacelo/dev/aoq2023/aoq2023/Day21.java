package beer.dacelo.dev.aoq2023.aoq2023;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day21 extends Day {
    /*-
    Day 21: Container Positioning Unit
    
    Sedrick has eaten many things. Well sort of. Some of those things he likes to keep. If he finds interesting things floating around in his overall gelatinous mass that he likes, he keeps them. He has kept so many things that it has now required a system and a machine to organize them! Thus was born Sedrick's "Container Positioning Unit"© It makes use of a tremendous quantity of buckets. Luckily he has eaten a huge number of those too!
    
    This is a machine that sorts buckets according to weight. It has a line of bucket locations that it travels along moving buckets back and forth, following a pre-programmed list of instructions.
    
    Specific details
    Buckets each have a known weight.
    There is a single line of bucket locations, which can each be empty or contain a bucket.
    The machine can be positioned at any location in the line.
    The machine can (but does not always) carry a single bucket.
    The machine moves up and down the line of locations one location at a time.
    The machine starts step 1 of its program at the first location, carrying nothing.
    Steps in the machine's program are processed in order.
    Operations can GO TO a different step number instead of proceeding to the next step.
    We don't know what happens if the machine is told to do something impossible, like pick up two buckets at once, or move past the end of the line. It probably explodes!
    Machine operations
    Sedrick has the ultimate top-of-the-line sorting machine with nearly eleven different things it can do:
    
    ID #1: Move ahead one location (away from start).
    ID #2: Move back one location (towards start).
    ID #3: Pick up the bucket at the current location (leaving the location empty).
    ID #4: Put down the carried bucket at the current location).
    ID #5: Swap the carried bucket with the one at the current location.
    ID #6: Compare the carried bucket to the current location and go to step N if the carried bucket is heavier.
    ID #7: If the current location is the end, go to step N.
    ID #8: If the current location is the start, go to step N.
    ID #9: Go to step N.
    ID #10: Stop.
    Program
    Sedrick hired the legendary software engineer Doctor Bubbles to program the machine with their famous sorting algorithm.
    
    Here are the program's steps:
    
    Step 1: If the current location is the end, go to step 24.
    Step 2: Pick up the bucket at the current location.
    Step 3: Move ahead one location.
    Step 4: Compare the carried bucket to the current location and go to step 9 if the carried bucket is heavier.
    Step 5: Move back one location.
    Step 6: Put down the carried bucket at the current location.
    Step 7: Move ahead one location.
    Step 8: Go to step 1.
    Step 9: Swap the carried bucket with the one at the current location.
    Step 10: Move back one location.
    Step 11: Put down the carried bucket at the current location.
    Step 12: Move ahead one location.
    Step 13: If the current location is the end, go to step 21.
    Step 14: Pick up the bucket at the current location.
    Step 15: Move ahead one location.
    Step 16: Compare the carried bucket to the current location and go to step 9 if the carried bucket is heavier.
    Step 17: Move back one location.
    Step 18: Put down the carried bucket at the current location.
    Step 19: Move ahead one location.
    Step 20: Go to step 13.
    Step 21: Move back one location.
    Step 22: If the current location is the start, go to step 1.
    Step 23: Go to step 21.
    Step 24: Stop.
    download the steps in machine readable form
    
    The program expects a line of buckets with no empty locations. It will sort the line into ascending order and then stop.
    
    Question
    Sedrick wants us to optimize the sorting machine's performance. We need to find out what it spends more time doing: comparing bucket weights, or moving between locations.
    
    Simulate the operation of the machine as it follows the steps, and count how many times the machine compares the buckets and how many times it moves to a new location.
    
    Submit your answer as two integers separated by a comma: compares,moves. For example with the line of buckets 3,2,1 the answer would be 6,22
    
    We need the result for this line of 20 buckets:
    
    41, 70, 29, 40, 50, 74, 26, 86, 84, 33, 80, 65, 77, 95, 73, 72, 47, 52, 79, 56
    */

    private class SortingMachine {
	int location;
	List<Bucket> buckets;
	Bucket carrying;
	List<List<Integer>> instructionSet;
	int compares;
	int moves;

	public SortingMachine() {
	    location = 0;
	    buckets = new ArrayList<Bucket>();
	    carrying = null;
	    instructionSet = new ArrayList<List<Integer>>();
	    compares = 0;
	    moves = 0;
	}

	public void addInstruction(String[] instruction) {
	    Integer stepNumber = Integer.parseInt(instruction[0]) - 1; // Steps start at 1, but arrays start at 0
	    Integer operationId = Integer.parseInt(instruction[1]);
	    Integer destinationStep = (instruction.length > 2 && instruction[2].length() > 0)
		    ? Integer.parseInt(instruction[2]) - 1 // Steps start at 1, but arrays start at 0
		    : -1;
	    List<Integer> instructions = new ArrayList<Integer>(List.of(stepNumber, operationId, destinationStep));
	    detail.add("  Adding instruction: input -> " + Arrays.asList(instruction) + ", output -> " + instructions);
	    instructionSet.add(instructions);
	}

	public void addBucket(Bucket b) {
	    detail.add("  adding bucket: " + b);
	    buckets.add(b);
	}

	public void parseInstruction() {
	    boolean run = true;
	    int step = 0;
	    Bucket b;
	    while (run) {
		List<Integer> instruction = instructionSet.get(step);
		int stepNumber = instruction.get(0);
		int operationId = instruction.get(1);
		int nextStep = stepNumber + 1;
		int destinationStep = instruction.get(2);
		detail.add("Processing: Step: " + step + ", operationId: " + operationId + ", destinationStep: "
			+ destinationStep);
		switch (operationId) {
		case 1: // ID #1: Move ahead one location (away from start).
		    if (location >= buckets.size() - 1)
			System.err.println(
				"Step 1: Trying to move further to the right while we're at the end - Explosion?");
		    location = (location + 1); // % buckets.size();
		    moves++;
		    break;
		case 2: // ID #2: Move back one location (towards start).
		    if (location <= 0)
			System.err.println("Step 2: Trying to move further to the left while we're at 0 - Explosion?");
		    location = (location - 1); // + buckets.size()) % buckets.size();
		    moves++;
		    break;
		case 3: // ID #3: Pick up the bucket at the current location (leaving the location
			// empty).
		    if (carrying != null)
			System.err.println("Step 3: Picking up a bucket while we're carrying one - Explosion?");
		    if (buckets.get(location) == null)
			System.err.println("Step 3: Picking up a bucket where there is none - Explosion?");
		    carrying = buckets.get(location);
		    buckets.set(location, null);
		    break;
		case 4: // ID #4: Put down the carried bucket at the current location).
		    if (carrying == null)
			System.err.println("Step 4: Putting down a bucket while we're carrying none - Explosion?");
		    if (buckets.get(location) != null)
			System.err.println("Step 3: Putting down a bucket where there is one - Explosion?");
		    buckets.set(location, carrying);
		    carrying = null;
		    break;
		case 5: // ID #5: Swap the carried bucket with the one at the current location.
		    if (carrying == null)
			System.err.println("Step 5: Swapping a bucket while we're carrying none - Explosion?");
		    if (buckets.get(location) == null)
			System.err.println("Step 5: Swapping a bucket where there is none - Explosion?");
		    b = buckets.get(location);
		    buckets.set(location, carrying);
		    carrying = b;
		    break;
		case 6: // ID #6: Compare the carried bucket to the current location and go to step N if
			// the carried bucket is heavier.
		    if (carrying == null)
			System.err.println("Step 5: Comparing a bucket while we're carrying none - Explosion?");
		    if (buckets.get(location) == null)
			System.err.println("Step 5: Comparing a bucket where there is none - Explosion?");
		    b = buckets.get(location);
		    if (carrying.weight > b.weight) { // if we're NOT heavier, we stay?
			nextStep = destinationStep;
		    }
		    compares++;
		    break;
		case 7: // ID #7: If the current location is the end, go to step N.
		    if (location == (buckets.size() - 1)) {
			nextStep = destinationStep;
		    }
		    break;
		case 8: // ID #8: If the current location is the start, go to step N.
		    if (location == 0) {
			nextStep = destinationStep;
		    }
		    break;
		case 9: // ID #9: Go to step N.
		    nextStep = destinationStep;
		    break;
		case 10: // ID #10: Stop.
		    run = false;
		    break;
		default:
		    System.err.println("We should never get here, operationId unknown: " + operationId + ", step: "
			    + step + ", " + stepNumber);
		}
		detail.add("  Step " + step + " processed, moving to " + nextStep);
		step = nextStep;
	    }
	}

	public String getResult() {
	    return compares + "," + moves;
	}
    }

    class Bucket {
	int weight;

	public Bucket(int weight) {
	    this.weight = weight;
	}

	public String toString() {
	    return "I AM A BUCKET! MY WEIGHT: " + weight;
	}
    }

    @Override
    public void solve(int n) {
	StringBuilder output = new StringBuilder();
	SortingMachine sm = new SortingMachine();
	String instructionFilePath = getFilePath().replace("test_", "").replace("input.txt",
		"sort_machine_language.csv");
	List<String> instructions = getFileContents(new File(instructionFilePath));
	detail.add("Reading instructions: ");
	for (int i = 1; i < instructions.size(); i++) { // start at 1, ignore the header
	    sm.addInstruction(instructions.get(i).split(","));
	}
	detail.add("Reading buckets: ");
	String[] inputBuckets = getFileContents(input).get(0).split(",");
	for (String s : inputBuckets) {
	    Bucket b = new Bucket(Integer.parseInt(s.trim()));
	    sm.addBucket(b);
	}

	sm.parseInstruction();
	detail.add(sm.getResult());
	output.append(sm.getResult());
	solver.add(output.toString());
    } // solve
}
