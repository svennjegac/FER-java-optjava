package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGeneticAlgorithm<T> implements IOptAlgorithm<T> {

	IPopulation<T> population;
	ISelectionOperator<T> selectionOperator;
	ICrossoverOperator<T> crossoverOperator;
	IMutationOperator<T> mutationOperator;
	
	IProblem<T> problem;
	
	int populationSize;
	double fitnessThreshold;
	int maxGenerations;
	int iteration;
	boolean stopAlgorithm;
	
	public AbstractGeneticAlgorithm(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations) {
		
		this.populationSize = populationSize;
		this.selectionOperator = selectionOperator;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.problem = problem;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		
		stopAlgorithm = false;
		this.population = createInitialPopulation(populationSize);
	}

	IPopulation<T> createInitialPopulation(int populationSize) {
		IPopulation<T> population = new Population<>();
		
		while (population.getSize() < populationSize) {
			population.add(problem.generateRandomSolution());
		}
		
		return population;
	}
	
	void resetPopulation() {
		population = createInitialPopulation(populationSize);
	}
	
	void setPopulation(IPopulation<T> population) {
		this.population = population;
	}

	@Override
	public ISolution<T> run() {
		iteration = 0;
		stopAlgorithm = false;
		
		while (iteration < maxGenerations && !stopAlgorithm) {
			if (foundGoodSolution()) {
				return population.getBestSolution();
			}
			
			population = makeNewPopulation();
			iteration++;
			
			if (printSolution(population.getBestSolution())) {
				printToStdOutput();
			}
		}
		
		return population.getBestSolution();
	}

	public ISolution<T> getBestSolution() {
		return population.getBestSolution();
	}
	
	public IPopulation<T> getPopulation() {
		return population;
	}
	
	void stopAlgorithm() {
		stopAlgorithm = true;
	}
	
	boolean foundGoodSolution() {
		return evaluatePopulation() >= fitnessThreshold;
	}

	double evaluatePopulation() {
		return population.getBestSolution().getFitness();
	}
	
	void printToStdOutput() {
		System.out.println(iteration + " -> " + population.getBestSolution() + " XXX popsize: " + population.getSize());
	}
	
	ISolution<T> getFirstParent(ISelectionOperator<T> selectionOperator) {
		return selectionOperator.select(population);
	}
	
	ISolution<T> getSecondParent(ISelectionOperator<T> selectionOperator, ISolution<T> parent1) {
		List<ISolution<T>> forbiddenSolutions = new ArrayList<>();
		forbiddenSolutions.add(parent1);
		return selectionOperator.select(population, forbiddenSolutions);
	}
	
	ISolution<T> getChild(
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			ISolution<T> parent1,
			ISolution<T> parent2) {
		
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
