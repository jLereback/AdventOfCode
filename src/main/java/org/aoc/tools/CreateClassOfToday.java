package main.java.org.aoc.tools;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CreateClassOfToday {
	static int day = java.time.LocalDate.now().getDayOfMonth();

	public static void main(String[] args) throws Exception {
		Path path = Path.of("src/main/java/org/aoc/Day" + day + ".java");
		createClass(path);
	}

	public static void createClass(Path path) {
		try {
			Files.writeString(path, CLASS, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	static String CLASS = String.format("""
			package main.java.org.aoc;
			   
			import main.java.org.aoc.tools.FileReader;
			   
			public class Day14 {
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
			
	
			}""",day);

}