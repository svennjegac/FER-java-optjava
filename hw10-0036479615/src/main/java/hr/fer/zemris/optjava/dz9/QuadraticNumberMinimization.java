package hr.fer.zemris.optjava.dz9;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithms.IProblem;
import hr.fer.zemris.optjava.dz9.algorithms.ISolution;

public class QuadraticNumberMinimization implements IProblem<double[]> {

	private static Random rand = new Random(System.currentTimeMillis());
	private static final double MIN = -5d;
	private static final double MAX = 5d;
	
	private int position;
	
	public QuadraticNumberMinimization(int position) {
		this.position = position;
	}

	@Override
	public double getFitness(ISolution<double[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<double[]> solution) {
		double[] repr = solution.getRepresentation();
		
		return Math.pow(repr[position], 2);
	}

	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] repr = new double[4];
		
		for (int i = 0; i < repr.length; i++) {
			repr[i] = rand.nextDouble() * (MAX - MIN) + MIN;
		}
		
		DoubleArraySolution sol = new DoubleArraySolution(repr);
		sol.setFitness(getFitness(sol));
		sol.setValue(getValue(sol));
		
		return sol;
	}
}