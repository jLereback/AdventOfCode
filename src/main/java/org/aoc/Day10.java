package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.List;

public class Day10 {
	static List<String> LIST_INPUT = FileReader.getFileAsList("AoC_Day10.txt");
	static List<String[]> INPUT = LIST_INPUT.stream().map(line -> line.split(" ")).toList();
	static List<Integer> STOPS = List.of(20, 60, 100, 140, 180, 220);
	static int x = 1;
	static int cycle = 0;
	static int signal = 0;

	public static void main(String[] args) {
		System.out.println(INPUT);
		System.out.println(STOPS.toString());

		System.out.println(part1());
	}

	private static int part1() {
		for (String[] line : INPUT) {
			if (line[0].equals("noop"))
				checkSignal();
			else if (line[0].contains("addx"))
				checkSignalAndX(line);
		}
		return signal;
	}

	private static void checkSignal() {
		cycle++;
		if (STOPS.contains(cycle))
			signal += cycle * x;
	}

	private static void checkSignalAndX(String[] line) {
		checkSignal();
		checkSignal();
		x += Integer.parseInt(line[1]);
	}
}
