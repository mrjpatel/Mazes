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
		boolean[][] visited = null;
        // if maze = hex
		if(maze.type == 2)
		{
            visited = new boolean[maze.sizeR][maze.sizeC + (maze.sizeC + 1) / 2];
            // do while loop to initialize current cell
            do
            {
                current = maze.map[initPos(maze.sizeR)][initPos(maze.sizeC)];
            }
            while(current == null);
        }
        // if maze = normal/tunnel
		else
		{
			visited = new boolean[maze.sizeR][maze.sizeC];
			current = map[initPos(maze.sizeR)][initPos(maze.sizeC)];
        }
        // initialize cell neighbour with params
		do
        {
            // getNeighbour retrieves the next cell taking into account the directions available
            Cell neighbour = getNeighbour(map, current, maze, maze.sizeR, maze.sizeC, visited, maze.type);
            // push to stack
            if(neighbour != null)
            {
                visited[current.r][current.c] = true;
                saved.push(current);
                // set current cell as neighbour after pushing to stack
                current = neighbour;
            }
            // pop from stack
            else
            {
                visited[current.r][current.c] = true;
                saved.pop();
                if(!saved.isEmpty())
                {
                    // peek to check current top of the saved stack
                    current = saved.peek();
                }
            }
        }
        while(!saved.isEmpty());
	}
	// get neighbouring cell
	public Cell getNeighbour(Cell map[][], Cell current, Maze maze, int sizeR, int sizeC, boolean visited[][], int type)
	{
		Cell neighbour = null;
        int direction[] = null;
        int movein = 0;
        direction = direction(6);
        if(current.tunnelTo != null)
        {
            if(!visited[current.tunnelTo.r][current.tunnelTo.c])
            {
                return current.tunnelTo;
            }
        }
        // simple for loop to set the direction to move in
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
            // for hex maze
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
} // end of class RecursiveBacktrackerGenerator
