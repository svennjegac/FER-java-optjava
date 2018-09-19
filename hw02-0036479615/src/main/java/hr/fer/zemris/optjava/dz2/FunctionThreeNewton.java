package hr.fer.zemris.optjava.dz2;

public class FunctionThreeNewton extends FunctionThree implements IHFunction {

	@Override
	public double[][] getHessianMatrix(double[] point) {
		double[][] matrix = new double[point.length][point.length];
		
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point.length; j++) {
				for (double[] row : problemRows) {
					matrix[i][j] += 2 * row[i] * row[j];
				}
			}
		}
		
		return matrix;
	}
}
