package beer.dacelo.dev.aoq2023.aoq2023;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import beer.dacelo.dev.aoq2023.generic.ColorUtils;
import beer.dacelo.dev.aoq2023.generic.Day;

public class Day14 extends Day {
    /**
     * Day 14: Sedrick: the ingredients.. 23 hours 52 minutes 37 seconds remaining
     * 
     * He has a few layers to him. Sedrick, as we know, is composed largely of candy
     * cane waste. Elves are rather traditional and stick to candy canes made of
     * red, green and white candy. We have learned from exploding Sedrick that this
     * make up, and the amounts of each are really important. If we are to try
     * blowing him up again we need to get this right. Luckily, based on images we
     * gathered from detonating him the first time we can determine the different
     * amounts of each of the colors. Recruit, we need you to read in the image and
     * figure out how much there is of each color (black being the areas of no
     * Sedrick).
     * 
     * For example:
     * 
     * Given the small image below, read in and count the Black, White, Red and
     * Green pixels:
     * 
     * Splat Small ((input_test.bmp))
     * 
     * Black = 24 Green = 15 Red = 29 White = 32 Total Pixels = 100 (10 x 10) We
     * will then ask you to enter the largest value (so we know you have them all).
     * In this case it would be 32 for White. You would simply answer: 32
     * 
     * For larger numbers we would not show commas or decimals (ie. 58921).
     * 
     * Sedrick is much bigger and spread out over a larger area. Please read in and
     * analyze the following image and answer in the same way as described above
     * (Enter the largest number) :
     * 
     * Splat ((input.bmp))
     */

    @Override
    public void solve(int n) {
	File imageFile = new File(getFilePath().replace(".txt", ".bmp"));
	BufferedImage image = null;
	try {
	    image = ImageIO.read(imageFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Map<Integer, Long> colorMap = new HashMap<Integer, Long>();
	int rgb;
	int width = image.getWidth(), height = image.getHeight();
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		rgb = image.getRGB(x, y);
		if (!colorMap.containsKey(rgb)) {
		    colorMap.put(rgb, 0L);
		}
		colorMap.put(rgb, colorMap.get(rgb) + 1);
	    }
	}

	Long maxCount = 0L;
	int maxRgb = 0;
	int red, green, blue;

	// Go through our list of found RGB codes, and find the maximum value
	// This can very easily be done by just getting maximum from the values()
	// by using Collections.max( colorMap.values() ) as the answer, but hey,
	// how would I then get to name all of the pretty colors that were found?!
	for (Integer i : colorMap.keySet()) {
	    red = (i >> 16) & 0x000000FF;
	    green = (i >> 8) & 0x000000FF;
	    blue = (i) & 0x000000FF;
	    detail.add("Frequency of i (" + ColorUtils.getColorNameFromHex(i) + "): [r: " + red + ", g: " + green + ", b: " + blue + "] = "
		    + colorMap.get(i));

	    Long count = colorMap.get(i);
	    if (count > maxCount) {
		maxCount = count;
		maxRgb = i;
	    }
	}

	// Found the maximum value, let's print it out once more
	red = (maxRgb >> 16) & 0x000000FF;
	green = (maxRgb >> 8) & 0x000000FF;
	blue = (maxRgb) & 0x000000FF;
	detail.add("color (" + ColorUtils.getColorNameFromHex(maxRgb) + "): [r: " + red + ", g: " + green + ", b: " + blue
		+ "] is appearing most frequently: " + maxCount);
	solver.add(maxCount.toString());
    } // solve
}
