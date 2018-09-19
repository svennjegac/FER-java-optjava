package hr.fer.zemris.optjava.dz7;

import java.util.List;

import hr.fer.zemris.optjava.dz7.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.algorithms.IProblem;
import hr.fer.zemris.optjava.dz7.algorithms.ISolution;
import hr.fer.zemris.optjava.dz7.algorithms.immune.ClonAlg;
import hr.fer.zemris.optjava.dz7.algorithms.swarm.GlobalNeighborhood;
import hr.fer.zemris.optjava.dz7.algorithms.swarm.InertionScaler;
import hr.fer.zemris.optjava.dz7.algorithms.swarm.LocalNeighborhood;
import hr.fer.zemris.optjava.dz7.algorithms.swarm.PSO;
import hr.fer.zemris.optjava.dz7.neuralnetwork.ANN;

public class ANNTrainer {
	
	private static final String PSO_A = "pso-a";
	private static final String PSO_B_D = "pso-b-";
	private static final String CLONALG = "clonalg";
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		IOptAlgorithm<ANN> alg;
		List<Data> data;
		
		try {
			//loading parameters
			data = Util.parseProblem(args[0]);
			IProblem<ANN> problem = new IrisProblem(data);
			int populationSize = Integer.parseInt(args[2]);
			double errorThreshold = Double.parseDouble(args[3]);
			int maxGenerations = Integer.parseInt(args[4]);
			
			//algorithm creation
			alg = createAlgorithm(args[1], problem, populationSize, errorThreshold, maxGenerations);
			
			if (data == null || alg == null) {
				throw new IllegalArgumentException();
			}
			
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println("Ne valjaju parametri.");
			return;
		}
		
		ISolution<ANN> solution = alg.run();
		classifyAll(solution, data);
	}

	private static void classifyAll(ISolution<ANN> solution, List<Data> data) {
		ANN ann = solution.getRepresentation();
		int wrongClassifications = 0;
		
		for (Data line : data) {
			double[] output = ann.calculateOutput(line.getInput());
			boolean wrong = false;
			
			for (int i = 0; i < output.length; i++) {
				if (output[i] > 0.5) {
					output[i] = 1;
				} else {
					output[i] = 0;
				}
			}
			
			for (int i = 0; i < output.length; i++) {
				if (Math.abs(output[i] - line.getOutput()[i] ) > 0.5) {
					wrongClassifications++;
					wrong = true;
					break;
				}
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("Real: ");
			for (double d : line.getOutput()) {
				sb.append(d + ", ");
			}
			sb.append(" $ Classified: ");
			for (double d : output) {
				sb.append(d + ", ");
			}
			if (wrong) {
				sb.append(" $ wrong");
			}
			
			System.out.println(sb.toString());
		}
		
		System.out.println("Broj krivih klasifikacija: " + wrongClassifications);
		IrisProblem pro = new IrisProblem(data);
		System.out.println("Greška: " + pro.getValue(solution));
	}

	private static IOptAlgorithm<ANN> createAlgorithm(String algorithm, IProblem<ANN> problem, int populationSize,
			double errorThreshold, int maxGenerations) {
		
		IOptAlgorithm<ANN> alg = null;
		
		switch (algorithm) {
		case PSO_A:
			alg = new PSO<>(
					new GlobalNeighborhood<>(),
					problem,
					new InertionScaler(0.01, 0.5, maxGenerations),
					1.5,
					1.5,
					populationSize,
					errorThreshold,
					maxGenerations);
			break;
		case CLONALG:
			alg = new ClonAlg<>(
					problem,
					errorThreshold,
					maxGenerations,
					populationSize,
					1,
					2d,
					0.5d);
			break;
		default:
			if (!algorithm.matches(PSO_B_D + "[0-9]+")) {
				break;
			}
			
			alg = new PSO<>(
					new LocalNeighborhood<>(Integer.parseInt(algorithm.substring(PSO_B_D.length()))),
					problem,
					new InertionScaler(0.01, 0.5, maxGenerations),
					1.8,
					1.8,
					populationSize,
					errorThreshold,
					maxGenerations);
		}
		
		return alg;
	}
}
