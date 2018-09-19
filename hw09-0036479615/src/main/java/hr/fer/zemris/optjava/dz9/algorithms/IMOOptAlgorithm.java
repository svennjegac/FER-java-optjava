package hr.fer.zemris.optjava.dz9.algorithms;

import java.util.List;

public interface IMOOptAlgorithm<T> {

	public List<List<SolutionFitnessesPair<T>>> run();
}
