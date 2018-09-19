package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Edge;
import hr.fer.zemris.optjava.dz6.algorithms.ant.EdgeData;
import hr.fer.zemris.optjava.dz6.algorithms.ant.IProblem;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolutionCreator;

public class SolutionCreator implements ISolutionCreator<int[]> {
	
	private static final int FINAL_MOVES = 10;
	private static final int SORTING_MOVES = 50;
	
	private IProblem<int[]> problem;
	private Map<Edge, EdgeData> pheromoneEdges;
	
	private Random rand;
	private double alpha;
	private double beta;
	
	public SolutionCreator(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public void setProblem(IProblem<int[]> problem) {
		this.problem = problem;
	}
	
	@Override
	public void setPheromoneEdges(Map<Edge, EdgeData> pheromoneEdges) {
		this.pheromoneEdges = pheromoneEdges;	
	}

	@Override
	public ISolution<int[]> createSolution(int startPoint) {
		
		int[] solution = new int[problem.getSize()];
		for (int i = 0; i < solution.length; i++) {
			solution[i] = -1;
		}
		
		solution[0] = startPoint;
		
		for (int i = 1, size = problem.getSize(); i < size; i++) {
			solution[i] = choosePath(solution, i);
		}
		
		TSPSolution tspSolution = new TSPSolution(solution, 0d, 0d);
		tspSolution.setFitness(problem.getFitness(tspSolution));
		tspSolution.setValue(problem.getValue(tspSolution));
		
		return tspSolution;
	}

	private int choosePath(int[] solution, int i) {
		int currentPoint = solution[i - 1];
		
		int[] possibleDestinations = problem.getPossibleMoves(currentPoint);
		int[] unvisitedPossibleDestinations = getUnvisitedPossibleDestinations(solution, possibleDestinations);
		
		double[] probabilitiesForDestinations = calculateProbabilities(currentPoint, unvisitedPossibleDestinations);
		int choosenPath = chooseSinglePath(probabilitiesForDestinations);
		
		return unvisitedPossibleDestinations[choosenPath];
	}

	private int[] getUnvisitedPossibleDestinations(int[] solution, int[] possibleDestinations) {
		List<Integer> unvisitedPossibleDestinations = new ArrayList<>();
		
		for (int possible : possibleDestinations) {
			boolean visited = false;
			
			for (int solutionDestination : solution) {
				if (possible == solutionDestination) {
					visited = true;
					break;
				}
			}
			
			if (visited) {
				continue;
			}
			
			unvisitedPossibleDestinations.add(possible);
		}
		
		if (unvisitedPossibleDestinations.isEmpty()) {
			int[] remainingMoves = problem.getRemainingMoves(solution);
			return Arrays.stream(remainingMoves).limit(SORTING_MOVES).sorted().limit(FINAL_MOVES).toArray();
		}
		
		return unvisitedPossibleDestinations.stream().mapToInt(a -> a).toArray();
	}
	
	private double[] calculateProbabilities(int currentPoint, int[] unvisitedPossibleDestinations) {
		double[] probabilities = new double[unvisitedPossibleDestinations.length];
		Map<Edge, Double> singleProbEffVals = new HashMap<>();
		
		for (int i = 0; i < probabilities.length; i++) {
			Edge edge = Edge.of(currentPoint, unvisitedPossibleDestinations[i]);
			EdgeData data = pheromoneEdges.get(edge);
			double probabilitiy = Math.pow(data.getPheromoneValue(), alpha) * Math.pow((double) 1 / data.getLength(), beta);
			singleProbEffVals.put(edge, probabilitiy);
		}
		
		double probabilitiesEffSum = singleProbEffVals
											.entrySet()
											.stream()
											.mapToDouble(entry -> entry.getValue())
											.sum();
		
		for (int i = 0; i < probabilities.length; i++) {
			double singleProbEffVal = singleProbEffVals.get(
												Edge.of(currentPoint,
												unvisitedPossibleDestinations[i]));
			
			probabilities[i] = singleProbEffVal / probabilitiesEffSum;
		}
		
		return probabilities;
	}
	
	private int chooseSinglePath(double[] probabilitiesForDestinations) {
		double prob = rand.nextDouble();
		double currentProb = 0;
		
		for (int i = 0; i < probabilitiesForDestinations.length; i++) {
			currentProb += probabilitiesForDestinations[i];
			
			if (prob < currentProb) {
				return i;
			}
		}
		
		return probabilitiesForDestinations.length - 1;
	}
}
