package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class ContainerCrossover implements ICrossoverOperator<List<Box>> {
	
	private Random rand = new Random(System.currentTimeMillis());

	@Override
	public ISolution<List<Box>> crossover(ISolution<List<Box>> solution1, ISolution<List<Box>> solution2) {
		List<Box> childBoxes = new ArrayList<>();
		BoxProvider provider1 = new BoxProvider(solution1.getRepresentation());
		BoxProvider provider2 = new BoxProvider(solution2.getRepresentation());
		
		int iter = 0;
		Box nextBox;
		
		while ((nextBox = getNextBox(provider1, provider2, iter)) != null) {
			iter++;
			childBoxes.add(nextBox);
		}
		
		if (!provider1.getRemainingSticks().isEmpty()) {
			childBoxes.addAll(BoxFactory.createBoxes(provider1.getRemainingSticks()));
		}
		
		return new Container(childBoxes);
	}
	
	private Box getNextBox(BoxProvider p1, BoxProvider p2, int iter) {
		if (iter % 2 == 0) {
			return fetchBoxFromProviders(p1, p2);
		} else {
			return fetchBoxFromProviders(p2, p1);
		}
	}
	
	private Box fetchBoxFromProviders(BoxProvider provider1, BoxProvider provider2) {
		Box nextBox = provider1.provideBox();
		
		if (nextBox != null) {
			provider2.removeBoxedSticks(nextBox);
		} else {
			nextBox = provider2.provideBox();
			if (nextBox != null) {
				provider1.removeBoxedSticks(nextBox);
			}
		}
		
		return nextBox;
	}
	
	private class BoxProvider {
		
		private static final double BEST_BOX_PROBABILITY = 0.5;
		private List<Box> boxList;
		private List<Stick> stickList;
		
		public BoxProvider(List<Box> boxList) {
			copyBoxList(boxList);
			unpackBoxes();
		}

		private void copyBoxList(List<Box> boxList) {
			this.boxList = new ArrayList<>();
			boxList.forEach(box -> this.boxList.add(box.copy()));
		}

		private void unpackBoxes() {
			stickList = new ArrayList<>();
			
			for (Box box : boxList) {
				for (Stick stick : box.getStickList()) {
					stickList.add(stick);
				}
			}
		}
		
		public Box provideBox() {
			if (rand.nextDouble() > BEST_BOX_PROBABILITY) {
				return getRandomBox();
			}
			
			return getBestBox();
		}
		
		private Box getBestBox() {
			List<Box> possibleBoxes = getPossibleBoxes();
			if (possibleBoxes.isEmpty()) {
				return null;
			}
			
			Optional<Box> bestBox = possibleBoxes
										.stream()
										.max((b1, b2) -> Integer.compare(
															b1.getLengthOfSticksInBox(),
															b2.getLengthOfSticksInBox()));
			
			if (bestBox.isPresent() &&
					bestBox.get().getLengthOfSticksInBox() > Box.GOOD_FILLING_THRESH) {
				
				removeBoxedSticks(bestBox.get());
				boxList.remove(bestBox.get());
				return bestBox.get();
			}
			
			return null;
		}
		
		private Box getRandomBox() {
			List<Box> possibleBoxes = getPossibleBoxes();
			if (possibleBoxes.isEmpty()) {
				return null;
			}
			
			possibleBoxes.sort((b1, b2) ->	Integer.compare(
												b2.getLengthOfSticksInBox(),
												b1.getLengthOfSticksInBox()));
			
			Box box = possibleBoxes.get(rand.nextInt(possibleBoxes.size() / 2 + 1));
			removeBoxedSticks(box);
			boxList.remove(box);
			
			return box;
		}
		
		private List<Box> getPossibleBoxes() {
			return boxList
					.stream()
					.filter(box -> {
								List<Stick> tempStickList = Stick.copyStickList(stickList);
								
								for (Stick stick : box.getStickList()) {
									if (!tempStickList.contains(stick)) {
										return false;
									}
									
									tempStickList.remove(stick);
								}
								
								return true;})
					.collect(Collectors.toList());
		}
		
		public void removeBoxedSticks(Box box) {
			box.getStickList().forEach(stick -> stickList.remove(stick));
		}
		
		public List<Stick> getRemainingSticks() {
			return stickList;
		}
	}
}
