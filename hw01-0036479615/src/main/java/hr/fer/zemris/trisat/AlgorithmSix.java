package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class AlgorithmSix extends AbstractAlgorithm {

	private static final Integer LOCAL_SEARCH_ITERATIONS = 100_000;
	private static final Integer MAX_TRIES = 1000;
	private static final Double THRESHOLD = 0.1;
	
	@Override
	public void runAlgorithm() {
		BitVector localOptimum = findLocalOptimum(LOCAL_SEARCH_ITERATIONS);
	
		for (int i = 0; i < MAX_TRIES; i++) {
			if (satFormula.isSatisfied(localOptimum)) {
				foundSolution = true;
				solution = localOptimum;
				return;
			}
			
			BitVector modifiedOptimum = modifyOptimum(localOptimum);
			localOptimum = findLocalOptimum(LOCAL_SEARCH_ITERATIONS, modifiedOptimum);
		}
	}

	private BitVector modifyOptimum(BitVector localOptimum) {
		Random rand = new Random(System.currentTimeMillis());
		MutableBitVector mbVector = new MutableBitVector(Arrays.copyOf(localOptimum.bits, localOptimum.numberOfBits));
		
		for (int i = 0; i < mbVector.getSize(); i++) {
			if (rand.nextDouble() < THRESHOLD) {
				mbVector.set(i, mbVector.get(i) ^ true);
			}
		}
		
		return mbVector;
	}
}
