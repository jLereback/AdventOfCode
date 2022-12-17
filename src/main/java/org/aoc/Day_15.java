package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_15 {
	static List<String> inputList= FileReader.getFileAsList("15_sample");
	static int LINE_TO_FIND = 10;

	public static void main(String[] args) {
		List<InputLine> lines = convertInput();
		part1(lines);


		//solve(inputList);
	}
	private static void part1(List<InputLine> lines) {
		Set<Integer> excludedBeacons = new HashSet<>();
		Set<Integer> invalidSpots = new HashSet<>();

		for (InputLine inputLine : lines) {
			if (inputLine.beaconY == LINE_TO_FIND)
				excludedBeacons.add(inputLine.beaconX);

			int remaining = getRemainingOnLine(inputLine, LINE_TO_FIND);

			if (remaining < 0)
				continue;

			for (int i = 0; i <= remaining; i++) {
				invalidSpots.add(inputLine.sensorX + i);
				invalidSpots.add(inputLine.sensorX - i);
			}
		}
		invalidSpots.removeAll(excludedBeacons);

		System.out.println(invalidSpots.size());
	}
	static void solve(List<InputLine> inputLines) {

		int max = 4000000;

		for (int i = 0; i < max; i++) {
			int x = 0;
			while (x <= max) {
				int newX = x;
				for (InputLine il : inputLines) {
					newX = skipOverLine(il, x, i);
					if (x != newX)
						break;
				}

				if (newX == x) {
					System.out.println(((long) x * max) + i);
					i = max + 1;
					break;
				}
				x = newX;
			}
		}
	}
	private static int getRemainingOnLine(InputLine inputLine, int lineToFind) {
		int dist = Math.abs(inputLine.sensorX - inputLine.beaconX) + Math.abs(inputLine.sensorY - inputLine.beaconY);

		if (dist - inputLine.sensorY > lineToFind)
			return inputLine.sensorY - lineToFind;
		else
			return lineToFind - inputLine.sensorY;
	}
	private static int skipOverLine(InputLine inputLine, int x, int lineToFind) {
		int remaining = getRemainingOnLine(inputLine, lineToFind);

		if (x >= inputLine.sensorX - remaining && x <= inputLine.sensorX + remaining)
			return inputLine.sensorX + remaining + 1;
		return x;
	}
	private static List<InputLine> convertInput() {
		List<InputLine> inputLines = new ArrayList<>();
		Pattern pattern = Pattern.compile(
				"Sensor at x=(\\d+), y=(\\d+): closest beacon is at x=(-\\d+|\\d+), y=(\\d+)");
		for (String line : inputList) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				inputLines.add(new InputLine(
						Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2)),
						Integer.parseInt(matcher.group(3)),Integer.parseInt(matcher.group(4))));
			}
		}
		return inputLines;
	}
	private record InputLine(int sensorX, int sensorY, int beaconX, int beaconY) {
		@Override
		public String toString() {
			return "InputLine{" +
				   "sensorX=" + sensorX +
				   ", sensorY=" + sensorY +
				   ", beaconX=" + beaconX +
				   ", beaconY=" + beaconY +
				   '}';
		}
	}
}