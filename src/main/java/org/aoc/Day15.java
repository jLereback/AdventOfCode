package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
	static Pattern pattern = Pattern.compile(
			"Sensor at x=(-\\d+|\\d+), y=(-\\d+|\\d+): closest beacon is at x=(-\\d+|\\d+), y=(-\\d+|\\d+)");
	static List<Beacon> beaconList = new ArrayList<>();
	static List<Sensor> sensorList = new ArrayList<>();

	static int LINE_TO_FIND = 2_000_000;

	static List<String> inputList = FileReader.getFileAsList("15");

	public static void main(String[] args) {
		inputList.forEach(Day15::fillSensorAndBeaconList);
		part1();


		solve(inputList);
	}

	private static void part1() {
		Set<Integer> beaconsToExclude = new HashSet<>();
		Set<Integer> invalidSpots = new HashSet<>();
		int remaining = 0;
		for (int i = 0; i < sensorList.size()-1; i++) {
			Beacon b = beaconList.get(i);
			Sensor s = sensorList.get(i);
			if (b.beaconY == LINE_TO_FIND)
				beaconsToExclude.add(b.beaconX);

			remaining = getRemainingOnLine(s, b, LINE_TO_FIND);

			if (remaining < 0)
				continue;

			for (int j = 0; j <= remaining; j++) {
				invalidSpots.add(s.sensorX + j);
				invalidSpots.add(s.sensorX - j);
			}
		}

		invalidSpots.removeAll(beaconsToExclude);

		System.out.println("Part1: " + invalidSpots.size());

	}


	static void solve(List<String> input) {
		List<Sensor> sensorList = new ArrayList<>();
		List<Beacon> beaconList = new ArrayList<>();

		for (String s : input) {
			int c1 = s.indexOf(",");
			int sx = Integer.parseInt(s.substring(12, c1));
			int colon = s.indexOf(":");
			int sy = Integer.parseInt(s.substring(c1 + 4, colon));
			int c2 = s.indexOf(",", colon);
			int bx = Integer.parseInt(s.substring(colon + 25, s.indexOf(",", c2)));
			int by = Integer.parseInt(s.substring(c2 + 4));
			sensorList.add(new Sensor(sx, sy));
			beaconList.add(new Beacon(bx, by));
		}

		int lineToFind = 2000000;
		Set<Integer> beaconsToExclude = new HashSet<>();
		Set<Integer> invalidPoints = new HashSet<>();
		for (int i = 0; i < sensorList.size()-1; i++) {
			Beacon b = beaconList.get(i);
			Sensor s = sensorList.get(i);
			if (b.beaconY == lineToFind)
				beaconsToExclude.add(b.beaconX);

			int remaining = getRemainingOnLine(s, b, LINE_TO_FIND);

			if (remaining < 0)
				continue;

			for (int j = 0; j <= remaining; j++) {
				invalidPoints.add(s.sensorX + j);
				invalidPoints.add(s.sensorX - j);
			}
		}

		invalidPoints.removeAll(beaconsToExclude);

		System.out.println(invalidPoints.size());

		int max = 4000000;

		for (int i = 0; i < max; i++) {
			int x = 0;
			while (x <= max) {
				int newX = x;
				for (int j = 0; j < sensorList.size()-1; j++) {
					Beacon b = beaconList.get(j);
					Sensor s = sensorList.get(j);
					newX = skipOverLine(s, b, x, j);
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

	private static int getRemainingOnLine(Sensor s, Beacon b, int lineToFind) {
		int dist = Math.abs(s.sensorX - b.beaconX) + Math.abs(s.sensorY - b.beaconY);

		if (dist - s.sensorY > lineToFind)
			return s.sensorY - lineToFind;
		else
			return lineToFind - s.sensorY;
	}

	private static int skipOverLine(Sensor s, Beacon b, int x, int lineToFind) {
		int remaining = getRemainingOnLine(s, b, lineToFind);

		if (x >= s.sensorX - remaining && x <= s.sensorX + remaining)
			return s.sensorX + remaining + 1;
		return x;
	}

	private static void fillSensorAndBeaconList(String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			sensorList.add(new Sensor(
					Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2))));
			beaconList.add(new Beacon(
					Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4))));
		}
	}


	private record Sensor(int sensorX, int sensorY) {
		@Override
		public String toString() {
			return "InputLine{" +
				   "sensorX=" + sensorX +
				   ", sensorY=" + sensorY +
				   '}';
		}
	}
	private record Beacon(int beaconX, int beaconY) {
		@Override
		public String toString() {
			return "InputLine{" +
				   ", beaconX=" + beaconX +
				   ", beaconY=" + beaconY +
				   '}';
		}

	}
}