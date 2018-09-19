package hr.fer.zemris.optjava.genetic;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
