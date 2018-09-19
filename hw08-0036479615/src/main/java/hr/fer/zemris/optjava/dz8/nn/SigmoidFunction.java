package hr.fer.zemris.optjava.dz8.nn;

public class SigmoidFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return 1d / (1d + Math.pow(Math.E, -input));
	}
}
