package hr.fer.zemris.optjava.dz6.algorithms.ant;

public interface IAntUtility<T> {

	public ISolution<T> createSolution(int startPoint);
	public void decayPheromones();
	public void dropPheromones(IAnt<T> ant);
}
