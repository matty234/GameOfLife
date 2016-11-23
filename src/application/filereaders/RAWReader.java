package application.filereaders;

import java.io.File;
import java.io.FileNotFoundException;
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

	private void read() throws FileNotFoundException {
		grid = new int[y][x];
		Scanner scanner = new Scanner(file);
		int j = 0;
		while (scanner.hasNext()&& !(j > y)) {
			char[] rowString = scanner.nextLine().toCharArray();
			for (int i = 0; i < x; i++) {
				grid[j][i] = (rowString.length > i && rowString[i] == '*')?1:0;
			}
			j++;
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
