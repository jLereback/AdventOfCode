package main.java.org.aoc;

import java.util.Objects;

public class Position {
	private int x;
	private int y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	Position getPosition() {
		return this;
	}
	void moveUP() {
		this.setY(this.getY() - 1);
	}
	void moveDOWN() {
		this.setY(this.getY() + 1);
	}
	void moveLEFT() {
		this.setX(this.getX() - 1);
	}
	void moveRIGHT() {
		this.setX(this.getX() + 1);
	}
	public Position getCopy(Position position) {
		return new Position(position.getX(), position.getY());
	}
	@Override
	public String toString() {
		return "Position{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Position position)) return false;
		return x == position.x && y == position.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
