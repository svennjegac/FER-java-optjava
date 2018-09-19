package hr.fer.zemris.optjava.dz3;

import java.util.List;

public class FunctionFour implements IFunction {

	private List<double[]> problemRows;
	
	public FunctionFour(List<double[]> problemRows) {
		this.problemRows = problemRows;
	}

	@Override
	public double valueAt(double[] point) {
		double error = 0;
		
		for (double[] row : problemRows) {
			double myEstimation = calculateValueInPointForRow(point, row);
			error += Math.pow(row[row.length - 1] - myEstimation, 2);
		}
		
		return error;
	}
	
	private double calculateValueInPointForRow(double[] point, double[] row) {
		double result = 0;
		
		result += point[0] * row[0];
		result += point[1] * Math.pow(row[0], 3) * row[1];
		result += point[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
		result += point[5] * row[3] * Math.pow(row[4], 2);
		
		return result;
	}
}
