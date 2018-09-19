package hr.fer.zemris.optjava.dz4.part1;

import java.util.List;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.GenerationElitisticGA;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISelectionOperator;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.RouletteWheelSelOp;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.TournamentSelOp;


public class GeneticAlgorithm {
	
	private static final String FILE_NAME = "02-zad-prijenosna.txt";
	private static final String ROULETTE_WHEEL = "rouletteWheel";
	private static final String TOURNAMENT_N = "tournament:[0-9]+";
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int populationSize;
		double fitnessThreshold;
		int maxGenerations;
		String selectionType;
		double sigma;
		
		try {
			populationSize = Integer.parseInt(args[0]);
			fitnessThreshold = Double.parseDouble(args[1]);
			maxGenerations = Integer.parseInt(args[2]);
			selectionType = args[3];
			sigma = Double.parseDouble(args[4]);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Greška pri parsiranju parametara.");
			return;
		}
		
		ISelectionOperator<double[]> selectionOperator = parseSelectionType(selectionType);
		if (selectionOperator == null) {
			System.out.println("Nepostojeći operator selekcije.");
			return;
		}
		
		List<double[]> problemRows = Util.parseProblem(FILE_NAME);
		if (problemRows == null) {
			System.out.println("Pogreška pri čitanju problema sa diska.");
			return;
		}
		
		IOptAlgorithm<double[]> optAlgoritm =
				new GenerationElitisticGA<>(
						populationSize,
						selectionOperator,
						new BLXAlphaCrossover(0.5),
						new DecimalMutationOperator(sigma, 0.2),
						new FunctionFour(problemRows),
						-fitnessThreshold,
						maxGenerations
				);
		
		ISolution<double[]> solution = optAlgoritm.run();
		printSolution(solution);
	}

	private static ISelectionOperator<double[]> parseSelectionType(String selectionType) {
		if (selectionType.equals(ROULETTE_WHEEL)) {
			return new RouletteWheelSelOp<>();
		}
		
		if (selectionType.matches(TOURNAMENT_N)) {
			int tournamentSize;
			
			try {
				tournamentSize = Integer.parseInt(selectionType.substring(selectionType.indexOf(":") + 1));
			} catch (NumberFormatException e) {
				return null;
			}
			
			return new TournamentSelOp<>(tournamentSize, true);
		}
		
		return null;
	}
	
	private static void printSolution(ISolution<double[]> solution) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Function value: " + solution.getValue() + System.lineSeparator());
		sb.append("FunctionFitness: " + solution.getFitness() + System.lineSeparator());
		
		for (int i = 0, size = solution.getRepresentation().length; i < size; i++) {
			sb.append(solution.getRepresentation()[i] + " ### ");
		}
		
		System.out.println(sb.toString());
	}
}
