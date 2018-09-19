package hr.fer.zemris.optjava.dz9;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithms.IProblem;
import hr.fer.zemris.optjava.dz9.algorithms.ISolution;

public class LinearMinimization implements IProblem<double[]> {

	private static final int NUM_FIELDS = 2;
	private static final double MIN_F1 = 0.1;
	private static final double MAX_F1 = 1d;
	private static final double MIN_F2 = 0d;
	private static final double MAX_F2 = 5d;
	private static Random rand = new Random(System.currentTimeMillis());
	
	
	@Override
	public double getFitness(ISolution<double[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<double[]> solution) {
		return solution.getRepresentation()[0];
	}

	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] repr = new double[NUM_FIELDS];
		
		repr[0] = rand.nextDouble() * (MAX_F1 - MIN_F1) + MIN_F1;
		repr[1] = rand.nextDouble() * (MAX_F2 - MIN_F2) + MIN_F2;
		
		DoubleArraySolution sol = new DoubleArraySolution(repr);
		sol.setFitness(getFitness(sol));
		sol.setValue(getValue(sol));
		return sol;
	}
}
