package hr.fer.zemris.optjava.dz6;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;

public class TSPSolution implements ISolution<int[]> {

	private int[] values;
	private double fitness;
	private double value;
	
	public TSPSolution(int[] values, double fitness, double value) {
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
		return new TSPSolution(Arrays.copyOf(values, values.length), fitness, value);
	}
	
	@Override
	public String toString() {
		int index = 0;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 0) {
				index = i;
				break;
			}
		}
		
		int[] first = Arrays.copyOfRange(values, index, values.length);
		int[] second = Arrays.copyOfRange(values, 0, index);
		
		int[] copy = merge(first, second);
		StringBuilder sb = new StringBuilder();
		sb.append("Len: " + value + System.lineSeparator());
		
		for (int i : copy) {
			sb.append((i + 1) + ", ");
		}
		
		return sb.toString();
	}

	private int[] merge(int[] first, int[] second) {
		int[] merged = new int[first.length + second.length];
		
		for (int i = 0; i < merged.length; i++) {
			if (i < first.length) {
				merged[i] = first[i];
				continue;
			}
			
			merged[i] = second[i - first.length];
		}
		
		return merged;
	}
}
