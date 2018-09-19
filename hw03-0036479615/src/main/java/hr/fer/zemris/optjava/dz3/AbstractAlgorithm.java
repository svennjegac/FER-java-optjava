package hr.fer.zemris.optjava.dz3;

public abstract class AbstractAlgorithm<T> implements IOptAlgorithm<T> {

	IDecoder<T> decoder;
	INeighborhood<T> neighborhood;
	T startWith;
	IFunction function;
	boolean minimize;
	IProbabilityCalculator probCalculator;
}
