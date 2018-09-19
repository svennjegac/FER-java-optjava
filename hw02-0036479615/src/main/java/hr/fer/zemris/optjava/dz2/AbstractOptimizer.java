package hr.fer.zemris.optjava.dz2;

import java.util.List;

import javax.naming.OperationNotSupportedException;

public abstract class AbstractOptimizer implements Optimizer {

	private List<double[]> problemRows;
	private IFunction function;
	
	public void setProblemRows(List<double[]> problemRows) {
		this.problemRows = problemRows;
	}
	
	public List<double[]> getProblemRows() {
		return problemRows;
	}
	
	public void setFunction(IFunction function) {
		this.function = function;
	}
	
	public IFunction getFunction() {
		return function;
	}
	
	public double getError(double[] point) {
		return function.getValue(point);
	}
}
