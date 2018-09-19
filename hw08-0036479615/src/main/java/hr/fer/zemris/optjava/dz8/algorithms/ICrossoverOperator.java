package hr.fer.zemris.optjava.dz8.algorithms;

public interface ICrossoverOperator<T> {

	public ISolution<T> crossover(ISolution<T> targetVector, ISolution<T> mutantVector);
}
