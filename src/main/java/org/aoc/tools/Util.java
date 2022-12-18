package main.java.org.aoc.tools;

import java.util.Objects;

public class Util {

	public record Position(int row, int col) {

		@Override
		public String toString() {
			return "Position{" +
				   "row=" + row +
				   ", col=" + col +
				   '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Position point)) return false;
			return row == point.row && col == point.col;
		}

		@Override
		public int hashCode() {
			return Objects.hash(row, col);
		}
	}

	public record Point(int x, int y) {

		@Override
		public String toString() {
			return "Point{" +
				   "x=" + x +
				   ", y=" + y +
				   '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Point point)) return false;
			return x == point.x && y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}



}
