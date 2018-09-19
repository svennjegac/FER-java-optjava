package hr.fer.zemris.optjava.dz6.algorithms.ant;

import java.util.Map;

public interface ICanvas<T> {

	public void showCanvas();
	public void drawSolution(ISolution<T> solution);
	public void drawPheromones(Map<Edge, EdgeData> pheromoneEdges);
}
