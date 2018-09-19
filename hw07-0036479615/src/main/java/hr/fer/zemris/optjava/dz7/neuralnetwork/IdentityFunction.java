package hr.fer.zemris.optjava.dz7.neuralnetwork;

public class IdentityFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return input;
	}
	
	@Override
	public IActivationFunction copy() {
		return new IdentityFunction();
	}
}
