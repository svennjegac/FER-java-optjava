package hr.fer.zemris.optjava.dz2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

public class Prijenosna {
	
	private static final Map<String, AbstractOptimizer> OPT_ALGORITHMS;
	static {
		OPT_ALGORITHMS = new HashMap<>();
		OPT_ALGORITHMS.put("grad", new AbstractOptimizer() {
			
			@Override
			public List<double[]> optimize(double[] point, int iterations) {
				setFunction(new FunctionFour());
				try {
					getFunction().setProblem(getProblemRows());
				} catch (OperationNotSupportedException e) {
				}
				
				return NumOptAlgorithms.gradientDescent(getFunction(), point, iterations);
			}
		});
		
		OPT_ALGORITHMS.put("newton", new AbstractOptimizer() {
			
			@Override
			public List<double[]> optimize(double[] point, int iterations) {
				setFunction(new FunctionFourNewton());
				try {
					getFunction().setProblem(getProblemRows());
				} catch (OperationNotSupportedException e) {
				}
				
				return NumOptAlgorithms.newtonsMethod((IHFunction) getFunction(), point, iterations);
			}
		});
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		String algorithm = args[0];
		int iterations = -1;
		String fileName = args[2];
		
		try {
			iterations = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Iteracije nisu valjane.");
			return;
		}
		
		List<double[]> problemRows = Util.parseProblem(fileName);
		if (problemRows == null) {
			System.out.println("Greška prilikom čitanja datoteke problema.");
			return;
		}
		
		AbstractOptimizer optimizer = OPT_ALGORITHMS.get(algorithm);
		optimizer.setProblemRows(problemRows);
		List<double[]> points = optimizer.optimize(null, iterations);
		System.out.println(Util.getPointsAsText(points));
		System.out.println("Iznos pogreške je: " + optimizer.getError(points.get(points.size() - 1)));
	}
}
