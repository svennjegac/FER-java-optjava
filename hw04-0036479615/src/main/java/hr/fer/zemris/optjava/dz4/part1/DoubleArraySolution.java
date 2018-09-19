package hr.fer.zemris.optjava.dz4.part1;

import java.text.DecimalFormat;
import java.util.Arrays;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class DoubleArraySolution implements ISolution<double[]> {

	private double[] values;
	private double fitness;
	private double value;
	
	public DoubleArraySolution(double[] values) {
		this.values = values;
	}
	
	public DoubleArraySolution(double[] values, double fitness) {
		this.values = values;
		this.fitness = fitness;
	}

	@Override
	public double[] getRepresentation() {
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
	public ISolution<double[]> copy() {
		return new DoubleArraySolution(Arrays.copyOf(values, values.length), fitness);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#########.#####");
		
		sb.append(df.format(fitness));
		
		for (int i = 0; i < values.length; i++) {
			sb.append(" ### " + df.format(values[i]));
		}
		
		return sb.toString();
	}
}
