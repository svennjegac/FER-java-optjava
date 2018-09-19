package hr.fer.zemris.optjava.genetic.impl;

import java.util.Arrays;

import hr.fer.zemris.optjava.genetic.ISolution;

public class IntSolution implements ISolution<int[]> {

	private int[] values;
	private double fitness;
	private double value;
	
	public IntSolution(int[] values, double fitness, double value) {
		this.values = values;
		this.fitness = fitness;
		this.value = value;
	}

	@Override
	public int[] getRepresentation() {
		return values;
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
	public ISolution<int[]> copy() {
		int[] valuesCopy = Arrays.copyOf(values, values.length);
		return new IntSolution(valuesCopy, fitness, value);
	}
	
	@Override
	public String toString() {
		return new String(new Double(fitness).toString());
	}
}