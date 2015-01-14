package com.misabiko.MazeSolver.Algorithms;

public abstract class Algorithm {
	protected int startX, startY, endX, endY, currX, currY;
//	, 'W', 'H', start, end, 'P', deadEnd;
	public int steps = 1;
	protected int deadEndIndex = 0;
	protected int tileSize = 1;
	protected enum direction { UP, RIGHT, DOWN, LEFT }
	protected direction currD;
	protected char[][] maze;
	
	public Algorithm(char[][] maze) {
		this.maze = maze;
		
//		'W' = Color.BLACK[);
//		'H' = Color.WHITE[);
//		start = Color.BLUE[);
//		end = Color.GREEN[);
//		'P' = Color.RED[);
//		deadEnd = Color.CYAN[);
		
////		Find the start point (blue) and end point (green)
//		for (int y = 0; y < maze[0].length; y++) {
//			for (int x = 0; x < maze.length; x++) {
//				if (maze[x, y) == start) {
//					startX = x;
//					startY = y;
//					System.out.println("Start is at "+startX+" "+startY);
//				}else if (maze[x, y) == end) {
//					endX = x;
//					endY = y;
//					System.out.println("End is at "+endX+" "+endY);
//				}
//			}
//		}
		
		findOpening();
		
		currX = startX;
		currY = startY;
		
//		Setting the start direction, start can't be in corners
		if (startY == 0)
			currD = direction.DOWN;
		else if (startY == maze[0].length)
			currD = direction.UP;
		else if (startX == 0)
			currD = direction.RIGHT;
		else
			currD = direction.LEFT;
		
	}
	
	public abstract char[][] solve();
	
//	Checks if the next tile is a 'W', a 'H' or the end and acts in consequence
	protected int check() {
		switch (currD) {
		case UP:
			if (currY-1 >= 0)
				return maze[currX][currY-1];
			else {
//				System.out.println("You're facing the upper void."+getCurrPos());
				return 0;
			}
		case RIGHT:
			if (currX+1 >= 0)
				return maze[currX+1][currY];
			else {
//				System.out.println("You're facing the eastern void."+getCurrPos());
				return 0;
			}
		case DOWN:
			if (currY+1 >= 0)
				return maze[currX][currY+1];
			else {
//				System.out.println("You're facing the lower void."+getCurrPos());
				return 0;
			}
		case LEFT:
			if (currX-1 >= 0)
				return maze[currX-1][currY];
			else {
//				System.out.println("You're facing the western void."+getCurrPos());
				return 0;
			}
		default:
//			System.out.println("The impossible happened! D:");//Seriously though, this is just to calm java, it won't happen
			return 0;
		}
	}
	
	protected void step() {
		switch (currD) {
			case UP:
				if (maze[currX][currY-1] == 'P') {
					maze[currX][currY] = 'D';
					
//					System.out.print("Backtracking up.");
				}else if (maze[currX][currY] == 'S') {
//					System.out.print("Going up.");
				}else {
					maze[currX][currY] = 'P';
					
//					System.out.print("Going up.");
				}
				currY--;
				deadEndIndex = 0;
//				System.out.println(getCurrPos());
				break;
			case RIGHT:
				if (maze[currX+1][currY] == 'P') {
					maze[currX][currY] = 'D';
					
//					System.out.print("Backtracking right.");
				}else if (maze[currX][currY] == 'S') {
//					System.out.print("Going right.");
				}else {
					maze[currX][currY] = 'P';
					
//					System.out.print("Going right.");
				}
				currX++;
				deadEndIndex = 0;
//				System.out.println(getCurrPos());
				break;
			case DOWN:
				if (maze[currX][currY+1] == 'P') {
					maze[currX][currY] = 'D';
					
//					System.out.print("Backtracking down.");
				}else if (maze[currX][currY] == 'S') {
//					System.out.print("Going down.");
				}else {
					maze[currX][currY] = 'P';
					
//					System.out.print("Going down.");
				}
				currY++;
				deadEndIndex = 0;
//				System.out.println(getCurrPos());
				break;
			case LEFT:
				if (maze[currX-1][currY] == 'P') {
					maze[currX][currY] = 'D';
					
//					System.out.print("Backtracking left.");
				}else if (maze[currX][currY] == 'S') {
//					System.out.print("Going left.");
				}else {
					maze[currX][currY] = 'P';
					
//					System.out.print("Going left.");
				}
				currX--;
				deadEndIndex = 0;
//				System.out.println(getCurrPos());
				break;
		}
	}
	
	protected String getCurrPos() {
		return " Now at ("+currX+", "+currY+").";
	}
	
	private void findOpening() {
		for (int i = 0;i< maze.length;i++) {
			if (maze[i][0] != 'W') {
				if (startX == 0 && startY == 0) {
					startX = i;
					startY = 0;
				}else if (endX == 0 && endY == 0) {
					endX = i;
					endY = 0;
					return;
				}
			}
		}
		for (int i = 0;i< maze.length;i++) {
			if (maze[i][maze[0].length-1] != 'W') {
				if (startX == 0 && startY == 0) {
					startX = i;
					startY = maze[0].length;
				}else if (endX == 0 && endY == 0) {
					endX = i;
					endY = maze[0].length;
					return;
				}
			}
		}
		for (int i = 0;i< maze[0].length;i++) {
			if (maze[0][i] != 'W') {
				if (startX == 0 && startY == 0) {
					startX = 0;
					startY = i;
				}else if (endX == 0 && endY == 0) {
					endX = 0;
					endY = i;
					return;
				}
			}
		}
		for (int i = 0;i< maze[0].length;i++) {
			if (maze[maze.length-1][i] != 'W') {
				if (startX == 0 && startY == 0) {
					startX = maze.length;
					startY = i;
				}else if (endX == 0 && endY == 0) {
					endX = maze.length;
					endY = i;
					return;
				}
			}
		}
	}
	
	private void setTileSize() {
		for (int i = 0;i< maze.length;i++) {
			if (maze[i][0] != 'W') {
				while (maze[i+tileSize][0] != 'W') {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze.length;i++) {
			if (maze[i][maze[0].length] != 'W') {
				while (maze[i+tileSize][maze[0].length] != 'W') {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze[0].length;i++) {
			if (maze[0][i] != 'W') {
				while (maze[0][i+tileSize] != 'W') {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze[0].length;i++) {
			if (maze[maze.length][i] != 'W') {
				while (maze[maze.length][i+tileSize] != 'W') {
					tileSize++;
					return;
				}
			}
		}
	}
}
