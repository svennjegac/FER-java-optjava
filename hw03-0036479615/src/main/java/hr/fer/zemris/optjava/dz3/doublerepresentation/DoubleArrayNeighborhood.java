package hr.fer.zemris.optjava.dz3.doublerepresentation;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.INeighborhood;

public abstract class DoubleArrayNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	Random random;
	
	public DoubleArrayNeighborhood(double[] deltas) {
		this.deltas = deltas;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
		DoubleArraySolution neighbor = solution.duplicate();
		
		double[] vector = new double[deltas.length];
		for (int i = 0; i < vector.length; i++) {
			vector[i] = generateVectorComponenet();
		}
		
		neighbor.randomize(random, deltas, vector);
		return neighbor;
	}
	
	protected abstract double generateVectorComponenet();
}
