package hr.fer.zemris.optjava.dz7;

import java.util.List;

import hr.fer.zemris.optjava.dz7.algorithms.IProblem;
import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.neuralnetwork.ANN;
import hr.fer.zemris.optjava.dz7.neuralnetwork.IActivationFunction;
import hr.fer.zemris.optjava.dz7.neuralnetwork.SigmoidFunction;

public class IrisProblem implements IProblem<ANN> {

	private List<Data> data;
	
	public IrisProblem(List<Data> data) {
		this.data = data;
	}

	@Override
	public double getFitness(ISolution<ANN> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<ANN> solution) {
		ANN ann = solution.getRepresentation();
		
		double errorSum = 0;
		
		for (Data singleData : data) {
			errorSum += getSingleError(ann, singleData);
		}
		
		return errorSum / data.size();
	}

	private double getSingleError(ANN ann, Data singleData) {
		double[] annOutput = ann.calculateOutput(singleData.getInput());
		
		double errorSum = 0;
		
		for (int i = 0; i < annOutput.length; i++) {
			errorSum += Math.pow(annOutput[i] - singleData.getOutput()[i], 2);
		}
		
		return errorSum;
	}

	@Override
	public ISolution<ANN> generateRandomSolution() {
		ANN ann = new ANN(
				new int[] { 4, 3, 3 },
				new IActivationFunction[] { new SigmoidFunction(), new SigmoidFunction() });
		ann.fullyConnectFeedforward();
		
		ISolution<ANN> sol = new ANNSolution(ann, 0, 0);
		sol.setFitness(getFitness(sol));
		sol.setValue(getValue(sol));
		sol.setPersonalBestFitness(sol.getFitness());
		sol.setPersonalBestValue(sol.getValue());
		
		return sol;
	}
}
