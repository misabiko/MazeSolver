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
	private int startX, startY, endX, endY, currX, currY, wall, path, deadEnd;
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
		for (int i = 0; i < 100;i++) {
			check();
		}
	}
	
//	Checks if the next tile is a wall, a hall or the end and acts in consequence
	private boolean check() {
		switch (currD) {
		case UP:
			if (maze.getRGB(currX,currY-1) == wall || maze.getRGB(currX,currY-1) == path) {
				nextDirection();
				if (deadEndIndex == 4) {
					maze.setRGB(currX, currY, deadEnd);
					currD = direction.DOWN;
					currY++;
					deadEndIndex = 0;
				}
				return false;
			}else {
				maze.setRGB(currX, currY-1, path);
				currY--;
				
				deadEndIndex = 0;
				System.out.println("Going "+currD.toString().toLowerCase()+". Now at ("+currX+", "+currY+").");
				return true;
			}
		case RIGHT:
			if (maze.getRGB(currX+1,currY) == wall || maze.getRGB(currX+1,currY) == path) {
				nextDirection();
				if (deadEndIndex == 4) {
					maze.setRGB(currX, currY, deadEnd);
					currD = direction.LEFT;
					currX--;
					deadEndIndex = 0;
				}
				return false;
			}else {
				maze.setRGB(currX+1, currY, path);
				currX++;
				
				deadEndIndex = 0;
				System.out.println("Going "+currD.toString().toLowerCase()+". Now at ("+currX+", "+currY+").");
				return true;
			}
		case DOWN:
			if (maze.getRGB(currX,currY+1) == wall || maze.getRGB(currX,currY+1) == path) {
				nextDirection();
				if (deadEndIndex == 4) {
					maze.setRGB(currX, currY, deadEnd);
					currD = direction.UP;
					currY--;
					deadEndIndex = 0;
				}
				return false;
			}else {
				maze.setRGB(currX, currY+1, path);
				currY++;
				
				deadEndIndex = 0;
				System.out.println("Going "+currD.toString().toLowerCase()+". Now at ("+currX+", "+currY+").");
				return true;
			}
		case LEFT:
			if (maze.getRGB(currX-1,currY) == wall || maze.getRGB(currX-1,currY) == path) {
				nextDirection();
				if (deadEndIndex == 4) {
					maze.setRGB(currX, currY, deadEnd);
					currD = direction.RIGHT;
					currX++;
					deadEndIndex = 0;
				}
				return false;
			}else {
				maze.setRGB(currX-1, currY, path);
				currX--;
				
				deadEndIndex = 0;
				System.out.println("Going "+currD.toString().toLowerCase()+". Now at ("+currX+", "+currY+").");
				return true;
			}
		default:
			System.out.println("The impossible happened! D:");//Seriously though, this is just to calm java, it won't happen
			return false;
		}
	}
	
//	Shifts the current direction (if up then right, else if right then down, etc)
	private void nextDirection() {
		if (currD.ordinal() == 3)
			currD = direction.UP;
		else
			currD = direction.values()[currD.ordinal()+1];
		
		deadEndIndex++;
		System.out.println(deadEndIndex);
		
		System.out.println("Facing "+currD.toString().toLowerCase()+". Now at ("+currX+", "+currY+").");
	}
	
	public void paint(Graphics g) {
		g.drawImage(maze,0,0,getWidth(),getHeight(),null);
	}
}
