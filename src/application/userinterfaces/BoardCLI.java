package application.userinterfaces;

import application.gameoflife.LifeGrid;

public class BoardCLI {

	
	public boolean show(int[][] grid) {
		boolean seen = false;
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
	

}
