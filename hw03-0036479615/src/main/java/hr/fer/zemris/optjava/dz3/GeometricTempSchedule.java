package hr.fer.zemris.optjava.dz3;

public class GeometricTempSchedule implements ITempSchedule {

	private double alpha;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	private int counter = 0;
	
	public GeometricTempSchedule(double tInitial, double tEnd, int innerLimit, int outerLimit) {
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	
		alpha = Math.pow(tEnd / tInitial, (double) 1 / (outerLimit - 1));
		tCurrent = tInitial / alpha;
	}

	@Override
	public double getNextTemperature() {
		tCurrent *= alpha;
		return tCurrent;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}
}
