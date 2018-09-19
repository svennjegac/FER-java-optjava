package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AlgorithmTwo extends AbstractAlgorithm {
	
	private static final Integer DEFAULT_ITERATIONS = 100_000;
	
	@Override
	public void runAlgorithm() {
		runAlgorithm(DEFAULT_ITERATIONS);
	}
	
	public void runAlgorithm(int iterations) {
		BitVector localOptimum = findLocalOptimum(iterations);
		
		if (satFormula.isSatisfied(localOptimum)) {
			foundSolution = true;
			solution = localOptimum;
		}
	}
}
