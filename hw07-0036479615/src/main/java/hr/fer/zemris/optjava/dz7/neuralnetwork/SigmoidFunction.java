package hr.fer.zemris.optjava.dz7.neuralnetwork;

public class SigmoidFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return 1d / (1d + Math.pow(Math.E, -input));
	}
	
	@Override
	public IActivationFunction copy() {
		return new SigmoidFunction();
	}
}
