package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.*;

public class Day6 {

	static String input = FileReader.getFileAsString("AoC_Day6.txt");

	public static void main(String[] args) {
		//part1 - 4 in row;
		calculate(4);

		//part2 - 14 in row;
		calculate(14);

	}

	private static void calculate(int numInRow) {
		Set<Character> list = new HashSet<>();
		for (int i = 0; i < input.length()-numInRow; i++) {
			for (int j = 0; j < numInRow; j++)
				list.add(input.charAt(i + j));
			if (list.size() == numInRow) {
				System.out.println(i+numInRow);
				break;
			}
			list.clear();
		}
	}
}