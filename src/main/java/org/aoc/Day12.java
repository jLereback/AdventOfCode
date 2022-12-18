package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;
import main.java.org.aoc.tools.Util.Position;

import java.util.*;
import java.util.List;


public class Day12 {
	static List<String> terrain = FileReader.getFileAsList("12");
	public static Map<Position, Integer> trail = new HashMap<>();


	public static void main(String[] args) {
		Position start = null;
		Position end = null;
		for (int row = 0; row < terrain.size(); row++) {
			String line = terrain.get(row);
			for (int column = 0; column < line.length(); column++) {
				Position p = new Position(row, column);
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

	public static Integer floodFill(Position start, Position end, String part) {
		Map<Position, Integer> bestTrail = new HashMap<>();
		bestTrail.put(start, 0);
		List<Position> queue = new ArrayList<>();
		queue.add(start);
		while (queue.size() > 0) {
			Position PointGrid = queue.remove(0);
			if (PointGrid.row() != 0)
				checkPointGrid(PointGrid, new Position(PointGrid.row() - 1, PointGrid.col()), bestTrail, queue, part);
			if (PointGrid.row() != terrain.size() - 1)
				checkPointGrid(PointGrid, new Position(PointGrid.row() + 1, PointGrid.col()), bestTrail, queue, part);
			if (PointGrid.col() != 0)
				checkPointGrid(PointGrid, new Position(PointGrid.row(), PointGrid.col() - 1), bestTrail, queue, part);
			if (PointGrid.col() != terrain.get(0).length() - 1)
				checkPointGrid(PointGrid, new Position(PointGrid.row(), PointGrid.col() + 1), bestTrail, queue, part);
		}
		return bestTrail.get(end);
	}

	public static void checkPointGrid(Position p, Position direction, Map<Position, Integer> bestTrail, List<Position> queue, String part) {
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
}
