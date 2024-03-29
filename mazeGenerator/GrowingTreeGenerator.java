package mazeGenerator;

import java.util.*;
import maze.Maze;
import maze.Cell;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

public class GrowingTreeGenerator implements MazeGenerator 
{
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	double threshold = 0.1;
	@Override
	// Same as RecursiveBacktracker, but only choose between 2 maze types
	public void generateMaze(Maze maze) 
	{
		if(maze.type == 0)
		{
			maze(maze, 4);
		}
		else
		{
			maze(maze, 6);
		}
	}
	// Actual method to generate maze
	public void maze(Maze maze, int edges)
	{
		Cell current = null;
		Cell map[][] = maze.map;
		boolean visited[][] = null;
		// Arraylist used here instead of stack 
		ArrayList<Cell> withinCell = new ArrayList<Cell>();
		// if maze is rectangular/normal
		if(maze.type == 0)
		{
			visited = new boolean[maze.sizeR][maze.sizeC];
			current = map[initPos(maze.sizeR)][initPos(maze.sizeC)];
		}
		// else if maze is hexagonal
		else
		{
			visited = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
			do
			{
				current = maze.map[initPos(maze.sizeR)][initPos(maze.sizeC + (maze.sizeR + 1) / 2)];
			}
			while(current == null);
		}
		// added current cell to ArrayList
		withinCell.add(current);
		do
		{
			// get neighbouring cell of current cell
			Cell neighbour = getNeighbour(map, current, maze, maze.sizeR, maze.sizeC, visited, maze.type);
			if(neighbour != null)
			{
				visited[current.r][current.c] = true;
				withinCell.add(neighbour);
				current = neighbour;
			}
			else
			{
				visited[current.r][current.c] = true;
				// remove cell from arraylist since neighbour is NULL
				withinCell.remove(current);
				if(!withinCell.isEmpty())
				{
					current = withinCell.get(initPos(withinCell.size()));
				}
			}
		}
		while(!withinCell.isEmpty());
	}



	public Cell getNeighbour(Cell map[][], Cell current, Maze maze, int sizeR, int sizeC, boolean visited[][], int type)
	{
		Cell neighbour = null;
		int direction[] = null;
		int movein = 0;
		direction = direction(6);
		for(int i = 0; i < 6; i++)
		{
			if(direction[i] == maze.NORTH)
			{
				movein = direction[i];
			}
			else if(direction[i] == maze.SOUTH)
			{
				movein = direction[i];
			}
			else if(direction[i] == maze.EAST)
			{
				movein = direction[i];
			}
			else if(direction[i] == maze.WEST)
			{
				movein = direction[i];
			}

			if(type == 2)
			{
				if(direction[i] == maze.NORTHEAST)
				{
					movein = direction[i];
				}
				else if(direction[i] == maze.NORTHWEST)
				{
					movein = direction[i];
				}
				else if(direction[i] == maze.SOUTHEAST)
				{
					movein = direction[i];
				}
				else if(direction[i] == maze.SOUTHWEST)
				{
					movein = direction[i];
				}
			}
			int newR = current.r + maze.deltaR[movein];
			int newC = current.c + maze.deltaC[movein];
			if(inGraph(newR, 0, maze.sizeR, newC, 0, maze.sizeC, type))
			{
				// if within boundary, and not visited then proceed
				if(!visited[newR][newC])
				{
					neighbour = map[newR][newC];
					// check if wall is present
					neighbour.wall[maze.oppoDir[movein]].present = false;
					current.wall[movein].present = false;
					return neighbour;
				}
			}
		}
		return neighbour;
	}
		// choose random position
	public int initPos(int maxsize)
	{
		Random number = new Random();
		int i = number.nextInt(maxsize);
		return i;
	}

	// choose random direction
	public int[] direction(int max)
	{
		int array[] = new int[max];
		for(int i = 0; i < max; i++)
		{
			array[i] = i;
		}
		for(int i = 0; i < max; i++)
		{
			Random number = new Random();
			int value = i + number.nextInt(max - i);
			int j = array[value];
		        array[value] = array[i];
		        array[i] = j;
		}
		return array;
	}

	// boolean to show if inside graph
	public boolean inGraph(int x, int minV, int maxV, int y, int minE, int maxE, int type)
	{
		if(type == 2)
		{
			return x >= minV && x < maxV && y >= (x + 1) / 2 && y < maxE + (x + 1) / 2;
		}
		else
        {
            return x >= minV && x < maxV && y >= minE && y < maxE;
        }
	}
}
