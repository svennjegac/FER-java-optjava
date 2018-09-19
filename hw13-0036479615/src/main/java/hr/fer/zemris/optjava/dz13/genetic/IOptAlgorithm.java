package hr.fer.zemris.optjava.dz13.genetic;

public interface IOptAlgorithm<T> {

	public ISolution<T> run();
	public void shutdown();
}
