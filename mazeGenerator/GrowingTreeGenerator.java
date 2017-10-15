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
			}while(current == null);
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
		}while(!withinCell.isEmpty());
	}

	// choose random position
	public int initPos(int maxsize)
	{
		Random number = new Random();
		return number.nextInt(maxsize);
	}

	// choose random direction
	public int[] direction(int max){
		Random number = new Random();
		int array[] = new int[max];
		for(int i = 0; i < max; i++)
		{
			array[i] = i;
		}
		for(int i = 0; i < max; i++)
		{
			int value = i + number.nextInt(max - i);
			int e = array[value];
		        array[value] = array[i];
		        array[i] = e;
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

	public Cell getNeighbour(Cell p[][], Cell current, Maze maze, int sizeR, int sizeC, boolean visited[][], int type)
	{
		Cell neighbour = null;
		int direction[] = null;
		int move = 0;
		direction = direction(6);
		for(int i = 0; i < 6; i++)
		{
			if(direction[i] == maze.NORTH)
			{
				move = direction[i];
			}
			else if(direction[i] == maze.SOUTH)
			{
				move = direction[i];
			}
			else if(direction[i] == maze.EAST)
			{
				move = direction[i];
			}
			else if(direction[i] == maze.WEST)
			{
				move = direction[i];
			}

			if(type == 2)
			{
				if(direction[i] == maze.NORTHEAST)
				{
					move = direction[i];
				}
				else if(direction[i] == maze.NORTHWEST)
				{
					move = direction[i];
				}
				else if(direction[i] == maze.SOUTHEAST)
				{
					move = direction[i];
				}
				else if(direction[i] == maze.SOUTHWEST)
				{
					move = direction[i];
				}
			}
			int newR = current.r + maze.deltaR[move];
			int newC = current.c + maze.deltaC[move];
			if(inGraph(newR, 0, maze.sizeR, newC, 0, maze.sizeC, type))
			{
				// if within boundary, and not visited then proceed
				if(!visited[newR][newC])
				{
					neighbour = p[newR][newC];
					// check if wall is present
					neighbour.wall[maze.oppoDir[move]].present = false;
					current.wall[move].present = false;
					return neighbour;
				}
			}
		}
		return neighbour;
	}
}
