package hr.fer.zemris.optjava.dz13.genetic;

public interface IReproductionOperator<T> {

	public ISolution<T> reproduce(ISolution<T> solution);
}
