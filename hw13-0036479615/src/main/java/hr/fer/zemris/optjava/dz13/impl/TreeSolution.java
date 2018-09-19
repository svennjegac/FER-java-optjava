package hr.fer.zemris.optjava.dz13.impl;

import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class TreeSolution implements ISolution<Tree> {

	private Tree tree;
	private double fitness;
	private double value;
	
	public TreeSolution(Tree tree, double fitness, double value) {
		this.tree = tree;
		this.fitness = fitness;
		this.value = value;
	}

	@Override
	public Tree getRepresentation() {
		return tree;
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
	public ISolution<Tree> copy() {
		return new TreeSolution(tree.copy(), fitness, value);
	}
}
