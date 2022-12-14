package main.java.org.aoc;

import main.java.org.aoc.tools.Converter;
import main.java.org.aoc.tools.FileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {
	static String INPUT = FileReader.getFileAsString("13");
	static List<List<String>> input = Converter.stringToListOfListOfStrings(INPUT);
	static List<CompareNode> node = new ArrayList<>();


	public static void main(String[] args) {
		System.out.println(task1());
		System.out.println(task2());
	}

	private static int task1() {
		int sum = 0;
		for (int i = 0; i < input.size(); i++) {
			Parser parser = new Parser(input.get(i).get(0), 0);
			ComparingList list1 = ComparingList.parse(parser);
			node.add(list1);
			parser = new Parser(input.get(i).get(1), 0);
			ComparingList list2 = ComparingList.parse(parser);
			node.add(list2);
			if (compare(list1, list2) > 0)
				sum += (i + 1);
		}
		return sum;
	}

	private static int task2() {
		CompareNode div2 = ComparingList.parse(new Parser("[[2]]", 0));
		node.add(div2);
		CompareNode div6 = ComparingList.parse(new Parser("[[6]]", 0));
		node.add(div6);

		node.sort(Day13::compare);
		Collections.reverse(node);

		int div2Index = 0;
		int div6Index = 0;
		for (int i = 0; i < node.size(); i++) {
			CompareNode compNode = node.get(i);
			if (compNode == div2)
				div2Index = i + 1;
			if (compNode == div6)
				div6Index = i + 1;
		}
		return div2Index * div6Index;
	}

	private static int compare(CompareNode n1, CompareNode n2) {
		if (n1 instanceof CompareNumber num1 && n2 instanceof CompareNumber num2)
			return num2.value - num1.value;
		else if (n1 instanceof ComparingList l1 && n2 instanceof ComparingList l2) {
			for (int i = 0; i < l1.nodes.size(); i++) {
				if (l2.nodes.size() == i)
					return -1;
				int val = compare(l1.nodes.get(i), l2.nodes.get(i));
				if (val != 0)
					return val;
			}
			if (l1.nodes.size() < l2.nodes.size())
				return 1;
			else
				return 0;

		} else if (n1 instanceof CompareNumber num1) {
			ComparingList list = new ComparingList();
			list.nodes.add(num1);
			return compare(list, n2);
		} else {
			ComparingList list = new ComparingList();
			list.nodes.add(n2);
			return compare(n1, list);
		}
	}

	private static class Parser {
		public String string;
		public int index;


		public Parser(String string, int index) {
			this.string = string;
			this.index = index;
		}

		@Override
		public String toString() {
			return "Parser{s='" + string + '\'' + ", index=" + index + '}';
		}
	}

	private static class CompareNode {

	}

	private static class ComparingList extends CompareNode {
		public List<CompareNode> nodes = new ArrayList<>();

		public static ComparingList parse(Parser parser) {
			ComparingList list = new ComparingList();
			parser.index++;
			char c = parser.string.charAt(parser.index);
			if (c != ']') {
				do {
					if (c == ',')
						parser.index++;
					else if (c == '[')
						list.nodes.add(ComparingList.parse(parser));
					else
						list.nodes.add(CompareNumber.parse(parser));
					c = parser.string.charAt(parser.index);

				} while (c!= ']');
			}
			parser.index++;

			return list;
		}
	}

	private static class CompareNumber extends CompareNode {
		public int value;

		public CompareNumber(int value) {
			this.value = value;
		}

		public static CompareNumber parse(Parser parser) {
			int comma = parser.string.indexOf(",", parser.index);

			comma = comma == -1 ? parser.string.length() : comma;
			int sqrBracket = parser.string.indexOf("]", parser.index);
			sqrBracket = sqrBracket == -1 ? parser.string.length() : sqrBracket;
			String numStr = parser.string.substring(parser.index, Math.min(comma, sqrBracket));
			parser.index += numStr.length();
			return new CompareNumber(Integer.parseInt(numStr));
		}
	}
}