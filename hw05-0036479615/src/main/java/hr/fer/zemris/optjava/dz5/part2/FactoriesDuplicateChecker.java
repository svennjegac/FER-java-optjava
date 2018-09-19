package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IGenotipDuplicateChecker;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class FactoriesDuplicateChecker implements IGenotipDuplicateChecker<int[]> {

	@Override
	public boolean sameGenotips(ISolution<int[]> solution1, ISolution<int[]> solution2) {
		if (solution1.getFitness() != solution2.getFitness()) {
			return false;
		}
		
		int[] rep1 = solution1.getRepresentation();
		int[] rep2 = solution2.getRepresentation();
		
		for (int i = 0; i < rep1.length; i++) {
			if (rep1[i] != rep2[i]) {
				return false;
			}
		}
		
		return true;
	}
}
