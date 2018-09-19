package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IGenotipDuplicateChecker;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class DuplicateChecker implements IGenotipDuplicateChecker<boolean[]> {

	@Override
	public boolean sameGenotips(ISolution<boolean[]> solution1, ISolution<boolean[]> solution2) {
		if (solution1.getFitness() != solution2.getFitness()) {
			return false;
		}
		
		boolean[] repr1 = solution1.getRepresentation();
		boolean[] repr2 = solution2.getRepresentation();
		
		for (int i = 0; i < repr1.length; i++) {
			if (repr1[i] != repr2[i]) {
				return false;
			}
		}
		
		return true;
	}
}
