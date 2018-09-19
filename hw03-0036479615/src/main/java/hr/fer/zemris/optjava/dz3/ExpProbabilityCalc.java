package hr.fer.zemris.optjava.dz3;

public class ExpProbabilityCalc implements IProbabilityCalculator {

	@Override
	public double calculateProbability(double delta, double temperature) {
		return Math.pow(Math.E, - delta / temperature);
	}
}
