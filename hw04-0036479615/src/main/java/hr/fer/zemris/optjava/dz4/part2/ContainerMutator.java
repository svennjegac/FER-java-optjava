package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.IMutationOperator;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class ContainerMutator implements IMutationOperator<List<Box>> {

	private static final double MAX_VALUE = 0.9;
	private static final double MIN_VALUE = 0.1;
	
	private Random rand;
	
	public ContainerMutator() {
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<List<Box>> mutate(ISolution<List<Box>> solution) {
		List<Box> boxList = solution.getRepresentation();
	
		int index = -1;
		boolean mutated = false;
		
		while (index < boxList.size() - 1) {
			index++;
			
			if (rand.nextDouble() > getMutationProbability(boxList.get(index))) {
				continue;
			}
			
			mutated = true;
			mutateListOnIndex(boxList, index);
		}
		
		if (!mutated) {
			mutateListOnIndex(boxList, rand.nextInt(boxList.size()));
		}
		
		return new Container(boxList);
	}

	private double getMutationProbability(Box box) {
		double value = (double) box.getLengthOfSticksInBox() / (Box.MAX_HEIGHT);
		value = 1 - value;
		return value * (MAX_VALUE - MIN_VALUE) + MIN_VALUE;
	}
	
	private void mutateListOnIndex(List<Box> boxList, int index) {
		Box currentBox = boxList.get(index);
		Stick stick = currentBox.removeRandomStick(rand);
		if (currentBox.isEmpty()) {
			boxList.remove(currentBox);
		}

		for (Box box : boxList) {
			if (box == currentBox) {
				continue;
			}
			
			box.addStick(stick);
			if (box.getLengthOfSticksInBox() > Box.MAX_HEIGHT) {
				box.removeLastStick();
			} else {
				return;
			}
		}
		
		putStickInNewBox(boxList, stick);
	}
	
	private void putStickInNewBox(List<Box> boxList, Stick stick) {
		List<Stick> oneStickList = new ArrayList<>();
		oneStickList.add(stick);
		boxList.add(new Box(oneStickList));
	}
}
