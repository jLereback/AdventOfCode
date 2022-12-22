package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.*;

public class Day18 {
	static List<String> input = FileReader.getFileAsList("18_sample");
	static Set<Cube> cubes = new HashSet<>();
	static int totalSides = 0;
	static int totalAirPocketSides = 0;

	public static void main(String[] args) {
		input.forEach(line -> cubes.add(Cube.of(line)));
		part1();
		part2();
	}

	private static void part1() {
		int cubeSides;
		for (Cube c : cubes) {
			cubeSides = 6;
			for (Cube c2 : cubes)
				if (c.isNextTo(c2))
					cubeSides--;
			totalSides += cubeSides;
		}
		System.out.println(totalSides);
	}

	private static void part2() {
		BigCube bigCube = getTotalCubeVolume();
		Cube min = bigCube.min;
		Cube max = bigCube.max;

		Cube start = bigCube.min;

		Set<Cube> air = new HashSet<>();
		floodFill(start, air, cubes, bigCube);

		Set<Cube> allCubes = new HashSet<>();

		for (int x = min.x; x <= max.x; x++)
			for (int y = 0; y <= max.y; y++)
				for (int z = 0; z <= max.z; z++)
					allCubes.add(Cube.of(x, y, z));

		allCubes.removeAll(air);
		allCubes.removeAll(cubes);

		allCubes.forEach(System.out::println);
		var airPocketSides= 0;
		for (var c : allCubes) {
			airPocketSides = 6;
			for (Cube c2 : allCubes)
				if (c.isNextTo(c2))
					airPocketSides--;
			totalAirPocketSides += airPocketSides;
		}
		System.out.println(airPocketSides);
		System.out.println(totalAirPocketSides);
	}

	private static void floodFill(Cube cube, Set<Cube> air, Set<Cube> lava, BigCube bigCube) {
		if (bigCube.contains(cube))
			return;

		if (air.contains(cube) || lava.contains(cube))
			return;

		air.add(cube);

		floodFill(Cube.of(cube.x - 1, cube.y, cube.z), air, lava, bigCube);
		floodFill(Cube.of(cube.x + 1, cube.y, cube.z), air, lava, bigCube);
		floodFill(Cube.of(cube.x, cube.y - 1, cube.z), air, lava, bigCube);
		floodFill(Cube.of(cube.x, cube.y + 1, cube.z), air, lava, bigCube);
		floodFill(Cube.of(cube.x, cube.y, cube.z - 1), air, lava, bigCube);
		floodFill(Cube.of(cube.x, cube.y, cube.z + 1), air, lava, bigCube);
	}

	private static BigCube getTotalCubeVolume() {
		int[] cubeX = cubes.stream().mapToInt(cube -> cube.x).toArray();
		int[] cubeY = cubes.stream().mapToInt(cube -> cube.y).toArray();
		int[] cubeZ = cubes.stream().mapToInt(cube -> cube.z).toArray();
		return new BigCube(
				new Cube(getMin(cubeX), getMin(cubeY), getMin(cubeZ)),
				new Cube(getMax(cubeX), getMax(cubeY), getMax(cubeZ)));
	}

	private static int getMin(int[] cube) {
		return Arrays.stream(cube).min().orElseThrow();
	}

	private static int getMax(int[] cube) {
		return Arrays.stream(cube).max().orElseThrow();
	}

	private record Cube(int x, int y, int z) {
		List<Cube> cubeNextTo() {
			return List.of(
					Cube.of(x - 1, y, z), Cube.of(x + 1, y, z),
					Cube.of(x, y - 1, z), Cube.of(x, y + 1, z),
					Cube.of(x, y, z - 1), Cube.of(x, y, z + 1));
		}
		static Cube of(int x, int y, int z) {
			return new Cube(x, y, z);
		}

		static Cube of(String line) {
			var xyz = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
			return new Cube(xyz[0], xyz[1], xyz[2]);
		}

		boolean isNextTo(Cube cube) {
			return cubeNextTo().contains(cube);
		}
	}

	private record BigCube(Cube min, Cube max) {
		boolean contains(Cube cube) {
			return min.x < cube.x || cube.x < max.x ||
				   min.y < cube.y || cube.y < max.y ||
				   min.z < cube.z || cube.z < max.z;
		}
	}
}