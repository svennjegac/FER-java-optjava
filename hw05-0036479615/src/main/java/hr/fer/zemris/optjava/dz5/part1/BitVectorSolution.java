package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class BitVectorSolution implements ISolution<boolean[]> {

	private boolean[] values;
	private double fitness;
	private double value;
	
	public BitVectorSolution(boolean[] values) {
		this.values = values;
	}

	@Override
	public boolean[] getRepresentation() {
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
	public ISolution<boolean[]> copy() {
		BitVectorSolution copySolution = new BitVectorSolution(Arrays.copyOf(values, values.length));
		copySolution.setFitness(fitness);
		copySolution.setValue(value);
		return copySolution;
	}
	
	@Override
	public String toString() {
		return "v: " + getValue() + ", f: " + getFitness();
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("v: " + getValue() + ", f: " + getFitness() + " $$$ ");
//		
//		for (int i = 0; i < values.length; i++) {
//			sb.append(values[i] + ", ");
//		}
//		
//		return sb.toString();
	}
}
