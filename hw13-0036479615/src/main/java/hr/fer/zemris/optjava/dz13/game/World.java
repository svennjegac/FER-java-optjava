package hr.fer.zemris.optjava.dz13.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class World {

	private int width;
	private int height;

	private int foodEaten;
	
	private int[][] map;
	private Orientation orientation;
	private Position position;
	
	public World(int width, int height, int foodEaten, int[][] map, Orientation orientation, Position position) {
		this.width = width;
		this.height = height;
		this.foodEaten = foodEaten;
		this.map = map;
		this.orientation = orientation;
		this.position = position;
	}

	public World(String fileName) {
		map = parseMap(fileName);
		width = map[0].length;
		height = map.length;
		
		foodEaten = 0;
		
		orientation = Orientation.RIGHT;
		position = new Position(0, 0);
		
		eatFood(position);
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getFoodEaten() {
		return foodEaten;
	}
	
	public boolean agentPosition(Position position) {
		return position.equals(this.position);
	}
	
	public void makeAction(Action action) {
		switch (action) {
		case MOVE:
			position = getNextPosition();
			eatFood(position);
			break;
		case LEFT:
			switch (orientation) {
			case UP:
				orientation = Orientation.LEFT;
				break;
			case LEFT:
				orientation = Orientation.DOWN;
				break;
			case DOWN:
				orientation = Orientation.RIGHT;
				break;
			case RIGHT:
				orientation = Orientation.UP;
			}
			break;
		case RIGHT:
			switch (orientation) {
			case UP:
				orientation = Orientation.RIGHT;
				break;
			case RIGHT:
				orientation = Orientation.DOWN;
				break;
			case DOWN:
				orientation = Orientation.LEFT;
				break;
			case LEFT:
				orientation = Orientation.UP;
			}
		}
	}

	public boolean foodAhead() {
		Position newPosition = getNextPosition();
		return foodAtPosition(newPosition);
	}
	
	public boolean foodAhead(List<Action> actions) {
		World copyWorld = copy();
		
		for (Action action : actions) {
			copyWorld.makeAction(action);
		}
		
		return copyWorld.foodAhead();
	}
	
	private Position getNextPosition() {
		Position newPosition = position.copy();
		
		switch (orientation) {
		case LEFT:
			newPosition.setX(newPosition.getX() - 1);
			break;
		case RIGHT:
			newPosition.setX(newPosition.getX() + 1);
			break;
		case UP:
			newPosition.setY(newPosition.getY() - 1);
			break;
		case DOWN:
			newPosition.setY(newPosition.getY() + 1);
		}

		newPosition.setX(((newPosition.getX() % width) + width) % width);
		newPosition.setY(((newPosition.getY() % height) + height) % height);
		
		return newPosition;
	}
	
	public boolean foodAtPosition(Position position) {
		return map[position.getY()][position.getX()] == 1;
	}
	
	public void eatFood(Position position) {
		if (foodAtPosition(position)) {
			foodEaten++;
		}
		
		map[position.getY()][position.getX()] = 0;
	}
	
	public World copy() {
		int[][] copyMap = new int[map.length][map[0].length];
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		return new World(width, height, foodEaten, copyMap, orientation, position.copy());
	}

	private int[][] parseMap(String fileName) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								World.class.getResourceAsStream("/" + fileName)))) {
			
			
			String[] dimensions = br.readLine().trim().split("x");
			int[][] map = new int[Integer.parseInt(dimensions[0])][Integer.parseInt(dimensions[1])];
			
			String line;
			int row = 0;
			
			while ((line = br.readLine()) != null) {
				line = line.trim().replaceAll("[.]", "0");
				
				for (int i = 0, size = map[0].length; i < size; i++) {
					map[row][i] = Integer.parseInt(line.substring(i, i + 1));
				}
				
				row++;
			}
			
			return map;
		} catch (Exception e) {
			throw new IllegalArgumentException("World unparseable.");
		}
	}
}
