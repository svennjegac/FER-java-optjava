package hr.fer.zemris.optjava.dz7.algorithms.swarm;

import java.util.List;

import hr.fer.zemris.optjava.dz7.algorithms.ISolution;

public interface INeighborhood<T> {

	public void setNeighborhood(List<ISolution<T>> solutions);
	
	default ISolution<T> getBestNeighbor(ISolution<T> solution) {
		return getNeighbors(solution)
				.stream()
				.max((s1, s2) -> Double.compare(s1.getFitness(), s2.getFitness()))
				.get();
	}
	
	public List<ISolution<T>> getNeighbors(ISolution<T> solution);
}
