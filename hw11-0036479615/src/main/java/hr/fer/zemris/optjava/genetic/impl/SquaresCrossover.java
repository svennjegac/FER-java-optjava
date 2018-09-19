package hr.fer.zemris.optjava.genetic.impl;

import hr.fer.zemris.optjava.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.genetic.ISolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SquaresCrossover implements ICrossoverOperator<int[]> {

	private int takeSquares;
	
	public SquaresCrossover(int takeSquares) {
		this.takeSquares = takeSquares;
	}

	@Override
	public ISolution<int[]> crossover(ISolution<int[]> solution1, ISolution<int[]> solution2) {
		int[] repr1 = solution1.getRepresentation();
		int[] repr2 = solution2.getRepresentation();
		int[] child = new int[repr1.length];
		
		IRNG rng = RNG.getRNG();
		
		if (rng.nextDouble() >= 0.5) {
			child[0] = repr1[0];
		} else {
			child[0] = repr2[0];
		}
		
		for (int i = 1; i < child.length; i++) {
			child[i] = repr1[i];
		}
		
		int[] indexes = getIndexes(child.length);
		copyIndexesToChild(child, repr2, indexes);
		
		return new IntSolution(child, 0d, 0d);
	}

	private void copyIndexesToChild(int[] child, int[] repr2, int[] indexes) {
		for (int i = 0; i < indexes.length; i++) {
			int square = indexes[i];
			
			for (int j = 0; j < 5; j++) {
				child[1 + square * 5 + j] = repr2[1 + square * 5 + j];
			}
		}
	}

	private int[] getIndexes(int length) {
		int squares = (length - 1) / 5;
		
		int[] indexes = new int[takeSquares];
		IRNG rng = RNG.getRNG();
		
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = rng.nextInt(0, squares);
			
			boolean exist = false;
			for (int j = 0; j < i; j++) {
				if (indexes[j] == indexes[i]) {
					exist = true;
					break;
				}
			}
			
			if (exist) {
				i--;
			}
		}
		
		return indexes;
	}
}
