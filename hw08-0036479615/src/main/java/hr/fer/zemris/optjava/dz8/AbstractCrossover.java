package hr.fer.zemris.optjava.dz8;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.algorithms.ICrossoverOperator;
import hr.fer.zemris.optjava.dz8.algorithms.ISolution;
import hr.fer.zemris.optjava.dz8.nn.IANN;

public abstract class AbstractCrossover implements ICrossoverOperator<IANN> {

	final double CR;
	Random rand;
	
	public AbstractCrossover(double cR) {
		CR = cR;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<IANN> crossover(ISolution<IANN> targetVector, ISolution<IANN> mutantVector) {
		double[] wTarget = targetVector.getRepresentation().getWeights();
		double[] wMutant = mutantVector.getRepresentation().getWeights();
		double[] wTrail = new double[wTarget.length];
		
		crossover(wTarget, wMutant, wTrail);
		
		ISolution<IANN> trailVector = targetVector.copy();
		trailVector.getRepresentation().setWeights(wTrail);
		return trailVector;
	}

	public abstract void crossover(double[] wTarget, double[] wMutant, double[] wTrail);
}
