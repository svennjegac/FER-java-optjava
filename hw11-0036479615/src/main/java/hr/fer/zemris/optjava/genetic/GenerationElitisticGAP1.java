package hr.fer.zemris.optjava.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.rng.EVOThread;

public class GenerationElitisticGAP1<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 0;
	
	private Queue<Task> jobQueue;
	private Queue<ISolution<T>> evaluatedQueue;
	
	public GenerationElitisticGAP1(
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
		
		jobQueue = new ConcurrentLinkedQueue<>();
		evaluatedQueue = new ConcurrentLinkedQueue<>();
		evaluate();
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<ISolution<T>> bestSolutions = new ArrayList<>(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		
		for (ISolution<T> solution : bestSolutions) {
			jobQueue.add(new Task(solution));
		}
		
		int counter = NUMBER_OF_ELITE_SOLUTIONS;
		
		while (counter < population.getSize()) {
			ISolution<T> parent1 = getFirstParent();
			ISolution<T> parent2 = getSecondParent(parent1);
			ISolution<T> child = getUnevaluatedChild(parent1, parent2);
			
			jobQueue.add(new Task(child));
			counter++;
		}
		
		while (evaluatedQueue.size() != population.getSize());
		
		Population<T> newPopulation = new Population<>();
		for (ISolution<T> solution : evaluatedQueue) {
			newPopulation.add(solution);
		}
		
		evaluatedQueue.clear();
		
		return newPopulation;
	}

	private void evaluate() {
		int threads = Runtime.getRuntime().availableProcessors();
		
		for (int i = 0; i < threads; i++) {
			EVOThread worker = new EVOThread(new Worker());
			worker.start();
		}
	}
	
	@Override
	public void shutdown() {
		jobQueue.add(new Task(null));
	}

	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
	
	private class Worker implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				Task task = jobQueue.poll();
				if (task == null) {
					continue;
				}
				
				if (task.isPoisonPill()) {
					jobQueue.add(new Task(null));
					return;
				}
				
				ISolution<T> solution = task.getSolution();
				if (solution == null) {
					continue;
				}
				
				solution.setFitness(problem.getFitness(solution));
				evaluatedQueue.add(solution);
			}
		}
	}
	
	private class Task {
		
		private ISolution<T> solution;
		
		public Task(ISolution<T> solution) {
			this.solution = solution;
		}

		public ISolution<T> getSolution() {
			return solution;
		}
		
		public boolean isPoisonPill() {
			return solution == null;
		}
	}
}
