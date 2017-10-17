package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

/**
 * @author Japan Patel
 * This maze is generated using Prim's Algorithm
 */
public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {

		Random randomNum = new Random();
		ArrayList<Cell> frontierCell = new ArrayList<>();
		ArrayList<Cell> neighbourCell = new ArrayList<>();
		ArrayList<Cell> newMaze = new ArrayList<>();
		int size = 0;
		Cell startCell = null;
		Cell cCell = null;
		Cell selectedFrontier = null;
		Cell selectedNeighbour = null;
		

		if(maze.type == Maze.NORMAL){
			int nRow = randomNum.nextInt(maze.sizeR);
			int nColumn = randomNum.nextInt(maze.sizeC);
			
			startCell = maze.map[nRow][nColumn];
			cCell = startCell;
			size = maze.sizeC * maze.sizeR;
			System.out.println("Start Cell: " + nRow + ", " + nColumn);
		}
		else if (maze.type == Maze.HEX) {
				int hRow = randomNum.nextInt(maze.sizeR);
				int hColumn = randomNum.nextInt(maze.sizeC + (maze.sizeR + 1) / 2);
				
				startCell = maze.map[hRow][hColumn];
				cCell = maze.map[hRow][hColumn];
				size = maze.sizeC * maze.sizeR;
				System.out.println("Start Cell: " + hRow + ", " + hColumn);
		}
		newMaze.add(startCell);

		// System.out.println("Size of maze: " + size);
		// System.out.println("Start Cell: " + startCell.r + "," + startCell.c);
		// System.out.println("Current Cell: " + cCell.r + "," + cCell.c);
		// System.out.println("----------------------------------------------------");

		while(newMaze.size() < size){
			//System.out.println("Current Cell: " + cCell.r + "," + cCell.c);
			getFrontierCells(cCell, maze, frontierCell, newMaze);
			// // System.out.print("Frontier Cell Size: "+frontierCell.size());
			// for(int i = 0; i < frontierCell.size(); i++){
			// 	System.out.print(frontierCell.get(i).r + ","+frontierCell.get(i).c + " | ");
			// }
			int randFrontier = randomNum.nextInt(frontierCell.size());
			selectedFrontier = frontierCell.get(randFrontier);
			frontierCell.remove(selectedFrontier);

			neighbourCell.clear();
			getNeighbourCells(maze, selectedFrontier, newMaze, neighbourCell);
			int randNeighbour = randomNum.nextInt(neighbourCell.size());
			selectedNeighbour = neighbourCell.get(randNeighbour);
			// System.out.print("\nNeighbour Size: "+neighbourCell.size()+" ");
			// System.out.println(selectedNeighbour.r +","+selectedNeighbour.c);
			for(int i = 0; i < neighbourCell.size(); i++){
				System.out.print(neighbourCell.get(i).r + ","+neighbourCell.get(i).c+" | ");
			}

			 removeWall(maze, selectedNeighbour, selectedFrontier);
			// for(int i = 0; i < maze.NUM_DIR; i++){
			// 	Cell newcell = selectedNeighbour.neigh[i];
			// 	if(newcell != null && newcell.r >= 0 && newcell.r < maze.sizeR && newcell.c >= 0 && 
			// 	newcell.c < maze.sizeC && newcell == selectedFrontier){
			// 			// if(newcell == selectedFrontier){
			// 				System.out.println("Path Carving between: "+selectedFrontier.r+","+selectedFrontier.c+" & "+newcell.r+","+newcell.c);
			// 				selectedNeighbour.wall[i].present = false;
			// 			// }
			// 	}
			// }



			newMaze.add(selectedFrontier);
			System.out.println("New Cell added to maze: "+selectedFrontier.r+","+selectedFrontier.c);
			cCell = selectedFrontier;
			System.out.println("New Maze size: " + newMaze.size());
			System.out.println("---");
			//add frontier cell to new maze
			//current cell = frontier cell
		}

		

	} // end of generateMaze()

	private ArrayList<Cell> getFrontierCells (Cell cCell, Maze maze, ArrayList<Cell> frontierCell, ArrayList<Cell> newMaze){
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell selectedCell = cCell.neigh[i];
			
			if(selectedCell != null && selectedCell.r >= 0 && selectedCell.r < maze.sizeR && selectedCell.c >= 0 && 
				selectedCell.c < maze.sizeC){
				if(!frontierCell.contains(selectedCell)){
					if(!newMaze.contains(selectedCell)){
						frontierCell.add(selectedCell);
					}
				}
			}
			
		}
		return frontierCell;
	}

	private ArrayList<Cell> getNeighbourCells (Maze maze, Cell selectedFrontier, ArrayList<Cell> newMaze, ArrayList<Cell> neighbourCell){
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell selectedNeighbour = selectedFrontier.neigh[i];
			if(selectedNeighbour != null && selectedNeighbour.r >= 0 && selectedNeighbour.r < maze.sizeR && selectedNeighbour.c >= 0 && 
				selectedNeighbour.c < maze.sizeC){
				if(newMaze.contains(selectedNeighbour)){
						neighbourCell.add(selectedNeighbour);
				}
			}
		}
		return neighbourCell;
	}

	private void removeWall (Maze maze, Cell selectedNeighbour, Cell selectedFrontier){
		for(int i = 0; i < maze.NUM_DIR; i++){
			Cell newcell = selectedNeighbour.neigh[i];
			if(newcell != null && newcell.r >= 0 && newcell.r < maze.sizeR && newcell.c >= 0 && 
			newcell.c < maze.sizeC && newcell == selectedFrontier){
					// if(newcell == selectedFrontier){
						System.out.println("Path Carving between: "+selectedFrontier.r+","+selectedFrontier.c+" & "+newcell.r+","+newcell.c);
						selectedNeighbour.wall[i].present = false;
					// }
			}
		}
		// return true;
	}

} // end of class ModifiedPrimsGenerator
