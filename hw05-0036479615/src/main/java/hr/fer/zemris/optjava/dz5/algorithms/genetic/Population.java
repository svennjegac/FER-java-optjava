package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Population<T> implements IPopulation<T> {

	private List<ISolution<T>> solutions;
	private Random random = new Random(System.currentTimeMillis());;
	
	public Population() {
		solutions = new ArrayList<>();
	}
	
	public Population(List<ISolution<T>> solutions) {
		this.solutions = solutions;
	}
	
	@Override
	public int getSize() {
		return solutions.size();
	}
	
	@Override
	public void add(ISolution<T> solution) {
		solutions.add(solution);
	}
	
	@Override
	public void remove(ISolution<T> solution) {
		solutions.remove(solution);	
	}
	
	@Override
	public void clear() {
		solutions.clear();	
	}
	
	@Override
	public boolean contains(ISolution<T> solution) {
		return solutions.contains(solution);
	}
	
	@Override
	public ISolution<T> getRandomSolution() {
		return solutions.get(random.nextInt(solutions.size()));
	}

	@Override
	public ISolution<T> getBestSolution() {
		return solutions
					.stream()
					.max((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()))
					.get();
	}

	@Override
	public List<ISolution<T>> getNBestSolutions(int n) {
		return solutions
					.stream()
					.sorted((s1, s2) -> Double.compare(s2.getFitness(), s1.getFitness()))
					.collect(Collectors.toList())
					.subList(0, n);
	}
	
	@Override
	public List<ISolution<T>> getSoultions() {
		return solutions;
	}
	
	@Override
	public ISolution<T> getSolution(int index) {
		return solutions.get(index);
	}
}
