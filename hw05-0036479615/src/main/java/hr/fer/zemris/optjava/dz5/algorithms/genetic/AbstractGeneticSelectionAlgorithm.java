package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.Random;

public abstract class AbstractGeneticSelectionAlgorithm<T> extends AbstractGeneticAlgorithm<T> {

	double maxSelectionPressure;

	double minCompFactor;
	double maxCompFactor;
	double currentCompFactor;
	
	ISelectionOperator<T> femaleSelectionOperator;
	IGenotipDuplicateChecker<T> genotipDuplChecker;
	Random rand;
	
	public AbstractGeneticSelectionAlgorithm(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ISelectionOperator<T> femaleSelectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IGenotipDuplicateChecker<T> genotipDuplChecker,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			double maxSelectionPressure,
			double minCompFactor,
			double maxCompFactor) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator, 
			problem,
			fitnessThreshold,
			maxGenerations);
		
		this.femaleSelectionOperator = femaleSelectionOperator;
		this.genotipDuplChecker = genotipDuplChecker;
		this.maxSelectionPressure = maxSelectionPressure;
		this.minCompFactor = minCompFactor;
		this.maxCompFactor = maxCompFactor;
		
		currentCompFactor = minCompFactor;
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		updateComparisonFactor();
		IPopulation<T> nextPopulation = new Population<>();
		IPopulation<T> solutionPool = new Population<>();
		
		while (!nextPopulationReachedSize(nextPopulation)
				&& !generatingReachedMaxSelPressThreh(nextPopulation, solutionPool)) {
			
			ISolution<T> parent1 = getFirstParent(selectionOperator);
			ISolution<T> parent2 = getSecondParent(femaleSelectionOperator, parent1);			
			ISolution<T> child = getChild(crossoverOperator, mutationOperator, parent1, parent2);
			
			if (childIsBetterThanParents(parent1, parent2, child) &&
					!populationHasGenotip(child, nextPopulation)) {
				nextPopulation.add(child);
			} else {
				solutionPool.add(child);
			}
		}

		if (actualSelectionPressure(nextPopulation, solutionPool) >= maxSelectionPressure) {
			stopAlgorithm();
		}
		
		fillNextPopulation(nextPopulation, solutionPool);
		
		return nextPopulation;
	}

	abstract boolean nextPopulationReachedSize(IPopulation<T> nextPopulation);
	
	abstract boolean generatingReachedMaxSelPressThreh(IPopulation<T> nextPopulation, IPopulation<T> solutionPool);
	
	void updateComparisonFactor() {
		if (iteration == 0) {
			return;
		}
		
		double nextFactor = currentCompFactor + (maxCompFactor - minCompFactor) / maxGenerations;
		
		if (nextFactor < maxCompFactor) {
			currentCompFactor = nextFactor;
		}
	}

	boolean childIsBetterThanParents(ISolution<T> parent1, ISolution<T> parent2, ISolution<T> child) {
		if (child.getFitness() > parentFitnessThresh(parent1, parent2)) {
			return true;
		}
		
		return false;
	}
	
	double parentFitnessThresh(ISolution<T> parent1, ISolution<T> parent2) {
		double min;
		double max;
		
		if (parent1.getFitness() < parent2.getFitness()) {
			min = parent1.getFitness();
			max = parent2.getFitness();
		} else {
			max = parent1.getFitness();
			min = parent2.getFitness();
		}
		
		return min + currentCompFactor * (max - min);
	}
	
	boolean populationHasGenotip(ISolution<T> solution, IPopulation<T> population) {
		for (ISolution<T> popSolution : population.getSoultions()) {
			if (genotipDuplChecker.sameGenotips(solution, popSolution)) {
				return true;
			}
		}
		
		return false;
	}
	
	double actualSelectionPressure(IPopulation<T> nextPopulation, IPopulation<T> solutionPool) {
		return (double) (nextPopulation.getSize() + solutionPool.getSize()) / maxSelectionPressure;
	}
	
	abstract void fillNextPopulation(IPopulation<T> nextPopulation, IPopulation<T> solutionPool);
}
