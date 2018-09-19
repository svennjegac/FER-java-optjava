package hr.fer.zemris.optjava.genetic;

public interface IOptAlgorithm<T> {

	public ISolution<T> run();
	public void shutdown();
}
