package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.List;

public class RandomSelectionOperator<T> implements ISelectionOperator<T> {

	@Override
	public ISolution<T> select(IPopulation<T> population) {
		return population.getRandomSolution();
	}

	@Override
	public ISolution<T> select(IPopulation<T> population, List<ISolution<T>> forbiddenSolutions) {
		while (true) {
			ISolution<T> solution = population.getRandomSolution();
			
			if (!forbiddenSolutions.contains(solution)) {
				return solution;
			}
		}
	}
}
