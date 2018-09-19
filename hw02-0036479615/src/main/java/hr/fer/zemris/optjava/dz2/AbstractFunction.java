package hr.fer.zemris.optjava.dz2;

import java.util.List;
import java.util.Random;

import javax.naming.OperationNotSupportedException;

public abstract class AbstractFunction implements IFunction {
	
	private static final double LOW_BOUND = -5d;
	private static final double HIGH_BOUND = 5d;

	@Override
	public void setProblem(List<double[]> problemRows) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
	
	@Override
	public double[] getStartingSolution() {
		Random rand = new Random(System.currentTimeMillis());
		double[] solution = new double[getNumberOfVariables()];
		
		for (int i = 0; i < solution.length; i++) {
			solution[i] = LOW_BOUND + rand.nextDouble() * (HIGH_BOUND - LOW_BOUND);
		}
		
		return solution;
	}
	
	@Override
	public double getGradientValue(double[] point) {
		double sum = 0;
		double[] gradient = getGradient(point);
		
		for (double grad : gradient) {
			sum += grad;
		}
		
		return sum;
	}
	
	@Override
	public double[] getNegatedGradient(double[] point) {
		double[] gradient = getGradient(point);
		
		for (int i = 0; i < gradient.length; i++) {
			gradient[i] = -gradient[i];
		}
		
		return gradient;
	}
}
