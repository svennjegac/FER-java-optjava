package hr.fer.zemris.optjava.dz6.algorithms.ant;

public interface IAnt<T> extends Iterable<Edge> {

	public void setSolution(ISolution<T> iSolution);
	public ISolution<T> getSolution();
	
	public double getFitness();
	public void setFitness(double fitness);
	
	public double getValue();
	public void setValue(double value);
	
	public int getStartPoint();
	public IAnt<T> copy();
}
