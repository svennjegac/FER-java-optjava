package hr.fer.zemris.optjava.dz2;

public class FunctionTwoNewton extends FunctionTwo implements IHFunction {

	@Override
	public double[][] getHessianMatrix(double[] point) {
		double[][] matrix = new double[2][2];
		
		matrix[0][0] = 2;
		matrix[0][1] = 0;
		matrix[1][0] = 0;
		matrix[1][1] = 20;
		
		return matrix;
	}

}
