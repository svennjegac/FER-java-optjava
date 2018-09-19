package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.dz6.algorithms.ant.IAnt;
import hr.fer.zemris.optjava.dz6.algorithms.ant.IAntSupplier;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;

public class AntSupplier implements IAntSupplier<int[]> {

	@Override
	public IAnt<int[]> newAnt(ISolution<int[]> solution, int startPoint) {
		return new Ant(solution, startPoint);
	}
}
