package beer.dacelo.dev.aoq2023.aoc2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day5 extends Day {
    List<Long> solver = new ArrayList<Long>();

    private class SeedMapper {
	List<String> inputs;

	public SeedMapper() {
	    inputs = new ArrayList<String>();
	}

	public void addInput(String input) {
	    inputs.add(input);
	}

	public Map<Long, Long> getAllSeeds() {
	    Map<Long, Long> allSeeds = new HashMap<Long, Long>();
	    for (String s : inputs) {
		String[] values = s.split(" ");
		for (int i = 0; i < values.length - 1; i += 2) {
		    Long start = Long.parseLong(values[i]);
		    Long length = Long.parseLong(values[i + 1]);
		    allSeeds.put(start, length);
		}
	    }
	    return allSeeds;
	}
    }

    private class DataMapper {
	List<String> inputs;
	String name;

	public DataMapper(String name) {
	    this.name = name;
	    inputs = new ArrayList<String>();
	}

	public void addInput(String input) {
	    inputs.add(input);
	}

	public Long convert(Long value) {
	    for (String s : inputs) {
		String[] input = s.split(" ");
		Long destination = Long.parseLong(input[0]);
		Long source = Long.parseLong(input[1]);
		Long length = Long.parseLong(input[2]);
		if (value >= source && value <= source + length) {
		    return (value - source) + destination;
		}
	    }
	    return value;
	}

	public String toString() {
	    return this.name;
	}
    }

    @Override
    public void solve(int n) {
	Long result = 0L;
	String processing = "";

	List<Long> seeds = new ArrayList<Long>();
	SeedMapper sm = new SeedMapper();
	DataMapper seedToSoil = new DataMapper("seed-to-soil");
	DataMapper soilToFertilizer = new DataMapper("soil-to-fertilizer");
	DataMapper fertilizerToWater = new DataMapper("fertilizer-to-water");
	DataMapper waterToLight = new DataMapper("water-to-light");
	DataMapper lightToTemperature = new DataMapper("light-to-temperature");
	DataMapper temperatureToHumidity = new DataMapper("temperature-to-humidity");
	DataMapper humidityToLocation = new DataMapper("humidity-to-location");
	/*
	 * Map<Long, Long> seedToSoil = new HashMap<Long, Long>(); Map<Long, Long>
	 * soilToFertilizer = new HashMap<Long, Long>(); Map<Long, Long>
	 * fertilizerToWater = new HashMap<Long, Long>(); Map<Long, Long> waterToLight =
	 * new HashMap<Long, Long>(); Map<Long, Long> lightToTemperature = new
	 * HashMap<Long, Long>(); Map<Long, Long> temperatureToHumidity = new
	 * HashMap<Long, Long>(); Map<Long, Long> humidityToLocation = new HashMap<Long,
	 * Long>();
	 */
	for (String line : getFileContents(input)) {
	    if (line.startsWith("seeds: ")) {
		if (n == 1) {
		    String[] seedLine = line.replace("seeds: ", "").split(" ");
		    for (String s : seedLine) {
			if (s.length() > 0)
			    seeds.add(Long.parseLong(s));
		    }
		} else if (n == 2) {
		    sm.addInput(line.replace("seeds: ", ""));
		}
	    } else if (line.startsWith("seed-to-soil map") || line.startsWith("soil-to-fertilizer map")
		    || line.startsWith("fertilizer-to-water map") || line.startsWith("water-to-light map")
		    || line.startsWith("light-to-temperature map") || line.startsWith("temperature-to-humidity map")
		    || line.startsWith("humidity-to-location map")) {
		processing = line;
	    } else if (line.length() > 0) {
		switch (processing) {
		case "seed-to-soil map:":
		    seedToSoil.addInput(line);
		    break;
		case "soil-to-fertilizer map:":
		    soilToFertilizer.addInput(line);
		    break;
		case "fertilizer-to-water map:":
		    fertilizerToWater.addInput(line);
		    break;
		case "water-to-light map:":
		    waterToLight.addInput(line);
		    break;
		case "light-to-temperature map:":
		    lightToTemperature.addInput(line);
		    break;
		case "temperature-to-humidity map:":
		    temperatureToHumidity.addInput(line);
		    break;
		case "humidity-to-location map:":
		    humidityToLocation.addInput(line);
		    break;
		}
	    }
	}

	Long lowestLocation = Long.MAX_VALUE;
	if (n == 1) {

	    for (Long seed : seeds) {
		Long soil = 0L, fertilizer = 0L, water = 0L, light = 0L, temperature = 0L, humidity = 0L, location = 0L;
		soil = seedToSoil.convert(seed); // seedToSoil.containsKey(seed) ? seedToSoil.get(seed) : seed;
		fertilizer = soilToFertilizer.convert(soil); // ? soilToFertilizer.get(soil) : soil;
		water = fertilizerToWater.convert(fertilizer); // ? fertilizerToWater.get(fertilizer) : fertilizer;
		light = waterToLight.convert(water); // ? waterToLight.get(water) : water;
		temperature = lightToTemperature.convert(light); // ? lightToTemperature.get(light) : light;
		humidity = temperatureToHumidity.convert(temperature); // ? temperatureToHumidity.get(temperature) :
								       // temperature;
		location = humidityToLocation.convert(humidity); // ? humidityToLocation.get(humidity) : humidity;
		/*
		 * detail.add(" - Seed " + seed + ", soil " + soil + ", fertilizer " +
		 * fertilizer + ", water " + water + ", light " + light + ", temperature " +
		 * temperature + ", humidity " + humidity + ", location " + location);
		 */
		lowestLocation = Math.min(lowestLocation, location);
	    }
	} else if (n == 2) {
	    Map<Long, Long> seedMap = sm.getAllSeeds();
	    for (Long l : seedMap.keySet()) {
		System.out.println("Processing: " + l + ", " + seedMap.get(l) + ", lowestLocation = " + lowestLocation);
		for (Long i = 0L; i < seedMap.get(l); i++) {
		    Long seed = l + i;
		    Long location = 0L;
		    /* if (i % 1000 == 0) {
			String header = "Processing: " + l + ", " + seedMap.get(l) + ", lowestLocation = "
				+ lowestLocation;
			Double progress = i.doubleValue() / seedMap.get(l).doubleValue();
			Util.LoadingWindow("Loading... " + progress + "%", header, progress, false); // LoadingWindow("Loading...",
										   // header,
		    } */
		    location = humidityToLocation
			    .convert(temperatureToHumidity.convert(lightToTemperature.convert(waterToLight.convert(
				    fertilizerToWater.convert(soilToFertilizer.convert(seedToSoil.convert(seed))))))); // ?
														       // humidityToLocation.get(humidity)
														       // :
														       // humidity;
		    /*
		     * soil = seedToSoil.convert(seed); // seedToSoil.containsKey(seed) ?
		     * seedToSoil.get(seed) : seed; fertilizer = soilToFertilizer.convert(soil); //
		     * ? soilToFertilizer.get(soil) : soil; water =
		     * fertilizerToWater.convert(fertilizer); // ? fertilizerToWater.get(fertilizer)
		     * : fertilizer; light = waterToLight.convert(water); // ?
		     * waterToLight.get(water) : water; temperature =
		     * lightToTemperature.convert(light); // ? lightToTemperature.get(light) :
		     * light; humidity = temperatureToHumidity.convert(temperature); // ?
		     * temperatureToHumidity.get(temperature) : // temperature; location =
		     * humidityToLocation.convert(humidity); // ? humidityToLocation.get(humidity) :
		     * humidity;
		     * 
		     * detail.add(" - Seed " + seed + ", soil " + soil + ", fertilizer " +
		     * fertilizer + ", water " + water + ", light " + light + ", temperature " +
		     * temperature + ", humidity " + humidity + ", location " + location);
		     */

		    lowestLocation = Math.min(lowestLocation, location);
		}
	    }
	}

	solver.add(lowestLocation);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(solver.get(0).toString());
	return solution;
    }
}
