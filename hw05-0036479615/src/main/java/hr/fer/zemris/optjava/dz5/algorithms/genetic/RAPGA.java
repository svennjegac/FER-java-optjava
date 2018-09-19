package hr.fer.zemris.optjava.dz5.algorithms.genetic;

public class RAPGA<T> extends AbstractGeneticSelectionAlgorithm<T> {

	private int minPopulationSize;
	private int maxPopulationSize;
	
	public RAPGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ISelectionOperator<T> femaleSelectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IGenotipDuplicateChecker<T> genotipDuplChecker,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			int minPopulationSize,
			int maxPopulationSize,
			double maxSelectionPressure,
			double minCompFactor,
			double maxCompFactor) {
		
		super(
			populationSize,
			selectionOperator,
			femaleSelectionOperator,
			crossoverOperator,
			mutationOperator, 
			genotipDuplChecker,
			problem,
			fitnessThreshold,
			maxGenerations,
			maxSelectionPressure,
			minCompFactor,
			maxCompFactor);
		
		this.minPopulationSize = minPopulationSize;
		this.maxPopulationSize = maxPopulationSize;
	}
	
	@Override
	boolean nextPopulationReachedSize(IPopulation<T> nextPopulation) {
		return !(nextPopulation.getSize() < maxPopulationSize);
	}
	
	@Override
	boolean generatingReachedMaxSelPressThreh(IPopulation<T> nextPopulation, IPopulation<T> solutionPool) {
		return !(nextPopulation.getSize() + solutionPool.getSize() < maxPopulationSize * maxSelectionPressure);
	}

	@Override
	void fillNextPopulation(IPopulation<T> nextPopulation, IPopulation<T> solutionPool) {
		while (nextPopulation.getSize() < (double) (minPopulationSize + maxPopulationSize) / 2d) {
			if (solutionPool.getSize() != 0) {
				nextPopulation.add(solutionPool.getRandomSolution());
			} else {
				nextPopulation.add(population.getRandomSolution());
			}
		}
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}