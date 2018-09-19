package hr.fer.zemris.optjava.dz6.algorithms.ant;

public abstract class AbstractAnt<T> implements IAnt<T> {

	private ISolution<T> solution;
	private int startPoint;
	
	public AbstractAnt(ISolution<T> solution, int startPoint) {
		this.solution = solution;
		this.startPoint = startPoint;
	}

	@Override
	public void setSolution(ISolution<T> solution) {
		this.solution = solution;
	}

	@Override
	public ISolution<T> getSolution() {
		return solution;
	}

	@Override
	public double getFitness() {
		return solution.getFitness();
	}

	@Override
	public void setFitness(double fitness) {
		solution.setFitness(fitness);
	}

	@Override
	public double getValue() {
		return solution.getValue();
	}

	@Override
	public void setValue(double value) {
		solution.setValue(value);
	}

	@Override
	public int getStartPoint() {
		return startPoint;
	}
}
