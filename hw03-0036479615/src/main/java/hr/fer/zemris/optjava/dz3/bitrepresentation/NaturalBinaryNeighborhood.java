package hr.fer.zemris.optjava.dz3.bitrepresentation;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.INeighborhood;

public class NaturalBinaryNeighborhood implements INeighborhood<BitVectorSolution> {

	private double[] deltas;
	private Random random;
	private static final double SIGN_CHANGE_PROBABILITY = 0.3;
	
	public NaturalBinaryNeighborhood(int bitsPerNumber) {
		deltas = new double[bitsPerNumber];
		random = new Random(System.currentTimeMillis());
		initDeltas();
	}
	
	private void initDeltas() {
		for (int i = 1; i < deltas.length; i++) {
			deltas[i] = Math.pow(1.1, -(deltas.length - i));
		}
		
		deltas[0] = SIGN_CHANGE_PROBABILITY;
	}

	@Override
	public BitVectorSolution randomNeighbor(BitVectorSolution solution) {
		BitVectorSolution neighbor = solution.duplicate();
		neighbor.randomize(random, deltas);
		return neighbor;
	}
}
