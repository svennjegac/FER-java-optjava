package hr.fer.zemris.optjava.dz6.algorithms.ant;

import java.util.Map;

public interface ISolutionCreator<T> {

	public ISolution<T> createSolution(int startPoint);
	public void setProblem(IProblem<T> problem);
	public void setPheromoneEdges(Map<Edge, EdgeData> pheromoneEdges);
}
