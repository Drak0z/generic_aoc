package beer.dacelo.dev.aoq2023.aoq2023;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day23 extends Day {
	/*-
	Day 23: Sedrick goes boom. For the last time.
	
	Ok, this is it. Thanks for all of you hard work recruits. We have finally gotten the math right on this. We have set off a charge that has exploded Sedrick again. This time for good though! Pieces of blob are flying high up into the air. This is going to make a terrible mess when they come back down.
	
	Santa has an air traffic control radar that he uses to keep track of the reindeer. It recorded all the flying chunks as they departed the area.
	
	The measurements from the radar are:
	
	heading in degrees (east = 0, north = 90, etc)
	vertical angle in degrees (horizontal = 0, vertical = 90)
	velocity
	As you know, Bob, we can find the distance a projectile travels over a flat surface with the formula d = (v * v * sin(2 * a)) / g where v = velocity, a = vertical angle, and g = 9.81.
	
	Please plot out where all the pieces will land so we can go and clean them up. (you can assume all the pieces start at 0,0)
	
	To verify your plotting routines, here is a compass to help you along.
	
	Compass
	
	This is the data for all the flying pieces.
	
	Enter the answer, you'll know it when you see it!
	
	As for poor, misunderstood Sedrick, we have seen the last of him. Unless a certain naughty elf, no one is saying Fred (but we are all thinking it) has kept a tiny piece of him and is feeding him candy canes.
	*/

	List<List<Integer>> coordinates = new ArrayList<List<Integer>>();

	public void plot(String outputFilePath) {
		int minX = Integer.MAX_VALUE, maxX = 0, minY = Integer.MAX_VALUE, maxY = 0;
		for (List<Integer> c : coordinates) {
			int x = c.get(0);
			int y = c.get(1);
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
		}
		int deltaX = 50, deltaY = 50;
		if (minX < 0)
			deltaX += minX * -1;
		if (minY < 0)
			deltaY += minY * -1;

		int width = (maxX - minX) + deltaX + 100, height = (maxY - minY) + 100;

		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		// into integer pixels
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics();
		ig2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ig2.setBackground(Color.WHITE);
		ig2.fillRect(0, 0, width, height);

		ig2.setColor(Color.LIGHT_GRAY);
		for (int i = 1; i < bi.getWidth() / 50; i++) {
			ig2.drawLine(i * 50, 50, i * 50, bi.getHeight() - 50);
		}

		for (int i = 1; i < bi.getHeight() / 50; i++) {
			ig2.drawLine(50, i * 50, bi.getWidth() - 50, i * 50);
		}

		ig2.setColor(Color.RED);
		for (List<Integer> l : coordinates) {
			drawOval(ig2, l, deltaX, deltaY);
		}

		try {
			ImageIO.write(bi, "PNG", new File(outputFilePath + "this_is_an_image.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Integer> getCoordinates(List<Double> l) {
		Double multiplier = 10.0;
		Double heading = l.get(0);
		Double angle = l.get(1);
		Double velocity = l.get(2);

		Double gravity = 9.81;
		Double distance = (Math.pow(velocity, 2) * Math.sin(2 * Math.toRadians(angle))) / gravity;

		Long x = Math.round(Math.cos(Math.toRadians(heading)) * distance * multiplier);
		Long y = Math.round(Math.sin(Math.toRadians(heading)) * distance * multiplier * -1);

		return new ArrayList<Integer>(List.of(x.intValue(), y.intValue()));
	}

	private void drawOval(Graphics2D ig2, List<Integer> l, Integer deltaX, Integer deltaY) {
		// HEADING,VERTICAL_ANGLE,VELOCITY
		int x = l.get(0);
		int y = l.get(1);
		detail.add("Coordinate: [" + x + "," + y + "]");
		ig2.drawOval(x + deltaX, y + deltaY, 3, 3);
	}

	@Override
	public void solve(int n) {
		String outputFilePath = getFilePath().replace("input.txt", "").replace("test_", "").replace("input", "output");
		for (String line : getFileContents(input)) {
			if (Character.isAlphabetic(line.charAt(1)))
				continue;
			// HEADING,VERTICAL_ANGLE,VELOCITY
			String[] havString = line.split(",");
			List<Double> hav = new ArrayList<Double>();
			for (String s : havString) {
				hav.add(Double.parseDouble(s));
			}
			coordinates.add(getCoordinates(hav));
		}
		plot(outputFilePath);
	} // solve
};