package hr.fer.zemris.optjava.dz8.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DifferentialEvolution<T> implements IOptAlgorithm<T> {

	private IPopulation<T> population;
	private IProblem<T> problem;
	private IBaseVectorChooser<T> baseVectorChooser;
	private ILinearCombinator<T> linearCombinator;
	private ICrossoverOperator<T> crossoverOperator;
	
	private int populationSize;
	private int maxGenerations;
	private double fitnessThreshold;
	
	private ExecutorService executorService;
	
	public DifferentialEvolution(
			IProblem<T> problem,
			IBaseVectorChooser<T> baseVectorChooser,
			ILinearCombinator<T> linearCombinator,
			ICrossoverOperator<T> crossoverOperator,
			int populationSize,
			int maxGenerations,
			double fitnessThreshold) {
		
		this.problem = problem;
		this.baseVectorChooser = baseVectorChooser;
		this.linearCombinator = linearCombinator;
		this.crossoverOperator = crossoverOperator;
		this.populationSize = populationSize;
		this.maxGenerations = maxGenerations;
		this.fitnessThreshold = fitnessThreshold;
	}

	@Override
	public ISolution<T> run() {
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		population = createInitialPopulation(populationSize);
		int iteration = 0;
		
		while (iteration < maxGenerations) {
			iteration++;
			
			population = makeDifferentialPopulation();
			
			if (foundGoodSolution()) {
				break;
			}
			
			System.out.println(iteration + " -> " + population.getBestSolution().getFitness());
		}
		
		executorService.shutdown();
		return population.getBestSolution();
	}
	
	IPopulation<T> createInitialPopulation(int populationSize) {
		IPopulation<T> population = new Population<>();
		
		while (population.getSize() < populationSize) {
			population.add(problem.generateRandomSolution());
		}
		
		return population;
	}
	
	private IPopulation<T> makeDifferentialPopulation() {
		IPopulation<T> newPopulation = new Population<>();
		
		List<Future<ISolution<T>>> tasks = new ArrayList<>();
		
		for (ISolution<T> solution : population) {
			tasks.add(executorService.submit(new DifferentiatorJob(solution)));
		}
		
		for (Future<ISolution<T>> future : tasks) {
			try {
				newPopulation.add(future.get());
			} catch (InterruptedException | ExecutionException ignorable) {
			}
		}
		
		return newPopulation;
	}
	
	boolean foundGoodSolution() {
		return evaluatePopulation() >= fitnessThreshold;
	}

	double evaluatePopulation() {
		return population.getBestSolution().getFitness();
	}
	
	private class DifferentiatorJob implements Callable<ISolution<T>> {
		private ISolution<T> solution;
		
		public DifferentiatorJob(ISolution<T> solution) {
			this.solution = solution;
		}

		@Override
		public ISolution<T> call() throws Exception {
			return differentiateSolution(solution);

		}
		
		private ISolution<T> differentiateSolution(ISolution<T> targetVector) {
			ISolution<T> baseVector = baseVectorChooser.getBaseVector(population, targetVector);
			ISolution<T> mutantVector = linearCombinator.combine(population, targetVector, baseVector);
			ISolution<T> trialVector = crossoverOperator.crossover(targetVector, mutantVector);
			
			trialVector.setFitness(problem.getFitness(trialVector));
			trialVector.setValue(problem.getValue(trialVector));
			
			if (trialVector.getFitness() >= targetVector.getFitness()) {
				return trialVector;
			}
			
			return targetVector;
		}
	}
}
