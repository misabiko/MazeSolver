package com.misabiko.MazeSolver.Algorithms;

import java.awt.image.BufferedImage;

public class FollowLeftWall extends Algorithm{

	public FollowLeftWall(BufferedImage maze) {
		super(maze);
	}
	
	public BufferedImage solve() {
		int i = 1;
		while (i<1000000) {
//			System.out.println("Step #"+i+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
			if (check() == end) {
				step();
				System.out.println("Solution has been found in "+i+" steps.");
				return maze;
			}else if (check() == hall) {
				step();
			}else if (deadEndIndex >= 4) {
				if (check() == wall || check() == deadEnd || check() == start) {
					nextDirection();
				}else {
					step();
				}
			}else if (check() == wall || check() == path || check() == deadEnd || check() == start) {
				nextDirection();
			}
			
			i++;
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
		
		System.out.println("Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
	}
}
