package hr.fer.zemris.optjava.dz2;


public class FunctionTwo extends AbstractFunction {

	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getValue(double[] point) {
		return Math.pow(point[0] - 1, 2) + 10 * Math.pow(point[1] - 2, 2);
	}

	@Override
	public double[] getGradient(double[] point) {
		double[] gradient = new double[2];
		
		gradient[0] = 2 * (point[0] - 1);
		gradient[1] = 20 * (point[1] - 2);
		
		return gradient;
	}
}
