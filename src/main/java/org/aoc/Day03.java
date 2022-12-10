package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Day03 {
	public static void main(String[] args) {
		String input = FileReader.getFileAsString("AoC_Day03.txt");

		System.out.println(solution1(input));
		System.out.println(solution2(input));
	}

	private static int solution1(String input) {
		return input.lines()
				.map(Day03::commonCharsInLine)
				.mapToInt(chars -> chars.stream().mapToInt(Day03::convertCharToInt).sum())
				.sum();
	}

	private static Set<Character> commonCharsInLine(String line) {
		Set<Character> compartment1 = new HashSet<>();
		Set<Character> compartment2 = new HashSet<>();
		for (int i = 0; i < line.length() / 2; i++)
			compartment1.add(line.charAt(i));

		for (int i = line.length() / 2; i < line.length(); i++)
			compartment2.add(line.charAt(i));

		compartment1.retainAll(compartment2);
		return compartment1;
	}

	private static int solution2(String input) {
		var lines = input.lines().toList();
		return IntStream.iterate(0, i -> i < lines.size(), i -> i + 3)
				.mapToObj(i -> getCommonCharInLines(lines.get(i), lines.get(i + 1), lines.get(i + 2)))
				.mapToInt(chars -> chars.stream().mapToInt(Day03::convertCharToInt).sum())
				.sum();
	}

	private static Set<Character> getCommonCharInLines(String... lines) {
		return Arrays.stream(lines)
				.map(Day03::toSet)
				.reduce(getReducedCharSet()).get();
	}

	private static BinaryOperator<Set<Character>> getReducedCharSet() {
		return (a, b) -> {
			a.retainAll(b);
			return a;
		};
	}

	private static Set<Character> toSet(String line) {
		Set<Character> result = new HashSet<>();
		for (var ch : line.toCharArray()) {
			result.add(ch);
		}
		return result;
	}

	private static int convertCharToInt(Character ch) {
		if (Character.isLowerCase(ch))
			return (int) ch - (int) 'a' + 1;
		else
			return (int) ch - (int) 'A' + 27;
	}
}