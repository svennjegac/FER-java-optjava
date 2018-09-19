package hr.fer.zemris.optjava.dz13.impl;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.NodeUtil;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class TreeCrossover implements ICrossoverOperator<Tree> {
	
	private static final int NODES = 8;
	private static final int MAX_DEPTH = 20;
	private static final int MAX_NODES = 50;
	
	@Override
	public ISolution<Tree> crossover(ISolution<Tree> solution1, ISolution<Tree> solution2) {
		Tree t1 = solution1.getRepresentation().copy();
		Tree t2 = solution2.getRepresentation().copy();
		
		//System.out.println("POČETAK");
		
//		if (t1.getNumberOfNodes() < NODES && t2.getNumberOfNodes() < NODES) {
//			System.out.println("########## Križanje ############");
//			System.out.println("Stablo 1: " + t1);
//			System.out.println("Stablo 2: " + t2);
//		}
		
		Node parentNode = NodeUtil.getRandomNode(t1);
		if (parentNode == null || !parentNode.hasChildren()) {
			return null;
		}
		
//		if (t1.getNumberOfNodes() < NODES && t2.getNumberOfNodes() < NODES) {
//			System.out.println("Parent: " + parentNode);
//		}
		
		
		Node child = NodeUtil.getRandomNode(t2);
		if (child == null) {
			return null;
		}
		
//		if (t1.getNumberOfNodes() < NODES && t2.getNumberOfNodes() < NODES) {
//			System.out.println("Child: " + child);
//		}
		
		parentNode.setRandomChild(child);
		
//		if (t1.getNumberOfNodes() < NODES && t2.getNumberOfNodes() < NODES) {
//			System.out.println("Završno stablo 1: " + t1);
//		}
		
		if (t1.getDepth() > MAX_DEPTH || t1.getNumberOfNodes() > MAX_NODES) {
			return null;
		}
		
		//System.out.println("KRAJ");
		
		return new TreeSolution(t1, 0, 0);
	}
}
