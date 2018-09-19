package hr.fer.zemris.optjava.dz3.doublerepresentation;


public class DoubleArrayUnifNeighborhood extends DoubleArrayNeighborhood {

	public DoubleArrayUnifNeighborhood(double[] deltas) {
		super(deltas);
	}
	
	@Override
	protected double generateVectorComponenet() {
		return (random.nextDouble() * 2) - 1;
	}
}
