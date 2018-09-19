package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.RAPGA;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.TournamentSelOp;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int bits;
		try {
			bits = Integer.parseInt(args[0]);
			
			if (bits < 5) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.out.println("Argumenti nisu validni.");
			return;
		}
		
		IOptAlgorithm<boolean[]> ones =
				new RAPGA<>(
						20,									//initial population size
						new TournamentSelOp<>(5, true),		//male selection operator
						new TournamentSelOp<>(5, true),	//female selection operator
						new TwoPointCrossover(),			//crossover operator
						new UnifProbabilityMutator(0.01),	//mutation operator
						new DuplicateChecker(),				//duplicate checker
						new MaxOnesProblem(bits),			//problem definition
						0.80001,								//fitness threshold
						3000,								//max algorithm generations (iterations)
						8,									//minimum population size
						40,								//maximum population size
						60,								//maximum selection pressure
						0.3,								//initial comparison factor
						0.95);								//end comparison factor
		
		System.out.println(ones.run());
	}
}