package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IMutationOperator;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class UnifProbabilityMutator implements IMutationOperator<boolean[]> {

	private double mutationThreshold;
	private Random rand;
	
	public UnifProbabilityMutator(double mutationThreshold) {
		this.mutationThreshold = mutationThreshold;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<boolean[]> mutate(ISolution<boolean[]> solution) {
		boolean[] childArray = Arrays.copyOf(solution.getRepresentation(), solution.getRepresentation().length);
		
		for (int i = 0; i < childArray.length; i++) {
			if (rand.nextDouble() < mutationThreshold) {
				childArray[i] = childArray[i] ^ true;
			}
		}
		
		return new BitVectorSolution(childArray);
	}
}
