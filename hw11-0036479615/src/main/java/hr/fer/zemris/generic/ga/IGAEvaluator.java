package hr.fer.zemris.generic.ga;

import hr.fer.zemris.optjava.genetic.ISolution;

public interface IGAEvaluator<T> {

	public double evaluate(ISolution<T> p);
}
