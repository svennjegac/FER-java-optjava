package hr.fer.zemris.optjava.dz7.algorithms.swarm;

public class InertionScaler implements IInertionScaler {

	private double minInertion;
	private double maxInertion;
	private int maxTime;
	
	public InertionScaler(double minInertion, double maxInertion, int maxTime) {
		this.minInertion = minInertion;
		this.maxInertion = maxInertion;
		this.maxTime = maxTime;
	}

	@Override
	public double getInertion(int iteration) {
		return (double) (maxTime - iteration) / maxTime * (maxInertion - minInertion) + minInertion;
	}
}
