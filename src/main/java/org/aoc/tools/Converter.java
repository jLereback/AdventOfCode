package main.java.org.aoc.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Converter {
	public static List<List<String>> stringToListOfListOfStrings(String s) {
		List<List<String>> initial = new ArrayList<>();
		initial.add(new ArrayList<>());
		return s.lines().reduce(initial, (subtotal, element) -> {
			if (element.trim().isEmpty()) {
				subtotal.add(new ArrayList<>());
			} else {
				subtotal.get(subtotal.size() - 1).add(element);
			}
			return subtotal;
		}, (list1, list2) -> List.of());
	}

	public static Collection<List<String>> createRows(List<String> inputList, int columnsPerRow) {
		AtomicInteger counter = new AtomicInteger();
		return inputList.stream().collect(Collectors.groupingBy(gr -> counter.getAndIncrement() / columnsPerRow)).values();
	}
}
