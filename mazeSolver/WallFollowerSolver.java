package mazeSolver;

import maze.Cell;
import maze.Maze;
import java.util.ArrayList;
import java.util.Stack;
import java.util.EmptyStackException;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver 
{
	Stack<Cell> solverPath = new Stack<Cell>();
	Cell current;
	Cell last;
	ArrayList<Cell> listed = new ArrayList<Cell>();
	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub
		// first declare entrance and exit cell
		current = maze.entrance;
		last = maze.exit;
		// push current cell into stack
		solverPath.push(current);
		// add current cell to list of visited cells
		listed.add(current);
		maze.drawFtPrt(current);
		while(isSolved() == false)
		{
			if(!solverPath.isEmpty())
			{
				// peek here to see current top of stack
				Cell current = (Cell) solverPath.peek();
				// declared cell neighbour for easier reference
				Cell cNorth = current.neigh[maze.NORTH];
				Cell cSouth = current.neigh[maze.SOUTH];
				Cell cEast = current.neigh[maze.EAST];
				Cell cWest = current.neigh[maze.WEST];
				Cell cNEast = current.neigh[maze.NORTHEAST];
				Cell cNWest = current.neigh[maze.NORTHWEST];
				Cell cSEast = current.neigh[maze.SOUTHEAST];
				Cell cSWest = current.neigh[maze.SOUTHWEST];
				// if maze is hex
				if(maze.type == 2)
				{
					// if statement checks if cell is null, if the wall on the east is present, and if the cell is within the visited cells list
					if(cEast != null && current.wall[maze.EAST].present == false && listed.contains(cEast) == false)
					{
						solverPath.push(cEast);
						listed.add(cEast);
						maze.drawFtPrt(cEast);
					}
					else if(cSEast != null && current.wall[maze.SOUTHEAST].present == false && listed.contains(cSEast) == false)
					{
						solverPath.push(cSEast);
						listed.add(cSEast);
						maze.drawFtPrt(cSEast);
					}
					else if(cSWest != null && current.wall[maze.SOUTHWEST].present == false && listed.contains(cSWest) == false)
					{
						solverPath.push(cSWest);
						listed.add(cSWest);
						maze.drawFtPrt(cSWest);
					}
					else if(cWest != null && current.wall[maze.WEST].present == false && listed.contains(cWest) == false)
					{
						solverPath.push(cWest);
						listed.add(cWest);
						maze.drawFtPrt(cWest);
					}
					else if(cNWest != null && current.wall[maze.NORTHWEST].present == false && listed.contains(cNWest) == false)
					{
						solverPath.push(cNWest);
						listed.add(cNWest);
						maze.drawFtPrt(cNWest);
					}
					else if(cNEast != null && current.wall[maze.NORTHEAST].present == false && listed.contains(cNEast) == false)
					{
						solverPath.push(cNEast);
						listed.add(cNEast);
						maze.drawFtPrt(cNEast);
					}
					else
					{
						// if cell is in none of the above criteria, then it will be popped from the stack
						solverPath.pop();
					}
				}
				// if maze is normal
				else
				{
					if(cEast != null && current.wall[maze.EAST].present == false && listed.contains(cEast) == false)
					{
						solverPath.push(cEast);
						listed.add(cEast);
						maze.drawFtPrt(cEast);
						// if statement regarding the tunnels in normal maze.
						if(cEast.tunnelTo != null)
						{
							// if cell to tunnelto is not null then it will be pushed onto the solver stack and visited cells list
							solverPath.push(cEast.tunnelTo);
							listed.add(cEast.tunnelTo);
							maze.drawFtPrt(cEast.tunnelTo);
						}
					}
					else if(cSouth != null && current.wall[maze.SOUTH].present == false && listed.contains(cSouth) == false)
					{
						solverPath.push(cSouth);
						listed.add(cSouth);
						maze.drawFtPrt(cSouth);
						if(cSouth.tunnelTo != null)
						{
							solverPath.push(cSouth.tunnelTo);
							listed.add(cSouth.tunnelTo);
							maze.drawFtPrt(cSouth.tunnelTo);
						}
					}
					else if(cWest != null && current.wall[maze.WEST].present == false && listed.contains(cWest) == false)
					{
						solverPath.push(cWest);
						listed.add(cWest);
						maze.drawFtPrt(cWest);
						if(cEast.tunnelTo != null)
						{
							solverPath.push(cWest.tunnelTo);
							listed.add(cWest.tunnelTo);
							maze.drawFtPrt(cWest.tunnelTo);
						}
					}
					else if(cNorth != null && current.wall[maze.NORTH].present == false && listed.contains(cNorth) == false)
					{
						solverPath.push(cNorth);
						listed.add(cNorth);
						maze.drawFtPrt(cNorth);
						if(cNorth.tunnelTo != null)
						{
							solverPath.push(cNorth.tunnelTo);
							listed.add(cNorth.tunnelTo);
							maze.drawFtPrt(cNorth.tunnelTo);
						}
					}
					else
					{
						solverPath.pop();
					}
				}
			}
		}
	} // end of solveMaze()
    
    
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		// if solverPath contains the last cell or exit cell then return true
		if(solverPath.contains(last))
		{
			return true;
		}
		return false;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		// simply returns size of list of visited cells
		return listed.size();
	} // end of cellsExplored()

} // end of class WallFollowerSolver
