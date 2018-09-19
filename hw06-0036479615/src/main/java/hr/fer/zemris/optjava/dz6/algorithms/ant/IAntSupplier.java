package hr.fer.zemris.optjava.dz6.algorithms.ant;

public interface IAntSupplier<T> {

	public IAnt<T> newAnt(ISolution<T> solution, int startPoint);
}
