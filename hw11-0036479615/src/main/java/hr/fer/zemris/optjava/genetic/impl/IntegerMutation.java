package hr.fer.zemris.optjava.genetic.impl;

import hr.fer.zemris.optjava.genetic.IMutationOperator;
import hr.fer.zemris.optjava.genetic.ISolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class IntegerMutation implements IMutationOperator<int[]> {

	private double mutationThreshold;
	private int min;
	private int max;

	public IntegerMutation(double mutationThreshold, int min, int max) {
		this.mutationThreshold = mutationThreshold;
		this.min = min;
		this.max = max;
	}

	@Override
	public ISolution<int[]> mutate(ISolution<int[]> solution) {
		int[] repr = solution.getRepresentation();
		int[] child = new int[repr.length];
		
		IRNG rng = RNG.getRNG();
		boolean mutated = false;
		
		for (int i = 0; i < child.length; i++) {
			if (rng.nextDouble() < mutationThreshold) {
				child[i] = repr[i] + rng.nextInt(min, max);
				mutated = true;
			} else {
				child[i] = repr[i];
			}
			
			if (child[i] < 0) {
				child[i] = rng.nextInt(0, 40);
			}
		}
		
		if (!mutated) {
			child[rng.nextInt(0, child.length)] += rng.nextInt(min, max);
		}
		
		return new IntSolution(child, 0d, 0d);
	}
}
