package hr.fer.zemris.optjava.dz9.algorithms;

import java.util.HashSet;
import java.util.Set;

public class SolutionFitnessesPair<T> {
	
	private ISolution<T> solution;
	private double[] fitnesses;
	private Set<SolutionFitnessesPair<T>> dominatedSolutions;
	private int isDominatedFrom;
	
	public SolutionFitnessesPair(ISolution<T> solution, int fitnessesNumber) {
		this.solution = solution;
		fitnesses = new double[fitnessesNumber];
		dominatedSolutions = new HashSet<>();
		isDominatedFrom = 0;
	}
	
	public ISolution<T> getSolution() {
		return solution;
	}
	
	public Set<SolutionFitnessesPair<T>> getDominatedSolutions() {
		return dominatedSolutions;
	}
	
	public int getIsDominatedFrom() {
		return isDominatedFrom;
	}
	
	public double[] getFitnesses() {
		return fitnesses;
	}
	
	public void updateFitness(double fitness, int index) {
		fitnesses[index] = fitness;
	}
	
	public void addToDominatedSolutions(SolutionFitnessesPair<T> solFitPair) {
		dominatedSolutions.add(solFitPair);
	}
	
	public void incrementDominatedFromCounter() {
		isDominatedFrom++;
	}
	
	public void decrementDominatedFromCounter() {
		isDominatedFrom--;
	}
	
	public boolean dominateOver(SolutionFitnessesPair<T> solFitPair) {
		boolean isBetterAtOnePoint = false;
		
		for (int i = 0; i < fitnesses.length; i++) {
			if (fitnesses[i] < solFitPair.fitnesses[i]) {
				return false;
			}
			
			if (fitnesses[i] > solFitPair.fitnesses[i]) {
				isBetterAtOnePoint = true;
			}
		}
		
		return isBetterAtOnePoint;
	}
}
