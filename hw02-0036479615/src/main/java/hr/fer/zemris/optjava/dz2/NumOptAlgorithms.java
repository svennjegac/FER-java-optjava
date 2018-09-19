package hr.fer.zemris.optjava.dz2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Jama.Matrix;

public class NumOptAlgorithms {

	private static double GRADIENT_THRESHOLD = 0.00000000000000000001;
	private static double BISECTION_THRESHOLD = 0.000000000000001;
	
	public static List<double[]> gradientDescent(IFunction function, double[] point, int iterations) {
		if (point == null) {
			point = function.getStartingSolution();
		}
		
		List<double[]> points = new ArrayList<>();
		
		for (int i = 0; i < iterations; i++) {
			points.add(point);
			
			if (Math.abs(function.getValue(point)) < GRADIENT_THRESHOLD) {
				return points;
			}
			
//			if (i % 200 == 0) {
//				System.out.println(i + "---" + function.getValue(point) + " -> " + Util.getPointsAsText(Arrays.asList(point)));
//			}
			
			double[] vector = function.getNegatedGradient(point);
			double lambda = bisectionMethod(function, point, vector);
			point = ThetaFunction.calculatePoint(point, vector, lambda);
		}
		
		return points;
	}
	
//	public static double bisectionMethod(IFunction function, double[] point, double[] vector) {
//		double lambda = 0.0000000000000000000000001;
//		ThetaFunction thetaFunction = new ThetaFunction(function, point, vector);
//		
//		while (true) {
//			double thetaGradientValue = thetaFunction.getGradientValue(lambda);
//			
//			if (Math.abs(thetaGradientValue) < BISECTION_THRESHOLD || thetaGradientValue > 0) {
//				return lambda;
//			}
//			
//			lambda *= 2;
//		}
//	}
	
	public static double bisectionMethod(IFunction function, double[] point, double[] vector) {
		double lambdaLower = 0;
		double lambdaUpper = 1;
		ThetaFunction thetaFunction = new ThetaFunction(function, point, vector);
		
		while (thetaFunction.getGradientValue(lambdaUpper) < 0) {
			lambdaUpper *= 2;
		}
		
		double oldThetaGradientValue = 0;
		int counter = 0;
		while (true) {
			counter++;
			double lambda = (lambdaLower + lambdaUpper) / 2;
			double thetaGradientValue = thetaFunction.getGradientValue(lambda);
			
			
			if (thetaGradientValue > 0) {
				lambdaUpper = lambda;
			} else if (thetaGradientValue < 0) {
				lambdaLower = lambda;
			}
			
			if (Math.abs(thetaGradientValue) < BISECTION_THRESHOLD) {
				return lambda;
			}
			
			if (oldThetaGradientValue == thetaGradientValue) {
				return lambda;
			}
			
			if (counter == 10000) {
				return lambda;
			}
			
			oldThetaGradientValue = thetaGradientValue;
		}
	}

	public static List<double[]> newtonsMethod(IHFunction function, double[] point, int iterations) {
		if (point == null) {
			point = function.getStartingSolution();
		}
		
		List<double[]> points = new ArrayList<>();
		
		for (int i = 0; i < iterations; i++) {
			points.add(point);
			
			if (Math.abs(function.getGradientValue(point)) < GRADIENT_THRESHOLD) {
				return points;
			}
			
			Matrix hessianMatrix = new Matrix(function.getHessianMatrix(point));
			Matrix hessianInverseMatrix = hessianMatrix.inverse();
			Matrix gradientMatrix = new Matrix(function.getGradient(point), function.getNumberOfVariables());
			
			Matrix matrixVector = hessianInverseMatrix.times(-1d).times(gradientMatrix);
			double[] vector = matrixVector.getColumnPackedCopy();
			
			point = ThetaFunction.calculatePoint(point, vector, 1);
		}
		
		return points;
	}
	
	private static class ThetaFunction {

		private IFunction function;
		private double[] point;
		private double[] vector;
		
		public ThetaFunction(IFunction function, double[] point, double[] vector) {
			this.function = function;
			this.point = point;
			this.vector = vector;
		}
		
		public double getGradientValue(double lambda) {
			double[] realPoint = calculatePoint(point, vector, lambda);
			double[] gradient = function.getGradient(realPoint);
			double result = 0;
			
			for (int i = 0; i < gradient.length; i++) {
				result += gradient[i] * vector[i];
			}
			
			return result;
		}

		private static double[] calculatePoint(double[] point, double[] vector, double lambda) {
			double[] realPoint = new double[point.length];
			
			for (int i = 0; i < point.length; i++) {
				realPoint[i] = point[i] + lambda * vector[i];
			}
			
			return realPoint;
		}
	}
}
