package main.java.org.aoc;

import main.java.org.aoc.tools.Converter;
import main.java.org.aoc.tools.FileReader;

import java.util.Arrays;

import static java.lang.Integer.*;

public class Day8 {
	static String INPUT = FileReader.getFileAsString("AoC_Day8.txt");
	static int[][] GRID = Converter.stringValuesTo2DIntArray(INPUT);
	static int TOTAL_ROWS = (int) INPUT.lines().count();
	static int MAX_ROW = TOTAL_ROWS - 1;
	static int TOTAL_COLUMNS = GRID[0].length;
	static int MAX_COLUMN = TOTAL_COLUMNS - 1;

	public static void main(String[] args) {
		System.out.println(part1());
		System.out.println(part2());
	}

	private static int part1() {
		boolean[][] visibility = getBooleanArray();
		for (int i = 0; i < TOTAL_ROWS; i++)
			checkVisibilityForRow(visibility, i);
		for (int i = 0; i < TOTAL_COLUMNS; i++)
			checkVisibilityForColumn(visibility, i);
		return checkVisibility(visibility);
	}

	private static int part2() {
		int max = 0;
		for (int i = 0; i < TOTAL_ROWS; i++) {
			for (int j = 0; j < TOTAL_COLUMNS; j++) {
				int score = getScenicScore(i, j);
				max = max(max, score);
			}
		}
		return max;
	}

	private static boolean[][] getBooleanArray() {
		boolean[][] visibility = new boolean[TOTAL_ROWS][TOTAL_COLUMNS];
		for (boolean[] row : visibility) Arrays.fill(row, false);
		return visibility;
	}

	private static void checkVisibilityForRow(boolean[][] visibility, int row) {
		if (isEqual(row, 0) || isEqual(row, MAX_ROW)) {
			for (int i = 0; i < TOTAL_COLUMNS; i++)
				visibility[row][i] = true;
			return;
		}
		int leftMax = 0;
		for (int i = 0; i < TOTAL_COLUMNS; i++) {
			if (GRID[row][i] > leftMax) {
				visibility[row][i] = true;
				leftMax = GRID[row][i];
			}
		}
		int rightMax = 0;
		for (int i = MAX_COLUMN; i >= 0; i--) {
			if (GRID[row][i] > rightMax) {
				visibility[row][i] = true;
				rightMax = GRID[row][i];
			}
		}
	}

	private static boolean isEqual(int term1, int term2) {
		return term1 == term2;
	}

	private static void checkVisibilityForColumn(boolean[][] visibility, int column) {
		if (isEqual(column, 0) || isEqual(column, MAX_COLUMN)) {
			for (int i = 0; i < TOTAL_ROWS; i++) {
				visibility[i][column] = true;
			}
			return;
		}
		int topMax = 0;
		for (int i = 0; i < TOTAL_ROWS; i++) {
			if (GRID[i][column] > topMax) {
				visibility[i][column] = true;
				topMax = GRID[i][column];
			}
		}
		int bottomMax = 0;
		for (int i = MAX_ROW; i >= 0; i--) {
			if (GRID[i][column] > bottomMax) {
				visibility[i][column] = true;
				bottomMax = GRID[i][column];
			}
		}
	}

	public static int checkVisibility(boolean[][] visibility) {
		int count = 0;
		for (int i = 0; i < TOTAL_ROWS; i++) {
			for (int j = 0; j < TOTAL_COLUMNS; j++) {
				count += visibility[i][j] ? 1 : 0;
			}
		}
		return count;
	}

	private static int getScenicScore(int row, int column) {
		int top = getTopVisible(row, column);
		int bottom = getBottomVisible(row, column);
		int left = getLeftVisible(row, column);
		int right = getRightVisible(row, column);
		return top * bottom * left * right;
	}

	private static int getTopVisible(int row, int column) {
		int count = 0;
		if (isEqual(row, 0)) return count;
		int i;
		for (i = row - 1; i >= 0; i--)
			if (GRID[i][column] < GRID[row][column]) count++;
			else break;
		return count + (i >= 0 ? 1 : 0);
	}

	private static int getBottomVisible(int row, int column) {
		int count = 0;
		if (isEqual(row, MAX_ROW)) return count;
		int i;
		for (i = row + 1; i < TOTAL_ROWS; i++)
			if (GRID[i][column] < GRID[row][column]) count++;
			else break;
		return count + (i < TOTAL_ROWS ? 1 : 0);
	}

	private static int getLeftVisible(int row, int column) {
		int count = 0;
		if (isEqual(column, 0)) return count;
		int i;
		for (i = column - 1; i > 0; i--)
			if (GRID[row][i] < GRID[row][column]) count++;
			else break;
		return count + (i >= 0 ? 1 : 0);
	}

	private static int getRightVisible(int row, int column) {
		int count = 0;
		if (isEqual(column, MAX_COLUMN)) return count;
		int i;
		for (i = column + 1; i < TOTAL_COLUMNS - 1; i++)
			if (GRID[row][i] < GRID[row][column]) count++;
			else break;
		return count + (i < TOTAL_COLUMNS ? 1 : 0);
	}
}

