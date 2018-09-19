package hr.fer.zemris.optjava.dz7.algorithms;

public interface IProblem<T> {

	public double getFitness(ISolution<T> solution);
	public double getValue(ISolution<T> solution);
	
	public ISolution<T> generateRandomSolution();
}
