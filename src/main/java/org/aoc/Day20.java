package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 {
	static List<String> input = FileReader.getFileAsList("20_sample");
	static List<Long> listOfAddends = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Part1: " + part1());
		System.out.println("Part2: " + part2());
	}

	private static long part1() {
		int multiplier = 1;

		int rounds = 3000;

		List<Number> mixList = getMixList(multiplier);

		List<Number> inputOrder = new ArrayList<>(mixList);

		moveElements(rounds, mixList, inputOrder);


		addAddendsToList(mixList);
		System.out.println(listOfAddends);
		return getSumFromAddends();
	}

	private static long part2() {
		int multiplier = 811589153;
		int rounds = 70;

		List<Number> mixList = getMixList(multiplier);

		List<Number> inputOrder = new ArrayList<>(mixList);

		moveElements(rounds, mixList, inputOrder);

		addAddendsToList(mixList);
		System.out.println(listOfAddends);
		return getSumFromAddends();
	}

	private static List<Number> getMixList(int multiplier) {
		List<Number> mixList = IntStream.range(0, input.size())
				.mapToObj(i -> new Number(Long.parseLong(input.get(i)) * multiplier, i))
				.collect(Collectors.toList());
		return mixList;
	}

	private static void moveElements(int rounds, List<Number> mixList, List<Number> inputOrder) {
		for (int times = 0; times < rounds; times++) {
			for (Number num : inputOrder) {
				int currentIndex = mixList.indexOf(num);
				mixList.remove(currentIndex);
				mixList.add(Math.floorMod(num.value + currentIndex, mixList.size()), num);
				times++;
			}
			times--;
		}
	}

	private static void addAddendsToList(List<Number> mixList) {
		listOfAddends.clear();
		int fromIndex = mixList.indexOf(mixList.stream().filter(n -> n.value == 0).findFirst().get());

		for (int i = 1000; i <= 3000; i += 1000) {
			listOfAddends.add(mixList.get((fromIndex + i) % mixList.size()).value);
		}
	}

	private static long getSumFromAddends() {
		long sum = 0;
		for (Long l : listOfAddends)
			sum += l;
		return sum;
	}

	record Number(long value, int inputIndex) {
	}
}