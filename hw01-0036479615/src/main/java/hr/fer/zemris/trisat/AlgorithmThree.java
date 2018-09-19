package hr.fer.zemris.trisat;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlgorithmThree extends AbstractAlgorithm {

	private static final Integer MAX_TRIES = 50;
	private static final Integer MAX_FLIPS = 10_000;
	private static final Integer NUMBER_OF_BEST = 2;
	
	@Override
	public void runAlgorithm() {
		for (int i = 0; i < MAX_TRIES; i++) {
			BitVector currentSolution = new BitVector(satFormula.getNumberOfVariables());
			SATFormulaStats sfStats = new SATFormulaStats();
			
			for (int j = 0; j < MAX_FLIPS; j++) {
				sfStats.setAssignment(currentSolution, true);
				
				if (sfStats.isSatisfied()) {
					foundSolution = true;
					solution = currentSolution;
					return;
				}
				
				currentSolution = getNewSolution(sfStats, currentSolution);	
			}
		}
	}
	
	private BitVector getNewSolution(SATFormulaStats sfStats, BitVector currentSolution) {
		BitVectorNGenerator nGen = new BitVectorNGenerator(currentSolution);
		Map<Double, MutableBitVector> solutions = new HashMap<>();
		
		for (MutableBitVector neighbour : nGen) {
			sfStats.setAssignment(neighbour, false);
			double score = calculateVectorScore(neighbour) + sfStats.getPercentageBonus();
			solutions.put(new Double(score), neighbour);
		}
		
		List<MutableBitVector> sortedSolutions =
				solutions.entrySet()
								.stream()
								.sorted((entry1, entry2) -> Double.compare(entry2.getKey(), entry1.getKey()))
								.map(entry -> entry.getValue())
								.collect(Collectors.toList());
		
		Random rand = new Random(System.currentTimeMillis());
		return sortedSolutions.get(
				sortedSolutions.size() < NUMBER_OF_BEST ?
						rand.nextInt(sortedSolutions.size()) : rand.nextInt(NUMBER_OF_BEST));
	}

	public class SATFormulaStats {
		
		private static final double PERCENTAGE_CONSTANT_UP = 0.1;
		private static final double PERCENTAGE_CONSTANT_DOWN = 0.25;
		private static final double PERCENTAGE_UNIT_AMOUNT = 30d;
		
		
		
		private BitVector assignment;
		private double[] clauseProbability;
		
		public SATFormulaStats() {
			clauseProbability = new double[satFormula.getNumberOfClauses()];
		}
		
		public void setAssignment(BitVector assignment, boolean updatePercentages) {
			this.assignment = assignment;
			if (!updatePercentages) {
				return;
			}
			
			
			for (int i = 0; i < clauseProbability.length; i++) {
				if (satFormula.getClause(i).isSatisfied(assignment)) {
					clauseProbability[i] = (1 - clauseProbability[i]) * PERCENTAGE_CONSTANT_UP;
				} else {
					clauseProbability[i] += (0 - clauseProbability[i]) * PERCENTAGE_CONSTANT_DOWN;
				}
			}
		}
		
		public int getNumberOfSatisfied() {
			return AlgorithmThree.this.calculateVectorScore(new MutableBitVector(assignment.bits));
		}
		
		public boolean isSatisfied() {
			return satFormula.isSatisfied(assignment);
		}
		
		public double getPercentageBonus() {
			double bonus = 0;
			
			for (int i = 0; i < clauseProbability.length; i++) {
				if (satFormula.getClause(i).isSatisfied(assignment)) {
					bonus += PERCENTAGE_UNIT_AMOUNT * (1 - getPercentage(i));
				} else {
					bonus -= PERCENTAGE_UNIT_AMOUNT * (1 - getPercentage(i));
				}
			}
			
			return bonus;
		}
		
		public double getPercentage(int index) {
			return clauseProbability[index];
		}
	}
}
