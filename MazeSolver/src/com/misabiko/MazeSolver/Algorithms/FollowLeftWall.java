package com.misabiko.MazeSolver.Algorithms;

import java.io.PrintWriter;

public class FollowLeftWall extends Algorithm{

	public FollowLeftWall(char[][] maze, PrintWriter out) {
		super(maze, out);
	}
	
	public char[][] solve() {
		out.println("Found the entrance at ("+startX+", "+startY+") and the exit at ("+endX+", "+endY+").");
		out.println("Starting aiming "+currD+".\n");
		
		while (steps<1000000) {
			out.println("Step #"+steps+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
			if (check() == 'E') {
				step();
				out.println("Solution has been found in "+steps+" steps.");
				return maze;
			}else if (check() == 'H') {
				step();
			}else if (deadEndIndex >= 4) {
				if (check() == 'W' || check() == 'D' || check() == 'S') {
					nextDirection();
				}else {
					step();
				}
			}else if (check() == 'W' || check() == 'P' || check() == 'D' || check() == 'S') {
				nextDirection();
			}
			
			steps++;
		}
		
		return maze;
	}

	//	Shifts the current direction (if up then right, else if right then down, etc)
	private void nextDirection() {
		if (currD.ordinal() == 0)
			currD = direction.LEFT;
		else
			currD = direction.values()[currD.ordinal()-1];
		
		deadEndIndex++;
		
		out.println("Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
	}
}
