package com.misabiko.MazeSolver.Core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import com.misabiko.MazeSolver.Algorithms.AStar;
import com.misabiko.MazeSolver.Algorithms.Algorithm;
import com.misabiko.MazeSolver.Algorithms.FollowLeftWall;
import com.misabiko.MazeSolver.Algorithms.FollowRightWall;

public class Main{;

	private static BufferedImage srcMaze, processedMaze, outputMaze;
	private static PrintWriter logOutput;
	private static char[][] solvedMaze;
	private static Algorithm[] algos = new Algorithm[3];
	private static String[] algoNames = new String[] {
		"TurnRight",
		"TurnLeft",
		"A*"
		};
	
	public static void main(String[] args) {
		try {
			logOutput = new PrintWriter("MazeSolvingLogs.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		logOutput.println("Attempt at solving the following maze:");
		logOutput.println(args[0]);
		logOutput.println("Using the "+algoNames[Integer.valueOf(args[1])]+" algorithm.\n");
		
		try {
			logOutput.println("Loading the maze.");
			srcMaze = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			logOutput.println("An error occur in the process, outputing error.");
			e.printStackTrace();
			e.printStackTrace(logOutput);
		}
		
		processedMaze = new BufferedImage(srcMaze.getWidth(),srcMaze.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		processedMaze.createGraphics().drawImage(srcMaze,0,0,null);
		
		char[][] charMaze = new char[processedMaze.getWidth()][srcMaze.getHeight()];
		for (int i = 0; i<srcMaze.getWidth();i++) {
			for (int j = 0; j<srcMaze.getHeight();j++) {
				if (processedMaze.getRGB(i, j) == Color.WHITE.getRGB())
					charMaze[i][j] = 'H';
				else if (processedMaze.getRGB(i, j) == Color.BLUE.getRGB())
					charMaze[i][j] = 'S';
				else if (processedMaze.getRGB(i, j) == Color.GREEN.getRGB())
					charMaze[i][j] = 'E';
				else
					charMaze[i][j] = 'W';
			}
		}
		
//		logOutput.println("Processed version of the maze converted to a char array:\n");
//		
//		for (int x = 0; x < charMaze.length; x++) {
//			for (int y = 0; y < charMaze[0].length; y++) {
//				logOutput.print(charMaze[x][y]);
//			}
//			logOutput.print("\n");
//		}
		
		logOutput.println();
		
		algos[0] = new FollowRightWall(charMaze, logOutput);
		algos[1] = new FollowLeftWall(charMaze, logOutput);
		algos[2] = new AStar(charMaze, logOutput);
		
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
//			ImageIO.write(outputMaze, "png", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"\\outputMaze.png"));
			for (int i = 0; i < 10; i++) {
				File file = new File(System.getProperty("user.dir")+"output"+i+".png");
				if (!file.exists() && !file.isDirectory()) {
					ImageIO.write(outputMaze, "png", new File(System.getProperty("user.dir")+"output"+i+".png"));
					break;
				}
			}
			logOutput.println("Saving solved maze at:");
//			logOutput.println(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"\\output.png");
			logOutput.println(System.getProperty("user.dir")+"output.png");
		} catch (IOException e) {
			logOutput.println("An error occur in the process, outputing error.");
			e.printStackTrace();
			e.printStackTrace(logOutput);
		}
		
	}
}
