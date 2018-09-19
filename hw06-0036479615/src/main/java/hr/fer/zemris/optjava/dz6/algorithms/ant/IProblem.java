package hr.fer.zemris.optjava.dz6.algorithms.ant;

public interface IProblem<T> extends Iterable<Point> {

	public double getFitness(ISolution<T> solution);
	public double getValue(ISolution<T> solution);
	
	public int[] getPossibleMoves(int location);
	public int[] getRemainingMoves(int[] solution);
	
	public int getRandomStartPoint();
	public int getSize();
	public Point getPoint(int id);
	
	public ISolution<T> generateRandomSolution();
	public void printSolution(ISolution<T> solution);
}
