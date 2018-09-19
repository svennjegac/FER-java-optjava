package hr.fer.zemris.optjava.dz9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithms.IMOOPProblem;
import hr.fer.zemris.optjava.dz9.algorithms.IProblem;
import hr.fer.zemris.optjava.dz9.algorithms.ISolution;

public class Problem2 implements IMOOPProblem<double[]> {

	private static final int NUM_OF_PROBLEMS = 2;
	private static final double MIN_F1 = 0.1;
	private static final double MAX_F1 = 1d;
	private static final double MIN_F2 = 0d;
	private static final double MAX_F2 = 5d;
	
	private static Random rand = new Random(System.currentTimeMillis());
	
	private List<IProblem<double[]>> problems;
	
	public Problem2() {
		problems = new ArrayList<>();
		problems.add(new LinearMinimization());
		problems.add(new FractionMinimization());
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
		
		if (repr[0] < MIN_F1) {
			repr[0] = MIN_F1;
		} else if (repr[0] > MAX_F1) {
			repr[0] = MAX_F1;
		}
		
		if (repr[1] < MIN_F2) {
			repr[1] = MIN_F2;
		} else if (repr[1] > MAX_F2) {
			repr[1] = MAX_F2;
		}
	}
}
