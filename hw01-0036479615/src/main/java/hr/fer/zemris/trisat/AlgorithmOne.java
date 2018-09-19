package hr.fer.zemris.trisat;

public class AlgorithmOne extends AbstractAlgorithm {

	@Override
	public void runAlgorithm() {
		MutableBitVector iteratingSolution = new MutableBitVector(new boolean[satFormula.getNumberOfVariables()]);
		
		while (true) {
			if (satFormula.isSatisfied(iteratingSolution)) {
				System.out.println(iteratingSolution);
				
				if (solution == null) {
					solution = iteratingSolution.copy();
					foundSolution = true;
				}
			}
			
			generateSuccessor(iteratingSolution);
			
			if (finishedIterating(iteratingSolution)) {
				break;
			}
		}
	}
	
	private void generateSuccessor(MutableBitVector mbVector) {
		for (int i = mbVector.getSize() - 1; i >= 0; i--) {
			mbVector.set(i, !mbVector.get(i));
			
			if (mbVector.get(i) == true) {
				break;
			}
		}
	}
	
	private boolean finishedIterating(MutableBitVector mbVector) {
		for (int i = 0; i < mbVector.getSize(); i++) {
			if (mbVector.get(i) == true) {
				return false;
			}
		}
		
		return true;
	}
}
