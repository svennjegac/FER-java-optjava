package hr.fer.zemris.optjava.genetic;

import java.util.List;

public interface IPopulation<T> extends Iterable<ISolution<T>> {

	public int getSize();
	public void add(ISolution<T> solution);
	public void remove(ISolution<T> solution);
	public void clear();
	public boolean contains(ISolution<T> solution);
	
	public ISolution<T> getRandomSolution();
	public ISolution<T> getBestSolution();
	public List<ISolution<T>> getNBestSolutions(int n);
	public List<ISolution<T>> getSoultions();
	public ISolution<T> getSolution(int index);
}
