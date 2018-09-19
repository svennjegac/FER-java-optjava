package hr.fer.zemris.optjava.dz7.algorithms.swarm;

import java.util.List;

import hr.fer.zemris.optjava.dz7.algorithms.ISolution;

public class GlobalNeighborhood<T> implements INeighborhood<T> {

	private List<ISolution<T>> solutions;
	
	@Override
	public void setNeighborhood(List<ISolution<T>> solutions) {
		this.solutions = solutions;		
	}
	
	@Override
	public List<ISolution<T>> getNeighbors(ISolution<T> solution) {
		return solutions;
	}
}
