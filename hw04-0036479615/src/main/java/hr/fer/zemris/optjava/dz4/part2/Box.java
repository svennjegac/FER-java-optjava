package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box {

	public static int MAX_HEIGHT;
	public static int GOOD_FILLING_THRESH;
	
	private List<Stick> stickList;

	public Box() {
		this(new ArrayList<>());
	}
	
	public Box(List<Stick> stickList) {
		this.stickList = stickList;
	}
	
	public List<Stick> getStickList() {
		return stickList;
	}
	
	public int getLengthOfSticksInBox() {
		return stickList
				.stream()
				.mapToInt(stick -> stick.getLength())
				.sum();
	}
	
	public boolean isEmpty() {
		return stickList.isEmpty();
	}
	
	public void addStick(Stick stick) {
		stickList.add(stick);
	}
	
	public Stick removeLastStick() {
		return stickList.remove(stickList.size() - 1);
	}
	
	public Stick removeRandomStick(Random rand) {
		return stickList.remove(rand.nextInt(stickList.size()));
	}

	public Box copy() {
		List<Stick> newList = new ArrayList<>();
		stickList.forEach(stick -> newList.add(stick));
		return new Box(newList);
	}
}
