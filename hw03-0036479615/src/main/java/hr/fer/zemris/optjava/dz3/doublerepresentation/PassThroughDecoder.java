package hr.fer.zemris.optjava.dz3.doublerepresentation;

import hr.fer.zemris.optjava.dz3.IDecoder;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

	@Override
	public double[] decode(DoubleArraySolution solution) {
		return solution.getValues();
	}
}
