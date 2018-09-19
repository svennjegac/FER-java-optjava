package hr.fer.zemris.optjava.dz7;

import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.neuralnetwork.ANN;

public class ANNSolution implements ISolution<ANN> {

	private ANN ann;
	private double[] velocity;
	private double fitness;
	private double value;
	
	private double[] personalBest;
	private double personalBestFitness;
	private double personalBestValue;
	
	public ANNSolution(ANN ann, double fitness, double value) {
		this.ann = ann;
		this.fitness = fitness;
		this.value = value;
		velocity = new double[ann.getWeightsCount()];
		personalBest = ann.getWeights();
	}

	@Override
	public ANN getRepresentation() {
		return ann;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public double getPersonalBestFitness() {
		return personalBestFitness;
	}
	
	@Override
	public void setPersonalBestFitness(double fitness) {
		personalBestFitness = fitness;	
	}

	@Override
	public double getPersonalBestValue() {
		return personalBestValue;
	}
	
	@Override
	public void setPersonalBestValue(double value) {
		personalBestValue = value;	
	}
	
	@Override
	public void updatePersonalBestToCurrent() {
		personalBestFitness = fitness;
		personalBestValue = value;
		personalBest = ann.getWeights();
	}

	@Override
	public ISolution<ANN> getPersonalBest() {
		ANN personalBest = ann.copy();
		personalBest.setWeights(this.personalBest);
		
		if (personalBest.getNeuronUtil() == null) {
			return null;
		}
		
		ISolution<ANN> bestSolution = new ANNSolution(personalBest, personalBestFitness, personalBestValue);
		bestSolution.updatePersonalBestToCurrent();
		
		return bestSolution;
	}

	@Override
	public void updateVelocity(double inertion, double selfConfidence, double neighborConfidence,
			ISolution<ANN> bestNeighbor, Random rand) {
		
		double[] neighborBest = bestNeighbor.getRepresentation().getWeights();
		double[] currentPosition = ann.getWeights();
		
		for (int i = 0; i < velocity.length; i++) {
			velocity[i] = inertion * velocity[i];
			velocity[i] += selfConfidence * rand.nextDouble() * (personalBest[i] - currentPosition[i]);
			velocity[i] += neighborConfidence * rand.nextDouble() * (neighborBest[i] - currentPosition[i]);
		}
	}
	
	@Override
	public void move() {
		double[] currentPosition = ann.getWeights();
		
		for (int i = 0; i < velocity.length; i++) {
			currentPosition[i] += velocity[i];
		}
		
		ann.setWeights(currentPosition);
	}
	
	@Override
	public ISolution<ANN> copy() {
		ANN ann = this.ann.copy();
		ISolution<ANN> solution = new ANNSolution(ann, fitness, personalBestValue);
		solution.updatePersonalBestToCurrent();
		return solution;
	}
	
	@Override
	public void mutate(double probability, Random rand) {
		double[] w = ann.getWeights();
		
		for (int i = 0; i < w.length; i++) {
			if (rand.nextDouble() > probability) {
				continue;
			}
			
			w[i] += rand.nextGaussian() * 0.005;
		}
		
		ann.setWeights(w);
	}
}
