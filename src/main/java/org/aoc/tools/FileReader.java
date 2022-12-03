package main.java.org.aoc.tools;

import com.google.common.io.Files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class FileReader {
	public static String getFileAsString(String name) {
		Path path = Path.of("src/se/aoc/data", name);

		String content = null;
		try {
			List<String> lines = Files.readLines(path.toFile(), StandardCharsets.UTF_8);
			content = String.join(System.lineSeparator(), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
}