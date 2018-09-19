package hr.fer.zemris.optjava.dz5.algorithms.genetic;

public class OffspringSelection<T> extends AbstractGeneticSelectionAlgorithm<T> {

	private double successRatio;
	
	public OffspringSelection(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ISelectionOperator<T> femaleSelectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IGenotipDuplicateChecker<T> genotipDuplChecker,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			double successRatio,
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
	
		this.successRatio = successRatio;
	}
	
	@Override
	boolean nextPopulationReachedSize(IPopulation<T> nextPopulation) {
		return !(nextPopulation.getSize() < population.getSize() * successRatio);
	}
	
	@Override
	boolean generatingReachedMaxSelPressThreh(IPopulation<T> nextPopulation, IPopulation<T> solutionPool) {
		return !((nextPopulation.getSize() + solutionPool.getSize()) < (population.getSize() * maxSelectionPressure));
	}

	@Override
	void fillNextPopulation(IPopulation<T> nextPopulation, IPopulation<T> solutionPool) {
		while (nextPopulation.getSize() < population.getSize()) {
			if (solutionPool.getSize() != 0) {
				nextPopulation.add(solutionPool.getRandomSolution());
			} else {
				nextPopulation.add(population.getRandomSolution());
			}
		}
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return false;
	}
}
