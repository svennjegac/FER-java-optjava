package hr.fer.zemris.optjava.dz13.genetic;

public interface IMutationOperator<T> {

	public ISolution<T> mutate(ISolution<T> solution);
}
