package hr.fer.zemris.optjava.dz2;


public class FunctionOne extends AbstractFunction {
	
	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getValue(double[] point) {
		return point[0] * point[0] + Math.pow(point[1] - 1, 2);
	}

	@Override
	public double[] getGradient(double[] point) {
		double[] gradient = new double[2];
		
		gradient[0] = 2 * point[0];
		gradient[1] = 2 * (point[1] - 1);
		
		return gradient;
	}
}
