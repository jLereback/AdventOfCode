package main.java.org.aoc.tools;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CreateClassOfToday {
	static int day = java.time.LocalDate.now().getDayOfMonth();

	public static void main(String[] args) throws Exception {
		Path pathOfClass = Path.of("src/main/java/org/aoc/Day" + day + ".java");
		Path pathOfData = Path.of("src/main/data/AoC_Day" + day + ".txt");
		Path pathOfPuzzle = Path.of("src/main/puzzle/puzzle_day" + day + ".md");
		createClass(pathOfClass);
		createTxtFile(pathOfData);
		createMd(pathOfPuzzle);
	}

	private static void createMd(Path pathOfPuzzle) {
		try {
			Files.writeString(pathOfPuzzle, MD, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private static void createTxtFile(Path pathOfData) {
		try {
			Files.writeString(pathOfData, "", StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void createClass(Path pathOfClass) {
		try {
			Files.writeString(pathOfClass, CLASS, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	static String MD = String.format("""
			#  Day %d:\s
			""", day);
	static String CLASS = String.format("""
			package main.java.org.aoc;
			   
			import main.java.org.aoc.tools.FileReader;
			   
			public class Day%d {
				static String INPUT = FileReader.getFileAsString("%d");
			   
				public static void main(String[] args) {
					System.out.println(INPUT);
			   
					part1();
					part2();
				}
			   
				private static void part1() {
			   
				}
			   
				private static void part2() {
			   
				}
			
	
			}""",day, day);

}