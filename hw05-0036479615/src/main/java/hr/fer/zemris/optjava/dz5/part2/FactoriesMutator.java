package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IMutationOperator;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class FactoriesMutator implements IMutationOperator<int[]> {

	private Random rand;
	private double mutationThreshold;
	
	public FactoriesMutator(double mutationThreshold) {
		this.mutationThreshold = mutationThreshold;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<int[]> mutate(ISolution<int[]> solution) {
		int[] repr = solution.getRepresentation();
		boolean mutated = false;
		
		for (int i = 0; i < repr.length; i++) {
			if (rand.nextDouble() > mutationThreshold) {
				continue;
			}
			
			mutated = true;
			swap(repr, i);
		}
		
		if (!mutated) {
			swap(repr, rand.nextInt(repr.length));
		}
		
		return new FactoriesLocationsSolution(repr, 0d, 0d);
	}

	private void swap(int[] repr, int i) {
		int j;
		while ((j = rand.nextInt(repr.length)) == i);
		
		int tmp = repr[i];
		repr[i] = repr[j];
		repr[j] = tmp;
	}
}
