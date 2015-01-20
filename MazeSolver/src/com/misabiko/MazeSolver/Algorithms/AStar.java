package com.misabiko.MazeSolver.Algorithms;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AStar extends Algorithm {
	private Node next;
	private Node[][] nodes;
	private ArrayList<Node> openList = new ArrayList<Node>();
	private boolean upExists;
	private boolean downExists;
	private boolean leftExists;
	private boolean rightExists;
	private final int directMoveCost = 10;
	private final int diagonalMoveCost = 10;
	private boolean done = false;
	
	public AStar(char[][] maze, PrintWriter out) {
		super(maze, out);
		
		nodes = new Node[maze.length][maze[0].length];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				nodes[i][j] = new Node(i,j,maze[i][j]);
			}
		}
		
		nodes[currX][currY].gValue = 0;
		maze[currX][currY] = 'D';
			
	}

	@Override
	public char[][] solve() {
		int i = 0;
		while (i < 1000000) {
			i++;
			updateNodes();
			openList.remove(next);
			if (next == null)
				System.out.println("boo");
			System.out.println(next.x+"\t"+next.y);
			maze[next.x][next.y] = 'D';
			currX = next.x;
			currY = next.y;
			if (done)
				return maze;
		}
		
		System.out.println("Didn't manage to solve it...");
		return maze;
	}
	
	private void updateNodes() {
		upExists = (nodes[currX][currY].y-1 >= 0);
		downExists = (nodes[currX][currY].y+1 <= nodes[0].length);
		leftExists = (nodes[currX][currY].x-1 >= 0);
		rightExists = (nodes[currX][currY].x+1 <= nodes.length);
		
		if (upExists)
			if (nodes[nodes[currX][currY].x][nodes[currX][currY].y-1].type != 'W')
				next = nodes[nodes[currX][currY].x][nodes[currX][currY].y-1];
			else
				System.out.println("whet1");
		else if (upExists && rightExists)
			if (nodes[nodes[currX][currY].x+1][nodes[currX][currY].y-1].type != 'W')
				next = nodes[nodes[currX][currY].x+1][nodes[currX][currY].y-1];
			else
				System.out.println("whet2");
		else if (rightExists)
			if (nodes[nodes[currX][currY].x+1][nodes[currX][currY].y].type != 'W')
				next = nodes[nodes[currX][currY].x+1][nodes[currX][currY].y];
			else
				System.out.println("whet3");
		else if (downExists && rightExists)
			if (nodes[nodes[currX][currY].x+1][nodes[currX][currY].y+1].type != 'W')
				next = nodes[nodes[currX][currY].x+1][nodes[currX][currY].y+1];
			else
				System.out.println("whet4");
		else if (downExists)
			if (nodes[nodes[currX][currY].x][nodes[currX][currY].y+1].type != 'W')
				next = nodes[nodes[currX][currY].x][nodes[currX][currY].y+1];
			else
				System.out.println("whet5");
		else if (downExists && leftExists)
			if (nodes[nodes[currX][currY].x-1][nodes[currX][currY].y+1].type != 'W')
				next = nodes[nodes[currX][currY].x-1][nodes[currX][currY].y+1];
			else
				System.out.println("whet6");
		else if (leftExists)
			if (nodes[nodes[currX][currY].x-1][nodes[currX][currY].y].type != 'W')
				next = nodes[nodes[currX][currY].x-1][nodes[currX][currY].y];
			else
				System.out.println("whet7");
		else if (upExists && leftExists)
			if (nodes[nodes[currX][currY].x-1][nodes[currX][currY].y-1].type != 'W')
				next = nodes[nodes[currX][currY].x-1][nodes[currX][currY].y-1];
			else
				System.out.println("whet8");
		
		
		if (upExists)
			nodes[nodes[currX][currY].x][nodes[currX][currY].y-1].update(true);
		if (done)
			return;
		if (upExists && rightExists)
			nodes[nodes[currX][currY].x+1][nodes[currX][currY].y-1].update(false);
		if (done)
			return;
		if (rightExists)
			nodes[nodes[currX][currY].x+1][nodes[currX][currY].y].update(true);
		if (done)
			return;
		if (downExists && rightExists)
			nodes[nodes[currX][currY].x+1][nodes[currX][currY].y+1].update(false);
		if (done)
			return;
		if (downExists)
			nodes[nodes[currX][currY].x][nodes[currX][currY].y+1].update(true);
		if (done)
			return;
		if (downExists && leftExists)
			nodes[nodes[currX][currY].x-1][nodes[currX][currY].y+1].update(false);
		if (done)
			return;
		if (leftExists)
			nodes[nodes[currX][currY].x-1][nodes[currX][currY].y].update(true);
		if (done)
			return;
		if (upExists && leftExists)
			nodes[nodes[currX][currY].x-1][nodes[currX][currY].y-1].update(false);
	}
	
	private void writePath(Node goal) {
		currX = goal.parent.x;
		currY = goal.parent.y;
		maze[currX][currY] = 'P';
		int i = 0;
		while (i < 1000 && nodes[currX][currY].parent != null) {
			i++;
			maze[nodes[currX][currY].parent.x][nodes[currX][currY].parent.y] = 'P';
			int newX = nodes[currX][currY].parent.x;
			int newY = nodes[currX][currY].parent.y;
			currX = newX;
			currY = newY;
		}
		
		if (i == 1000)
			System.out.println("Something went wrong on retracing");
		
		done = true;
	}
	
	private class Node {
		public int x, y, heuristic, gValue, fValue;
		public char type;
		public Node parent;
		
		public Node(int x, int y, char type) {
			this.x = x;
			this.y = y;
			this.type = type;
			heuristic = (endX - x)+(endY - y);
		}
		
		public void update(boolean direct) {
			if (type == 'E') {
				parent = nodes[currX][currY];
				writePath(this);
			} else if (type != 'W' && type != 'D') {
				if (openList.contains(this))
					if (nodes[currX][currY].gValue + (direct ? directMoveCost : diagonalMoveCost) < gValue) {
						parent = nodes[currX][currY];
						gValue = parent.gValue + (direct ? directMoveCost : diagonalMoveCost);
						fValue = gValue+heuristic;
					}
				else {
					if (fValue < next.fValue)
						next = this;
					openList.add(this);
				}
			}
		}
	}
}
