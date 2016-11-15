package application.gameoflife;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LifeGrid {
	int[][] grid;
	int generation = 0;
	int width;
	int height;
	
	public LifeGrid(int x, int y, String seedFile) throws FileNotFoundException {
		this.height = (y);
		this.width = (x);
		grid = new int[y][x];
		if(null == seedFile) this.randomise();
		else readSeed(seedFile);
	}
	
	private void readSeed(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		int j = 0;
		while (scanner.hasNext()) {
			char[] rowString = scanner.nextLine().toCharArray();
			for (int i = 0; i < rowString.length; i++) {
				grid[j][i] = (rowString[i] == '*')?1:0;
			}
			j++;
		}
		scanner.close();
	}
	
	public boolean show() {
		boolean seen = false;
		System.out.println("Generation: "+this.generation);
		String topRow = (grid.length > 0)?new String(new char[(2*grid[0].length)+3]).replace("\0", "-"):"";
		System.out.println(topRow);
		for (int i = 0; i < grid.length; i++) {
			System.out.print("|");
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(" "+((grid[i][j] > 0)?"*":" "));
				if(!seen && grid[i][j] > 0) seen = true;
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
	
	public int neighbours(int x, int y){
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
	}
	private boolean isInGrid(int y, int x) {
	    return !(x < 0 || y < 0)&&!(x >= grid[0].length || y >= grid.length);
	}
	


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void toggleCell(int x, int y) {
		grid[y][x] = 1;
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
	}

}
