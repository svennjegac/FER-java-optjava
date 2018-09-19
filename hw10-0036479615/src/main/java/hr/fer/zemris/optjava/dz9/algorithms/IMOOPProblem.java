package hr.fer.zemris.optjava.dz9.algorithms;

public interface IMOOPProblem<T> {

	public int getNumberOfOptProblems();
	public IProblem<T> getProblem(int index);
	public ISolution<T> generateRandomSolution();
	public void transformSolutionToAllowedArea(ISolution<T> solution);
}
