package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Day15 {
	static final String REGEX = "(-?\\d+)";
	static final int ROW = 2000000;
	static int rowDistance;
	static List<String> inputList = FileReader.getFileAsList("15");

	public static void main(String[] args) {
		System.out.println(part1());
	}

	private static int part1() {
		var sensorData = getSensorList();
		Set<Integer> invalidSpotForBeacon = new HashSet<>();
		for (PointsAndDist p : sensorData) {
			if (isOutsideRange(p))
				continue;
			rowDistance = getRowDistance(p);
			for (var i = p.sX - rowDistance; i < p.sX + rowDistance; i++)
				invalidSpotForBeacon.add(i);
		}
		return invalidSpotForBeacon.size();
	}

	private static boolean isOutsideRange(PointsAndDist p) {
		int maxYPos = p.dist + p.sY;
		int minYPos = p.sY - p.dist;

		return maxYPos < ROW && ROW < minYPos;
	}

	private static int getRowDistance(PointsAndDist p) {
		int dist = Math.abs(p.sX - p.bX) + Math.abs(p.sY - p.bY);
		if (ROW > p.sY)
			return (p.sY + dist) - ROW;
		else if (ROW < p.sY)
			return ROW - (p.sY - p.dist);
		else
			return 0;
	}

	private static List<PointsAndDist> getSensorList() {
		return inputList.stream().map(Day15::parseToIntArray)
				.map(p -> new PointsAndDist(p[0], p[1], p[2], p[3], getDistance(p)))
				.toList();
	}

	private static int getDistance(int[] p) {
		return Math.abs(p[0] - p[2]) + Math.abs(p[1] - p[3]);
	}

	private static int[] parseToIntArray(String inputLine) {
		return Pattern.compile(REGEX).matcher(inputLine).results()
				.mapToInt(result -> Integer.parseInt(result.group()))
				.toArray();
	}

	record PointsAndDist(int sX, int sY, int bX, int bY, int dist) {
		@Override
		public String toString() {
			return "Position{" +
				   "sX=" + sX +
				   ", sY=" + sY +
				   ", bX=" + bX +
				   ", bY=" + bY +
				   ", dist=" + dist +
				   '}';
		}
	}
}