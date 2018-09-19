package hr.fer.zemris.optjava.genetic;

import java.util.ArrayList;
import java.util.List;

public class GenerationElitisticGA<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 0;
	
	public GenerationElitisticGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<ISolution<T>> solutions = new ArrayList<>(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		IPopulation<T> newPopulation = new Population<>(solutions);
		
		while (newPopulation.getSize() < population.getSize()) {
			ISolution<T> parent1 = getFirstParent();
			ISolution<T> parent2 = getSecondParent(parent1);
			ISolution<T> child = getChild(parent1, parent2);
			
			newPopulation.add(child);
		}
		
		return newPopulation;
	}
	
	@Override
	public void shutdown() {	
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}
