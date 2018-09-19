package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.optjava.genetic.IProblem;
import hr.fer.zemris.optjava.genetic.ISolution;
import hr.fer.zemris.optjava.genetic.impl.IntSolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class PictureProblem implements IProblem<int[]> {
	
	private static final int MIN = 0;
	private static final int MAX = 255;

	private int numberOfSquares;
	private String image;
	
	public PictureProblem(int numberOfSquares, String image) {
		this.numberOfSquares = numberOfSquares;
		this.image = image;
	}
	
	@Override
	public double getFitness(ISolution<int[]> solution) {
		Evaluator evaluator = EvaluatorProvider.getEvaluator(image);
		return evaluator.evaluate(solution);
	}

	@Override
	public double getValue(ISolution<int[]> solution) {
		return 0d;
	}

	@Override
	public ISolution<int[]> generateRandomSolution() {
		int[] repr = new int[1 + 5 * numberOfSquares];
		IRNG rng = RNG.getRNG();
		
		for (int i = 0; i < repr.length; i++) {
			repr[i] = rng.nextInt(MIN, MAX);
		}
		
		IntSolution solution = new IntSolution(repr, 0d, 0d);
		solution.setFitness(getFitness(solution));
		return solution;
	}
}
