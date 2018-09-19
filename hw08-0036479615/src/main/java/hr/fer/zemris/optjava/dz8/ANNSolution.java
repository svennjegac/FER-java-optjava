package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.algorithms.ISolution;
import hr.fer.zemris.optjava.dz8.nn.IANN;

public class ANNSolution implements ISolution<IANN> {

	private IANN ann;
	private double fitness;
	private double value;
	
	public ANNSolution(IANN ann, double fitness, double value) {
		this.ann = ann;
		this.fitness = fitness;
		this.value = value;
	}

	@Override
	public IANN getRepresentation() {
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
	public ISolution<IANN> copy() {
		return new ANNSolution(ann.copy(), fitness, value);
	}
}
