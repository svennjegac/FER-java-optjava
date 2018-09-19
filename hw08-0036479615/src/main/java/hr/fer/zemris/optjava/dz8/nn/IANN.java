package hr.fer.zemris.optjava.dz8.nn;

public interface IANN {

	public double[] calculateOutput(double[] input);
	
	public int getNumberOfInputs();
	public int getNumberOfOutputs();
	
	public int getWeightsCount();
	public double[] getWeights();
	public void setWeights(double[] weights);
	
	public IANN copy();
	public void reset();
}
