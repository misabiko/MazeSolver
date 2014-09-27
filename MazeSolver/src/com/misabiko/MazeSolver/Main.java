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
	private static BufferedImage maze;
	private int startX, startY, endX, endY, currX, currY, wall, hall, path, deadEnd;
	private int deadEndIndex = 0;
	private enum direction { UP, RIGHT, DOWN, LEFT }
	private direction currD;
	
	public void init() {
		
//		Set the maze's BufferedImage
		try {
			maze = ImageIO.read(new File("bigmaze.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Find the start point (blue) and end point (green)
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				if (maze.getRGB(x, y) == Color.BLUE.getRGB()) {
					startX = x;
					startY = y;
					System.out.println("Start is at "+startX+" "+startY);
				}else if (maze.getRGB(x, y) == Color.GREEN.getRGB()) {
					endX = x;
					endY = y;
					System.out.println("End is at "+endX+" "+endY);
				}
			}
		}
		
		currX = startX;
		currY = startY;
		
		wall = Color.BLACK.getRGB();
		hall = Color.WHITE.getRGB();
		path = Color.RED.getRGB();
		deadEnd = Color.CYAN.getRGB();
		
//		Setting the start direction, start can't be in corners
		if (startY == 0)
			currD = direction.DOWN;
		else if (startY == maze.getHeight())
			currD = direction.UP;
		else if (startX == 0)
			currD = direction.RIGHT;
		else
			currD = direction.LEFT;
		
	}
	
	public void start() {
		int i = 1;
		while (i < 10000) {
//			System.out.println("Step #"+i+": Facing "+currD.toString().toLowerCase()+"."+getCurrPos()+" Dead End Index is at "+deadEndIndex+".");
			if (check() == Color.GREEN.getRGB()) {
				step();
				System.out.println("Solution has been found in "+i+" steps.");
				return;
			}else if (check() == hall) {
				step();
			}else if (deadEndIndex >= 4) {
				if (check() == wall || check() == deadEnd || check() == Color.BLUE.getRGB()) {
					nextDirection();
				}else {
					step();
				}
			}else if (check() == wall || check() == path || check() == deadEnd || check() == Color.BLUE.getRGB()) {
				nextDirection();
			}
			
			i++;
		}
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
				}else if (maze.getRGB(currX,currY) == Color.BLUE.getRGB()) {
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
				}else if (maze.getRGB(currX,currY) == Color.BLUE.getRGB()) {
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
				}else if (maze.getRGB(currX,currY) == Color.BLUE.getRGB()) {
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
				}else if (maze.getRGB(currX,currY) == Color.BLUE.getRGB()) {
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
	
	public void paint(Graphics g) {
		g.drawImage(maze,0,0,getWidth(),getHeight(),null);
	}
}
