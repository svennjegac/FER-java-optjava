package hr.fer.zemris.optjava.dz8.nn;

public class LearningSample {

	private double[] input;
	private double[] expectedOutput;
	
	public LearningSample(double[] input, double[] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public double[] getInput() {
		return input;
	}
	
	public double[] getExpectedOutput() {
		return expectedOutput;
	}
}
