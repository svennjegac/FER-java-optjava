package hr.fer.zemris.optjava.dz9.algorithms;

public interface ISpaceRepresentationGetter<T> {
	
	public double[] getRepresentation(SolutionFitnessesPair<T> solFitPair);
}
