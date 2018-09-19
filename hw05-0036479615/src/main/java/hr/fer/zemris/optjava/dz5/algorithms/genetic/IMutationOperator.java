package hr.fer.zemris.optjava.dz5.algorithms.genetic;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
