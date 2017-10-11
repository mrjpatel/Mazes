package mazeGenerator;

import maze.Maze;
import maze.Cell;
import java.util.*;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		if(maze.type == 0)
		{
			maze(maze, 4);
		}
		else if(maze.type == 1)
		{
			maze(maze, 4);
		}
		else if(maze.type == 2)
		{
			maze(maze, 6);
		}
	} // end of generateMaze()

	public void maze(Maze maze, int edges)
	{
        Stack<Cell> saved = new Stack<Cell>(); // create stack to push and pop
        Cell map[][] = maze.map;
		Cell current = null;
		boolean testNext = true;
		boolean[][] visited = null;
		int count = 0;
		if(maze.type == 2)
		{
			visited = new boolean[maze.sizeR][maze.sizeC + (maze.sizeC + 1) / 2];
            do
            {
                current = maze.map[initPos(maze.sizeR)][initPos(maze.sizeC)];
            }while(current == null);
		}
		else
		{
			visited = new boolean[maze.sizeR][maze.sizeC];
			current = map[initPos(maze.sizeR)][initPos(maze.sizeC)];
		}
		do
        {
            Cell neighbour = getNeighbour(map, current, maze, maze.sizeR, maze.sizeC, visited, maze.type);
            if(neighbour != null)
            {
                visited[current.r][current.c] = true;
                saved.push(current);
                current = neighbour;
            }
            else
            {
                visited[current.r][current.c] = true;
                saved.pop();
                if(!saved.isEmpty())
                {
                    current = saved.peek();
                }
            }
        }while(!saved.isEmpty());
	}
	// choose random position
	public int initPos(int maxsize)
	{
		Random rand = new Random();
		return rand.nextInt(maxsize);
	}

	//choose random direction
	public int[] direction(int max){
		Random rand = new Random();
		int array[] = new int[max];
		for(int i = 0; i < max; i++)
		{
			array[i] = i;
		}
		for(int i = 0; i < max; i++)
		{
			int value = i + rand.nextInt(max - i);
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
	//get neighbouring cell
	public Cell getNeighbour(Cell p[][], Cell current, Maze maze, int sizeR, int sizeC, boolean visited[][], int type)
	{
		Cell neighbour = null;
        int direction[] = null;
        int move = 0;
        direction = direction(6);
        if(current.tunnelTo != null)
        {
            if(!visited[current.tunnelTo.r][current.tunnelTo.c])
            {
                return current.tunnelTo;
            }
        }
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
                if(!visited[newR][newC])
                {
                    neighbour = p[newR][newC];
                    neighbour.wall[maze.oppoDir[move]].present = false;
                    current.wall[move].present = false;
                    return neighbour;
                }
            }
        }
		return neighbour;
	}
} // end of class RecursiveBacktrackerGenerator
