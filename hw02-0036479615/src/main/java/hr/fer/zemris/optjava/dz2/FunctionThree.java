package hr.fer.zemris.optjava.dz2;

import java.util.List;

import javax.naming.OperationNotSupportedException;

public class FunctionThree extends AbstractFunction {

	protected List<double[]> problemRows;
	
	@Override
	public void setProblem(List<double[]> problemRows) throws OperationNotSupportedException {
		this.problemRows = problemRows;
	}
	
	@Override
	public int getNumberOfVariables() {
		return problemRows.get(0).length - 1;
	}

	@Override
	public double getValue(double[] point) {
		double error = 0;
		
		for (double[] row : problemRows) {
			
			double myFunctionValue = 0;
			for (int i = 0; i < row.length - 1; i++) {
				myFunctionValue += row[i] * point[i];
			}
			
			error += Math.pow(row[row.length - 1] - myFunctionValue, 2);
		}
		
		return error;
	}

	@Override
	public double[] getGradient(double[] point) {
		double[] gradient = new double[problemRows.get(0).length - 1];
		
		for (int i = 0; i < gradient.length; i++) {
			double gradValue = 0;
			
			for (double[] row : problemRows) {
				double rowSum = 0;
				
				for (int j = 0; j < row.length - 1; j++) {
					rowSum += row[j] * point[j];
				}
				
				rowSum -= row[row.length - 1];
				rowSum *= 2;
				rowSum *= row[i];
				gradValue += rowSum;
			}
			
			gradient[i] = gradValue;
		}
		
		return gradient;
	}
}
