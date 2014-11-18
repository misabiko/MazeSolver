package com.misabiko.MazeSolver;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main extends Applet{;

	private static final long serialVersionUID = 1L;
	private static BufferedImage srcMaze, maze;
	private int startX, startY, endX, endY, currX, currY, wall, hall, start, end, path, deadEnd;
	private int deadEndIndex = 0;
	private int tileSize = 1;
	private enum direction { UP, RIGHT, DOWN, LEFT }
	private direction currD;
	
	public void init() {
		
//		Set the maze's BufferedImage
		try {
			srcMaze = ImageIO.read(new File("newmaze.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		maze = new BufferedImage(srcMaze.getWidth(),srcMaze.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		maze.createGraphics().drawImage(srcMaze,0,0,null);
		
		char[][] charArray = new char[srcMaze.getWidth()][srcMaze.getHeight()];
		for (int i = 0; i<srcMaze.getWidth();i++) {
			for (int j = 0; j<srcMaze.getHeight();j++) {
				if (srcMaze.getRGB(i, j) == Color.BLACK.getRGB())
					charArray[i][j] = 'W';
				else if (srcMaze.getRGB(i, j) == Color.WHITE.getRGB())
					charArray[i][j] = 'H';
				else if (srcMaze.getRGB(i, j) == Color.BLUE.getRGB())
					charArray[i][j] = 'S';
				else if (srcMaze.getRGB(i, j) == Color.GREEN.getRGB())
					charArray[i][j] = 'E';
			}
		}
		
		for (int i = 0; i<srcMaze.getWidth();i++) {
			for (int j = 0; j<srcMaze.getHeight();j++) {
				System.out.print(charArray[j][i]+"\t");
			}
			System.out.println();
		}
		
//		wall = Color.BLACK.getRGB();
//		hall = Color.WHITE.getRGB();
//		start = Color.BLUE.getRGB();
//		end = Color.GREEN.getRGB();
//		path = Color.RED.getRGB();
//		deadEnd = Color.CYAN.getRGB();
//		
////		Find the start point (blue) and end point (green)
//		for (int y = 0; y < maze.getHeight(); y++) {
//			for (int x = 0; x < maze.getWidth(); x++) {
//				if (maze.getRGB(x, y) == start) {
//					startX = x;
//					startY = y;
//					System.out.println("Start is at "+startX+" "+startY);
//				}else if (maze.getRGB(x, y) == end) {
//					endX = x;
//					endY = y;
//					System.out.println("End is at "+endX+" "+endY);
//				}
//			}
//		}
//		
////		findOpening();
//		
//		currX = startX;
//		currY = startY;
//		
////		Setting the start direction, start can't be in corners
//		if (startY == 0)
//			currD = direction.DOWN;
//		else if (startY == maze.getHeight())
//			currD = direction.UP;
//		else if (startX == 0)
//			currD = direction.RIGHT;
//		else
//			currD = direction.LEFT;
//		
	}
	
	public void start() {
//		int i = 1;
//		while (i<100000) {
//			System.out.println("Step #"+i+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
//			if (check() == end) {
//				step();
//				System.out.println("Solution has been found in "+i+" steps.");
//				return;
//			}else if (check() == hall) {
//				step();
//			}else if (deadEndIndex >= 4) {
//				if (check() == wall || check() == deadEnd || check() == start) {
//					nextDirection();
//				}else {
//					step();
//				}
//			}else if (check() == wall || check() == path || check() == deadEnd || check() == start) {
//				nextDirection();
//			}
//			
//			i++;
//		}
	}
	
//	Checks if the next tile is a wall, a hall or the end and acts in consequence
	private int check() {
		switch (currD) {
		case UP:
			if (currY-1 >= 0)
				return maze.getRGB(currX,currY-1);
			else {
				System.out.println("You're facing the upper void."+getCurrPos());
				return 0;
			}
		case RIGHT:
			if (currX+1 >= 0)
				return maze.getRGB(currX+1,currY);
			else {
				System.out.println("You're facing the eastern void."+getCurrPos());
				return 0;
			}
		case DOWN:
			if (currY+1 >= 0)
				return maze.getRGB(currX,currY+1);
			else {
				System.out.println("You're facing the lower void."+getCurrPos());
				return 0;
			}
		case LEFT:
			if (currX-1 >= 0)
				return maze.getRGB(currX-1,currY);
			else {
				System.out.println("You're facing the western void."+getCurrPos());
				return 0;
			}
		default:
			System.out.println("The impossible happened! D:");//Seriously though, this is just to calm java, it won't happen
			return 0;
		}
	}
	
	private void step() {
		switch (currD) {
			case UP:
				if (maze.getRGB(currX, currY-1) == path) {
					maze.setRGB(currX, currY, deadEnd);
					
					System.out.print("Backtracking up.");
				}else if (maze.getRGB(currX,currY) == start) {
					System.out.print("Going up.");
				}else {
					maze.setRGB(currX, currY, path);
					
					System.out.print("Going up.");
				}
				currY--;
				deadEndIndex = 0;
				System.out.println(getCurrPos());
				break;
			case RIGHT:
				if (maze.getRGB(currX+1, currY) == path) {
					maze.setRGB(currX, currY, deadEnd);
					
					System.out.print("Backtracking right.");
				}else if (maze.getRGB(currX,currY) == start) {
					System.out.print("Going right.");
				}else {
					maze.setRGB(currX, currY, path);
					
					System.out.print("Going right.");
				}
				currX++;
				deadEndIndex = 0;
				System.out.println(getCurrPos());
				break;
			case DOWN:
				if (maze.getRGB(currX, currY+1) == path) {
					maze.setRGB(currX, currY, deadEnd);
					
					System.out.print("Backtracking down.");
				}else if (maze.getRGB(currX,currY) == start) {
					System.out.print("Going down.");
				}else {
					maze.setRGB(currX, currY, path);
					
					System.out.print("Going down.");
				}
				currY++;
				deadEndIndex = 0;
				System.out.println(getCurrPos());
				break;
			case LEFT:
				if (maze.getRGB(currX-1, currY) == path) {
					maze.setRGB(currX, currY, deadEnd);
					
					System.out.print("Backtracking left.");
				}else if (maze.getRGB(currX,currY) == start) {
					System.out.print("Going left.");
				}else {
					maze.setRGB(currX, currY, path);
					
					System.out.print("Going left.");
				}
				currX--;
				deadEndIndex = 0;
				System.out.println(getCurrPos());
				break;
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
	
	private String getCurrPos() {
		return " Now at ("+currX+", "+currY+").";
	}
	
	private void findOpening() {
		for (int i = 0;i< maze.getWidth();i++) {
			if (maze.getRGB(i, 0) != wall) {
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
		for (int i = 0;i< maze.getWidth();i++) {
			if (maze.getRGB(i, maze.getHeight()-1) != wall) {
				if (startX == 0 && startY == 0) {
					startX = i;
					startY = maze.getHeight();
				}else if (endX == 0 && endY == 0) {
					endX = i;
					endY = maze.getHeight();
					return;
				}
			}
		}
		for (int i = 0;i< maze.getHeight();i++) {
			if (maze.getRGB(0, i) != wall) {
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
		for (int i = 0;i< maze.getHeight();i++) {
			if (maze.getRGB(maze.getWidth()-1, i) != wall) {
				if (startX == 0 && startY == 0) {
					startX = maze.getWidth();
					startY = i;
				}else if (endX == 0 && endY == 0) {
					endX = maze.getWidth();
					endY = i;
					return;
				}
			}
		}
	}
	
	private void setTileSize() {
		for (int i = 0;i< maze.getWidth();i++) {
			if (maze.getRGB(i, 0) != wall) {
				while (maze.getRGB(i+tileSize, 0) != wall) {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze.getWidth();i++) {
			if (maze.getRGB(i, maze.getHeight()) != wall) {
				while (maze.getRGB(i+tileSize, maze.getHeight()) != wall) {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze.getHeight();i++) {
			if (maze.getRGB(0, i) != wall) {
				while (maze.getRGB(0, i+tileSize) != wall) {
					tileSize++;
					return;
				}
			}
		}
		for (int i = 0;i< maze.getHeight();i++) {
			if (maze.getRGB(maze.getWidth(), i) != wall) {
				while (maze.getRGB(maze.getWidth(), i+tileSize) != wall) {
					tileSize++;
					return;
				}
			}
		}
	}
	public void paint(Graphics g) {
		g.drawImage(maze,0,0,getWidth(),getHeight(),null);
	}
}
