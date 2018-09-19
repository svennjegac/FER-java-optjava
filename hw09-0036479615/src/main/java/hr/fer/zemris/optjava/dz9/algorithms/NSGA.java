package hr.fer.zemris.optjava.dz9.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NSGA<T> extends AbstractGeneticAlgorithm<T> {
	
	private ISpaceRepresentationGetter<T> spaceReprGetter;
	private double sigmaShare = 0.8;
	private double alpha = 2d;
	private double fitnessScaler = 0.9;
	
	public NSGA(
			int populationSize,
			ISelectionOperator<T> selectionOperator,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IMOOPProblem<T> problem,
			ISpaceRepresentationGetter<T> spaceReprGetter,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selectionOperator,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
		
		this.spaceReprGetter = spaceReprGetter;
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		IPopulation<T> newPopulation = new Population<>();
		
		while (newPopulation.getSize() < population.getSize()) {
			ISolution<T> parent1 = getFirstParent(selectionOperator);
			ISolution<T> parent2 = getSecondParent(selectionOperator, parent1);
			ISolution<T> child = getChild(crossoverOperator, mutationOperator, parent1, parent2);
			problem.transformSolutionToAllowedArea(child);
			
			newPopulation.add(child);
		}
		
		fronts = updatePopulationFitnesses(newPopulation);
		
		return newPopulation;
	}
	
	private List<List<SolutionFitnessesPair<T>>> updatePopulationFitnesses(IPopulation<T> newPopulation) {
		List<SolutionFitnessesPair<T>> solFitPairs = getSolutionFitnessesPairs(newPopulation);
		updateDominations(solFitPairs);
		
		List<List<SolutionFitnessesPair<T>>> fronts = getFronts(solFitPairs);
		updateFitnesses(fronts, newPopulation.getSize(), solFitPairs);
		
		return fronts;
	}

	private List<SolutionFitnessesPair<T>> getSolutionFitnessesPairs(IPopulation<T> newPopulation) {
		List<SolutionFitnessesPair<T>> solFitPairs = new ArrayList<>();
		
		for (ISolution<T> solution : newPopulation) {
			SolutionFitnessesPair<T> solFitPair = new SolutionFitnessesPair<>(solution, problem.getNumberOfOptProblems());
			
			for (int i = 0, size = problem.getNumberOfOptProblems(); i < size; i++) {
				solFitPair.updateFitness(problem.getProblem(i).getFitness(solution), i);
			}
			
			solFitPairs.add(solFitPair);
		}
		
		return solFitPairs;
	}
	
	private void updateDominations(List<SolutionFitnessesPair<T>> solFitPairs) {
		for (SolutionFitnessesPair<T> solFitPair1 : solFitPairs) {
			for (SolutionFitnessesPair<T> solFitPair2 : solFitPairs) {
				if (solFitPair1.dominateOver(solFitPair2)) {
					solFitPair1.addToDominatedSolutions(solFitPair2);
					solFitPair2.incrementDominatedFromCounter();
				}
			}
		}
	}
	
	private List<List<SolutionFitnessesPair<T>>> getFronts(List<SolutionFitnessesPair<T>> solFitPairs) {
		List<List<SolutionFitnessesPair<T>>> fronts = new ArrayList<>();
		
		List<SolutionFitnessesPair<T>> front = new ArrayList<>();
		for (SolutionFitnessesPair<T> solFitPair : solFitPairs) {
			if (solFitPair.getIsDominatedFrom() == 0) {
				front.add(solFitPair);
			}
		}
		
		fronts.add(front);
		
		while (true) {
			List<SolutionFitnessesPair<T>> lastFront = fronts.get(fronts.size() - 1);
			List<SolutionFitnessesPair<T>> newFront = new ArrayList<>();
			
			for (SolutionFitnessesPair<T> solFitPair : lastFront) {
				solFitPair.getDominatedSolutions().forEach(dominatedSolution -> {
					dominatedSolution.decrementDominatedFromCounter();
					if (dominatedSolution.getIsDominatedFrom() == 0) {
						newFront.add(dominatedSolution);
					}
				});
			}
			
			if (newFront.isEmpty()) {
				return fronts;
			}
			
			fronts.add(newFront);
		}
	}
	
	private void updateFitnesses(List<List<SolutionFitnessesPair<T>>> fronts, int size, List<SolutionFitnessesPair<T>> solFitPairs) {
		double currentFitness = (double) size;
		int index = 0;
		
		double[] maxXk = getXkOutliers(solFitPairs, (d1, d2) -> Double.compare(d1, d2));
		double[] minXk = getXkOutliers(solFitPairs, (d1, d2) -> Double.compare(d2, d1));
		
		while (true) {
			if (index >= fronts.size()) {
				return;
			}
			
			List<SolutionFitnessesPair<T>> front = fronts.get(index);
			for (SolutionFitnessesPair<T> solFitPair : front) {
				solFitPair.getSolution().setFitness(currentFitness);
			}
			
			for (SolutionFitnessesPair<T> solFitPair : front) {
				double nicheDensity = 0;
				
				for (SolutionFitnessesPair<T> iteratingSolution : front) {
					nicheDensity +=
							DistanceCalculator.getSharedDistance(
									DistanceCalculator.getDistance(
											spaceReprGetter.getRepresentation(solFitPair),
											spaceReprGetter.getRepresentation(iteratingSolution),
											maxXk,
											minXk),
									sigmaShare,
									alpha);
				}
				
				solFitPair.getSolution().setFitness(solFitPair.getSolution().getFitness() / nicheDensity);
			}
			
			currentFitness = front
								.stream()
								.mapToDouble(solFitPair -> solFitPair.getSolution().getFitness())
								.min()
								.getAsDouble();
			
			currentFitness *= fitnessScaler;
			index++;
		}
	}

	private double[] getXkOutliers(List<SolutionFitnessesPair<T>> solFitPairs, Comparator<Double> comparator) {
		double[] array = new double[spaceReprGetter.getRepresentation(solFitPairs.get(0)).length];
		
		for (int i = 0; i < array.length; i++) {
			Integer ind = new Integer(i);
			array[i] = solFitPairs
							.stream()
							.map(solFitPair -> {
								return spaceReprGetter.getRepresentation(solFitPair)[ind];
							})
							.max(comparator)
							.get();
		}
		
		return array;
	}

	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}
