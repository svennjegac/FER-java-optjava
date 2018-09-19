package hr.fer.zemris.optjava.dz7.neuralnetwork;

public class Weight {

	private double value;

	public Weight(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Weight copy() {
		return new Weight(value);
	}
}
