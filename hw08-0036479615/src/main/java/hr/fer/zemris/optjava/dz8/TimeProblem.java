package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.algorithms.IProblem;
import hr.fer.zemris.optjava.dz8.algorithms.ISolution;
import hr.fer.zemris.optjava.dz8.nn.IANN;
import hr.fer.zemris.optjava.dz8.nn.IDataset;
import hr.fer.zemris.optjava.dz8.nn.LearningSample;

public class TimeProblem implements IProblem<IANN> {

	private IDataset dataset;
	private IANNFactory annFactory;

	public TimeProblem(IDataset dataset, IANNFactory annFactory) {
		this.dataset = dataset;
		this.annFactory = annFactory;
	}

	@Override
	public double getFitness(ISolution<IANN> solution) {
		return - getValue(solution);
	}

	@Override
	public double getValue(ISolution<IANN> solution) {
		IANN ann = solution.getRepresentation();
		ann.reset();
		
		double error = 0;
		
		for (LearningSample learningSample : dataset.getLearningSamples()) {
			error += sampleError(ann, learningSample);
		}
		
		return error / dataset.numberOfSamples();
	}

	private double sampleError(IANN ann, LearningSample learningSample) {
		double[] output = ann.calculateOutput(learningSample.getInput());
		double[] expectedOutput = learningSample.getExpectedOutput();
		
		double error = 0;
		for (int i = 0; i < output.length; i++) {
			error += Math.pow(output[i] - expectedOutput[i], 2);
		}
		
		return error;
	}

	@Override
	public ISolution<IANN> generateRandomSolution() {
		ISolution<IANN> solution = new ANNSolution(annFactory.createAnn(), 0d, 0d);
		solution.setFitness(getFitness(solution));
		solution.setValue(getValue(solution));
		return solution;
	}
}
