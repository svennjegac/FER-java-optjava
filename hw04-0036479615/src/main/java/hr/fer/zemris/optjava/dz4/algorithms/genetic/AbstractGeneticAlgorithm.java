package hr.fer.zemris.optjava.dz4.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGeneticAlgorithm<T> implements IOptAlgorithm<T> {

	IPopulation<T> population;
	ISelectionOperator<T> selectionOperator;
	ICrossoverOperator<T> crossoverOperator;
	IMutationOperator<T> mutationOperator;
	
	IProblem<T> problem;
	
	double fitnessThreshold;
	int maxGenerations;
	
	public AbstractGeneticAlgorithm(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		this.selectionOperator = selectionOperator;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.problem = problem;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		this.population = createInitialPopulation(populationSize);
	}

	private IPopulation<T> createInitialPopulation(int populationSize) {
		IPopulation<T> population = new Population<>();
		
		while (population.getSize() < populationSize) {
			population.add(problem.generateRandomSolution());
		}
		
		return population;
	}

	@Override
	public ISolution<T> run() {
		int iteration = 0;
		
		while (iteration < maxGenerations) {
			if (printSolution(population.getBestSolution())) {
				System.out.println(iteration + " -> " + population.getBestSolution());
			}
			
			if (evaluatePopulation() >= fitnessThreshold) {
				return population.getBestSolution();
			}
			
			population = makeNewPopulation();
			iteration++;
		}
		
		return population.getBestSolution();
	}

	private double evaluatePopulation() {
		return population.getBestSolution().getFitness();
	}
	
	ISolution<T> getFirstParent() {
		return selectionOperator.select(population);
	}
	
	ISolution<T> getSecondParent(ISolution<T> parent1) {
		List<ISolution<T>> forbiddenSolutions = new ArrayList<>();
		forbiddenSolutions.add(parent1);
		return selectionOperator.select(population, forbiddenSolutions);
	}
	
	ISolution<T> getChild(ISolution<T> parent1, ISolution<T> parent2) {
		ISolution<T> child = crossoverOperator.crossover(parent1, parent2);
		child = mutationOperator.mutate(child);
		setChildProperties(child);
		return child;
	}
	
	void setChildProperties(ISolution<T> child) {
		child.setFitness(problem.getFitness(child));
		child.setValue(problem.getValue(child));
	}
	
	abstract IPopulation<T> makeNewPopulation();
	abstract boolean printSolution(ISolution<T> solution);
}
