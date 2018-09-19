package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RouletteWheelSelOp<T> implements ISelectionOperator<T> {

	@Override
	public ISolution<T> select(IPopulation<T> population) {
		List<WheelObject<T>> wheelObjects = constructWheelObjectsList(population, null);
		return spinWheel(wheelObjects);
	}

	@Override
	public ISolution<T> select(IPopulation<T> population, List<ISolution<T>> forbiddenSolutions) {
		List<WheelObject<T>> wheelObjects = constructWheelObjectsList(population, forbiddenSolutions);
		return spinWheel(wheelObjects);
	}
	
	private List<WheelObject<T>> constructWheelObjectsList(IPopulation<T> population, List<ISolution<T>> forbiddenSolutions) {
		List<WheelObject<T>> wheelObjects = createWheelObjectsList(population, forbiddenSolutions);
		return updateEffectiveFitnesses(wheelObjects);
	}

	private List<WheelObject<T>> createWheelObjectsList(IPopulation<T> population,
			List<ISolution<T>> forbiddenSolutions) {
		
		List<WheelObject<T>> wheelObjects = new ArrayList<>();
		
		for (ISolution<T> solution : population.getSoultions()) {
			if (forbiddenSolutions != null && forbiddenSolutions.contains(solution)) {
				continue;
			}
			
			wheelObjects.add(new WheelObject<>(solution));
		}
		
		return wheelObjects;
	}
	
	private List<WheelObject<T>> updateEffectiveFitnesses(List<WheelObject<T>> wheelObjects) {
		double minFitness = findMinimalFitness(wheelObjects);

		return wheelObjects
				.stream()
				.peek(wo -> wo.setEffectiveFitness(wo.getRealFitness() - minFitness))
				.collect(Collectors.toList());
	}

	private double findMinimalFitness(List<WheelObject<T>> wheelObjects) {
		return wheelObjects
				.stream()
				.min((wo1, wo2) -> Double.compare(wo1.getRealFitness(), wo2.getRealFitness()))
				.get()
				.getRealFitness();
	}

	private ISolution<T> spinWheel(List<WheelObject<T>> wheelObjects) {
		RouletteWheel<T> rouletteWheel = new RouletteWheel<>(wheelObjects);
		return rouletteWheel.getWinningWheelObject().getSolution();
	}
	
	private static class RouletteWheel<T> {
		
		private List<WheelObject<T>> wheelObjects;
		private double[] wheelProbabilities;

		public RouletteWheel(List<WheelObject<T>> wheelObjects) {
			this.wheelObjects = wheelObjects;
			wheelProbabilities = new double[wheelObjects.size()];
			
			initProbabilities();
		}

		private void initProbabilities() {
			double efFitnessSum = wheelObjects
									.stream()
									.mapToDouble(wo -> wo.getEffectiveFitness())
									.sum();
			
			for (int i = 0; i < wheelProbabilities.length; i++) {
				wheelProbabilities[i] = wheelObjects.get(i).getEffectiveFitness() / efFitnessSum;
			}
		}
		
		public WheelObject<T> getWinningWheelObject() {
			Random rand = new Random(System.currentTimeMillis());
			
			double value = rand.nextDouble();
			double probabilitiesSum = 0;
			
			for (int i = 0; i < wheelProbabilities.length; i++) {
				probabilitiesSum += wheelProbabilities[i];
				
				if (value < probabilitiesSum) {
					return wheelObjects.get(i);
				}
			}
			
			return wheelObjects.get(wheelObjects.size() - 1);
		}
	}
	
	private static class WheelObject<T> {
		
		private ISolution<T> solution;
		private double effectiveFitness;
		
		public WheelObject(ISolution<T> solution) {
			this.solution = solution;
		}
		
		public ISolution<T> getSolution() {
			return solution;
		}
		
		public double getRealFitness() {
			return solution.getFitness();
		}
		
		public double getEffectiveFitness() {
			return effectiveFitness;
		}
		
		public void setEffectiveFitness(double effectiveFitness) {
			this.effectiveFitness = effectiveFitness;
		}
	}
}