package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {

	static String input = FileReader.getFileAsString("AoC_Day6.txt");
	;

	public static void main(String[] args) {
		input = FileReader.getFileAsString("AoC_Day6.txt");
		var regex = "[a-z]";

		var matcher = Pattern.compile(regex).matcher(input);

		System.out.println(matcher);
		//part1(4, matcher);
		part11(4, input);
	}

	private static void part11(int numInRow, String input) {
		int count = 0;
		for (int i = 3; i < input.length(); i++) {
			char ch = input.charAt(i);


			for (int j = 0; j < numInRow; j++) {
				char c = input.charAt(i-j);
				if (ch == c)
					System.out.println(c);
			}
			count++;
		}
	}

	private static void part1(int numInRow, Matcher matcher) {
		for (int i = 0; i < input.length(); i++) {
			while (matcher.find()) {
				System.out.println("Full match: " + matcher.group());


/*			for (int i = 1; i <= matcher.groupCount(); i++) {
				System.out.println("Group " + i + ": " + matcher.group(i));
			}*/
			}
		}
	}


}