package hr.fer.zemris.optjava.dz8;

import java.util.Random;

import hr.fer.zemris.optjava.dz8.nn.IANN;

public class IANNFactory {

	private static final double MIN_WEIGHT = -1.0;
	private static final double MAX_WEIGHT = 1.0;
	
	private IANN referentAnn;
	private Random rand;
	
	public IANNFactory(IANN referentAnn) {
		this.referentAnn = referentAnn;
		rand = new Random(System.currentTimeMillis());
	}
	
	public IANN createAnn() {
		IANN ann = referentAnn.copy();
		double[] weights = ann.getWeights();
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = rand.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
		}
		
		ann.setWeights(weights);
		return ann;
	}
}
