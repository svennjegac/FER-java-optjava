package hr.fer.zemris.optjava.dz8.algorithms;

public interface IBaseVectorChooser<T> {

	public ISolution<T> getBaseVector(IPopulation<T> population, ISolution<T> targetVector);
}
