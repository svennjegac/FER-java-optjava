package hr.fer.zemris.optjava.dz9.algorithms;

public class CrowdingElement<T> {

	private SolutionFitnessesPair<T> solFitPair;
	private int index;
	private double crowdingDistance;
	
	public CrowdingElement(SolutionFitnessesPair<T> solFitPair, int index) {
		this.solFitPair = solFitPair;
		this.index = index;
		this.crowdingDistance = 0;
	}
	
	public SolutionFitnessesPair<T> getSolFitPair() {
		return solFitPair;
	}
	
	public int getIndex() {
		return index;
	}
	
	public double getCrowdingDistance() {
		return crowdingDistance;
	}
	
	public void setCrowdingDistance(double crowdingDistance) {
		this.crowdingDistance = crowdingDistance;
	}
}
