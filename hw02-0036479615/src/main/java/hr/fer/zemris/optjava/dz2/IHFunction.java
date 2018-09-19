package hr.fer.zemris.optjava.dz2;

public interface IHFunction extends IFunction {

	public double[][] getHessianMatrix(double[] point);
}
