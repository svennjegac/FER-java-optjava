package hr.fer.zemris.optjava.dz3.bitrepresentation;

import hr.fer.zemris.optjava.dz3.IDecoder;

public class NaturalBinaryDecoder implements IDecoder<BitVectorSolution> {

	private static final double MAX_INTERVAL = 10_000;
	private static final double MIN_INTERVAL = -10_000;
	
	@Override
	public double[] decode(BitVectorSolution solution) {
		double[] numberRepresentation = decodeBitsToRoundedDoubles(solution);
		
		scaleValuesToMinMaxInterval(numberRepresentation, solution);
		
		
		return numberRepresentation;
	}
	
	private double[] decodeBitsToRoundedDoubles(BitVectorSolution solution) {
		double[] numberRepresentation = new double[solution.getNumberOfVariables()];
		int bitsPerNumber = solution.getBits().length / solution.getNumberOfVariables();
		
		for (int i = 0, vars = solution.getNumberOfVariables(); i < vars; i++) {
			double variable = 0;
			
			for (int j = 0; j < bitsPerNumber - 1; j++) {
				byte b = solution.getBits()[i * bitsPerNumber + bitsPerNumber - 1 - j];
				variable += Math.pow(2, (double) (b * j));
			}
			
			if (solution.getBits()[i * bitsPerNumber] == 1) {
				variable = - variable;
			}
			
			numberRepresentation[i] = variable;
		}
		
		return numberRepresentation;
	}
	
	private void scaleValuesToMinMaxInterval(double[] numberRepresentation, BitVectorSolution solution) {
		int bitsPerNumber = solution.getBits().length / solution.getNumberOfVariables();
		double maxValue = Math.pow(2, (double) (bitsPerNumber - 1));
		double minValue = - maxValue;
		
		for (int i = 0; i < numberRepresentation.length; i++) {
			double scaler;
			
			if (numberRepresentation[i] > 0) {
				scaler = MAX_INTERVAL / (maxValue - 1);
			} else {
				scaler = MIN_INTERVAL / (minValue - 1);
			}
			
			numberRepresentation[i] *= scaler;
		}
	}
}
