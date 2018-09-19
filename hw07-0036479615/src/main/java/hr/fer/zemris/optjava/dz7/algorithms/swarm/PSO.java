package hr.fer.zemris.optjava.dz7.algorithms.swarm;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.algorithms.IPopulation;
import hr.fer.zemris.optjava.dz7.algorithms.IProblem;
import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.algorithms.Population;

public class PSO<T> implements IOptAlgorithm<T> {

	private IPopulation<T> population;
	private INeighborhood<T> neighborhood;
	private IProblem<T> problem;
	private IInertionScaler inertionScaler;
	
	private double selfConfidence;
	private double neighborConfidence;
	private int populationSize;
	private double fitnessThreshold;
	private int maxGenerations;
	private Random rand;
	
	public PSO(
			INeighborhood<T> neighborhood,
			IProblem<T> problem,
			IInertionScaler inertionScaler,
			double selfConfidence,
			double neighborConfidence,
			int populationSize,
			double fitnessThreshold,
			int maxGenerations) {
		
		this.neighborhood = neighborhood;
		this.problem = problem;
		this.inertionScaler = inertionScaler;
		this.selfConfidence = selfConfidence;
		this.neighborConfidence = neighborConfidence;
		this.populationSize = populationSize;
		this.fitnessThreshold = fitnessThreshold;
		this.maxGenerations = maxGenerations;
		rand = new Random(System.currentTimeMillis());
		
		this.population = createInitialPopulation(populationSize);
		this.neighborhood.setNeighborhood(population.getSoultions());
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
		int iteration = 0;
		
		while (iteration < maxGenerations) {
			if (foundGoodSolution()) {
				return bestEverSolution();
			}
			
			System.out.println(iteration + " -> " + bestEverSolution().getPersonalBestFitness());
			
			moveSwarm(iteration);
			updatePersonalBest();
			
			iteration++;
		}
		
		return bestEverSolution();
	}

	private void moveSwarm(int iteration) {
		for (int i = 0, size = population.getSize(); i < size; i++) {
			ISolution<T> solution = population.getSolution(i);
			
			solution.updateVelocity(
					inertionScaler.getInertion(iteration),
					selfConfidence,
					neighborConfidence,
					neighborhood.getBestNeighbor(solution),
					rand);
	
			solution.move();
			solution.setFitness(problem.getFitness(solution));
			solution.setValue(problem.getValue(solution));
		}
	}

	private void updatePersonalBest() {
		for (ISolution<T> solution : population) {
			double newFitness = solution.getFitness();
			
			if (newFitness >= solution.getPersonalBestFitness()) {
				solution.updatePersonalBestToCurrent();
			}
		}
	}
	
	public ISolution<T> bestEverSolution() {
		return population
				.getSoultions()
				.stream()
				.max((s1, s2) -> Double.compare(s1.getPersonalBestFitness(), s2.getPersonalBestFitness()))
				.get()
				.getPersonalBest();
	}
	
	boolean foundGoodSolution() {
		return evaluatePopulation() >= fitnessThreshold;
	}

	double evaluatePopulation() {
		return bestEverSolution().getFitness();
	}
}
