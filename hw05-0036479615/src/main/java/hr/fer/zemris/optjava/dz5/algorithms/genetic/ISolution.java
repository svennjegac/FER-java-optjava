package hr.fer.zemris.optjava.dz5.algorithms.genetic;

public interface ISolution<T> {

	public T getRepresentation();
	
	public double getFitness();
	public void setFitness(double fitness);
	
	public double getValue();
	public void setValue(double value);
	
	public ISolution<T> copy();
}
