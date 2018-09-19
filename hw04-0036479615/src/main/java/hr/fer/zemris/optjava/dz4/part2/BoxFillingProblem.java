package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.util.List;

import hr.fer.zemris.optjava.dz4.algorithms.genetic.IProblem;
import hr.fer.zemris.optjava.dz4.algorithms.genetic.ISolution;


public class BoxFillingProblem implements IProblem<List<Box>> {

	private List<Stick> stickList;
	
	public BoxFillingProblem(String file) {
		try {
			stickList = Util.parseProblem(file);
			Box.MAX_HEIGHT = Integer.parseInt(file.split("-")[1]);
			Box.GOOD_FILLING_THRESH = 17;
		} catch (IOException | NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("Pogreška pri čitanju datoteke.");
		}
	}
	
	@Override
	public double getFitness(ISolution<List<Box>> solution) {
		return - getValue(solution);
	}

	@Override
	public double getValue(ISolution<List<Box>> solution) {
		return (double) solution.getRepresentation().size();
	}

	@Override
	public ISolution<List<Box>> generateRandomSolution() {
		Container container = new Container(BoxFactory.createRandomBoxPerStick(stickList));
		container.setFitness(getFitness(container));
		container.setValue(getValue(container));
		return container;
	}
}
