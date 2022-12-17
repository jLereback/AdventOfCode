package main.java.org.aoc.tools;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FileReader {
	private static File getFile(String name) {
		Path path = Path.of("src/main/data", name);
		return path.toFile();
	}

	private static File getFileWithDay(String day) {
		Path path = Path.of("src/main/data/AoC_Day" + day + ".txt");
		return path.toFile();
	}

	public static String getFileAsString(String fileName) {
		String content = null;
		List<String> lines;
		try {
			if (fileName.contains("AoC"))
				lines = Files.readLines(getFile(fileName), StandardCharsets.UTF_8);
			else
				lines = Files.readLines(getFileWithDay(fileName), StandardCharsets.UTF_8);
			content = String.join(System.lineSeparator(), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static List<String> getFileAsList(String fileName) {
		List<String> lines = null;
		try {
			if (fileName.contains("AoC"))
				lines = Files.readLines(getFile(fileName), StandardCharsets.UTF_8);
			else
				lines = Files.readLines(getFileWithDay(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		return lines.stream().toList();
	}

	public static String[] getFileAsStringArray(String fileName, String regex) {
		String content = null;
		List<String> lines;
		try {
			if (fileName.contains("AoC"))
				lines = Files.readLines(getFile(fileName), StandardCharsets.UTF_8);
			else
				lines = Files.readLines(getFileWithDay(fileName), StandardCharsets.UTF_8);
			content = String.join(System.lineSeparator(), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert content != null;
		return content.split(regex);
	}

	public static List<List<String>> getFileAsListOfListOfString(String fileName, String regex) {
		List<String> lines = null;
		try {
			if (fileName.contains("AoC"))
				lines = Files.readLines(getFile(fileName), StandardCharsets.UTF_8);
			else
				lines = Files.readLines(getFileWithDay(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		return lines.stream().map(list -> Arrays.stream(list.split(regex)).toList()).toList();
	}

	public static List<String[]> getFileAsListOfStringArray(String fileName, String regex) {
		List<String> lines = null;
		try {
			if (fileName.contains("AoC"))
				lines = Files.readLines(getFile(fileName), StandardCharsets.UTF_8);
			else
				lines = Files.readLines(getFileWithDay(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		return lines.stream().toList().stream().map(line -> line.split(regex)).toList();
	}
}