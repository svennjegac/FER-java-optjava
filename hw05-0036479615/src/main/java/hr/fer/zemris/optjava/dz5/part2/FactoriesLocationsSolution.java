package hr.fer.zemris.optjava.dz5.part2;

import java.text.DecimalFormat;
import java.util.Arrays;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class FactoriesLocationsSolution implements ISolution<int[]> {

	private int[] factoriesLocations;
	private double fitness;
	private double value;
	
	public FactoriesLocationsSolution(int[] factoriesLocations, double fitness, double value) {
		this.factoriesLocations = factoriesLocations;
		this.fitness = fitness;
		this.value = value;
	}

	@Override
	public int[] getRepresentation() {
		return factoriesLocations;
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
		return new FactoriesLocationsSolution(
						Arrays.copyOf(factoriesLocations, factoriesLocations.length),
						fitness,
						value);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("############.##");
		
		sb.append("f: " + df.format(fitness) + ", cost: " + df.format(value) + " $$$ ");
		
		for (int i = 0; i < factoriesLocations.length; i++) {
			sb.append(factoriesLocations[i] + ", ");
		}
		
		return sb.toString();
	}
}
