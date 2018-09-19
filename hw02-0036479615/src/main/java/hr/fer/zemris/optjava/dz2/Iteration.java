package hr.fer.zemris.optjava.dz2;

import java.util.Arrays;
import java.util.List;

import javax.naming.OperationNotSupportedException;

public class Iteration {

	public static void main(String[] args) {
		
		double e = Double.MAX_VALUE;
		double[] pt = null;
		
		for (int i = 0; i < 1; i++) {
			System.out.println(i);
			AbstractOptimizer opt = new AbstractOptimizer() {
				
				@Override
				public List<double[]> optimize(double[] point, int iterations) {
					setFunction(new FunctionFour());
					try {
						getFunction().setProblem(getProblemRows());
					} catch (OperationNotSupportedException e) {
					}
					
					return NumOptAlgorithms.gradientDescent(getFunction(), point, iterations);
				}
			};
			
			opt.setProblemRows(Util.parseProblem("02-zad-prijenosna.txt"));
			List<double[]> points = opt.optimize(null, 30000);
			//new double[] { 6.99, -3.001, 2.028, 0.0996, -3.02, 3.05 }
			double err = opt.getError(points.get(points.size() - 1));
			if (err < e) {
				e = err;
				pt = points.get(points.size() - 1);
			}
		}
		
		System.out.println("==================================");
		System.out.println(e);
		System.out.println(Util.getPointsAsText(Arrays.asList(pt)));
	}
}
