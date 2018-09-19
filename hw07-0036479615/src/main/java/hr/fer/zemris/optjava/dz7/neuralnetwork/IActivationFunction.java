package hr.fer.zemris.optjava.dz7.neuralnetwork;

public interface IActivationFunction {

	public double valueAt(double input);
	public IActivationFunction copy();
}
