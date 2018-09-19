package hr.fer.zemris.optjava.genetic.impl;

import hr.fer.zemris.optjava.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.genetic.ISolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class IntegerCrossover implements ICrossoverOperator<int[]> {

	private double firstChildWeight;
	
	public IntegerCrossover(double firstChildWeight) {
		this.firstChildWeight = firstChildWeight;
	}

	@Override
	public ISolution<int[]> crossover(ISolution<int[]> solution1, ISolution<int[]> solution2) {
		int[] repr1 = solution1.getRepresentation();
		int[] repr2 = solution2.getRepresentation();
		
		int[] child = new int[repr1.length];
		IRNG rng = RNG.getRNG();
		
		for (int i = 0; i < child.length; i++) {
			if (rng.nextDouble() < firstChildWeight) {
				child[i] = repr1[i];
			} else {
				child[i] = repr2[i];
			}
		}
		
		return new IntSolution(child, 0d, 0d);
	}
}
