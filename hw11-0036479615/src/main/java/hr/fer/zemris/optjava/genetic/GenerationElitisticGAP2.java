package hr.fer.zemris.optjava.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.rng.EVOThread;


public class GenerationElitisticGAP2<T> extends AbstractGeneticAlgorithm<T> {

	private static final Integer NUMBER_OF_ELITE_SOLUTIONS = 0;
	
	private int jobsPerThread;
	private Queue<Task> jobQueue;
	private Queue<ISolution<T>> evaluatedQueue;
	
	public GenerationElitisticGAP2(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			int jobsPerThread) {
		
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
		this.jobsPerThread = jobsPerThread;
		evaluate();
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<ISolution<T>> bestSolutions = new ArrayList<>(population.getNBestSolutions(NUMBER_OF_ELITE_SOLUTIONS));
		
		for (ISolution<T> solution : bestSolutions) {
			evaluatedQueue.add(solution);
		}
		
		int counter = NUMBER_OF_ELITE_SOLUTIONS;
		
		while (counter < population.getSize()) {
			jobQueue.add(new Task(false));
			counter += jobsPerThread;
		}
		
		while (evaluatedQueue.size() < population.getSize());
		
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
			Thread worker = new EVOThread(new Worker());
			worker.start();
		}
	}
	
	@Override
	public void shutdown() {
		jobQueue.add(new Task(true));
	}

	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
	
	private class Worker implements Runnable {

		public Worker() {
		}
		
		@Override
		public void run() {
			while (true) {
				Task task = jobQueue.poll();
				if (task == null) {
					continue;
				}
				
				if (task.isPoisonPill()) {
					jobQueue.add(new Task(true));
					return;
				}
				
				for (int i = 0; i < jobsPerThread; i++) {
					ISolution<T> parent1 = getFirstParent();
					ISolution<T> parent2 = getSecondParent(parent1);
					ISolution<T> child = getChild(parent1, parent2);
					
					child.setFitness(problem.getFitness(child));
					evaluatedQueue.add(child);
				}
			}
		}
	}
	
	private class Task {
		
		private boolean poisonPill;
		
		public Task(boolean poisonPill) {
			this.poisonPill = poisonPill;
		}

		public boolean isPoisonPill() {
			return poisonPill;
		}
	}
}
