package hr.fer.zemris.trisat;

public class AlgorithmFour extends AbstractAlgorithm {

	private static final Integer MAX_TRIES = 1000;
	private static final Integer MAX_FLIPS = 100_000;
	
	@Override
	public void runAlgorithm() {
		AlgorithmTwo alg2 = new AlgorithmTwo();
		alg2.setSATFormula(satFormula);
		
		for (int i = 0; i < MAX_TRIES; i++) {
			alg2.runAlgorithm(MAX_FLIPS);
			
			if (alg2.foundSolution()) {
				this.solution = alg2.getSolution();
				this.foundSolution = alg2.foundSolution();
				break;
			}
		}
	}
}
