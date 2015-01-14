package com.misabiko.MazeSolver.Algorithms;

import java.awt.image.BufferedImage;

import com.misabiko.MazeSolver.Algorithms.Algorithm.direction;

public class FollowRightWall extends Algorithm{
	
	public FollowRightWall(char[][] maze) {
		super(maze);
	}
	
	public char[][] solve() {
		while (steps<1000000) {
//			System.out.println("Step #"+i+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
			if (check() == 'E') {
				step();
//				System.out.println("Solution has been found in "+steps+" steps.");
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
		if (currD.ordinal() == 3)
			currD = direction.UP;
		else
			currD = direction.values()[currD.ordinal()+1];
		
		deadEndIndex++;
		
//		System.out.println("Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
	}
}
