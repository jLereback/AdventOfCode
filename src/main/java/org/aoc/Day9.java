package main.java.org.aoc;

import main.java.org.aoc.tools.FileReader;

import java.util.*;

public class Day9 {
	static List<List<String>> INPUT = FileReader.getFileAsListOfListOfString("AoC_Day9.txt", " ");


	static int HX = 0;
	static int HY = 0;
	static int TX = 0;
	static int TY = 0;
	static Position HEAD = new Position(HX, HY);
	static Position TAIL = new Position(TX, TY);
	static List<Position> headPos = new ArrayList<>();

	static List<Position> tailPos = new ArrayList<>();

	static Deque<Position> tailpos2 = new ArrayDeque<>();


	public static void main(String[] args) {


		task1();
		System.out.println(headPos);
		System.out.println(tailPos);


/*		var tst = tailPos.stream().collect(Collectors.toSet());
		tst.stream().forEach(position -> tailpos2.push(position));*/

//		System.out.println(tailpos2);
		System.out.println(headPos.size());
		System.out.println(tailPos.size());
//		System.out.println(tailpos2.size());


	}

	private static void task1() {
		moveHead();
int count = 0;
		for (int i = 0; i < 24; i++) {
count++;
			if (tailPos.isEmpty()) {
				tailPos.add(TAIL);
				continue;
			}

			boolean b = checkClose(headPos.get(i), tailPos.get(tailPos.size()-1));

			System.out.println(b);


			if (!b) {
				if (tailPos.contains(headPos.get(i - 1)))
					break;
				else
					tailPos.add(headPos.get(i - 1));
			}


		}
		System.out.println(count);

	}

	private static void moveHead() {
		for (List<String> s : INPUT) {
			for (int j = 0; j < Integer.parseInt(s.get(1)); j++) {

				//System.out.print(s.get(1));
				if (Objects.equals(s.get(0), "R")) {
					HEAD.moveRIGHT();
					headPos.add(HEAD.getCopy(HEAD));
					//System.out.println(HEAD.getPosition().toString());
					//System.out.println("RIGHT");
				} else if (Objects.equals(s.get(0), "L")) {
					HEAD.moveLEFT();
					headPos.add(HEAD.getCopy(HEAD));
					//System.out.println("LEFT");

				} else if (Objects.equals(s.get(0), "D")) {
					HEAD.moveDOWN();
					headPos.add(HEAD.getCopy(HEAD));
					//System.out.println("DOWN");
				} else if (Objects.equals(s.get(0), "U")) {
					HEAD.moveUP();
					headPos.add(HEAD.getCopy(HEAD));
					//System.out.println("UP");
				}
			}
		}
	}

	public static Boolean checkClose(Position headPo, Position tailPo) {
		return (headPo.getX() == tailPo.getX() - 1) && (headPo.getY() == tailPo.getY() - 1) ||
				(headPo.getX() == tailPo.getX() - 1) && (headPo.getY() == tailPo.getY()) ||
				(headPo.getX() == tailPo.getX() - 1) && (headPo.getY() == tailPo.getY() + 1) ||
				(headPo.getX() == tailPo.getX()) && (headPo.getY() == tailPo.getY() - 1) ||
				(headPo.getX() == tailPo.getX()) && (headPo.getY() == tailPo.getY()) ||
				(headPo.getX() == tailPo.getX()) && (headPo.getY() == tailPo.getY() + 1) ||
				(headPo.getX() == tailPo.getX() + 1) && (headPo.getY() == tailPo.getY() - 1) ||
				(headPo.getX() == tailPo.getX() + 1) && (headPo.getY() == tailPo.getY()) ||
				(headPo.getX() == tailPo.getX() + 1) && (headPo.getY() == tailPo.getY() + 1);
	}

}

/*

	public static Boolean checkClose(Position headPo) {
		return  (headPo.getX(4) == TAIL.getX(3) - 1) && (headPo.getY(-1) == TAIL.getY(-2) - 1) ||
				(headPo.getX(4) == TAIL.getX(3) - 1) && (headPo.getY(-1) == TAIL.getY(-1)    ) ||
				(headPo.getX(4) == TAIL.getX(3) - 1) && (headPo.getY(-1) == TAIL.getY( 0) + 1) ||
				(headPo.getX(4) == TAIL.getX(4)    ) && (headPo.getY(-1) == TAIL.getY(-2) - 1) ||
				(headPo.getX(4) == TAIL.getX(4)    ) && (headPo.getY(-1) == TAIL.getY(-1)    ) ||
				(headPo.getX(4) == TAIL.getX(4)    ) && (headPo.getY(-1) == TAIL.getY( 0) + 1) ||
				(headPo.getX(4) == TAIL.getX(5) + 1) && (headPo.getY(-1) == TAIL.getY(-2) - 1) ||
				(headPo.getX(4) == TAIL.getX(5) + 1) && (headPo.getY(-1) == TAIL.getY(-1)    ) ||
				(headPo.getX(4) == TAIL.getX(5) + 1) && (headPo.getY(-1) == TAIL.getY( 0) + 1);

	}

}

..##..
...##.
.####.
....#.
s###..  13

== Initial State ==

HEAD 0, 0
TAIL 0, 0
......
......
......
......
H.....  (H covers T, s)

== R 4 ==

HEAD 1, 0
TAIL 0, 0
......
......
......  Sparas ej dubblett
......
TH....  (T covers s)

HEAD 2, 0
TAIL 1, 0
......
......
......
......
sTH...

HEAD 3, 0
TAIL 2, 0
......
......
......
......
s.TH..

HEAD 4, 0
TAIL 3, 0
......
......
......
......
s..TH.

== U 4 ==
HEAD 4, -1
TAIL 3, 0
......
......
......  Sparas ej dubblett
....H.
s..T..

HEAD 4, -2
TAIL 4, -1
......
......
....H.
....T.
s.....

HEAD 4, -3
TAIL 4, -2
......
....H.
....T.
......
s.....

HEAD 4, -4
TAIL 4, -3
....H.
....T.
......
......
s.....

== L 3 ==


HEAD 3, -4
TAIL 4, -3
...H..
....T.  Sparas ej dubblett
......
......
s.....

HEAD 2, -4
TAIL 3, -4
..HT..
......
......
......
s.....

HEAD 1, -4
TAIL 2, -4
.HT...
......
......
......
s.....

== D 1 ==

HEAD 1, -3
TAIL 2, -4
..T...  Sparas ej dubblett
.H....
......
......
s.....

== R 4 ==

HEAD 2, -3
TAIL 2, -4
..T...  Sparas ej dubblett
..H...
......
......
s.....

HEAD 3, -3
TAIL 2, -4
..T...  Sparas ej dubblett
...H..
......
......
s.....


HEAD 4, -3
TAIL 3, -3
......
...TH.
......
......
s.....

-------------------------------------------------------------
HEAD 5, -3
TAIL 4, -3
......
....TH		Sparas ej dubblett
......
......
s.....

== D 1 ==

HEAD 5, -2
TAIL 4, -3
......
....T.		Sparas ej dubblett
.....H
......
s.....

== L 5 ==

HEAD 4, -2
TAIL 4, -3
......
....T.  Sparas ej dubblett
....H.
......
s.....

HEAD 3, -2
TAIL 4, -3
......
....T.  Sparas ej dubblett
...H..
......
s.....
----------------------------------------------------------
HEAD 2, -2
TAIL 3, -2
......
......
..HT..  REGISTRERAS EJ
......
s.....

HEAD 1, -2
TAIL 2, -2
......
......
.HT...  REGISTRERAS EJ
......
s.....


HEAD 0, -2
TAIL 1, -2
......
......
HT....  REGISTRERAS EJ
......
s.....

== R 2 ==

HEAD 1, -2
TAIL 1, -2
......
......
.H....  (H covers T)  Sparas ej dubblett
......
s.....

HEAD 2, -2
TAIL 1, -2
......
......
.TH...  Sparas ej dubblett
......
s.....

..##..
...##.
.####.
....#.
s###..

*/
