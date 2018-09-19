package hr.fer.zemris.optjava.dz8;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz8.algorithms.ILinearCombinator;
import hr.fer.zemris.optjava.dz8.algorithms.IPopulation;
import hr.fer.zemris.optjava.dz8.algorithms.ISolution;
import hr.fer.zemris.optjava.dz8.nn.IANN;

public class SimpleLinearCombinator implements ILinearCombinator<IANN> {

	private final double F;
		
	public SimpleLinearCombinator(double f) {
		F = f;
	}

	@Override
	public ISolution<IANN> combine(IPopulation<IANN> population, ISolution<IANN> targetVector,
			ISolution<IANN> baseVector) {
		
		List<ISolution<IANN>> vectors = new ArrayList<>();
		vectors.add(targetVector);
		vectors.add(baseVector);
		
		ISolution<IANN> r01 = getVector(population, vectors);
		ISolution<IANN> r02 = getVector(population, vectors);
		
		return mutate(baseVector, r01, r02);
	}

	private ISolution<IANN> getVector(IPopulation<IANN> population, List<ISolution<IANN>> vectors) {
		while (true) {
			ISolution<IANN> r = population.getRandomSolution();
			if (!vectors.contains(r)) {
				vectors.add(r);
				return r;
			}
		}
	}
	
	private ISolution<IANN> mutate(ISolution<IANN> baseVector, ISolution<IANN> r01, ISolution<IANN> r02) {
		double[] wBase = baseVector.getRepresentation().getWeights();
		double[] wR01 = r01.getRepresentation().getWeights();
		double[] wR02 = r02.getRepresentation().getWeights();
		
		double[] wMutant = new double[wBase.length];
		
		for (int i = 0; i < wBase.length; i++) {
			wMutant[i] = wBase[i] + F * (wR01[i] - wR02[i]);
		}
		
		ISolution<IANN> mutantSolution = baseVector.copy();
		mutantSolution.getRepresentation().setWeights(wMutant);
		return mutantSolution;
	}
}
