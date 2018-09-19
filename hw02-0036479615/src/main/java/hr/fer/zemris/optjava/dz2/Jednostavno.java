package hr.fer.zemris.optjava.dz2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jednostavno {
	
	private static final Map<String, Optimizer> OPT_ALGORITHMS;
	static {
		OPT_ALGORITHMS = new HashMap<>();
		OPT_ALGORITHMS.put("1a", (point, iterations) -> NumOptAlgorithms.gradientDescent(new FunctionOne(), point, iterations));
		OPT_ALGORITHMS.put("1b", (point, iterations) -> NumOptAlgorithms.newtonsMethod(new FunctionOneNewton(), point, iterations));
		OPT_ALGORITHMS.put("2a", (point, iterations) -> NumOptAlgorithms.gradientDescent(new FunctionTwo(), point, iterations));
		OPT_ALGORITHMS.put("2b", (point, iterations) -> NumOptAlgorithms.newtonsMethod(new FunctionTwoNewton(), point, iterations));
	}
	
	private static final int EDGE = 900;
	
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 4) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		String algorithm = args[0];
		int iterations = -1;
		double[] startPoint = null;
		
		try {
			iterations = Integer.parseInt(args[1]);
			
			if (args.length == 4) {
				startPoint = new double[2];
				startPoint[0] = Double.parseDouble(args[2]);
				startPoint[1] = Double.parseDouble(args[3]);
			}
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Argumenti nisu valjani.");
			return;
		}
		
		Optimizer optimizer = OPT_ALGORITHMS.get(algorithm);
		if (optimizer == null) {
			System.out.println("Naveli ste nepostojeci optimizacijski algoritam.");
			return;
		}
		
		List<double[]> points = optimizer.optimize(startPoint, iterations);
		Util.drawTrajectory(EDGE, points, algorithm);
		System.out.println(Util.getPointsAsText(points));
	}
}
