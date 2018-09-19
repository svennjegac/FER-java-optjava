package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlgorithmFive extends AbstractAlgorithm {

	private static final Integer MAX_TRIES = 1000;
	private static final Integer MAX_FLIPS = 100_000;
	
	private static final Double RANDOM_OPERATION = 0.3;
	private Random rand;
	
	public AlgorithmFive() {
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public void runAlgorithm() {
		for (int i = 0; i < MAX_TRIES; i++) {
			BitVector currentSolution = new BitVector(satFormula.getNumberOfVariables());

			for (int j = 0; j < MAX_FLIPS; j++) {
				if (satFormula.isSatisfied(currentSolution)) {
					this.foundSolution = true;
					this.solution = currentSolution;
					return;
				}
				
				Clause unsatisfiedClause = getRandomUnsatisfiedClause(currentSolution);
				
				if (rand.nextDouble() < RANDOM_OPERATION) {
					currentSolution = randomlySatisfyClause(currentSolution, unsatisfiedClause);
				} else {
					currentSolution = optimalySatisfyClause(currentSolution, unsatisfiedClause);
				}
			}
		}
	}

	private Clause getRandomUnsatisfiedClause(BitVector currentSolution) {
		List<Clause> unsatisfiedClauses = new ArrayList<>();
		
		for (int i = 0, size = satFormula.getNumberOfClauses(); i < size; i++) {
			if (satFormula.getClause(i).isSatisfied(currentSolution)) {
				continue;
			}
			
			unsatisfiedClauses.add(satFormula.getClause(i));
		}
		
		return unsatisfiedClauses.get(rand.nextInt(unsatisfiedClauses.size()));
	}
	
	private BitVector randomlySatisfyClause(BitVector currentSolution, Clause unsatisfiedClause) {
		MutableBitVector mbVector = new MutableBitVector(currentSolution.bits);
		
		int literal = unsatisfiedClause.getLiteral(rand.nextInt(unsatisfiedClause.getSize()));
		
		mbVector.set(Math.abs(literal) - 1, mbVector.get(Math.abs(literal) - 1) ^ true);
		return mbVector;
	}
	
	private BitVector optimalySatisfyClause(BitVector currentSolution, Clause unsatisfiedClause) {
		int bestScore = -1;
		MutableBitVector bestSolution = null;
		
		for (int i = 0, size = unsatisfiedClause.getSize(); i < size; i++) {
			MutableBitVector mbVector = new MutableBitVector(Arrays.copyOf(currentSolution.bits, currentSolution.numberOfBits));
			int literal = unsatisfiedClause.getLiteral(i);
			
			mbVector.set(Math.abs(literal) - 1, mbVector.get(Math.abs(literal) - 1) ^ true);
			int score = calculateVectorScore(mbVector);
			
			if (score > bestScore) {
				bestScore = score;
				bestSolution = mbVector;
			}
		}
		
		return bestSolution;
	}
}
