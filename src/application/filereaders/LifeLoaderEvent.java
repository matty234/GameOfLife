package application.filereaders;

public interface LifeLoaderEvent {
	void onLifeLoaded(int x, int y, int[][] grid);
}
