package hr.fer.zemris.optjava.dz6;

import java.util.List;

import hr.fer.zemris.optjava.dz6.algorithms.ant.AntAlgorithm;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ICanvas;
import hr.fer.zemris.optjava.dz6.algorithms.ant.IOptAlgorithm;
import hr.fer.zemris.optjava.dz6.algorithms.ant.IProblem;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Point;

public class TSPSolver {

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int candidates;
		int antsNumber;
		int maxIterations;
		IProblem<int[]> problem;
		ISolution<int[]> bestSolution;
		ICanvas<int[]> canvas;
		
		try {
			candidates = Integer.parseInt(args[1]);
			antsNumber = Integer.parseInt(args[2]);
			maxIterations = Integer.parseInt(args[3]);
			boolean active = Boolean.parseBoolean(args[4]);
			
			List<Point> coordinates = Util.getCoordinates(args[0]);
			double[][] distances = Util.getDistances(coordinates);
			problem = new TSPProblem(coordinates, distances, candidates);
			
			bestSolution = Util.getBestSolution(args[0]);
			bestSolution.setFitness(problem.getFitness(bestSolution));
			bestSolution.setValue(problem.getValue(bestSolution));
			canvas = new GUI(coordinates, active);
			
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Zadani parametri ne valjaju.");
			return;
		}
		
		IOptAlgorithm<int[]> algorithm =
				new AntAlgorithm<>(
						canvas,
						problem,
						new AntSupplier(),
						new SolutionCreator(1.1, 2.2),
						0.0002,
						0.002,
						1.01,
						antsNumber,
						maxIterations,
						Util.createInitialSolution(problem));
		
		ISolution<int[]> solution = algorithm.run();
		
		System.out.println("My solution:");
		System.out.println(solution);
		System.out.println("Best: ");
		System.out.println(bestSolution);
	}
}