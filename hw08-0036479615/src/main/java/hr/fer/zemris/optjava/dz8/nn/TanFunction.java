package hr.fer.zemris.optjava.dz8.nn;

public class TanFunction implements IActivationFunction {

	private SigmoidFunction sigm;
	
	public TanFunction() {
		sigm = new SigmoidFunction();
	}
	
	@Override
	public double valueAt(double input) {
		return 2.0 * sigm.valueAt(input) - 1;
	}
}
