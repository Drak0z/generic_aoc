package beer.dacelo.dev.aoq2023.soq2024;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day7 extends Day {
	/*-
	Day 7: Clean-up
	
	We’re are expecting an influx of attendees tomorrow (of all ages) and our custodial crew needs some help calculating the expected cost cleaning supplies for the day. As it turns out, feeding people a pile of free pancakes (totally a Stampede thing) and then throwing them onto a spinning /jolting/bouncing ride can cause some “issues” in need of cleaning.
	
	The probability of a clean-up-requiring-“issue” is defined in the following table (the custodial crew have kept very detailed records over the years). (also available in CSV format: cleanup.csv)
	
	Age Group	Whirling Whip Lash	Dizzy Dropper	Barf-o-Blaster	Twist ‘n’ Spew	Puke-a-palooza	Gut Buster Spin
	0-2	0.25	0.39	0.45	0.14	0.22	0.28
	3-9	0.04	0.07	0.08	0.02	0.04	0.04
	10-19	0.02	0.02	0.02	0.01	0.01	0.02
	20-39	0.07	0.11	0.11	0.02	0.07	0.09
	40-59	0.1	0.17	0.22	0.07	0.16	0.18
	60+	0.35	0.29	0.39	0.08	0.28	0.32
	For the sake of clarity, the date ranges are inclusive, so the range 0-2 would mean any child from birth to the day before their 3rd birthday (i.e. it would include children ages 1mo, 5mo, 18mo, 30mo, but not 36mo).
	
	Given the list of attendees and their planned ride schedule, we need your help calculating the expected cost of cleaning supplies given the anticipated number of “incidents”. Each incident requires two cleaning rags and 5 squirts of cleaning solution. Each rag costs $0.50 and a bottle of cleaning solution (containing exactly 250 squirts) costs $25.
	
	So, for example, if the expected attendee list was (attendees_short.csv):
	
	Name	Age	Rides
	Jack Thomas	76yr	Twist ‘n’ Spew; Puke-a-palooza; Whirling Whip Lash
	Mason Perez	95yr	Twist ‘n’ Spew; Puke-a-palooza; Whirling Whip Lash
	Tom Young	22yr	Twist ‘n’ Spew
	David Baker	47yr	Dizzy Dropper; Barf-o-Blaster; Gut Buster Spin
	Tom Young	84yr	Puke-a-palooza; Twist ‘n’ Spew
	Amelia Green	21mo	Twist ‘n’ Spew
	Sue Carter	39yr	Twist ‘n’ Spew; Puke-a-palooza; Gut Buster Spin
	Liam Scott	18yr	Gut Buster Spin; Puke-a-palooza
	We would expect the total cost to be 4.08 (but the custodial crew pays their bills in loonies, so we round up to $5).
	
	Now you may be wondering, how are we going to predict who is going to be riding which rides tomorrow? Fortunately, one of the attractions at the Stampede is the psychic Madame Mysteria the All-Knowing. Mysteria has provided us this list of attendees for tomorrow. Lucky!
	
	Full list as CSV: attendees.csv
	
	Your answer should be the dollar amount rounded up to the next whole number without any additional punctuation. i.e.
	
	Valid answer formats: 123, 4567890, 1
	Invalid answer formats: $123, 456.2, 745,234, three hundred, 1️⃣1️⃣1️⃣
	 */

	private class Attendee {
		private String name;
		private double age;
		private List<String> ridesList;

		public Attendee(String[] input) {
			this.name = input[0];
			this.age = strToAge(input[1]);
			this.ridesList = strToRidesList(input[2]);
		}

		private double strToAge(String age) {
			if (age.indexOf("yr") > -1) {
				return Integer.parseInt(age.replace("yr", ""));
			} else if (age.indexOf("mo") > -1) {
				return Integer.parseInt(age.replace("mo", "")) / 12.0;
			}
			return 0;
		}

		private List<String> strToRidesList(String rides) {
			List<String> ridesList = new ArrayList<>();
			String[] ridesArray = rides.split(";");
			for (int i = 0; i < ridesArray.length; i++) {
				ridesList.add(ridesArray[i].trim());
			}
			return ridesList;
		}

		public String getName() {
			return this.name;
		}

		public List<String> getRidesList() {
			return this.ridesList;
		}

		public double getAge() {
			return this.age;
		}

		public String toString() {
			return "Attendee name: " + getName() + ", age: " + getAge() + ", rides: " + getRidesList();
		}
		
		public String getAgeGroup() {
			if (age >= 0 && age < 3) return "0-2";
			if (age >= 3 && age < 10) return "3-9";
			if (age >= 10 && age < 20) return "10-19";
			if (age >= 20 && age < 40) return "20-39";
			if (age >= 40 && age < 60) return "40-59";
			if (age >= 60) return "60+";
			return "ERROR";
		}
	}

	private Integer calculateCleanupCost(List<List<String>> cleanupList, List<Attendee> attendeeList) {
		double incidentProbability = 0;
		List<String> incidentHeader = cleanupList.get(0);
		for (Attendee a : attendeeList) {
			List<String> incidentPct = new ArrayList<String>();
			for (List<String> s : cleanupList) {
				if (s.get(0).equals(a.getAgeGroup())) incidentPct = s;
			}
			
			for (String ride : a.getRidesList()) {
				int index = incidentHeader.indexOf(ride);
				incidentProbability += Double.parseDouble(incidentPct.get(index));
			}
		}
		
		double ragCost = 0.5 * 2 * incidentProbability;
		double bottleCost = 25 * 5 * incidentProbability / 250;
		System.out.println("Total cost (double) = " + (ragCost+bottleCost) + " Rounded up: " + (int) Math.ceil(ragCost+bottleCost));
		return (int) Math.ceil(ragCost+bottleCost);
	}

	@Override
	public void solve(int n) {
		Integer cleanupCost = 0;

		List<String> attendeesFileContents = getFileContents(input);
		System.out.println(getFilePath());
		List<String> cleanupFileContents = getFileContents(new File("soq2024\\input\\day7\\cleanup2.csv"));

		List<List<String>> cleanupList = new ArrayList<>();
		List<Attendee> attendeeList = new ArrayList<>();

		for (String line : cleanupFileContents) {
			cleanupList.add(Arrays.asList(line.split(",")));
		}

		for (int i = 1; i < attendeesFileContents.size(); i++) {
			attendeeList.add(new Attendee(attendeesFileContents.get(i).split(",")));
		}
		
		cleanupCost = calculateCleanupCost (cleanupList, attendeeList);
		
		solver.add(cleanupCost.toString());
	}

	@Override
	public List<String> getSolution(int n) {
		solution = new ArrayList<String>();
		for (int i = 0; i < solver.size(); i++) {
			solution.add(solver.get(i).toString());
		}
		return solution;
	}
};