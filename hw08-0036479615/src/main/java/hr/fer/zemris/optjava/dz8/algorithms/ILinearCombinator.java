package hr.fer.zemris.optjava.dz8.algorithms;

public interface ILinearCombinator<T> {

	public ISolution<T> combine(IPopulation<T> population, ISolution<T> targetVector, ISolution<T> baseVector);
}
