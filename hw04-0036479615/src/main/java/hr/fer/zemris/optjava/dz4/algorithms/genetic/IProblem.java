package hr.fer.zemris.optjava.dz4.algorithms.genetic;

public interface IProblem<T> {

	public double getFitness(ISolution<T> solution);
	public double getValue(ISolution<T> solution);
	
	public ISolution<T> generateRandomSolution();
}
