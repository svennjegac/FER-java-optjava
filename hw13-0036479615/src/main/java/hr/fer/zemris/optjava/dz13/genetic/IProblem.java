package hr.fer.zemris.optjava.dz13.genetic;

public interface IProblem<T> {

	public double getFitness(ISolution<T> solution);
	public double getValue(ISolution<T> solution);
}
