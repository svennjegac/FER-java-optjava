package hr.fer.zemris.trisat;

import java.util.List;

public interface Algorithm {

	public void runAlgorithm();
	public boolean foundSolution();
	public BitVector getSolution();
	public void setSATFormula(SATFormula satFormula);
}
