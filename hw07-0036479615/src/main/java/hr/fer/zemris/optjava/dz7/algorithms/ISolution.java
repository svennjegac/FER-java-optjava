package hr.fer.zemris.optjava.dz7.algorithms;

import java.util.Random;

public interface ISolution<T> {

	public T getRepresentation();
	
	public double getFitness();
	public void setFitness(double fitness);
	
	public double getValue();
	public void setValue(double value);
	
	public double getPersonalBestFitness();
	public void setPersonalBestFitness(double fitness);
	
	public double getPersonalBestValue();
	public void setPersonalBestValue(double value);
	
	public void updatePersonalBestToCurrent();
	public ISolution<T> getPersonalBest();
	
	public void updateVelocity(
			double inertion,
			double selfConfidence,
			double neighborConfidence,
			ISolution<T> bestNeighbor,
			Random rand);
	
	public void move();
	
	public ISolution<T> copy();
	public void mutate(double probability, Random rand);
}
