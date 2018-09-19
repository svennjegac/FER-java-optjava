package hr.fer.zemris.optjava.dz8;

public class ExponentialCrossover extends AbstractCrossover {

	public ExponentialCrossover(double cR) {
		super(cR);
	}
	
	@Override
	public void crossover(double[] wTarget, double[] wMutant, double[] wTrail) {
		int index = rand.nextInt(wTarget.length);
		boolean failed = false;
		
		for (int i = 0; i < wTarget.length; i++) {
			if (i == 0) {
				wTrail[index] = wMutant[index];
			} else {
				if (rand.nextDouble() < CR && !failed) {
					wTrail[index] = wMutant[index];
				} else {
					wTrail[index] = wMutant[index];
					failed = true;
				}
			}
			
			index += 1;
			index %= wTarget.length;
		}
	}
}
