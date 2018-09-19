package hr.fer.zemris.optjava.dz4.algorithms.genetic;


public class SteadyStateGA<T> extends AbstractGeneticAlgorithm<T> {

	private ISelectionOperator<T> dieSelectionOperator;
	private boolean dieRule;
	private double lastSolutionFitness = -Double.MAX_VALUE;
	
	public SteadyStateGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ISelectionOperator<T> dieSelectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IProblem<T> problem,
			double fitnessThreshold,
			int maxGenerations,
			boolean dieRule) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
		
		this.dieSelectionOperator = dieSelectionOperator;
		this.dieRule = dieRule;
	}

	@Override
	IPopulation<T> makeNewPopulation() {
		ISolution<T> parent1 = getFirstParent();
		ISolution<T> parent2 = getSecondParent(parent1);
		ISolution<T> child = getChild(parent1, parent2);
		
		ISolution<T> dieSolution = dieSelectionOperator.select(population);
		
		if (dieRule) {
			if (child.getFitness() < dieSolution.getFitness()) {
				return population;
			}
		}
		
		population.remove(dieSolution);
		population.add(child);
		return population;
	}
	
	@Override
	boolean printSolution(ISolution<T> solution) {
		if (solution.getFitness() > lastSolutionFitness) {
			lastSolutionFitness = solution.getFitness();
			return true;
		}
		
		return false;
	}
}
