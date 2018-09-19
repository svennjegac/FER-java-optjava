package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoxFactory {

	public static List<Box> createBoxes(List<Stick> stickList) {
		List<Box> boxList = new ArrayList<>();
		
		Box box = new Box();
		for (Stick stick : stickList) {
			box.addStick(stick);
			
			if (box.getLengthOfSticksInBox() > Box.MAX_HEIGHT) {
				box.removeLastStick();
				boxList.add(box);
				box = new Box();
				box.addStick(stick);
			}
		}
		boxList.add(box);
		
		return boxList;
	}
	
	public static List<Box> createRandomBoxes(List<Stick> stickList) {
		Collections.shuffle(stickList, new Random(System.currentTimeMillis()));
		return createBoxes(stickList);
	}
	
	public static List<Box> createRandomBoxPerStick(List<Stick> stickList) {
		Collections.shuffle(stickList, new Random(System.currentTimeMillis()));
		List<Box> boxList = new ArrayList<>();
		
		stickList.forEach(stick -> {
			List<Stick> stList = new ArrayList<>();
			stList.add(stick);
			boxList.add(new Box(stList));
		});
		
		return boxList;
	}
}
