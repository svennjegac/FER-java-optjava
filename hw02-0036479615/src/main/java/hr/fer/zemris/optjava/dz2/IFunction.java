package hr.fer.zemris.optjava.dz2;

import java.util.List;

import javax.naming.OperationNotSupportedException;

public interface IFunction {
	
	public void setProblem(List<double[]> problemRows) throws OperationNotSupportedException;
	public double[] getStartingSolution();
	public int getNumberOfVariables();
	public double getValue(double[] point);
	public double getGradientValue(double[] point);
	public double[] getGradient(double[] point);
	public double[] getNegatedGradient(double[] point);
}
