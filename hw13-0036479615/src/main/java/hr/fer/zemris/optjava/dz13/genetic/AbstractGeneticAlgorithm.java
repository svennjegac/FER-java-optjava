package hr.fer.zemris.optjava.dz13.genetic;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGeneticAlgorithm<T> implements IOptAlgorithm<T> {

	IPopulation<T> population;
	ISelectionOperator<T> selectionOperator;
	ICrossoverOperator<T> crossoverOperator;
	IMutationOperator<T> mutationOperator;
	IPopulationGenerator<T> populationGenerator;
	
	IProblem<T> problem;
	
	double fitnessThreshold;
	int maxGenerations;
	
	public AbstractGeneticAlgorithm(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IPopulationGenerator<T> populationGenerator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		this.selectionOperator = selectionOperator;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.populationGenerator = populationGenerator;
		this.problem = problem;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		this.population = createInitialPopulation(populationSize);
	}

	private IPopulation<T> createInitialPopulation(int populationSize) {
		return populationGenerator.generatePopulation();
	}

	@Override
	public ISolution<T> run() {
		int iteration = 0;
		
		while (iteration < maxGenerations) {
			if (printSolution(population.getBestSolution())) {
				System.out.println(iteration + " -> " + population.getBestSolution().getFitness());
				System.out.println(population.getBestSolution().getRepresentation());
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
		ISolution<T> child = getUnevaluatedChild(parent1, parent2);
		setChildProperties(child);
		return child;
	}
	
	ISolution<T> getUnevaluatedChild(ISolution<T> parent1, ISolution<T> parent2) {
		ISolution<T> child = crossoverOperator.crossover(parent1, parent2);
		child = mutationOperator.mutate(child);
		return child;
	}
	
	void setChildProperties(ISolution<T> child) {
		child.setFitness(problem.getFitness(child));
	}
	
	abstract IPopulation<T> makeNewPopulation();
	abstract boolean printSolution(ISolution<T> solution);
}
