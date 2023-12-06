package beer.dacelo.dev.aoq2023.aoq2023;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day3 extends Day {
    /**
     * Day 3: How Big Will He Get?!?
     * 
     * He's big now. I mean really big. Think of something really big. He's bigger.
     * Unless you thought of like a planet. He's not that big. But if you thought of
     * a building, he's bigger. With this growth, his appetite has grown too. How
     * much?? That, recruits, is what we must work out today.
     * 
     * Our recon team has found some notes from the elves. It seems they were
     * worried about the rate at which he was growing. They started doing a daily
     * weight check. Sedrick didn't take to kindly to it as there are only a few
     * days of data. This is what we have:
     * 
     * Daily check in
     * 
     * Aug 20, 2023 = 400 kg 
     * Aug 21, 2023 = 420 kg 
     * Aug 22, 2023 = 441 kg 
     * 
     * Not to give away any spoilers here, but we think the best strategy is to blow him up. 
     * To calculate the minimum amount of explosive, we need to know his mass.
     * 
     * Given the data above, and assuming he continues to grow at the same rate,
     * calculate his mass at the end of Dec 2. Enter the number answer to a
     * precision of 2 decimal places.
     * 
     * For example, on Sep 5, the correctly rounded answer would be 873.15.
     */
    List<String> solver = new ArrayList<String>();

    @Override
    public void solve(int n) {	
	Double startWeight = 400.0;
	Double growthRate = ((420.0 / 400.0) + (441.0 / 420))/2; // I know this is 5%, but hey, things may change
	String startDateStr = "Aug 20, 2023";
	String targetDateStr = getFileContents(input).get(0);
	DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		    .parseCaseInsensitive()
		    .appendPattern("MMM d, yyyy")
		    .toFormatter(Locale.ENGLISH);
	
	LocalDate currentDate = LocalDate.parse(startDateStr, formatter);
	LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
	long diff = currentDate.until(targetDate, ChronoUnit.DAYS);
	
	detail.add("Start Date: " + currentDate + ", Target Date: " + targetDate + ", Days between: " + diff);
	detail.add("Start Weight: " + startWeight + ", Daily growth rate: " + growthRate + ", Total growth: " + Math.pow(growthRate, diff));
	
	/* We don't need a loop if we just "power" through it
	 * Double currentWeight = startWeight;
	 * detail.add("At: " + currentDate + " Weight: " + df.format(currentWeight));
	 * while (currentDate.isBefore(targetDate)) {
	 *     currentWeight *= growthRate;
	 *     currentDate = currentDate.plusDays(1);
	 *     detail.add("At: " + currentDate + " Weight: " + df.format(currentWeight));
	 * } */

	Double currentWeight = startWeight * Math.pow(growthRate, diff);
	detail.add("Final weight at date " + targetDate + ": " + currentWeight);
	
	DecimalFormat df = new DecimalFormat("#.##");	
	df.setRoundingMode(RoundingMode.HALF_UP);	
	solver.add(df.format(currentWeight));
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0));
	return solution;
    }
}
