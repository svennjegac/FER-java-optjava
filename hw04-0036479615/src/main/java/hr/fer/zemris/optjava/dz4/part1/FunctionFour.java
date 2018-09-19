package hr.fer.zemris.optjava.dz4.part1;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.IProblem;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class FunctionFour implements IProblem<double[]> {
	
	private static final double MAX_INTERVAL = 5d;
	private static final double MIN_INTERVAL = -5d;
	private static final int SOLUTION_LENGTH = 6;
	
	private List<double[]> problemRows;
	private static Random random = new Random(System.currentTimeMillis());
	
	public FunctionFour(List<double[]> problemRows) {
		this.problemRows = problemRows;
	}
	
	@Override
	public double getFitness(ISolution<double[]> solution) {
		return - valueAt(solution.getRepresentation());
	}
	
	@Override
	public double getValue(ISolution<double[]> solution) {
		return valueAt(solution.getRepresentation());
	}
	
	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] values = new double[SOLUTION_LENGTH];
		
		for (int i = 0; i < values.length; i++) {
			values[i] = random.nextDouble() * (MAX_INTERVAL - MIN_INTERVAL) + MIN_INTERVAL;
		}
		
		DoubleArraySolution solution = new DoubleArraySolution(values);
		solution.setFitness(getFitness(solution));
		solution.setValue(getValue(solution));
		return solution;
	}
	
	public double valueAt(double[] point) {
		double error = 0;
		
		for (double[] row : problemRows) {
			double myEstimation = calculateValueInPointForRow(point, row);
			error += Math.pow(row[row.length - 1] - myEstimation, 2);
		}
		
		return error;
	}
	
	private double calculateValueInPointForRow(double[] point, double[] row) {
		double result = 0;
		
		result += point[0] * row[0];
		result += point[1] * Math.pow(row[0], 3) * row[1];
		result += point[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
		result += point[5] * row[3] * Math.pow(row[4], 2);
		
		return result;
	}
}
