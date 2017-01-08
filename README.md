# Conways 'Game of Life' CS1801 Submission - Matt Brown 

## Usage

For normal use, the program accepts the following parameters:

java -jar GameOfLife.jar -h <height> -w <width> [-t] [—file=<filename.rle>]

## Parameters

The program accepts the following parameters: (these can also be accessed by just running java -jar GameOfLife.jar)

  -c,--cellsize <arg>   The pixel dimensions of each cell (only in GUI)
 
  -d,--delay <arg>      The number of milliseconds between each refresh
 
  -f,--file <arg>       The location of the seed file
 
  -h,--height <arg>     The height of the grid
 
  -t,--text             Output the contents of the game to the console
 
  -w,--width <arg>      The width of the grid


For the seed file argument, files can be provided in any of the following formats: RLE, Life (1.05, 1.06), RAW. Please use 'dot and star' or 'space and star' notation for RAW.


## Source

The source code can be found in the src/ directory.

## Performance

If attempting to use a graphical grid of height and/or width greater than 550 units, graphics acceleration is required to ensure smooth generation refreshes.
