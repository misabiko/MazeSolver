package com.misabiko.MazeSolver.Core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import com.misabiko.MazeSolver.Algorithms.Algorithm;
import com.misabiko.MazeSolver.Algorithms.FollowLeftWall;
import com.misabiko.MazeSolver.Algorithms.FollowRightWall;

public class Main{;

	private static BufferedImage srcMaze, processedMaze, outputMaze;
	private static char[][] solvedMaze;
	private static Algorithm[] algos = new Algorithm[2];
	
	public static void main(String[] args) {
		try {
			srcMaze = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		processedMaze = new BufferedImage(srcMaze.getWidth(),srcMaze.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		processedMaze.createGraphics().drawImage(srcMaze,0,0,null);
		
		char[][] charMaze = new char[processedMaze.getWidth()][srcMaze.getHeight()];
		for (int i = 0; i<srcMaze.getWidth();i++) {
			for (int j = 0; j<srcMaze.getHeight();j++) {
				if (processedMaze.getRGB(i, j) == Color.BLACK.getRGB())
					charMaze[i][j] = 'W';
				else if (processedMaze.getRGB(i, j) == Color.WHITE.getRGB())
					charMaze[i][j] = 'H';
				else if (processedMaze.getRGB(i, j) == Color.BLUE.getRGB())
					charMaze[i][j] = 'S';
				else if (processedMaze.getRGB(i, j) == Color.GREEN.getRGB())
					charMaze[i][j] = 'E';
			}
		}
		
		algos[0] = new FollowRightWall(charMaze);
		algos[1] = new FollowLeftWall(charMaze);
		
		solvedMaze = algos[Integer.valueOf(args[1])].solve();
		
		outputMaze = new BufferedImage(solvedMaze.length,solvedMaze[0].length,BufferedImage.TYPE_3BYTE_BGR);
		
		for (int x = 0; x < solvedMaze.length; x++) {
			for (int y = 0; y < solvedMaze[0].length; y++) {
				if (solvedMaze[x][y] == 'W')
					outputMaze.setRGB(x, y, Color.BLACK.getRGB());
				else if (solvedMaze[x][y] == 'H')
					outputMaze.setRGB(x, y, Color.WHITE.getRGB());
				else if (solvedMaze[x][y] == 'S')
					outputMaze.setRGB(x, y, Color.BLUE.getRGB());
				else if (solvedMaze[x][y] == 'E')
					outputMaze.setRGB(x, y, Color.GREEN.getRGB());
				else if (solvedMaze[x][y] == 'P')
					outputMaze.setRGB(x, y, Color.RED.getRGB());
				else if (solvedMaze[x][y] == 'D')
					outputMaze.setRGB(x, y, Color.CYAN.getRGB());
			}
		}
		
		try {
			ImageIO.write(outputMaze, "png", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"\\output.png"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
