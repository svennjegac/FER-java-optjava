package hr.fer.zemris.optjava.dz8.nn;

public class Weight implements Cloneable {

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
