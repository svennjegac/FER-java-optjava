package hr.fer.zemris.optjava.dz13.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationElitisticGA<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 1;
	private static final double CROSSOVER_PROB = 0.3;
	private static final double MUTATION_PROB = 0.6;
	private static final double CHILD_PROB = 0.7;
	private static final double PLAG_COST = 0.9;
	
	private IReproductionOperator<T> reproductionOperator;
	private Random rand;
	
	public GenerationElitisticGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IReproductionOperator<T> reproductionOperator,
			IPopulationGenerator<T> populationGenerator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			populationGenerator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
		
		this.reproductionOperator = reproductionOperator;
		rand = new Random();
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<ISolution<T>> solutions = new ArrayList<>(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		IPopulation<T> newPopulation = new Population<>(solutions);
		
		while (newPopulation.getSize() < population.getSize()) {
			ISolution<T> parent1 = getFirstParent();
			ISolution<T> parent2 = getSecondParent(parent1);
			
			ISolution<T> child = getChild(parent1, parent2);
			if (child == null) {
				continue;
			}
			
			if (child.getFitness() > parent1.getFitness() || child.getFitness() > parent2.getFitness() || rand.nextDouble() > CHILD_PROB) {
				newPopulation.add(child);
			} else if (parent1.getFitness() > parent2.getFitness()) {
				newPopulation.add(parent1);
			} else {
				newPopulation.add(parent2);
			}
		}
		
		return newPopulation;
	}
	
	@Override
	ISolution<T> getChild(ISolution<T> parent1, ISolution<T> parent2) {
		double prob = rand.nextDouble();
		ISolution<T> child = null;
		
		int index = 0;
		
		while (index < 5) {
			if (prob < CROSSOVER_PROB) {
				child = crossoverOperator.crossover(parent1, parent2);
			} else if (prob < CROSSOVER_PROB + MUTATION_PROB) {
				child = mutationOperator.mutate(parent1);
			} else {
				child = reproductionOperator.reproduce(parent1);
			}
			
			if (child != null || index > 5) {
				break;
			}
			
			index++;
		}
		
		if (child == null) {
			return null;
		}
		
		child.setFitness(problem.getFitness(child));
		child.setValue(problem.getValue(child));
		
		if (child.getValue() == parent1.getValue() || child.getValue() == parent2.getValue()) {
			child.setFitness(child.getFitness() * PLAG_COST);
		}
		
		return child;
	}
	
	@Override
	public void shutdown() {	
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}
