package hr.fer.zemris.optjava.dz9.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NSGA2<T> extends AbstractGeneticAlgorithm<T> {
	
	private static final int CROWDING_INFINITY = 500;
	
	private ISpaceRepresentationGetter<T> spaceReprGetter;
	private double sigmaShare = 0.8;
	private double alpha = 2d;
	private double fitnessScaler = 0.9;
	
	public NSGA2(
			int populationSize,
			ISelectionOperator<T> selOp,
			ICrossoverOperator<T> crossoverOperator,
			IMutationOperator<T> mutationOperator,
			IMOOPProblem<T> problem,
			ISpaceRepresentationGetter<T> spaceReprGetter,
			double fitnessThreshold,
			int maxGenerations) {
		
		super(
			populationSize,
			selOp,
			crossoverOperator,
			mutationOperator,
			problem,
			fitnessThreshold,
			maxGenerations
		);
		
		selectionOperator = new CrowdedTournament(3);
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
		
		IPopulation<T> combinedPopulation = new Population<>();
		population.forEach(combinedPopulation::add);
		newPopulation.forEach(combinedPopulation::add);
		
		List<List<SolutionFitnessesPair<T>>> fronts = createFronts(combinedPopulation);
		fronts = getNBestSoluftions(fronts);
		
		List<SolutionFitnessesPair<T>> solFitPairs = new ArrayList<>();
		for (List<SolutionFitnessesPair<T>> front : fronts) {
			for (SolutionFitnessesPair<T> solutionFitnessesPair : front) {
				solFitPairs.add(solutionFitnessesPair);
			}
		}
		
		updateFitnesses(fronts, populationSize, solFitPairs);
		this.fronts = fronts;
		
		return new Population<>(solFitPairs
									.stream()
									.map(SolutionFitnessesPair::getSolution)
									.collect(Collectors.toList()));
	}
	
	private List<List<SolutionFitnessesPair<T>>> getNBestSoluftions(List<List<SolutionFitnessesPair<T>>> fronts) {
		int index = 0;
		int size = 0;
		
		while (true) {
			if (size + fronts.get(index).size() > populationSize) {
				break;
			}

			size += fronts.get(index).size();
			index++;
		}
		
		List<SolutionFitnessesPair<T>> lastFront = fronts.get(index);
		lastFront = pickFrontPartition(lastFront, populationSize - size);
		
		List<List<SolutionFitnessesPair<T>>> newFronts = new ArrayList<>();
		for (int i = 0; i < index; i++) {
			newFronts.add(fronts.get(i));
		}

		if (!lastFront.isEmpty()) {
			newFronts.add(lastFront);
		}
		
		return newFronts;
	}

	private List<SolutionFitnessesPair<T>> pickFrontPartition(List<SolutionFitnessesPair<T>> lastFront, int n) {
		List<CrowdingElement<T>> crowdingElements = new ArrayList<>();
		for (int i = 0, size = lastFront.size(); i < size; i++) {
			crowdingElements.add(new CrowdingElement<>(lastFront.get(i), i));
		}
		
		for (int i = 0, size = problem.getNumberOfOptProblems(); i < size; i++) {
			Integer index = new Integer(i);
			crowdingElements.sort((c1, c2) -> {
				return Double.compare(c1.getSolFitPair().getFitnesses()[index], c2.getSolFitPair().getFitnesses()[index]);
			});
			
			double min = crowdingElements.get(0).getSolFitPair().getFitnesses()[i];
			double max = crowdingElements.get(crowdingElements.size() - 1).getSolFitPair().getFitnesses()[i];
			
			//set infinity to the first and last element
			crowdingElements.get(0).setCrowdingDistance(
					crowdingElements.get(0).getCrowdingDistance() + CROWDING_INFINITY);
			crowdingElements.get(crowdingElements.size() - 1).setCrowdingDistance(
					crowdingElements.get(crowdingElements.size() - 1).getCrowdingDistance() + CROWDING_INFINITY);
		
			for (int j = 1, size2 = crowdingElements.size() - 1; j < size2; j++) {
				
				double crowdingAddition =
						crowdingElements.get(j + 1).getSolFitPair().getFitnesses()[i] -
						crowdingElements.get(j - 1).getSolFitPair().getFitnesses()[i];
				crowdingAddition = crowdingAddition / (max - min);
				
				crowdingElements.get(j).setCrowdingDistance(crowdingElements.get(j).getCrowdingDistance() + crowdingAddition);
			}
		}
		
		return crowdingElements
							.stream()
							.sorted((c1, c2) -> Double.compare(c2.getCrowdingDistance(), c1.getCrowdingDistance()))
							.map(CrowdingElement::getSolFitPair)
							.limit(n)
							.collect(Collectors.toList());
	}

	private List<List<SolutionFitnessesPair<T>>> createFronts(IPopulation<T> combinedPopulation) {
		List<SolutionFitnessesPair<T>> solFitPairs = getSolutionFitnessesPairs(combinedPopulation);
		updateDominations(solFitPairs);
		return getFronts(solFitPairs);
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
	
	private class CrowdedTournament implements ISelectionOperator<T> {

		int tournamentSize;
		private Random rand = new Random(System.currentTimeMillis());
		
		public CrowdedTournament(int tournamentSize) {
			this.tournamentSize = tournamentSize;
		}

		@Override
		public ISolution<T> select(IPopulation<T> population) {
			List<ISolution<T>> solutions = population.getNBestSolutions(tournamentSize);
			
			return selectSolution(solutions);
		}

		private ISolution<T> selectSolution(List<ISolution<T>> solutions) {
			if (fronts == null) {
				return solutions.get(rand.nextInt(solutions.size()));
			}
			
			int rank = fronts.size() - 1;
			List<ISolution<T>> bestRankSolutions = new ArrayList<>();
			
			for (ISolution<T> solution : solutions) {
				int solRank = getRank(solution);
				
				if (solRank == rank) {
					bestRankSolutions.add(solution);
				}
				
				if (solRank < rank) {
					rank = solRank;
					bestRankSolutions.clear();
					bestRankSolutions.add(solution);
				}
			}
			
			List<SolutionFitnessesPair<T>> solFitPairs = getSolFitPairs(bestRankSolutions);
			return pickFrontPartition(solFitPairs, 1).get(0).getSolution();
		}

		private List<SolutionFitnessesPair<T>> getSolFitPairs(List<ISolution<T>> bestRankSolutions) {
			List<SolutionFitnessesPair<T>> solFitPairs = new ArrayList<>();
			
			for (ISolution<T> solution : bestRankSolutions) {
				for (List<SolutionFitnessesPair<T>> front : fronts) {
					for (SolutionFitnessesPair<T> solutionFitnessesPair : front) {
						if (solutionFitnessesPair.getSolution() == solution) {
							solFitPairs.add(solutionFitnessesPair);
						}
					}
				}
			}
			
			return solFitPairs;
		}

		private int getRank(ISolution<T> solution) {
			for (int i = 0; i < fronts.size(); i++) {
				List<SolutionFitnessesPair<T>> front = fronts.get(i);
				for (SolutionFitnessesPair<T> solutionFitnessesPair : front) {
					if (solutionFitnessesPair.getSolution() == solution) {
						return i;
					}
				}
			}
			
			throw new IllegalStateException();
		}

		@Override
		public ISolution<T> select(IPopulation<T> population, List<ISolution<T>> forbiddenSolutions) {
			List<ISolution<T>> solutions = new ArrayList<>();
			
			while (solutions.size() < tournamentSize) {
				ISolution<T> solution = population.getRandomSolution();
				if (forbiddenSolutions.contains(solution)) {
					continue;
				}
				
				solutions.add(solution);
			}
			
			return selectSolution(solutions);
		}
	}
}
