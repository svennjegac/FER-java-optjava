package hr.fer.zemris.optjava.dz7.algorithms.immune;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.algorithms.IPopulation;
import hr.fer.zemris.optjava.dz7.algorithms.IProblem;
import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.algorithms.Population;

public class ClonAlg<T> implements IOptAlgorithm<T> {

	private IProblem<T> problem;
	private IPopulation<T> population;
	private Random rand;
	
	private double fitnessThreshold;
	private int maxGenerations;
	
	private int n;
	private int d;
	private double beta;
	private double ro;
	
	
	
	public ClonAlg(
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			int n,
			int d,
			double beta,
			double ro) {
		
		this.problem = problem;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		this.n = n;
		this.d = d;
		this.beta = beta;
		this.ro = ro;
		rand = new Random(System.currentTimeMillis());
	}

	IPopulation<T> createInitialPopulation(int populationSize) {
		IPopulation<T> population = new Population<>();
		
		while (population.getSize() < populationSize) {
			population.add(problem.generateRandomSolution());
		}
		
		return population;
	}
	
	@Override
	public ISolution<T> run() {
		population = createInitialPopulation(n);
		int iteration = 0;
		
		while (iteration < maxGenerations) {
			IPopulation<T> clones = createClones();
			hypermutate(clones);
			updatePopulationFitnesses(clones);
			
			IPopulation<T> choosen = choose(clones);
			IPopulation<T> birth = birthSolutions(clones);
			population = swap(choosen, birth);
			
			iteration++;
			if (foundGoodSolution()) {
				break;
			}
			
			System.out.println(iteration + " -> " + population.getBestSolution().getFitness());
		}
		
		return population.getBestSolution();
	}
	
	private IPopulation<T> createClones() {
		List<ISolution<T>> solutions = population.getNBestSolutions(population.getSize());
		IPopulation<T> clones = new Population<>();
		
		for (int i = 0, size = solutions.size(); i < size; i++) {
			int numClones = (int) (beta * size / (i + 1));
			ISolution<T> solution = solutions.get(i);
			
			for (int j = 0; j < numClones; j++) {
				clones.add(solution.copy());
			}
		}
		
		return clones;
	}
	
	private void hypermutate(IPopulation<T> clones) {
		List<ISolution<T>> solutions = clones.getNBestSolutions(clones.getSize());
		ISolution<T> worstSolution = solutions.get(solutions.size() - 1);
		
		List<NormalizedPair> normalizedPairs = new ArrayList<>();
		for (ISolution<T> solution : solutions) {
			normalizedPairs.add(new NormalizedPair(solution, solution.getFitness() - worstSolution.getFitness()));
		}
		
		for (NormalizedPair normalizedPair : normalizedPairs) {
			mutate(normalizedPair);
		}
	}

	private void mutate(ClonAlg<T>.NormalizedPair normalizedPair) {
		double probability = Math.pow(Math.E, -ro * normalizedPair.normalizedFitness);
		normalizedPair.solution.mutate(probability, rand);
	}
	
	private void updatePopulationFitnesses(IPopulation<T> population) {
		for (ISolution<T> solution : population) {
			solution.setFitness(problem.getFitness(solution));
			solution.setValue(problem.getValue(solution));
		}
	}
	
	private IPopulation<T> choose(IPopulation<T> clones) {
		return new Population<>(clones.getNBestSolutions(n));
	}
	
	private IPopulation<T> birthSolutions(IPopulation<T> clones) {
		List<ISolution<T>> solutions = clones.getNBestSolutions(clones.getSize());
		return new Population<>(solutions.subList(solutions.size() - d, solutions.size()));
	}

	private IPopulation<T> swap(IPopulation<T> choosen, IPopulation<T> birth) {
		IPopulation<T> population = new Population<>(choosen.getNBestSolutions(choosen.getSize() - d));
		for (ISolution<T> solution : birth) {
			population.add(solution);
		}
		
		return population;
	}
	
	boolean foundGoodSolution() {
		return evaluatePopulation() >= fitnessThreshold;
	}

	double evaluatePopulation() {
		return population.getBestSolution().getFitness();
	}
	
	private class NormalizedPair {
		
		private ISolution<T> solution;
		private double normalizedFitness;
		
		public NormalizedPair(ISolution<T> solution, double normalizedFitness) {
			this.solution = solution;
			this.normalizedFitness = normalizedFitness;
		}
	}
}
