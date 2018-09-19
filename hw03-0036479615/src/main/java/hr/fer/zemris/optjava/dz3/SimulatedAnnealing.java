package hr.fer.zemris.optjava.dz3;

import java.text.DecimalFormat;
import java.util.Random;

public class SimulatedAnnealing<T> extends AbstractAlgorithm<T> {

	private ITempSchedule tempSchedule;
	private Random rand;
	
	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith, IFunction function,
			ITempSchedule tempSchedule, boolean minimize, IProbabilityCalculator probCalculator) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.tempSchedule = tempSchedule;
		this.minimize = minimize;
		this.probCalculator = probCalculator;
		rand = new Random();
	}

	@Override
	public T run() {
		T currentSolution = startWith;
		
		for (int i = 0, outer = tempSchedule.getOuterLoopCounter(); i < outer; i++) {
			double currentTemperature = tempSchedule.getNextTemperature();
			
			for (int j = 0, inner = tempSchedule.getInnerLoopCounter(); j < inner; j++) {
				T neighbor = neighborhood.randomNeighbor(currentSolution);
				
				double delta = function.valueAt(decoder.decode(neighbor)) - function.valueAt(decoder.decode(currentSolution));
				currentSolution = determineCurrentSolution(currentSolution, neighbor, delta, currentTemperature);
				printStatus(currentSolution, currentTemperature);
			}
		}
		
		return currentSolution;
	}

	private void printStatus(T currentSolution, double currentTemperature) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#######.##");
		
		sb.append(df.format(function.valueAt(decoder.decode(currentSolution))));
		sb.append(" ### " + df.format(currentTemperature) + " -> ");
		for (double value : decoder.decode(currentSolution)) {
			sb.append(df.format(value) + " ... ");
		}
		
		System.out.println(sb.toString());
	}

	private T determineCurrentSolution(T currentSolution, T neighbor, double delta, double currentTemperature) {
		if (minimize && delta <= 0
				|| !minimize && delta >= 0) {
			return neighbor;
		}
		
		if (probCalculator.calculateProbability(minimize ? delta : -delta, currentTemperature) > rand.nextDouble()) {
			return neighbor;
		}
		
		return currentSolution;
	}
}
