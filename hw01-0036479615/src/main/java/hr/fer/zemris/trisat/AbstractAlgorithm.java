package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractAlgorithm implements Algorithm {

	SATFormula satFormula;
	boolean foundSolution;
	BitVector solution;

	@Override
	public boolean foundSolution() {
		return foundSolution;
	}

	@Override
	public BitVector getSolution() {
		return solution;
	}
	
	@Override
	public void setSATFormula(SATFormula satFormula) {
		this.satFormula = satFormula;
		foundSolution = false;
		solution = null;
	}
	
	public BitVector findLocalOptimum(int iterations, BitVector currentSolution) {
		for (int i = 0; i < iterations; i++) {
			List<BitVector> bestNeighbours = findBestNeighbours(currentSolution);
			
			Random rand = new Random();
			BitVector bestNeighbour = bestNeighbours.get(rand.nextInt(bestNeighbours.size()));
			
			if (satFormula.isSatisfied(bestNeighbour)) {
				return bestNeighbour;
			}
			
			if (currentSolution.equals(bestNeighbour)) {
				return currentSolution;
			} else {
				currentSolution = bestNeighbour;
			}
		}
		
		return currentSolution;
	}
	
	public BitVector findLocalOptimum(int iterations) {
		BitVector currentSolution = new BitVector(satFormula.getNumberOfVariables());
		return findLocalOptimum(iterations, currentSolution);
	}

	private List<BitVector> findBestNeighbours(BitVector currentSolution) {
		BitVectorNGenerator nGenerator = new BitVectorNGenerator(currentSolution);
		List<BitVector> bestNeighbours = new ArrayList<>();
		
		int bestNeighbourScore = -1;
		for (MutableBitVector neighbour : nGenerator) {
			if (bestNeighbours.isEmpty()) {
				bestNeighbours.add(neighbour);
				bestNeighbourScore = calculateVectorScore(neighbour);
				continue;
			}
			
			int neighbourScore = calculateVectorScore(neighbour);
			
			if (neighbourScore > bestNeighbourScore) {
				bestNeighbourScore = neighbourScore;
				bestNeighbours.clear();
				bestNeighbours.add(neighbour);
				continue;
			} else if (neighbourScore == bestNeighbourScore) {
				bestNeighbours.add(neighbour);
			} else {
				continue;
			}
		}
		
		return bestNeighbours;
	}
	
	protected int calculateVectorScore(MutableBitVector neighbour) {
		int score = 0;
		
		for (int i = 0, size = satFormula.getNumberOfClauses(); i < size; i++) {
			if (satFormula.getClause(i).isSatisfied(neighbour)) {
				score++;
			}
		}
		
		return score;
	}
}
