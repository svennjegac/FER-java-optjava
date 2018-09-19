package hr.fer.zemris.optjava.dz3;

import java.util.List;
import hr.fer.zemris.optjava.dz3.bitrepresentation.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.bitrepresentation.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.bitrepresentation.NaturalBinaryNeighborhood;
import hr.fer.zemris.optjava.dz3.doublerepresentation.DoubleArrayNormalNeighborhood;
import hr.fer.zemris.optjava.dz3.doublerepresentation.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.doublerepresentation.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.doublerepresentation.PassThroughDecoder;

public class RegresijaSustava {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		String fileName = args[0];
		String algorithm = args[1];
		
		List<double[]> problemRows = Util.parseProblem(fileName);
		if (problemRows == null) {
			System.out.println("Ne uspijevam pročitati datoteku sa problemom.");
			return;
		}
		
		IFunction function = new FunctionFour(problemRows);
		double[] solution = findSolution(algorithm, function);
		if (solution == null) {
			System.out.println("Pogrešno zadan algoritam.");
			return;
		}
		
		printSolutionStats(function, solution);
	}

	private static double[] findSolution(String algorithm, IFunction function) {
		if (algorithm.equals("decimal")) {
			IOptAlgorithm<DoubleArraySolution> decimalAlgorithm = new SimulatedAnnealing<DoubleArraySolution>(
					new PassThroughDecoder(),
					new DoubleArrayNormalNeighborhood(new double[]{ 0.2, 0.2, 0.2, 0.2, 0.1, 0.2 }),
					new DoubleArraySolution(6),
					function,
					new GeometricTempSchedule(3_500d, 0.1d, 600, 500),
					true,
					new ExpProbabilityCalc());
			
			return decimalAlgorithm.run().getValues();
			
		} else if (algorithm.matches("binary:[0-9]+")) {
			int bitsPerVariable = Integer.parseInt(algorithm.split(":")[1]);
			if (bitsPerVariable < 1) {
				return null;
			}
			
			NaturalBinaryDecoder binaryDecoder = new NaturalBinaryDecoder();
			
			IOptAlgorithm<BitVectorSolution> binaryAlgorithm = new SimulatedAnnealing<BitVectorSolution>(
					binaryDecoder,
					new NaturalBinaryNeighborhood(bitsPerVariable),
					new BitVectorSolution(new byte[6 * bitsPerVariable], 6),
					function,
					new GeometricTempSchedule(20_000d, 0.1d, 400, 400),
					true,
					new ExpProbabilityCalc());
			
			return binaryDecoder.decode(binaryAlgorithm.run());
			
		} else {
			return null;
		}
	}
	
	private static void printSolutionStats(IFunction function, double[] solution) {
		StringBuilder sb = new StringBuilder();
		sb.append("==========================" + System.lineSeparator());
		
		for (double value : solution) {
			sb.append(value + ", ");
		}
		
		sb.append(System.lineSeparator());
		sb.append("Function value: " + function.valueAt(solution));
		System.out.println(sb.toString());
	}
}
