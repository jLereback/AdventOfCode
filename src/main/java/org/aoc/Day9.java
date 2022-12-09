package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.HashSet;
import java.util.List;

public class Day9 {

	static String INPUT = FileReader.getFileAsString("AoC_Day9.txt");


	public static void main(String[] args) {

		var positionList = INPUT.lines().map(Position::pars).toList();
		System.out.println(part1(positionList));
		System.out.println(part2(positionList));
	}

	private static int part1(List<Position> positionList) {
		return moveRope(positionList, initKnots(2));
	}

	private static int part2(List<Position> positionList) {
		return moveRope(positionList, initKnots(10));
	}

	private static Coordinates[] initKnots(int numKnots) {
		var knots = new Coordinates[numKnots];
		for (int i = 0; i < numKnots; ++i) {
			knots[i] = new Coordinates(0, 0);
		}
		return knots;
	}

	private static int moveRope(List<Position> positionList, Coordinates[] knots) {
		var tailPos = new HashSet<>();
		tailPos.add(new Coordinates(0, 0));
		for (var position : positionList) {
			for (int i = 0; i < position.count; ++i) {
				knots[0] = knots[0].moveKnot(position);
				for (int j = 1; j < knots.length; ++j) {
					knots[j] = getNewTailPos(knots[j - 1], knots[j]);
				}
				tailPos.add(knots[knots.length - 1]);
			}
		}
		return tailPos.size();
	}

	private static Coordinates getNewTailPos(Coordinates head, Coordinates tail) {
		if (tail.checkClose(head))
			return tail;
		else if (tail.y() == head.y()) {
			if (tail.x() < head.x())
				return tail.moveKnot(new Position(1, 0, 0));
			else
				return tail.moveKnot(new Position(-1, 0, 0));
		} else if (tail.x() == head.x()) {
			if (tail.y() < head.y())
				return tail.moveKnot(new Position(0, 1, 0));
			else
				return tail.moveKnot(new Position(0, -1, 0));
		} else if (tail.y() < head.y()) {
			if (tail.x() < head.x())
				return tail.moveKnot(new Position(1, 1, 0));
			else
				return tail.moveKnot(new Position(-1, 1, 0));
		} else {
			if (tail.x() < head.x())
				return tail.moveKnot(new Position(1, -1, 0));
			else
				return tail.moveKnot(new Position(-1, -1, 0));
		}
	}

	private record Position(int xDif, int yDif, int count) {
		static Position pars(String line) {
			var parts = line.split(" ");
			var steps = Integer.parseInt(parts[1]);
			return switch (parts[0]) {
				case "L" -> new Position(-1, 0, steps);
				case "R" -> new Position(1, 0, steps);
				case "U" -> new Position(0, -1, steps);
				case "D" -> new Position(0, 1, steps);
				default -> throw new IllegalStateException("Error");
			};
		}
	}

	private record Coordinates(int x, int y) {
		boolean checkClose(Coordinates coord) {
			return Math.abs(x - coord.x) <= 1 && Math.abs(y - coord.y) <= 1;
		}

		Coordinates moveKnot(Position position) {
			return new Coordinates(x + position.xDif(), y + position.yDif());
		}
	}
}