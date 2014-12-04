package com.misabiko.MazeSolver.Core;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.misabiko.MazeSolver.Algorithms.Algorithm;
import com.misabiko.MazeSolver.Algorithms.FollowLeftWall;
import com.misabiko.MazeSolver.Algorithms.FollowRightWall;

public class Main extends Applet{;

	private static final long serialVersionUID = 1L;
	private static BufferedImage srcMaze, mazeRight, mazeLeft;
	private Algorithm algoRight, algoLeft;
	
	public void init() {
		
//		Set the maze's BufferedImage
		try {
			srcMaze = ImageIO.read(new File("bigmaze24bit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mazeRight = new BufferedImage(srcMaze.getWidth(),srcMaze.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		mazeRight.createGraphics().drawImage(srcMaze,0,0,null);
		
		mazeLeft = new BufferedImage(srcMaze.getWidth(),srcMaze.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		mazeLeft.createGraphics().drawImage(srcMaze,0,0,null);
		
		algoRight = new FollowRightWall(mazeRight);
		algoLeft = new FollowLeftWall(mazeLeft);
		
//		char[][] charArray = new char[srcMaze.getWidth()][srcMaze.getHeight()];
//		for (int i = 0; i<srcMaze.getWidth();i++) {
//			for (int j = 0; j<srcMaze.getHeight();j++) {
//				if (srcMaze.getRGB(i, j) == Color.BLACK.getRGB())
//					charArray[i][j] = 'W';
//				else if (srcMaze.getRGB(i, j) == Color.WHITE.getRGB())
//					charArray[i][j] = 'H';
//				else if (srcMaze.getRGB(i, j) == Color.BLUE.getRGB())
//					charArray[i][j] = 'S';
//				else if (srcMaze.getRGB(i, j) == Color.GREEN.getRGB())
//					charArray[i][j] = 'E';
//			}
//		}
//		
//		for (int i = 0; i<srcMaze.getWidth();i++) {
//			for (int j = 0; j<srcMaze.getHeight();j++) {
//				System.out.print(charArray[j][i]+"\t");
//			}
//			System.out.println();
//		}
		
	}
	
	public void start() {
		mazeRight = algoRight.solve();
		mazeLeft = algoLeft.solve();
		
		System.out.println("The FollowRightWall algorithm took "+algoRight.steps+" steps.");
		System.out.println("The FollowLeftWall algorithm took "+algoLeft.steps+" steps.");
	}
	
	public void paint(Graphics g) {
		g.drawImage(mazeRight,	0,				0,	getWidth()/2,		getHeight(),	null);
		g.drawImage(mazeLeft,	getWidth()/2,	0,	getWidth()/2,		getHeight(),	null);
	}
}
