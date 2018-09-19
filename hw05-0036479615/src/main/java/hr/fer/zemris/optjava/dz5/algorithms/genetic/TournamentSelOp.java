package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TournamentSelOp<T> implements ISelectionOperator<T> {

	private int tournametSize;
	private boolean bestSolution;
	
	public TournamentSelOp(int tournametSize, boolean bestSolution) {
		this.tournametSize = tournametSize;
		this.bestSolution = bestSolution;
	}

	@Override
	public ISolution<T> select(IPopulation<T> population) {
		return getBestSolution(makeSolutionsSet(population, null));
	}

	@Override
	public ISolution<T> select(IPopulation<T> population, List<ISolution<T>> forbiddenSolutions) {
		return getBestSolution(makeSolutionsSet(population, forbiddenSolutions));
	}
	
	private ISolution<T> getBestSolution(Set<ISolution<T>> solutions) {
		List<ISolution<T>> sortedSolutions = solutions
												.stream()
												.sorted((s1, s2) -> Double.compare(s2.getFitness(), s1.getFitness()))
												.collect(Collectors.toList());
		
		return sortedSolutions.get(bestSolution ? 0 : sortedSolutions.size() - 1);
	}
	
	private Set<ISolution<T>> makeSolutionsSet(IPopulation<T> population,
			List<ISolution<T>> forbiddenSolutions) {
		
		Set<ISolution<T>> solutions = new HashSet<>();
		
		while (solutions.size() < tournametSize) {
			ISolution<T> solution = population.getRandomSolution();
			
			if (forbiddenSolutions != null && forbiddenSolutions.contains(solution)) {
				continue;
			}
			
			solutions.add(solution);
		}
		
		return solutions;
	}
}
