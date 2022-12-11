package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
	static List<String> INPUT = FileReader.getFileAsList("AoC_Day11.txt");
	static List<String> input = INPUT.stream().map(String::trim).toList();
	static List<Monkey> monkeyList = new ArrayList<>();
	static Map<Integer, Monkey> monkeyMap = new HashMap<>();
	static List<Integer> STOPS = List.of(1, 20, 9999, 10000);
	static Monkey monkey;
	static int bigModulus = 1;


	public static void main(String[] args) {
		checkPatterns();
		System.out.println(bigModulus);

		//part1();
		part2();
	}
	private static void part1() {
		for (int i = 0; i < 20; i++) {
			for (Monkey monkey1 : monkeyList) {
				monkey1.roundPart1(monkeyMap);
			}
		}
		List<Integer> inspections = new ArrayList<>();
		for (Monkey monkey1 : monkeyList) {
			inspections.add(monkey1.getInspections());
		}

		inspections.sort((a, b) -> b - a);
		System.out.println(BigInteger.valueOf(inspections.get(0)).multiply(BigInteger.valueOf(inspections.get(1))));
	}

	private static void part2() {
		for (int i = 0; i < 10000; i++) {

			if (STOPS.contains(i)) {
				System.out.println("== After round " + i + " ==");
				for (Monkey monkey1 : monkeyList)
					monkey1.printInspections();
				System.out.println();
			}
			for (Monkey monkey1 : monkeyList)
				monkey1.roundPart2(monkeyMap, bigModulus);

		}

		List<Integer> inspections = new ArrayList<>();
		for (Monkey monkey1 : monkeyList)
			inspections.add(monkey1.getInspections());

		inspections.sort((a, b) -> b - a);

		System.out.println(BigInteger.valueOf(inspections.get(0)) + "* " + (BigInteger.valueOf(inspections.get(1))));
		System.out.println(BigInteger.valueOf(inspections.get(0)).multiply(BigInteger.valueOf(inspections.get(1))));
	}

	private static void checkPatterns() {
		Pattern monkeyPattern = Pattern.compile("Monkey (\\d):");
		Pattern multiplyPattern = Pattern.compile("Operation: new = old \\* (\\d+)");
		Pattern additionPattern = Pattern.compile("Operation: new = old \\+ (\\d+)");
		Pattern testPattern = Pattern.compile("Test: divisible by (\\d+)");
		Pattern truePattern = Pattern.compile("If true: throw to monkey (\\d+)");
		Pattern falsePattern = Pattern.compile("If false: throw to monkey (\\d+)");
		for (String line : input) {
			Matcher monkeyMatch = monkeyPattern.matcher(line);
			if (monkeyMatch.find()) {
				monkey = new Monkey(monkeyMatch.group(1));
				monkeyList.add(monkey);
			}
			if (!monkeyList.isEmpty()) {
				Matcher multiplyMatch = multiplyPattern.matcher(line);
				if (multiplyMatch.find())
					monkey.addMultiplyValue(multiplyMatch.group(1));

				Matcher additionMatch = additionPattern.matcher(line);
				if (additionMatch.find())
					monkey.addAdditionValue(additionMatch.group(1));

				Matcher testMatch = testPattern.matcher(line);
				if (testMatch.find()) {
					monkey.addTestValue(testMatch.group(1));
					bigModulus *= Integer.parseInt(testMatch.group(1));
				}

				Matcher trueMatch = truePattern.matcher(line);
				if (trueMatch.find())
					monkey.addTrueValue(trueMatch.group(1));

				Matcher falseMatch = falsePattern.matcher(line);
				if (falseMatch.find())
					monkey.addFalseValue(falseMatch.group(1));

				if (line.startsWith("Starting items: ")) {
					String[] items = line.substring("Starting items: ".length()).split(", ");
					for (String item : items)
						monkey.addItem(item);
				}

				if (line.equals("Operation: new = old * old"))
					monkey.multiSelf();
			}
		}
		for (Monkey monkey1 : monkeyList) {
			monkeyMap.put(monkey1.getGroup(), monkey1);
		}
	}
}

class Monkey {
	private final int group;
	private int multi = -1;
	private int addition = -1;
	private int test = -1;
	private int trueValue;
	private int falseValue;
	private final Deque<Long> items = new ArrayDeque<>();
	private int inspections = 0;

	public Monkey(String group) {
		this.group = Integer.parseInt(group);
	}

	public void addMultiplyValue(String group) {
		this.multi = Integer.parseInt(group);
	}

	public void addAdditionValue(String group) {
		this.addition = Integer.parseInt(group);
	}

	public void addTestValue(String group) {
		this.test = Integer.parseInt(group);
	}

	public void addTrueValue(String group) {
		this.trueValue = Integer.parseInt(group);
	}

	public void addFalseValue(String group) {
		this.falseValue = Integer.parseInt(group);
	}

	public Integer getGroup() {
		return this.group;
	}

	public void addItem(String item) {
		this.items.add(Long.parseLong(item));
	}

	public void addItem(long item) {
		this.items.add(item);
	}

	public void roundPart1(Map<Integer, Monkey> monkeyMap) {
		while (!this.items.isEmpty()) {
			long item = this.items.pop();
			item = calcItem(item);
			inspections++;
			item = Math.floorDiv(item, 3);

			if (item % test == 0)
				monkeyMap.get(trueValue).addItem(item);
			else
				monkeyMap.get(falseValue).addItem(item);
		}
	}

	private long calcItem(long item) {
		if (multi == -2)
			item *= item;
		else if (multi != -1)
			item *= multi;
		else if (addition != -1)
			item += addition;
		return item;
	}

	public void roundPart2(Map<Integer, Monkey> monkeyMap, int modulus) {
		while (!this.items.isEmpty()) {
			long item = this.items.pop();
			item = calcItem(item);
			inspections++;
			item %= modulus;

			if (item % test == 0)
				monkeyMap.get(trueValue).addItem(item);
			else
				monkeyMap.get(falseValue).addItem(item);
		}
	}

	public void multiSelf() {
		this.multi = -2;
	}

	public Integer getInspections() {
		return this.inspections;
	}

	public void printInspections() {
		System.out.println("Monkey " + group + " inspected items " + inspections + " times");
	}
}