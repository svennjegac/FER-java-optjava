package hr.fer.zemris.optjava.dz13.genetic;

public interface ICrossoverOperator<T> {

	public ISolution<T> crossover(ISolution<T> solution1, ISolution<T> solution2);
}
