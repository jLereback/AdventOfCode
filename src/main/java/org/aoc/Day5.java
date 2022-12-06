package main.java.org.aoc;


import main.java.org.aoc.tools.FileReader;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;

public class Day5 {
	public static List<Command> commands = FileReader.getFileAsString("AoC_Day5.txt").lines()
			.map(Command::parse).toList();
	static String input = FileReader.getFileAsString("AoC_Day5_2.txt");

	public static void main(String[] args) {
		List<Deque<String>> crates = new ArrayList<>();

		part1(crates, commands);
		crates.clear();

		part2(crates, commands);
	}

	private static void part1(List<Deque<String>> crates, List<Command> commands) {
		input.lines().forEach(line -> crates.add(new ArrayDeque<>(singletonList(line))));
		commands.forEach(c -> c.executor9000(crates));
		for (Deque<String> d : crates) {
			System.out.print(d.getLast());
		}
	}

	private static void part2(List<Deque<String>> crates, List<Command> commands) {
		input.lines().forEach(line -> crates.add(new ArrayDeque<>(singletonList(line))));
		commands.forEach(c -> c.executor9001(crates));
		for (Deque<String> d : crates) {
			System.out.print(d.getLast());
		}
	}

	private record Command(int count, int from, int to) {
		private static final Pattern pattern = Pattern.compile("move (\\d+) from (\\d) to (\\d)");

		void executor9000(List<Deque<String>> crates) {
			for (int i = 0; i < count; i++)
				crates.get(to).addLast(crates.get(from).pollLast());
		}

		void executor9001(List<Deque<String>> crates) {
			Deque<String> tempDeque = new ArrayDeque<>();
			for (int i = 0; i < count; i++)
				tempDeque.addLast(crates.get(from).pop());
			crates.get(to).addAll(tempDeque);
		}

		static Command parse(String line) {
			var matcher = pattern.matcher(line);
			if (matcher.matches())
				return new Command(
						Integer.parseInt(matcher.group(1)),
						Integer.parseInt(matcher.group(2)) - 1,
						Integer.parseInt(matcher.group(3)) - 1);
			throw new IllegalStateException(line);
		}
	}
}