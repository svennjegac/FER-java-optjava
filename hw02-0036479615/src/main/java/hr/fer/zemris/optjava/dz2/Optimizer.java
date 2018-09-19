package hr.fer.zemris.optjava.dz2;

import java.util.List;

public interface Optimizer {

	public List<double[]> optimize(double[] point, int iterations);
}
