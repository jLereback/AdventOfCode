package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.List;

public class Day10 {
	static List<String[]> INPUT = FileReader.getFileAsList("AoC_Day10.txt")
			.stream().map(line -> line.split(" ")).toList();
	static List<Integer> STOPS = List.of(20, 60, 100, 140, 180, 220);
	static int WIDTH = 40;
	static int cycle = 0;
	static int x = 1;
	static int position = 1;
	static int signal = 0;

	public static void main(String[] args) {
		part1();
	}

	private static void part1() {
		for (String[] line : INPUT)
			checkCycle(line);
		System.out.println("Part1: " + signal);
	}

	private static void checkCycle(String[] line) {
		if (line[0].equals("noop"))
			checkSignal(line);
		else if (line[0].contains("addx"))
			checkSignalAndX(line);
	}

	private static void checkSignal(String[] line) {
		cycle++;
		part2(line);
		if (STOPS.contains(cycle))
			signal += cycle * x;
	}

	private static void checkSignalAndX(String[] line) {
		checkSignal(line);
		checkSignal(line);
		x += Integer.parseInt(line[1]);
	}

	private static void part2(String[] line) {
		if (cycle % WIDTH == 0)
			System.out.println();
		else if (cycle % WIDTH == position || cycle % WIDTH == position - 1 || cycle % WIDTH == position + 1)
		System.out.print(" #");
        else
		System.out.print("  ");
		if (line.length == 2)
			position  = (x + Integer.parseInt(line[1])) % WIDTH;
	}
}