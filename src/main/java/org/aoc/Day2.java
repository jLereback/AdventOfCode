package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.Arrays;
import java.util.List;

public class Day2 {
	public static final String ROCK = "A", PAPER = "B", SCISSORS = "C";
	public static final String MY_ROCK = "X", MY_PAPER = "Y", MY_SCISSORS = "Z";
	public static final String LOSE = "X", DRAW = "Y";
	public static final int DRAW_POINTS = 3, WINNER_POINTS = 6;
	public static final int ROCK_POINT = 1, PAPER_POINTS = 2, SCISSORS_POINTS = 3;
	static List<String> encryptedGuide;

	public static void main(String[] args) {
		encryptedGuide = Arrays.stream(
				FileReader.getFileAsString("AoC_Day2.txt").split("\\n")).toList();
		System.out.println(getExpectedScore());
		System.out.println(getIntendedScore());
	}

	private static int getExpectedScore() {
		int myScore = 0;
		for (String eachTurn : encryptedGuide) {
			String oppo = String.valueOf(eachTurn.charAt(0));
			String myMove = String.valueOf(eachTurn.charAt(2));

			myScore += getPointsForMyMove(myMove);

			myScore += getPointsPerTurn(oppo, myMove);
		}
		return myScore;
	}

	private static int getPointsForMyMove(String myMove) {
		int points = 0;

		if (myMove.equals("X") || myMove.equals(ROCK))
			points += ROCK_POINT;
		else if (myMove.equals("Y") || myMove.equals(PAPER))
			points += PAPER_POINTS;
		else
			points += SCISSORS_POINTS;
		return points;
	}

	private static int getPointsPerTurn(String oppo, String myMove) {
		int points = 0;

		if (isWinner(oppo, myMove))
			points += WINNER_POINTS;
		else if (isDraw(oppo, myMove))
			points += DRAW_POINTS;
		return points;
	}

	private static boolean isWinner(String oppo, String myMove) {
		return (oppo.equals(ROCK) && (myMove.equals(MY_PAPER) || myMove.equals(PAPER))) ||
				(oppo.equals(PAPER) && (myMove.equals(MY_SCISSORS) || myMove.equals(SCISSORS))) ||
				(oppo.equals(SCISSORS) && (myMove.equals(MY_ROCK) || myMove.equals(ROCK)));
	}

	private static boolean isDraw(String oppo, String myMove) {
		return (oppo.equals(ROCK) && (myMove.equals(MY_ROCK) || myMove.equals(oppo))) ||
				(oppo.equals(PAPER) && (myMove.equals(MY_PAPER) || myMove.equals(oppo))) ||
				(oppo.equals(SCISSORS) && (myMove.equals(MY_SCISSORS) || myMove.equals(oppo)));
	}


	private static int getIntendedScore() {
		int myScore = 0;
		for (String eachTurn : encryptedGuide) {
			String oppo = String.valueOf(eachTurn.charAt(0));
			String myMove = String.valueOf(eachTurn.charAt(2));

			if (myMove.equals(LOSE)) {
				if (oppo.equals(ROCK))
					myMove = SCISSORS;
				else if (oppo.equals(PAPER))
					myMove = ROCK;
				else
					myMove = PAPER;
			} else if (myMove.equals(DRAW)) {
				if (oppo.equals(ROCK))
					myMove = ROCK;
				else if (oppo.equals(PAPER))
					myMove = PAPER;
				else
					myMove = SCISSORS;
			} else {
				if (oppo.equals(ROCK))
					myMove = PAPER;
				else if (oppo.equals(PAPER))
					myMove = SCISSORS;
				else
					myMove = ROCK;
			}
			myScore += getPointsForMyMove(myMove);

			myScore += getPointsPerTurn(oppo, myMove);
		}
		return myScore;
	}
}