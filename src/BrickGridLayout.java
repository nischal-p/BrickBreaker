import java.io.BufferedReader;
import java.util.Random;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BrickGridLayout {
	
	public static final int BRICK_GRID_WIDTH = GameCourt.COURT_WIDTH/GameCourt.SCALING;
	public static final int BRICK_GRID_HEIGHT = 8;
	
	public static String[][] getBrickGridLayout(String filePath) {
		String[][] brickGridMap = new String[BRICK_GRID_HEIGHT][BRICK_GRID_WIDTH];
		BufferedReader bReader = null;
		String line = "";
		try {
			bReader = new BufferedReader(new FileReader(filePath));
			for (int x=0; x < BRICK_GRID_HEIGHT; x++) {
				line = bReader.readLine();
				String[] lettersInLine = line.split("");
				if (lettersInLine.length != BRICK_GRID_WIDTH){
					throw new IndexOutOfBoundsException();
				} else {
					brickGridMap[x] = lettersInLine;
				}
			}
		} catch (IOException e) {
			System.out.println("Maze file does not exist");
			throw new IllegalArgumentException();
		} catch (NullPointerException e) {
			System.out.println("FileName cannot be null");
			throw new IllegalArgumentException();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Maze file does not follow the required format");
			throw new IllegalArgumentException();
		} finally {
			try {
				bReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return brickGridMap;
	}
	
	public static void saveCurrentGridLayout(String currentLayoutInString) {
		BufferedWriter bWriter= null;
		try {
			// overwrites the contents of the file
			bWriter = new BufferedWriter (new FileWriter (GameCourt.SAVED_BRICK_MAP));
			bWriter.write(currentLayoutInString);
		} catch (IOException e) {
			System.out.println("Save file does not exist");
			throw new IllegalArgumentException();
		} finally {
			try {
				bWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String[][] getRandomBrickLayout() {
		String[][] brickGridMap = new String[BRICK_GRID_HEIGHT][BRICK_GRID_WIDTH];
		int randomInt = 0;
		for (int x=0; x < BRICK_GRID_HEIGHT; x++) {
			for (int y=0; y < BRICK_GRID_WIDTH; y++) {
				//creating bricks with a 1:2 ratio to empty spaces
				Random rd = new Random();
				randomInt = rd.nextInt(3);
				if (randomInt == 0 || randomInt  == 1) {
					brickGridMap[x][y] = "0";
				} else {
					brickGridMap[x][y] = "#";
				}
			}
		}
		return brickGridMap;
	}
}
