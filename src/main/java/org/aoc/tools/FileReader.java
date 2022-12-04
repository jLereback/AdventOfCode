package main.java.org.aoc.tools;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FileReader {
	private static File getFile(String name) {
		Path path = Path.of("src/main/data", name);
		return path.toFile();
	}
	public static String getFileAsString(String name) {
		String content = null;
		try {
			List<String> lines = Files.readLines(getFile(name), StandardCharsets.UTF_8);
			content = String.join(System.lineSeparator(), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static List<String> getFileAsList(String name) {
		List<String> lines = null;
		try {
			lines = Files.readLines(getFile(name), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		return lines.stream().toList();
	}

	public static List<List<String>> getFileAsListOfListOfString(String name, String regex) {
		List<String> lines = null;
		try {
			lines = Files.readLines(getFile(name), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		return lines.stream().map(list -> Arrays.stream(list.split(regex)).toList()).toList();
	}
}