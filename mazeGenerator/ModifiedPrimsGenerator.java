package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

/**
 * Normal & Hex mazes are generated using Prim's Algorithm
 * @author Japan Patel
 */
public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {

		Random randomNum = new Random();						//generates random numbers.
		ArrayList<Cell> frontierCell = new ArrayList<>();		//stores information about the neighbour(frontier) cells.
		ArrayList<Cell> neighbourCell = new ArrayList<>();		//stores information about the neighbour cells that are in new maze.
		ArrayList<Cell> newMaze = new ArrayList<>();			//stores information about the cells that are in the complete maze.
		int size = 0;											//stores the integer value for size of maze.
		Cell cCell = null;										//stores the value of current cell.
		Cell selectedFrontier = null;							//temporary froniter cell to destroy wall.
		Cell selectedNeighbour = null;							//temporary cell to destroy wall.
		

		/** 
		 * 
		 * Assigns random value to row and coloum value.
		 * Adds the random row and column to a cell to start generating maze.
		 * 
		*/

		//Normal Maze
		if(maze.type == Maze.NORMAL){
			int nRow = randomNum.nextInt(maze.sizeR);
			int nColumn = randomNum.nextInt(maze.sizeC);
			
			//assigning the row and column to the current cell
			cCell = maze.map[nRow][nColumn];
			size = maze.sizeC * maze.sizeR;
		}
		//Hex Maze
		else if (maze.type == Maze.HEX) {
				int hRow = randomNum.nextInt(maze.sizeR);
				int hColumn = randomNum.nextInt(maze.sizeC + (maze.sizeR + 1) / 2);
				
				//assigning the row and coloum to current cell
				cCell = maze.map[hRow][hColumn];
				size = maze.sizeC * maze.sizeR;
		}
		// adding the current cell in a new maze
		newMaze.add(cCell);

		/**
		 * 
		 * Loops through the old maze until the new Maze is same size as the old Maze
		 * While it loops
		 * 		- It gets the frontier cells and add it to the frontier list.
		 * 		- Selects a random frontier cell and add it to the new maze.
		 * 		- And remove the wall between the current cell and the frontier cell.
		 * 
		 */
		while(newMaze.size() < size){
			//gets the frontier cell
			getFrontierCells(cCell, maze, frontierCell, newMaze);

			//selects a random frontier cell
			int randFrontier = randomNum.nextInt(frontierCell.size());
			selectedFrontier = frontierCell.get(randFrontier);
			//removes the randomly selected cell from frontier list
			frontierCell.remove(selectedFrontier);

			//gets the neighbour cell of the frontier cell
			getNeighbourCells(maze, selectedFrontier, newMaze, neighbourCell);

			//selects a random neigbour cell that is a neigbour to selected frontier cell
			int randNeighbour = randomNum.nextInt(neighbourCell.size());
			selectedNeighbour = neighbourCell.get(randNeighbour);
			
			//removes the wall between the current cell and the selected frontier cell
			removeWall(maze, selectedNeighbour, selectedFrontier);
			
			//adds the selected frontier cell into new maze
			newMaze.add(selectedFrontier);
			//current cell is now the frontier cell
			cCell = selectedFrontier;

		}// end of while loop

	} // end of generateMaze()

	/**
	 * 
	 * Gets Frontier Cells in the frontier list
	 * 	- Checks the type of maze
	 * 	- Checks if the cell is in the maze
	 * 	- Adds all the neighbour cells to the frontier List
	 * 
	 */
	private ArrayList<Cell> getFrontierCells (Cell cCell, Maze maze, ArrayList<Cell> frontierCell, ArrayList<Cell> newMaze){
		//A loop to get all the cell from all the direction
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell selectedCell = cCell.neigh[i];
			
			//Gets frontier Cell if the maze if NORMAL
			if(maze.type == Maze.NORMAL){
				//checks if the cell is in the maze
				if(selectedCell != null && selectedCell.r >= 0 && selectedCell.r < maze.sizeR && selectedCell.c >= 0 && 
				selectedCell.c < maze.sizeC){
					//checks if the cell is already in the frontier Cell list.
					if(frontierCell.contains(selectedCell)){
						//
					}
					else{
						//checks if the cell is already in the New Maze.
						if(newMaze.contains(selectedCell)){
							//
						}
						else{
							//adds the cell into the frontier list.
							frontierCell.add(selectedCell);
						}
					}
				}
			}
			//Gets frontier Cell if the maze if HEX
			else{
				//checks if the cell is in the maze
				if(selectedCell != null && selectedCell.r >= 0 && selectedCell.r < maze.sizeR && selectedCell.c >= (selectedCell.r + 1) / 2 && 
				selectedCell.c < maze.sizeC + (selectedCell.r + 1) / 2){
					if(frontierCell.contains(selectedCell)){
						//
					}
					else{
						//checks if the cell is already in the New Maze.
						if(newMaze.contains(selectedCell)){
							//
						}
						else{
							//adds the cell into the frontier list.
							frontierCell.add(selectedCell);
						}
					}
				}
			}
		}// end of for loop
		return frontierCell; // returns the frontier Cell list.
	} // end of getFrontierCells()

	/**
	 * 
	 * Gets Neighbour Cells of the frontier Cell in the Neighbour list
	 * 	- Checks the type of maze
	 * 	- Checks if the cell is in the maze
	 * 	- Adds all the neighbour cells to the Neigbour List
	 * 
	 */
	private ArrayList<Cell> getNeighbourCells (Maze maze, Cell selectedFrontier, ArrayList<Cell> newMaze, ArrayList<Cell> neighbourCell){
		neighbourCell.clear();

		//A loop to get all the cell from all the direction
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell selectedNeighbour = selectedFrontier.neigh[i];

			//Gets Neigbour Cell if the maze if NORMAL
			if(maze.type == Maze.NORMAL){
				//checks if the cell is in the maze
				if(selectedNeighbour != null && selectedNeighbour.r >= 0 && selectedNeighbour.r < maze.sizeR && selectedNeighbour.c >= 0 && 
					selectedNeighbour.c < maze.sizeC){

					//checks if the neigbour cells is in the new maze
					if(newMaze.contains(selectedNeighbour)){
						//adds the cell in the Neighbour List
						neighbourCell.add(selectedNeighbour);
					}
				}
			}
			//Gets Neigbour Cell if the maze if NORMAL
			else{
				//checks if the cell is in the maze
				if(selectedNeighbour != null && selectedNeighbour.r >= 0 && selectedNeighbour.r < maze.sizeR && selectedNeighbour.c >= (selectedNeighbour.r + 1) / 2 && 
				selectedNeighbour.c < maze.sizeC + (selectedNeighbour.r + 1) / 2){
				
					//checks if the neigbour cells is in the new maze
					if(newMaze.contains(selectedNeighbour)){
						//adds the cell in the Neighbour List
						neighbourCell.add(selectedNeighbour);
					}
				}
			}
		}// end of for loop
		return neighbourCell; //returns the Neighbour list.
	}// end of getNeigbourCells()

	/**
	 * 
	 * Removes the wall between the current cell and the frontier cell
	 * 	- Checks the maze type.
	 * 	- Checks if both cells are in the maze.
	 * 	- cheks if the temporary cell is same as frontier cell.
	 * 	- removes the wall.
	 * 
	 */
	private void removeWall (Maze maze, Cell selectedNeighbour, Cell selectedFrontier){
		//A loop to get all the cell from all the direction
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell tempCell = selectedNeighbour.neigh[i];
			
			//removes the wall if the maze if NORMAL
			if(maze.type == Maze.NORMAL){
				//checks if the cell is in the maze
				if(tempCell != null && tempCell.r >= 0 && tempCell.r < maze.sizeR && tempCell.c >= 0 && 
				tempCell.c < maze.sizeC){

					//checks if the temp Cell is same as frontier cell
					if(tempCell == selectedFrontier){
						//removes the wall between the current cell and frontier cell
						selectedNeighbour.wall[i].present = false;
					}
				}
			}
			//removes the wall if the maze if HEX
			else{
				//checks if the cell is in the maze
				if(tempCell != null && tempCell.r >= 0 && tempCell.r < maze.sizeR && tempCell.c >= (tempCell.r + 1) / 2 && 
				tempCell.c < maze.sizeC +(tempCell.r + 1) / 2){

					//checks if the temp Cell is same as frontier cell
					if(tempCell == selectedFrontier){
						//removes the wall between the current cell and frontier cell
						selectedNeighbour.wall[i].present = false;
					}
				}
			}
		}// end of for loop
	}// end of removeWall()

} // end of class ModifiedPrimsGenerator
