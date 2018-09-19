package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class Container implements ISolution<List<Box>> {

	private List<Box> boxList;
	
	private double fitness;
	private double value;	
	
	public Container(List<Box> boxList) {
		this.boxList = boxList;
	}

	@Override
	public List<Box> getRepresentation() {
		return boxList;
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
	public ISolution<List<Box>> copy() {
		List<Box> newList = new ArrayList<>();
		boxList.forEach(box -> newList.add(box.copy()));
		return new Container(newList);
	}
	
	@Override
	public String toString() {
		return "" + boxList.size();
	}
}
