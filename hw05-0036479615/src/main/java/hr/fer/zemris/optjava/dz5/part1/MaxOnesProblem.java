package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IProblem;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class MaxOnesProblem implements IProblem<boolean[]> {

	private Random rand;
	private int n;
	
	private static final double LOWER_THRESH = 0.8;
	private static final double UPPER_THRESH = 0.9;
	
	public MaxOnesProblem(int n) {
		this.n = n;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public double getFitness(ISolution<boolean[]> solution) {
		int ones = countOnes(solution);
		
		if (ones <= LOWER_THRESH * n) {
			return (double) ones / n;
		} else if (ones <= UPPER_THRESH * n) {
			return LOWER_THRESH;
		} else {
			return (double) (2 * ones) / n - 1;
		}
	}

	@Override
	public double getValue(ISolution<boolean[]> solution) {
		return countOnes(solution);
	}

	@Override
	public ISolution<boolean[]> generateRandomSolution() {
		boolean[] representation = new boolean[n];
		
		for (int i = 0; i < n; i++) {
			representation[i] = rand.nextBoolean();
		}
		
		BitVectorSolution randomSolution = new BitVectorSolution(representation);
		randomSolution.setFitness(getFitness(randomSolution));
		randomSolution.setValue(getValue(randomSolution));
		return randomSolution;
	}
	
	private int countOnes(ISolution<boolean[]> solution) {
		int counter = 0;
		
		for (boolean value : solution.getRepresentation()) {
			if (value == true) {
				counter++;
			}
		}
		
		return counter;
	}
}
