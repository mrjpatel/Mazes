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
		else if(maze.type == 2)
		{
			maze(maze, 6);
		}
		else
		{
			System.out.println("Tunnels");
		}
	} // end of generateMaze()

	public void maze(Maze maze, int edges)
	{
		boolean testNext = true;
		boolean[][] visited = null;
		Stack<Cell> saved = new Stack<Cell>(); // create stack to push and pop 
		Cell map[][] = maze.map;
		Cell current = null;
		int count = 0;
		if(maze.type == 2)
		{
			visited = new boolean[maze.sizeR][maze.sizeC + (maze.sizeC + 1) / 2];
			current = map[initPos(maze.sizeR)][initPos(maze.sizeC)];
		}
		else
		{
			visited = new boolean[maze.sizeR][maze.sizeC];
			current = map[initPos(maze.sizeR)][initPos(maze.sizeC)];
		}
		while(testNext)
		{
			int chooseDirection[] = direction(edges);
			if(neighbourCheck(current, maze, visited, edges))
			{
				for(int i = 0; i < chooseDirection.length; i++)
				{
					int test = moveInDir(maze, chooseDirection[i]);
					if(!isVisited(current.r + maze.deltaR[test], current.c + maze.deltaC[test], maze.sizeR, maze.sizeC, visited, maze.type))
					{
						Cell nextCell = getNeighbour(current, maze, visited , test);
						if(nextCell != null)
						{
							nextCell.wall[Maze.oppoDir[test]].present = false;
							current.wall[test].present = false;
							saved.push(current);
							visited[current.r][current.c] = true;
							current = nextCell;
							count++;
							break;
						}
					}
				}
			}
			else
			{
				visited[current.r][current.c] = true;
				saved.pop();
				if(!saved.isEmpty())
				{
					current = saved.peek();
				}
				else
				{
					testNext = false;
				}
			}
			
		}
	}
	// choose direction 
	public int initPos(int maxsize)
	{
		Random rand = new Random();
		return rand.nextInt(maxsize);
	}
	public int[] direction(int max){
		Random rand = new Random();
		int array[] = new int[max];
		for(int i = 0;i<max;i++){
			array[i] = i;
		}
		for(int i=0;i<max;i++){
			int val = i + rand.nextInt(max - i);
			int elem = array[val];
		        array[val] = array[i];
		        array[i] = elem;
		}
		return array;
	}
	public boolean isVisited(int r, int c, int maxR, int maxC, boolean visited[][], int type){
		if(type == Maze.HEX)
			maxC = maxC + (maxR+1) / 2;
		if(inGraph(r, 0, maxR) && inGraph(c, 0,maxC)){
			return visited[r][c];
		}
		return true;
	}
	public boolean inGraph(int x, int minV, int maxV)
	{
		if(x >= minV && x < maxV)
		{
			return true;
		}
		return false;
	}

	public Cell getNeighbour(Cell current, Maze maze, boolean visited[][], int dir)
	{
		Cell neighbour = null;
		int dirX = current.r;
		int dirY = current.c;
		if(dir == maze.NORTH)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.SOUTH)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.EAST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.WEST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.NORTHEAST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.NORTHWEST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.SOUTHEAST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		else if(dir == maze.SOUTHWEST)
		{
			if(inGraph(dirX + maze.deltaR[dir], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[dir], 0, maze.sizeC))
			{
				if(!visited[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]])
				{
					neighbour = maze.map[dirX + maze.deltaR[dir]][dirY + maze.deltaC[dir]];
				}
			}
		}
		return neighbour;
	}

	public boolean neighbourCheck(Cell current, Maze maze, boolean visited[][], int edges){
		int dirX = current.r;
		int dirY = current.c;
		int count = 0;
		if(inGraph(dirX + maze.deltaR[maze.NORTH], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[maze.NORTH], 0, maze.sizeC)){
			if(visited[dirX + maze.deltaR[maze.NORTH]][dirY + maze.deltaC[maze.NORTH]])
			{
				count++;
			}
		}
		else
		{
			count++;
		}
		if(inGraph(dirX + maze.deltaR[maze.EAST], 0, maze.sizeR) && inGraph(dirY + maze.deltaC[maze.EAST], 0, maze.sizeC))
		{
			if(visited[dirX + maze.deltaR[maze.EAST]][dirY + maze.deltaC[maze.EAST]])
			{
				count++;
			}
		}
		else
		{
			count++;
		}
		if(inGraph(dirX+maze.deltaR[maze.SOUTH], 0, maze.sizeR) && inGraph(dirY+maze.deltaC[maze.SOUTH], 0, maze.sizeC))
		{
			if(visited[dirX + maze.deltaR[maze.SOUTH]][dirY + maze.deltaC[maze.SOUTH]])
			{
				count++;
			}
		}
		else
		{
			count++;
		}
		if(inGraph(dirX+maze.deltaR[maze.WEST], 0, maze.sizeR) && inGraph(dirY+maze.deltaC[maze.WEST], 0, maze.sizeC))
		{
			if(visited[dirX + maze.deltaR[maze.WEST]][dirY + maze.deltaC[maze.WEST]])
			{
				count++;
			}
		}
		else
		{
			count++;
		}
		if(maze.type == maze.HEX)
		{
			if(inGraph(dirX+maze.deltaR[maze.NORTHEAST], 0, maze.sizeR) && inGraph(dirY+maze.deltaC[maze.NORTHEAST], 0, maze.sizeC))
			{
				if(visited[dirX + maze.deltaR[maze.NORTHEAST]][dirY + maze.deltaC[maze.NORTHEAST]])
				{
					count++;
				}
			}
			else
			{
				count++;
			}
			if(inGraph(dirX+maze.deltaR[maze.SOUTHWEST], 0, maze.sizeR) && inGraph(dirY+maze.deltaC[maze.SOUTHWEST], 0, maze.sizeC))
			{
				if(visited[dirX + maze.deltaR[maze.SOUTHWEST]][dirY + maze.deltaC[maze.SOUTHWEST]])
				{
					count++;
				}
			}
			else
			{
				count++;
			}
		}
		if(count == edges)
		{
			return false;
		}
		return true;
	}

	public static int moveInDir(Maze maze, int direction)
	{
		if(direction == 0)
		{
			return maze.NORTH;
		}
		else if(direction == 1)
		{
			return maze.SOUTH;
		}
		else if(direction == 2)
		{
			return maze.EAST;
		}
		else if(direction == 3)
		{
			return maze.WEST;
		}
		else if(direction == 4)
		{
			return maze.NORTHEAST;
		}
		else if(direction == 5)
		{
			return maze.NORTHWEST;
		}
		else if(direction == 6)
		{
			return maze.SOUTHEAST;
		}
		else if(direction == 7)
		{
			return maze.SOUTHWEST;
		}
		return maze.NORTH;
	}

} // end of class RecursiveBacktrackerGenerator
