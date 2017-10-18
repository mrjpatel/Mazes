package mazeSolver;

import java.util.*;
import maze.Cell;
import maze.Maze;

/**
 * The following maze solver is implemented using the biDirectional Recursive Backtracker.
 * 	- The solver uses DFS from both sides entry and exit.
 * 	- When there are no unvisited Cells it backtracks the unvisted neighbour.
 *  - The path from entry to exit is the maze solution.
 * 
 * The following maze solver is not implemented for the tunnel maze.
 * 
 * @author Japan Patel
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	
	@Override
	public void solveMaze(Maze maze) {

		/**
		 * Declaring required variables, stack and array list
		 */
		ArrayList<Cell> entryCells = new ArrayList<>();		//stores cells visited from entry
		ArrayList<Cell> exitCells = new ArrayList<>();		//stores cells visited from exit
		ArrayList<Cell> visitedCells = new ArrayList<>();	//store cells visited
		
		int randCell = 0;

		Cell cEntryCell = maze.entrance;	//Assigning the current entry cell the maze entry value
		Cell cExitCell = maze.exit;			//Assigning the current exit cell the maze exit value

       	Stack<Cell> pEntryCell = new Stack<>();			//stack to store previous entry cells
       	Stack<Cell> pExitCell = new Stack<>();			//stack to store previous exit cells
       	
		maze.drawFtPrt(cEntryCell);
		maze.drawFtPrt(cExitCell);

		entryCells.add(cEntryCell); 	//adding the current entry cell to visited entry cells
		exitCells.add(cExitCell);    	//adding the current exit cell to visited exit cells

		pEntryCell.add(cEntryCell);		//adding the current entry cell to visited previous entry cells
		pExitCell.add(cExitCell);		//adding the current exit cell to visited previous exit cells

		visitedCells.add(cEntryCell);
		boolean mazeMeet = false; 			// loop stopper
		Random randomNum = new Random();	//random number generator

		/**
		 * Loops Until the both DFS search meet each other
		 * 	- Get the univisted entry Neighour cell
		 * 	- Mark that cell visited
		 * 
		 * 	- Get the univisted exit Neighour cell
		 * 	- Mark that cell visited
		 */
        while (!mazeMeet) {
        	ArrayList<Integer> unvisitedNeighbors = new ArrayList<>();
			
			//Getting Entry Cell neighbours
			getUnvistedNeighbours(maze, cEntryCell, unvisitedNeighbors, entryCells, visitedCells);
			//Marking the Entry Cell neighbours visited
			markEntryVisited(maze, mazeMeet, unvisitedNeighbors, pEntryCell, cEntryCell, pExitCell, entryCells, visitedCells);
	
			unvisitedNeighbors = new ArrayList<>();
			//Getting Exit Cell neighbours
			getUnvistedNeighbours(maze, cExitCell, unvisitedNeighbors, entryCells, visitedCells);
			
			/**
			 * Marking the Exit Cell neighbours visited
			 * 	- Getting a random univisted neighbour cell
			 * 	- Adding that random cell to the stack
			 * 	- Checking if both DFS meet each other
			 */
            if (unvisitedNeighbors.size() > 0) {
                randCell = unvisitedNeighbors.get(randomNum.nextInt(unvisitedNeighbors.size()));
                pExitCell.add(cExitCell);
                cExitCell = cExitCell.neigh[randCell];

				maze.drawFtPrt(cExitCell);
				if((pEntryCell.contains(cExitCell))){
                	mazeMeet = true;
                	isSolved();
                 }
                exitCells.add(cExitCell);
                visitedCells.add(cExitCell);
			} 
			else {
            	if (pExitCell.size() > 0){
					cExitCell = pExitCell.pop();
				}
            	else{
					break;
				}
            }
		}// end of ehile loop
		
	} // end of solveMaze()


	/**
	 * Checks if the maze is solved
	 */
	@Override
	public boolean isSolved() {
		return true;
	} // end if isSolved()

	@Override
	public int cellsExplored() {
		return 1;
	} // end of cellsExplored()

	/**
	 * 
	 * Checks the type of maze
	 * Then checks if the Cell is in the maze
	 * The check if the cell is visited or not
	 * Adds the univisited cells to a list and returns the list
	 * 
	 */
	private ArrayList<Integer> getUnvistedNeighbours(Maze maze, Cell cell, ArrayList<Integer> unvisitedNeighbors, ArrayList<Cell> entryCells, ArrayList<Cell> visitedCells){
		//A loop to get all the cell from all the direction
		for (int i = 0; i < maze.NUM_DIR; i++) {
			Cell cNeighbour = cell.neigh[i];
			
			//checks if the maze is NORMAL
			if(maze.type == Maze.NORMAL){
				//Checks if the cell is in maze
				if(cNeighbour != null && !visitedCells.contains(cNeighbour) && cNeighbour.r >= 0 && cNeighbour.c >= 0
				&& cNeighbour.r < maze.sizeR && cNeighbour.c < maze.sizeC){
					
					//checks if randomly selected cell is the visited entry cells list
					if(!entryCells.contains(cNeighbour)){
						
						//Checks if the path exists
						if(!cell.wall[i].present){
							unvisitedNeighbors.add(i);
						}
					}
				}
			}
			//checks if the maze is HEX
			else{
				//Checks if the cell is in maze
				if(cNeighbour != null && !visitedCells.contains(cNeighbour) && cNeighbour.r >= 0 
				&& cNeighbour.c >= (cNeighbour.r + 1) / 2 && cNeighbour.r < maze.sizeR && 
				cNeighbour.c < maze.sizeC + (cNeighbour.r + 1) / 2){

					//checks if randomly selected cell is the visited entry cells list
					if(!entryCells.contains(cNeighbour)){
						
						//Checks if the path exists
						if(!cell.wall[i].present){
							unvisitedNeighbors.add(i);
						}
					}
				}
			}
		}
		return unvisitedNeighbors;
	}// end of getUnvisitedNeigbourCells()

	/**
	 * Marking the Entry Cell neighbours visited
	 * 	- Getting a random univisted neighbour cell
	 * 	- Adding that random cell to the stack
	 * 	- Checking if both DFS meet each other
	 */
	private void markEntryVisited (Maze maze, boolean mazeMeet, ArrayList<Integer> unvisitedNeighbors, Stack<Cell> pEntryCells, 
			Cell cEntryCell, Stack<Cell> pExitCells,  ArrayList<Cell> cells, ArrayList<Cell> visitedCells){
				Random randomNum = new Random();
				int randCell = 0;
				
				if (unvisitedNeighbors.size() > 0) {
					randCell = unvisitedNeighbors.get(randomNum.nextInt(unvisitedNeighbors.size()));
					pEntryCells.add(cEntryCell);
					cEntryCell = cEntryCell.neigh[randCell];
					
					maze.drawFtPrt(cEntryCell);
					if((pExitCells.contains(cEntryCell))){
						mazeMeet = true;
						isSolved();
					}
					
					cells.add(cEntryCell);
					visitedCells.add(cEntryCell);
				} 
				else {
					if (pEntryCells.size() > 0){
						cEntryCell = pEntryCells.pop();
					}
				}
	}// end of markVisited()
} // end of class BiDirectionalRecursiveBackTrackerSolver