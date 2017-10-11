package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;

	
	@Override
	public void generateMaze(Maze maze) 
	{


	}

	//choose random position
	public int initPos(int maxsize)
	{
		Random number = new Random();
		return number.nextInt(maxsize);
	}

	//choose random direction
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

	//boolean to show if inside graph
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
