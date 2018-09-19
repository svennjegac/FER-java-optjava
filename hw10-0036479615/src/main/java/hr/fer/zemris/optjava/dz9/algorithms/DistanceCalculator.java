package hr.fer.zemris.optjava.dz9.algorithms;

public class DistanceCalculator {

	public static double getSharedDistance(double distance, double sigmaShare, double alpha) {
		if (distance >= sigmaShare) {
			return 0;
		}
		
		return 1d - Math.pow(distance / sigmaShare, alpha);
	}
	
	public static double getDistance(double[] x1, double[] x2, double[] maxXks, double[] minXks) {
		double sum = 0;
		
		for (int i = 0; i < x1.length; i++) {
			sum += Math.pow((x1[i] - x2[i]) / (maxXks[i] - minXks[i]), 2);
		}
		
		return Math.sqrt(sum);
	}
}
