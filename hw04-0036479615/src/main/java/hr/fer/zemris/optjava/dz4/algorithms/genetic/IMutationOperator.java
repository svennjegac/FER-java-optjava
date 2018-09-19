package hr.fer.zemris.optjava.dz4.algorithms.genetic;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
