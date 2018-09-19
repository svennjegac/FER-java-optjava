package hr.fer.zemris.optjava.dz8.nn;

public class IdentityFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return input;
	}
}
