package application.gameoflife;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import application.filereaders.LifeReader;
import application.filereaders.RAWReader;
import application.filereaders.RLEReader;

public class LifeGrid {
	int[][] grid;
	int generation = 0;

	
	public LifeGrid(int x, int y) {
		grid = new int[y][x];
		randomise();
	}
	
	public LifeGrid(File seedFile, int x, int y) throws FileNotFoundException {
		if(null == seedFile) {
			grid = new int[y][x];
			randomise();
		} else {
			readSeed(seedFile, x, y);
		}
	}

	

	private void readSeed(File file, int x, int y) throws FileNotFoundException {
		// RLEReader ignores the given X, Y attributes here
		LifeReader reader = (RLEReader.isFileRLE(file)) ? new RLEReader(file, x, y) : new RAWReader(file, x, y);
		this.grid = reader.getGrid();
		
	}
	
	public boolean show() {
		boolean seen = false;
		System.out.println("Generation: "+this.generation);
		String topRow = (grid.length > 0)?new String(new char[(2*grid[0].length)+4]).replace("\0", "-"):"";
		System.out.println(topRow);
		for (int i = 0; i < grid.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(((grid[i][j] > 0)?"* ":"  "));
				if(!seen && grid[i][j] > 0) seen = true;
			}
			System.out.println(" |");
		}
		System.out.println(topRow);
		return seen;
	}
	
	public boolean showDelta(int[][] delta) {
		boolean seen = false;
		System.out.println("Generation: "+this.generation);
		String topRow = (delta.length > 0)?new String(new char[(2*delta[0].length)+3]).replace("\0", "-"):"";
		System.out.println(topRow);
		for (int i = 0; i < delta.length; i++) {
			System.out.print("|");
			for (int j = 0; j < delta[i].length; j++) {
				System.out.print(" "+(delta[i][j]));
				if(!seen && delta[i][j] > 0) seen = true;
			}
			System.out.println(" |");
		}
		System.out.println(topRow);
		return seen;
	}
	
	public int[][] run() {
		int[][] newGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < newGrid.length; i++) {
			for (int j = 0; j < newGrid[i].length; j++) {
				if(this.grid[i][j] > 0){
					if(neighbours(j, i) < 2){
						newGrid[i][j] = 0;
					} else if(neighbours(j, i) > 3) {
						newGrid[i][j] = 0;
					} else if(neighbours(j, i) == 2 || neighbours(j, i) == 3) {
						newGrid[i][j] = 2;
					} 
				} else {
					if(neighbours(j, i) == 3) {
						newGrid[i][j] = 1;
					}
				}
			}
		}

		grid = newGrid;
		generation ++;
		return newGrid;
	}
	public int[][] runDelta() {
		int[][] deltaGrid = new int[grid.length][grid[0].length];
		int[][] originalGrid = run();
		
		for (int i = 0; i < originalGrid.length; i++) {
			for (int j = 0; j < originalGrid[i].length; j++) {
				if(originalGrid[i][j] > 0){
					if(neighbours(j, i) < 2){
						deltaGrid[i][j] = 1;
					} else if(neighbours(j, i) > 3) {
						deltaGrid[i][j] = 1;
					} else if((neighbours(j, i) == 2 || neighbours(j, i) == 3)) {
						deltaGrid[i][j] = 3;
					} 

				} else {
					if(neighbours(j, i) == 3 ) {
						deltaGrid[i][j] = 2;
					}
				}
			}
		}
		return deltaGrid;
	}
	
	public int neighbours(int x, int y){
		try {
			int n = 0;
			if(isInGrid(y, x-1) && grid[y][x-1] > 0) n++;
			if(isInGrid(y+1, x-1) && grid[y+1][x-1] > 0) n++;
			if(isInGrid(y+1, x) && grid[y+1][x] > 0) n++;
			if(isInGrid(y+1, x+1) && grid[y+1][x+1] > 0) n++;
			if(isInGrid(y, x+1) && grid[y][x+1] > 0) n++;
			if(isInGrid(y-1, x+1) && grid[y-1][x+1] > 0) n++;
			if(isInGrid(y-1, x) && grid[y-1][x] > 0) n++;
			if(isInGrid(y-1, x-1) && grid[y-1][x-1] > 0) n++;
			return n;
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("err at: "+x+", "+y);
			System.exit(0);
		}
		return 0;
		
	}
	
	public void growIfNeeded(int x, int y){
		if(!isInGrid(y, x+1)) growGrid(1, 0);
		else if(!isInGrid(y+1, x)) growGrid(0, 1);
		else if(!isInGrid(y+1, x+1)) growGrid(1, 1);
	}
	
	private boolean isInGrid(int y, int x) {
	    return !(x < 0 || y < 0)&&!(x >= grid.length || y >= grid[0].length);
	}
	
	private void growGrid(int x, int y) {
		
		/*grid = Arrays.copyOf(grid, grid.length+1);
		grid[grid.length] = new int[grid[0].length];*/
		
		
		
		/*for (int j = 0; j < grid.length; j++) {
			int[] newY = new int[grid[j].length];
			System.arraycopy(grid[j], 0, newY, 0, grid[j].length + 1);
			grid[j] = newY;
		}  */
		
		
		/*for (int i = 0; i < grid.length; i++) {
			/*if(y != 0)
				grid = Arrays.copyOf(grid, grid.length + 1);*/
		/*	if(x != 0) {
				for (int j = 0; j < grid[i].length; j++) {
					grid[i] = Arrays.copyOf(grid[i], grid[i].length + 1);
				}
			}
				
		}*/
	}
	
	
	   public void resize(int cols, int rows) {
	    
	    }


	public int getWidth() {
		return grid.length;
	}

	public int getHeight() {
		return grid[0].length;
	}
	
	public char getAtPoint(int x, int y) {
		return (grid[y][x] == 1 || grid[y][x] == 2)?'*':' ';
	}

	public int toggleCell(int x, int y) {
		return (grid[y][x] = (grid[y][x] == 1 || grid[y][x] == 2)?0:1);
	}
	public int[] row(int i) {
		return grid[i];
	}

	public int getGeneration() {
		return generation;
	}
	
	public void randomise() {
		for (int i = 0; i < grid.length; i++) {
			int[] row = grid[i];
			for (int j = 0; j < row.length; j++) {
				grid[i][j] = (Math.random() < 0.5)?1:0;
			}
		}
		generation = 0;
	}
}
