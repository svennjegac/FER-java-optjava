package hr.fer.zemris.optjava.dz8;

import java.util.List;

import hr.fer.zemris.optjava.dz8.algorithms.DifferentialEvolution;
import hr.fer.zemris.optjava.dz8.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.dz8.nn.ElmanNN;
import hr.fer.zemris.optjava.dz8.nn.FeedForwardANN;
import hr.fer.zemris.optjava.dz8.nn.IANN;
import hr.fer.zemris.optjava.dz8.nn.IActivationFunction;
import hr.fer.zemris.optjava.dz8.nn.LearningSample;
import hr.fer.zemris.optjava.dz8.nn.TanFunction;

public class ANNTrainer {

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		int populationSize;
		double errorThreshold;
		int maxGenerations;
		
		try {
			populationSize = Integer.parseInt(args[2]);
			errorThreshold = Double.parseDouble(args[3]);
			maxGenerations = Integer.parseInt(args[4]);
		} catch (NumberFormatException | NullPointerException e) {
			System.out.println("Ne valjaju parametri");
			return;
		}
		
		TimeProblem problem = createProblem(args[0], args[1]);
		if (problem == null) {
			System.out.println("Ne mogu konstruirati problem.");
			return;
		}
		
		IOptAlgorithm<IANN> algoritm =
				new DifferentialEvolution<>(
						problem,
						new RandomBaseVectorChooser(),
						new SimpleLinearCombinator(0.000005),
						new UniformCrossover(0.001),
						populationSize,
						maxGenerations,
						-errorThreshold);
		
		algoritm.run();
	}

	private static TimeProblem createProblem(String file, String arhitecture) {
		IANN ann = parseArh(arhitecture);
		if (ann == null) {
			return null;
		}
		
		List<LearningSample> learningSamples = Util.loadData(file, ann.getNumberOfInputs(), 500);
		if (learningSamples == null) {
			return null;
		}
		
		return new TimeProblem(new TimeDataset(learningSamples), new IANNFactory(ann));
	}

	private static IANN parseArh(String arhitecture) {
		if (arhitecture.matches("tdnn-[1-9][0-9]*(x[1-9][0-9]*)*x1")) {
			int[] arhNumFields = getArhNumFields(arhitecture);
			IActivationFunction[] actFunctions = getActivationFunctions(arhNumFields.length);
			
			FeedForwardANN ffAnn = new FeedForwardANN(arhNumFields, actFunctions);
			ffAnn.fullyConnectFeedforward();
			
			return ffAnn;
			
		} else if (arhitecture.matches("elman-1(x[1-9][0-9]*)*x1")) {
			int[] arhNumFields = getArhNumFields(arhitecture);
			IActivationFunction[] actFunctions = getActivationFunctions(arhNumFields.length);
			
			ElmanNN elmanAnn = new ElmanNN(arhNumFields, actFunctions);
			elmanAnn.fullyConnectFeedforward();
			
			return elmanAnn;
		}
		
		return null;
	}

	private static int[] getArhNumFields(String arhitecture) {
		try {
			String arh = arhitecture.substring(arhitecture.indexOf("-") + 1);
			String[] args = arh.split("x");
			
			int[] arhNumFields = new int[args.length];
			
			for(int i = 0; i < args.length; i++) {
				arhNumFields[i] = Integer.parseInt(args[i]);
			}
			
			return arhNumFields;
			
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private static IActivationFunction[] getActivationFunctions(int arhLength) {
		IActivationFunction[] actFunctions = new IActivationFunction[arhLength - 1];
		
		for (int i = 0; i < actFunctions.length; i++) {
			actFunctions[i] = new TanFunction();
		}
		
		return actFunctions;
	}
}