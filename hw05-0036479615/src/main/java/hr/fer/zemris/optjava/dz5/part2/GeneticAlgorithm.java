package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.AbstractGeneticAlgorithm;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.OffspringSelection;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.SASEGASA;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.TournamentSelOp;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int[][] distances;
		int[][] traffic;
		int numberOfChromosomes;
		int numberOfPopulations;
		double fitnessThreshold;
		
		try {
			int[][][] data = Util.getData(args[0]);
			distances = data[0];
			traffic = data[1];
			
			numberOfChromosomes = Integer.parseInt(args[1]);
			numberOfPopulations = Integer.parseInt(args[2]);
			fitnessThreshold = Double.parseDouble(args[3]);
		} catch (NullPointerException | NumberFormatException e) {
			System.out.println("Nisam u stanju formulirati zadatak od proslijeđenih parametara.");
			return;
		}
		
		FactoriesLocationsProblem2 problem = new FactoriesLocationsProblem2(distances.length, distances, traffic);
		
		AbstractGeneticAlgorithm<int[]> offSpringSelection =
				new OffspringSelection<>(
						30,
						new TournamentSelOp<>(6, true),
						new TournamentSelOp<>(6, true),
						new FactoriesCrossover(),
						new FactoriesMutator(0.1),
						new FactoriesDuplicateChecker(),
						problem,
						fitnessThreshold,
						5,
						0.5,
						50,
						0.3,
						0.6);
		
		IOptAlgorithm<int[]> alg =
				new SASEGASA<>(
						numberOfChromosomes,
						problem,
						offSpringSelection,
						fitnessThreshold,
						numberOfPopulations);
		
		System.out.println(alg.run());	
	}
}
