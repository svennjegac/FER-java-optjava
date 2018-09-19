package hr.fer.zemris.optjava.dz2;

import java.util.List;
import javax.naming.OperationNotSupportedException;

public class FunctionFour extends AbstractFunction {

	protected List<double[]> problemRows;
	
	@Override
	public void setProblem(List<double[]> problemRows) throws OperationNotSupportedException {
		this.problemRows = problemRows;
	}
	
	@Override
	public int getNumberOfVariables() {
		return 6;
	}

	@Override
	public double getValue(double[] point) {
		double error = 0;
		
		for (double[] row : problemRows) {
			double myEstimation = calculateValueInPointForExample(point, row);
			error += Math.pow(row[row.length - 1] - myEstimation, 2);
		}
		
		return error;
	}
	
	protected double calculateValueInPointForExample(double[] point, double[] example) {
		double result = 0;
		
		result += point[0] * example[0];
		result += point[1] * Math.pow(example[0], 3) * example[1];
		result += point[2] * Math.pow(Math.E, point[3] * example[2]) * (1 + Math.cos(point[4] * example[3]));
		result += point[5] * example[3] * Math.pow(example[4], 2);
		
		return result;
	}

	@Override
	public double[] getGradient(double[] point) {
		double[] gradient = new double[6];
		
		for (double[] row : problemRows) {
			gradient[0] += calcFirstPart(point, row) * row[0];
			gradient[1] += calcFirstPart(point, row) * Math.pow(row[0], 3) * row[1];
			gradient[2] += calcFirstPart(point, row) * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			gradient[3] += calcFirstPart(point, row) * point[2] * row[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			gradient[4] += calcFirstPart(point, row) * (-1) * point[2] * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]);
			gradient[5] += calcFirstPart(point, row) * row[3] * Math.pow(row[4], 2);
		}
		
		return gradient;
	}
	
	private double calcFirstPart(double[] point, double[] row) {
		return 2 * (calculateValueInPointForExample(point, row) - row[row.length - 1]);
	}
}
