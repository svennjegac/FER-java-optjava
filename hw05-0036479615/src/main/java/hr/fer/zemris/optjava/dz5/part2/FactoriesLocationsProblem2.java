package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IProblem;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class FactoriesLocationsProblem2 implements IProblem<int[]> {

	private int n;
	private int[][] distances;
	private int[][] traffic;
	private Random rand;
	
	public FactoriesLocationsProblem2(int n, int[][] distances, int[][] traffic) {
		this.n = n;
		this.distances = distances;
		this.traffic = traffic;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public double getFitness(ISolution<int[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<int[]> solution) {
		int[] locations = solution.getRepresentation();
		double cost = 0;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				cost += traffic[locations[i]][locations[j]] * distances[i][j];
			}
		}
		
		return cost;
	}

	@Override
	public ISolution<int[]> generateRandomSolution() {
		int[] permutation = new int[n];
		
		for (int i = 0; i < n; i++) {
			permutation[i] = i;
		}
		
		for (int i = 0; i < n; i++) {
			swap(permutation);
		}
		
		FactoriesLocationsSolution solution = new FactoriesLocationsSolution(permutation, 0d, 0d);
		solution.setFitness(getFitness(solution));
		solution.setValue(getValue(solution));
		
		return solution;
	}

	private void swap(int[] permutation) {
		int i = rand.nextInt(n);
		int j = rand.nextInt(n);
	
		int tmp = permutation[i];
		permutation[i] = permutation[j];
		permutation[j] = tmp;
	}
}
