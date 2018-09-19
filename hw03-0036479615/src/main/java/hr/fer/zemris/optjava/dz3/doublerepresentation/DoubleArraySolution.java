package hr.fer.zemris.optjava.dz3.doublerepresentation;

import java.util.Arrays;
import java.util.Random;

public class DoubleArraySolution {

	private double[] values;
	private static final double MAX_INIT = 5d;
	private static final double MIN_INIT = -5d;
	
	public DoubleArraySolution(double[] values) {
		this.values = values;
	}
	
	public DoubleArraySolution(int variables) {
		values = new double[variables];
		initValues();
	}

	private void initValues() {
		Random rand = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < values.length; i++) {
			values[i] = (rand.nextDouble() - 0.5) * (MAX_INIT - MIN_INIT);
		}
	}

	public double[] getValues() {
		return values;
	}
	
	public DoubleArraySolution duplicate() {
		return new DoubleArraySolution(Arrays.copyOf(values, values.length));
	}
	
	public void randomize(Random random, double[] deltas, double[] vector) {
		boolean changed = false;
		
		for (int i = 0; i < values.length; i++) {
			if (random.nextDouble() < deltas[i]) {
				continue;
			}
			
			values[i] += vector[i];
			changed = true;
		}
		
		if (!changed) {
			int position = random.nextInt(values.length);
			values[position] += vector[position];
		}
	}
}
