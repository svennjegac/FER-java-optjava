package hr.fer.zemris.optjava.dz7.algorithms.swarm;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz7.algorithms.ISolution;

public class LocalNeighborhood<T> implements INeighborhood<T> {

	private List<ISolution<T>> solutions;
	private int dOneSideNeighbors;
	
	public LocalNeighborhood(int dOneSideNeighbors) {
		this.dOneSideNeighbors = dOneSideNeighbors;
	}

	@Override
	public void setNeighborhood(List<ISolution<T>> solutions) {
		this.solutions = solutions;	
	}
	
	@Override
	public List<ISolution<T>> getNeighbors(ISolution<T> solution) {
		int index = solutions.indexOf(solution);
		index -= dOneSideNeighbors;
		
		List<ISolution<T>> neighbors = new ArrayList<>();
		
		for (int i = index; i < index + 2 * dOneSideNeighbors + 1; i++) {
			neighbors.add(solutions.get((i + solutions.size()) % solutions.size()));
		}
		
		return neighbors;
	}
}
