package hr.fer.zemris.optjava.dz8;

public class UniformCrossover extends AbstractCrossover {

	public UniformCrossover(double cR) {
		super(cR);
	}

	@Override
	public void crossover(double[] wTarget, double[] wMutant, double[] wTrail) {
		int index = rand.nextInt(wTarget.length);
		
		for (int i = 0; i < wTarget.length; i++) {
			if (i == 0) {
				wTrail[index] = wMutant[index];
			} else {
				wTrail[index] = rand.nextDouble() < CR ? wMutant[index] : wTarget[index];
			}
			
			index += 1;
			index %= wTarget.length;
		}
	}
}
