package application.filereaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Scanner;

public class RAWReader implements LifeReader{
	private File file;
	private int x;
	private int y;
	private int[][] grid;
	public RAWReader(File file, int x, int y) throws FileNotFoundException {
		this.file = file;
		this.x = x;
		this.y = y;
		read();
	}
	
	public RAWReader(File file) throws FileNotFoundException {
		this.file = file;
		
		read();
	}


	private void read() throws FileNotFoundException {
		grid = new int[y][x];
		Scanner scanner = new Scanner(file);
		int yLen = 0;
		int xLen = 0;
		while (scanner.hasNext()) {
			char[] rowString = scanner.nextLine().toCharArray();
			for (int i = 0; i < rowString.length; i++) {
				grid[yLen][i] = (rowString[i] == '*')?1:0;
			}
			xLen = rowString.length;
			yLen++;
		}
		
		if(x == 0 || y == 0) {
			y = yLen;
			x = xLen;
			
		}
		scanner.close();
	}

	@Override
	public int[][] getGrid() {
		return grid;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
