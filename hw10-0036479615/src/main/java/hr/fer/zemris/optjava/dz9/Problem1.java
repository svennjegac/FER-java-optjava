package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithms.IMOOPProblem;
import hr.fer.zemris.optjava.dz9.algorithms.IProblem;
import hr.fer.zemris.optjava.dz9.algorithms.ISolution;

public class Problem1 implements IMOOPProblem<double[]> {

	private static final int NUM_OF_PROBLEMS = 4;
	private static final double MIN = -5d;
	private static final double MAX = 5d;
	
	private static Random rand = new Random(System.currentTimeMillis());
	
	private List<IProblem<double[]>> problems;
	
	public Problem1() {
		problems = new ArrayList<>();
		
		for (int i = 0; i < NUM_OF_PROBLEMS; i++) {
			problems.add(new QuadraticNumberMinimization(i));
		}
	}
	
	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] repr = problems.get(rand.nextInt(NUM_OF_PROBLEMS)).generateRandomSolution().getRepresentation();
		return new DoubleArraySolution(repr);
	}

	@Override
	public int getNumberOfOptProblems() {
		return NUM_OF_PROBLEMS;
	}

	@Override
	public IProblem<double[]> getProblem(int index) {
		return problems.get(index);
	}

	@Override
	public void transformSolutionToAllowedArea(ISolution<double[]> solution) {
		double[] repr = solution.getRepresentation();
		
		for (int i = 0; i < repr.length; i++) {
			if (repr[i] < MIN) {
				repr[i] = MIN;
			} else if (repr[i] > MAX) {
				repr[i] = MAX;
			}
		}
	}
}