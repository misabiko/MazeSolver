package com.misabiko.MazeSolver;

import java.awt.image.BufferedImage;

public class FollowWall extends Algorithm{

	
	public FollowWall(BufferedImage maze) {
		super(maze);
	}
	
	public void start() {
		int i = 1;
		while (i<1000000) {
//			System.out.println("Step #"+i+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
			if (check() == end) {
				step();
				System.out.println("Solution has been found in "+i+" steps.");
				return;
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
