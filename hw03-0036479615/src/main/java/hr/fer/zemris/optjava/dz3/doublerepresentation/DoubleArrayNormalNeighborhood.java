package hr.fer.zemris.optjava.dz3.doublerepresentation;

public class DoubleArrayNormalNeighborhood extends DoubleArrayNeighborhood {

	public DoubleArrayNormalNeighborhood(double[] deltas) {
		super(deltas);
	}
	
	@Override
	protected double generateVectorComponenet() {
		return random.nextGaussian() * 0.1;
	}
}
