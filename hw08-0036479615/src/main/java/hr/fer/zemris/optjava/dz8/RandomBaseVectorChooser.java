package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.algorithms.IBaseVectorChooser;
import hr.fer.zemris.optjava.dz8.algorithms.IPopulation;
import hr.fer.zemris.optjava.dz8.algorithms.ISolution;
import hr.fer.zemris.optjava.dz8.nn.IANN;

public class RandomBaseVectorChooser implements IBaseVectorChooser<IANN> {

	@Override
	public ISolution<IANN> getBaseVector(IPopulation<IANN> population, ISolution<IANN> targetVector) {
		ISolution<IANN> baseVector;
		
		while (true) {
			baseVector = population.getRandomSolution();
			if (baseVector != targetVector) {
				return baseVector;
			}
		}
	}
}
