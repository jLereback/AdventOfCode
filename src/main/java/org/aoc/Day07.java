package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day07 {
	static final int LIMIT = 100000;
	static final int DISC_SIZE = 70000000;
	static final int NEED_SPACE = 30000000;
	static List<String> INPUT = FileReader.getFileAsList("AoC_Day07.txt");


	public static void main(String[] args) {
		var root = new FSElement(0, "/", false, null);
		FSElement current = root;
		for (int i = 1; i < INPUT.size(); i++) {
			current = readLines(current, INPUT.get(i));
		}
		fixSizes(root);
		List<FSElement> result = new ArrayList<>();
		for (var e : root.subElements) {
			if (!e.file) {
				result.add(e);
				result.addAll(collectDirs(e));
			}
		}
		part1(result);
		part2(root, result);
	}

	private static void part1(List<FSElement> dirs) {
		System.out.println(dirs.stream().filter(d -> d.size < LIMIT).mapToInt(d -> d.size).sum());
	}

	private static void part2(FSElement root, List<FSElement> dirs) {
		int availableSpace = DISC_SIZE - root.size;
		int needed = NEED_SPACE - availableSpace;
		System.out.println(dirs.stream().filter(d -> d.size >= needed)
				.min(Comparator.comparingInt(a -> a.size)).orElseThrow().size);
	}

	private static FSElement readLines(FSElement current, String line) {
		if (line.startsWith("$ cd"))
			current = changeDir(current, line);
		else if (line.equals("$ ls")) {}
		else if (line.startsWith("dir "))
			readDir(current, line);
		else
			readFile(current, line);
		return current;
	}

	private static void readFile(FSElement current, String line) {
		var parts = line.split(" ");
		int size = Integer.parseInt(parts[0]);
		String name = parts[1];
		current.subElements.add(new FSElement(size, name, true, current));
	}

	private static void readDir(FSElement current, String line) {
		var dirName = line.substring("dir ".length());
		current.subElements.add(new FSElement(0, dirName, false, current));
	}

	private static FSElement changeDir(FSElement current, String line) {
		var dirName = line.substring("$ cd ".length());
		if (dirName.equals("..")) {
			return current.parent;
		} else {
			return current.subElements.stream().filter(fse -> fse.name.equals(dirName)).findAny().orElseThrow();
		}
	}

	private static void fixSizes(FSElement element) {
		for (FSElement e : element.subElements) {
			fixSizes(e);
		}
		if (!element.file) {
			element.size = element.subElements.stream().mapToInt(e -> e.size).sum();
		}
	}

	private static List<FSElement> collectDirs(FSElement element) {
		List<FSElement> result = new ArrayList<>();
		for (var e : element.subElements) {
			if (!e.file) {
				result.add(e);
				result.addAll(collectDirs(e));
			}
		}
		return result;
	}

	private static class FSElement {
		String name;
		int size;
		boolean file;
		List<FSElement> subElements = new ArrayList<>();
		FSElement parent;

		FSElement(int size, String name, boolean file, FSElement parent) {
			this.size = size;
			this.name = name;
			this.file = file;
			this.parent = parent;
		}

	}
}