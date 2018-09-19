package hr.fer.zemris.optjava.dz13.impl;

import hr.fer.zemris.optjava.dz13.genetic.IReproductionOperator;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class TreeReproduction implements IReproductionOperator<Tree> {

	@Override
	public ISolution<Tree> reproduce(ISolution<Tree> solution) {
		return solution.copy();
	}
}
