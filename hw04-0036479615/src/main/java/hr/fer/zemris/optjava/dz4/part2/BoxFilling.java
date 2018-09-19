package hr.fer.zemris.optjava.dz4.part2;

import java.util.List;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.SteadyStateGA;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.TournamentSelOp;


public class BoxFilling {

	public static void main(String[] args) {
		if (args.length != 7) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		String file;
		int populationSize;
		int parentTournament;
		int dieTournament;
		boolean dieRule;
		int maxGenerations;
		int satisfiableContSize;
		
		try {
			file = args[0];
			populationSize = Integer.parseInt(args[1]);
			parentTournament = Integer.parseInt(args[2]);
			dieTournament = Integer.parseInt(args[3]);
			dieRule = Boolean.parseBoolean(args[4]);
			maxGenerations = Integer.parseInt(args[5]);
			satisfiableContSize = Integer.parseInt(args[6]);
		} catch (NumberFormatException e) {
			System.out.println("Problem pri parsiranju argumenata.");
			return;
		}
		
		IOptAlgorithm<List<Box>> optAlgorithm;
		try {
			optAlgorithm = new SteadyStateGA<>(
									populationSize,
									new TournamentSelOp<>(parentTournament, true),
									new TournamentSelOp<>(dieTournament, false),
									new ContainerCrossover(),
									new ContainerMutator(),
									new BoxFillingProblem(file),
									(double) -satisfiableContSize,
									maxGenerations,
									dieRule);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		ISolution<List<Box>> bestSolution = optAlgorithm.run();
		System.out.println("Našao rješenje: ");
		for (int i = 0, size = bestSolution.getRepresentation().size(); i < size; i++) {
			System.out.print(i + " -> ");
			for (int j = 0, size2 = bestSolution.getRepresentation().get(i).getStickList().size(); j < size2; j++) {
				System.out.print(bestSolution.getRepresentation().get(i).getStickList().get(j) + ", ");
			}
			
			System.out.println("");
		}
	}
}
