package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.optjava.genetic.GenerationElitisticGAP1;
import hr.fer.zemris.optjava.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.genetic.ISolution;
import hr.fer.zemris.optjava.genetic.TournamentSelOp;
import hr.fer.zemris.optjava.genetic.impl.IntegerMutation;
import hr.fer.zemris.optjava.genetic.impl.SquaresCrossover;
import hr.fer.zemris.optjava.rng.EVOThread;

public class Pokretac1 {

	public static void main(String[] args) {
		if (args.length != 7) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int numOfSquares = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		int maxGenerations = Integer.parseInt(args[3]);
		double fitnessThresh = Double.parseDouble(args[4]);
		
		Thread t = new EVOThread(new Runnable() {
			
			@Override
			public void run() {
				IOptAlgorithm<int[]> alg =
						new GenerationElitisticGAP1<>(
								populationSize,
								new TournamentSelOp<>(3, true),
								new SquaresCrossover(3),
								new IntegerMutation(0.01, -50, 50),
								new PictureProblem(numOfSquares, args[0]),
								fitnessThresh,
								maxGenerations);
				
				ISolution<int[]> solution = alg.run();
				Util.writeToFile(args[5], solution.getRepresentation());
				
				Evaluator evaluator = EvaluatorProvider.getEvaluator(args[0]);
				GrayScaleImage gsi = evaluator.draw(solution, null);
				
				try {
					gsi.save(new File(args[6]));
				} catch (IOException e) {
				}
				alg.shutdown();
			}
		});
		
		t.start();
	}
}
