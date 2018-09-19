package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.neuralnetwork.ANN;
import hr.fer.zemris.optjava.dz7.neuralnetwork.IActivationFunction;
import hr.fer.zemris.optjava.dz7.neuralnetwork.SigmoidFunction;

public class Proba2 {

	public static void main(String[] args) {
		IrisProblem problem = new IrisProblem(Util.parseProblem("07-iris-formatirano.data"));
		
		ANN ann1 = new ANN(new int[] { 4, 3, 3 }, new IActivationFunction[] { new SigmoidFunction(), new SigmoidFunction() });
		ann1.fullyConnectFeedforward(() -> {return 0.1;});
		ISolution<ANN> sol1 = new ANNSolution(ann1, 0, 0);
		System.out.println(problem.getValue(sol1));
		
		ANN ann2 = ann1.copy();
		double[] w = ann2.getWeights();
		for (int i = 0; i < w.length; i++) {
			w[i] = -0.2;
		}
		
		System.out.println(ann1.getWeights().length);
		
		ann2.setWeights(w);
		ISolution<ANN> sol2 = new ANNSolution(ann2, 0, 0);
		System.out.println(problem.getValue(sol2));
	}
}
