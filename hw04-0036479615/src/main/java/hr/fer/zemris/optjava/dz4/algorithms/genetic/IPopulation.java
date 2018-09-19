package hr.fer.zemris.optjava.dz4.algorithms.genetic;

import java.util.List;

public interface IPopulation<T> {

	public int getSize();
	public void add(ISolution<T> solution);
	public void remove(ISolution<T> solution);
	
	public ISolution<T> getRandomSolution();
	public ISolution<T> getBestSolution();
	public List<ISolution<T>> getNBestSolutions(int n);
	public List<ISolution<T>> getSoultions();
}
