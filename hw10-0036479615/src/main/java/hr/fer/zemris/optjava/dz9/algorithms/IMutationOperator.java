package hr.fer.zemris.optjava.dz9.algorithms;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
