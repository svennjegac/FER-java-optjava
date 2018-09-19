package hr.fer.zemris.optjava.dz13;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.rmi.CORBA.Util;
import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;
import hr.fer.zemris.optjava.dz13.genetic.GenerationElitisticGA;
import hr.fer.zemris.optjava.dz13.genetic.IOptAlgorithm;
import hr.fer.zemris.optjava.dz13.genetic.IProblem;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.genetic.TournamentSelOp;
import hr.fer.zemris.optjava.dz13.impl.PopulationGenerator;
import hr.fer.zemris.optjava.dz13.impl.TreeCrossover;
import hr.fer.zemris.optjava.dz13.impl.TreeMutation;
import hr.fer.zemris.optjava.dz13.impl.TreeReproduction;
import hr.fer.zemris.optjava.dz13.impl.TreeSolution;
import hr.fer.zemris.optjava.dz13.impl.WorldProblem;
import hr.fer.zemris.optjava.dz13.nodes.IfFoodAheadNode;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.Prog3Node;
import hr.fer.zemris.optjava.dz13.nodes.TerminalNode;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class AntTrailGA {

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		World world;
		int maxGenerations;
		int populationSize;
		int fitnessThreshold;
		
		try {
			world = new World(args[0]);
			maxGenerations = Integer.parseInt(args[1]);
			populationSize = Integer.parseInt(args[2]);
			fitnessThreshold = Integer.parseInt(args[3]);
		} catch (IllegalArgumentException e) {
			System.out.println("Nepravilno zadani argumenti.");
			return;
		}
		
		IProblem<Tree> problem = new WorldProblem(world);
		
		IOptAlgorithm<Tree> algorithm =
				new GenerationElitisticGA<>(
						populationSize,
						new TournamentSelOp<>(4, true),
						new TreeCrossover(),
						new TreeMutation(),
						new TreeReproduction(),
						new PopulationGenerator(3, populationSize, problem),
						problem,
						fitnessThreshold,
						maxGenerations);
		
		ISolution<Tree> solution = algorithm.run();
		
//		Node node = 
//	            new IfFoodAheadNode(
//	                    new TerminalNode(Action.MOVE, null, null),
//	                    new Prog3Node(
//	                            new TerminalNode(Action.RIGHT, null, null),
//	                            new Prog3Node(
//	                                    new IfFoodAheadNode(
//	                                            new TerminalNode(Action.MOVE, null, null),
//	                                            new TerminalNode(Action.RIGHT, null, null)),
//	                                    new TerminalNode(Action.MOVE, null, null),
//	                                    new IfFoodAheadNode(
//	                                            new TerminalNode(Action.MOVE, null, null),
//	                                            new TerminalNode(Action.RIGHT, null, null))),
//	                            new IfFoodAheadNode(
//	                                    new TerminalNode(Action.MOVE, null, null),
//	                                    new TerminalNode(Action.RIGHT, null, null))));
//
//	        Tree tree = new Tree(node);
//	        ISolution<Tree> solution = new TreeSolution(tree, 0, 0);
//	        System.out.println(solution.getRepresentation());
//	        System.out.println(problem.getFitness(solution));
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(args[4]))))) {
			bw.write(solution.getRepresentation().toString());
		} catch (IOException e) {
			System.out.println("Ne mogu napisati rez datoteku.");
		}
		
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				new WorldGUI(world, solution).setVisible(true);
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
