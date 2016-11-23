package application.filereaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RLEReader implements LifeReader{
	int x;
	int y; 
	int[][] grid;
	String rule;
	File file;
	String name;
	
	public RLEReader(File file) throws FileNotFoundException {
		this.file = file;
		read();
	}
	
	public static boolean isFileRLE(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		if(sc.hasNextLine()){
			String header;
			while((header = sc.nextLine()).startsWith("#"));
			sc.close();
			return (header.contains("rule") && header.contains("x") && header.contains("y"));
		} 
		sc.close();
		return false;
	}
	
	private void read() throws FileNotFoundException{
		int gridColPointer = 0;
		int gridRowPointer = 0;

		Scanner sc = new Scanner(file);
		if(sc.hasNextLine()){
			String header;
			while((header = sc.nextLine()).startsWith("#")) {
				if(header.split("#")[1].startsWith("N")){
					name = header.split("#")[1].split("N")[1].trim();
				}
			}
			String[] headerItems = header.split(",");
			for (int i = 0; i < headerItems.length; i++) {
				if(headerItems[i].contains("x =")) 		 this.x = Integer.parseInt(headerItems[i].replaceAll("\\D+",""));
				else if(headerItems[i].contains("y ="))  this.y = Integer.parseInt(headerItems[i].replaceAll("\\D+",""));
				else if(headerItems[i].contains("rule =")){
					Pattern pattern = Pattern.compile("[A-Z 1-9]{2}\\/[A-Z 1-9]{3}");
					Matcher matcher = pattern.matcher(headerItems[i]);
					if(matcher.find()) rule = matcher.group(0);
				}
			}
			grid = new int[y][x];
		
			while(sc.hasNextLine()){
				char[] lineChars = sc.nextLine().trim().toCharArray();
				int consecCells = 1;
				for (int i = 0; i < lineChars.length; i++) {
					if(Character.isDigit(lineChars[i])){
						int numOfInts = 1;
						int startInt = i;
						while(Character.isDigit(lineChars[i])) {
							numOfInts ++;
							i++;
						}
						char[] intRep = new char[numOfInts];
						for (int j = 0; j < numOfInts-1; j++) {
							intRep[j]  = lineChars[startInt+j];
						}
						consecCells = Integer.parseInt(new String(intRep).trim());		
					}
					
					
					if(lineChars[i] == 'b') {
						for (int j = 1; j <= consecCells; j++) {
							grid[gridColPointer][gridRowPointer] = 0;
							gridRowPointer ++;
						}
						consecCells = 1;
					} else if(lineChars[i] == 'o') {
						for (int j = 1; j <= consecCells; j++) {
							grid[gridColPointer][gridRowPointer] = 1;
							gridRowPointer ++;
						}
						consecCells = 1;
					} else if(lineChars[i] == '$') {
						gridColPointer ++;
						gridRowPointer = 0;
						consecCells = 1;
					}
				}
			}			
			
		} 
		sc.close();
	}
	
	public void print() {
		System.out.println(Arrays.deepToString(grid));
	}

	public int[][] getGrid() {
		return grid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
