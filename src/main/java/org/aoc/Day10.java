package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.List;

public class Day10 {
	static List<String> LIST_INPUT = FileReader.getFileAsList("AoC_Day10.txt");
	static List<String[]> INPUT = LIST_INPUT.stream().map(line -> line.split(" ")).toList();
	static List<Integer> STOPS = List.of(20, 60, 100, 140, 180, 220);
	static int ROW = 6;
	static int WIDTH = 40;
	static int x1 = 1;
	static int x2 = 1;
	static int signal = 0;

	public static void main(String[] args) {
		System.out.println(INPUT);
		System.out.println(STOPS.toString());

		System.out.println(part1());
		part2();
	}

	private static int part1() {
		int cycle = 0;
		for (String[] line : INPUT)
			cycle = checkCycle(cycle, line);
		return signal;
	}

	private static int checkCycle(int cycle, String[] line) {
		if (line[0].equals("noop"))
			cycle = checkSignal(cycle);
		else if (line[0].contains("addx"))
			cycle = checkSignalAndX(line, cycle);
		return cycle;
	}

	private static int checkSignal(int cycle) {
		cycle++;
		if (STOPS.contains(cycle))
			signal += cycle * x1;
		return cycle;
	}

	private static int checkSignalAndX(String[] line, int cycle) {
		cycle = checkSignal(cycle);
		cycle = checkSignal(cycle);
		x1 += Integer.parseInt(line[1]);
		return cycle;
	}


	private static void part2() {
		int cycle = 0;

		if (cycle%40 == 0 ) {

		}

		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < WIDTH; j++) {

			}

/*

			if (line[0].equals("noop"))
				checkSignal(cycle);
			else if (line[0].contains("addx"))
				checkSignalAndX(line, cycle);
*/

		}

	}
}
