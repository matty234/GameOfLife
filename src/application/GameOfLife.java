package application;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.apache.commons.cli.ParseException;

import application.gameoflife.LifeGrid;

public class GameOfLife {
	static LifeGrid grid;
	static int x;
	static int y;
	static int delay = 200;
	static String file = null;

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {

		Options options = new Options();

		Option fileOption = new Option("f", "file", true, "The location of the seed file (RLE, Life 1.06, ");
		fileOption.setRequired(false);
		options.addOption(fileOption);
		
		Option heightOption = new Option("h", "height", true, "The height of the grid");
		heightOption.setRequired(true);
		options.addOption(heightOption);

		Option widthOption = new Option("w", "width", true, "The width of the grid");
		widthOption.setRequired(true);
		options.addOption(widthOption);


		Option textOption = new Option("t", "text", false, "Output the contents of the game to the console");
		textOption.setRequired(false);
		options.addOption(textOption);

		Option delayOption = new Option("d", "delay", true, "The number of miliseconds between each refresh");
		delayOption.setRequired(false);
		options.addOption(delayOption);
		
		Option cellSize = new Option("c", "cellsize", true, "The pixel dimensions of each cell (only in GUI)");
		cellSize.setRequired(false);
		options.addOption(cellSize);
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("java -jar GameOfLife.jar", options);
			System.out.println("\nSeed files can be provided in any of the following formats: RLE, Life (1.05, 1.06), RAW");
			System.out.println("Please use 'dot and star' or 'space and star' notation for RAW.\n");

			System.exit(1);
			return;
		}

		if (cmd.hasOption("text")) {
			x = Integer.parseInt(cmd.getOptionValue("width"));
			y = Integer.parseInt(cmd.getOptionValue("height"));

			if (cmd.hasOption("file")) {
				file = cmd.getOptionValue("file");
			} else {
				file = null;
			}

			if (cmd.hasOption("delay")) {
				delay = Integer.parseInt(cmd.getOptionValue("delay"));
			}

			launchText();
		} else {
			GUI gui = new GUI();

			gui.x = Integer.parseInt(cmd.getOptionValue("width"));
			gui.y = Integer.parseInt(cmd.getOptionValue("height"));
			
			try {
				if(cmd.hasOption("cellsize")) gui.cellSize = Integer.parseInt(cmd.getOptionValue("cellsize"));
			} catch (NumberFormatException e) {
				System.err.println("Please enter an integer value for the cell size.");
				System.exit(1);
			}
			
			if (cmd.hasOption("file")) {
				gui.file = cmd.getOptionValue("file");
			} else {
				gui.file = null;
			}

			if (cmd.hasOption("delay")) {
				gui.delay = Integer.parseInt(cmd.getOptionValue("delay"));
			}
			javafx.application.Application.launch(GUI.class);
			try {
				gui.launch(args);
			} catch (java.lang.RuntimeException e) {
			}
		}

	}

	private static void launchText() throws InterruptedException, FileNotFoundException {
		if(null == file) {
			grid = new LifeGrid(x, y);
		} else {
			grid = new LifeGrid(new File(file), x, y);
		}
		grid.show();
		do {
			Thread.sleep(delay);
			grid.run();
		} while (grid.show());
	}

}
