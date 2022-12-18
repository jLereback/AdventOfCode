package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 {
	static List<String> input = FileReader.getFileAsList("18");
	static List<Cube> cubes = new ArrayList<>();

	public static void main(String[] args) {
		input.forEach(line -> cubes.add(newCube(line)));
		part1();
	}

	private static void part1() {
		int visibleSides;
		int totalVisibleSides = 0;
		for (Cube c : cubes) {
			visibleSides = 6;
			for (Cube c2 : cubes) {
				if (c.equals(c2))
					continue;
				if (c.isNextTo(c2))
					visibleSides--;
			}
			totalVisibleSides += visibleSides;
		}
		System.out.println(totalVisibleSides);
	}

	static Cube newCube(String line) {
		var xyz = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
		return new Cube(xyz[0], xyz[1], xyz[2]);
	}

	record Cube(int x, int y, int z) {
		List<Cube> nextTo() {
			return List.of(
					new Cube(x - 1, y, z), new Cube(x + 1, y, z),
					new Cube(x, y - 1, z), new Cube(x, y + 1, z),
					new Cube(x, y, z - 1), new Cube(x, y, z + 1));
		}

		boolean isNextTo(Cube c2) {
			return nextTo().contains(c2);
		}
	}


}