package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.*;
import java.util.List;


public class Day12 {
	static List<String> terrain = FileReader.getFileAsList("12");
	public static Map<Point, Integer> trail = new HashMap<>();


	public static void main(String[] args) {
		Point start = null;
		Point end = null;
		for (int row = 0; row < terrain.size(); row++) {
			String line = terrain.get(row);
			for (int column = 0; column < line.length(); column++) {
				Point p = new Point(row, column);
				char c = line.charAt(column);
				if (c == 'S') {
					start = p;
					c = 'a';
				} else if (c == 'E') {
					end = p;
					c = 'z';
				}
				trail.put(p, c - 'a');
			}
		}
		System.out.println(floodFill(start, end, "Part1"));
		System.out.println(floodFill(start, end, "Part2"));
	}

	public static Integer floodFill(Point start, Point end, String part) {
		Map<Point, Integer> bestTrail = new HashMap<>();
		bestTrail.put(start, 0);
		List<Point> queue = new ArrayList<>();
		queue.add(start);
		while (queue.size() > 0) {
			Point point = queue.remove(0);
			if (point.row() != 0)
				checkPoint(point, new Point(point.row() - 1, point.column()), bestTrail, queue, part);
			if (point.row() != terrain.size() - 1)
				checkPoint(point, new Point(point.row() + 1, point.column()), bestTrail, queue, part);
			if (point.column() != 0)
				checkPoint(point, new Point(point.row(), point.column() - 1), bestTrail, queue, part);
			if (point.column() != terrain.get(0).length() - 1)
				checkPoint(point, new Point(point.row(), point.column() + 1), bestTrail, queue, part);
		}
		return bestTrail.get(end);
	}

	public static void checkPoint(Point p, Point direction, Map<Point, Integer> bestTrail, List<Point> queue, String part) {
		int gridHeight = trail.get(direction);
		if (gridHeight - trail.get(p) <= 1) {
			int pathLen = bestTrail.get(p) + 1;

			if (bestTrail.containsKey(direction)) {
				if (bestTrail.get(direction) > pathLen) {
					queue.add(direction);
					if (part.contains("2") && gridHeight == 0)
						bestTrail.put(direction, 0);
					else
						bestTrail.put(direction, pathLen);
				}
			} else {
				queue.add(direction);
				if (part.contains("2") && gridHeight == 0)
					bestTrail.put(direction, 0);
				else
					bestTrail.put(direction, pathLen);
			}
		}
	}

	record Point(int row, int column) {
	}
}
