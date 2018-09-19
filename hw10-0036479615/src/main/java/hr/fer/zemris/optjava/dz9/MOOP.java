package hr.fer.zemris.optjava.dz9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import hr.fer.zemris.optjava.dz9.algorithms.IMOOPProblem;
import hr.fer.zemris.optjava.dz9.algorithms.IMOOptAlgorithm;
import hr.fer.zemris.optjava.dz9.algorithms.NSGA2;
import hr.fer.zemris.optjava.dz9.algorithms.SolutionFitnessesPair;

public class MOOP {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		IMOOPProblem<double[]> problem = parseProblem(args[0]);
		if (problem == null) {
			System.out.println("Nepostojeći problem.");
			return;
		}
		
		int populationSize;
		int maxGenerations;
		
		try {
			populationSize = Integer.parseInt(args[1]);
			maxGenerations = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			System.out.println("Nepravilno zadani argumenti.");
			return;
		}
		
		IMOOptAlgorithm<double[]> mooptAlg =
				new NSGA2<>(
						populationSize,
						null,
						new BLXAlphaCrossover(0.2),
						new DecimalMutationOperator(0.2, 0.2),
						problem,
						solFitPair -> solFitPair.getSolution().getRepresentation(),
						101,
						maxGenerations);

		List<List<SolutionFitnessesPair<double[]>>> fronts = mooptAlg.run();
		printFronts(fronts);
		saveDecisionSpaceSolutions(fronts);
		saveObjectiveSpaceFitnesses(fronts);
		
		drawProblem(problem);		
	}

	private static void drawProblem(IMOOPProblem<double[]> problem) {
		List<Point> points = getPoints(problem);
		if (points == null) {
			return;
		}
		
		GUI gui = new GUI(points);
		gui.setVisible(true);
		gui.draw();
	}

	private static List<Point> getPoints(IMOOPProblem<double[]> problem) {
		if (!(problem instanceof Problem2)) {
			return null;
		}
		
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Files.newInputStream(Paths.get("izlaz-obj.txt"))))) {
			
			String line;
			List<Point> points = new ArrayList<>();
			
			while ((line = br.readLine()) != null) {
				String[] xy = line.trim().split("\\s+");
				points.add(new Point(-Double.parseDouble(xy[0]) * 4, -Double.parseDouble(xy[1])));
			}
			
			return points;
		} catch (IOException e) {
			return null;
		}
	}

	private static void printFronts(List<List<SolutionFitnessesPair<double[]>>> fronts) {
		for (int i = 0, size = fronts.size(); i < size; i++) {
			System.out.println("Fronta " + (i + 1) + ", rješenja u fronti: " + fronts.get(i).size());
		}
	}
	
	private static void saveDecisionSpaceSolutions(List<List<SolutionFitnessesPair<double[]>>> fronts) {
		try (BufferedWriter bw =
				new BufferedWriter(
						new OutputStreamWriter(Files.newOutputStream(Paths.get("izlaz-dec.txt"))))) {
			
			for (List<SolutionFitnessesPair<double[]>> front : fronts) {
				for (SolutionFitnessesPair<double[]> solutionFitnessesPair : front) {
					double[] repr = solutionFitnessesPair.getSolution().getRepresentation();
					
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < repr.length; i++) {
						sb.append(repr[i] + " ");
					}
					
					bw.write(sb.toString());
					bw.write(System.lineSeparator());
				}
			}
		} catch (IOException e) {
			System.out.println("Ne mogu spremit dec space.");
			return;
		}
	}
	
	private static void saveObjectiveSpaceFitnesses(List<List<SolutionFitnessesPair<double[]>>> fronts) {
		try (BufferedWriter bw =
				new BufferedWriter(
						new OutputStreamWriter(Files.newOutputStream(Paths.get("izlaz-obj.txt"))))) {
			
			for (List<SolutionFitnessesPair<double[]>> front : fronts) {
				for (SolutionFitnessesPair<double[]> solutionFitnessesPair : front) {
					double[] fitnesses = solutionFitnessesPair.getFitnesses();
					
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < fitnesses.length; i++) {
						sb.append((-fitnesses[i]) + " ");
					}
					
					bw.write(sb.toString());
					bw.write(System.lineSeparator());
				}
			}
		} catch (IOException e) {
			System.out.println("Ne mogu spremit obj space.");
			return;
		}
	}

	private static IMOOPProblem<double[]> parseProblem(String problem) {
		switch (problem) {
		case "1":
			return new Problem1();
		case "2":
			return new Problem2();
		default:
			return null;
		}
	}
}