package hr.fer.zemris.optjava.dz13.impl;

import java.util.Random;

import hr.fer.zemris.optjava.dz13.genetic.IMutationOperator;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.NodeUtil;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class TreeMutation implements IMutationOperator<Tree> {

	private static final int MAX_DEPTH = 20;
	private static final int MAX_NODES = 200;
	
	private Random rand = new Random();
	
	@Override
	public ISolution<Tree> mutate(ISolution<Tree> solution) {
		Tree tree = solution.getRepresentation().copy();
		
		Node parent = NodeUtil.getRandomNode(tree);
		if (parent == null || !parent.hasChildren()) {
			return null;
		}
		
//		System.out.println("MUTACIJA");
//		
//		System.out.println("DRVO: " + tree);
//		System.out.println("PARENT:" + parent);
//		
		Node node = NodeUtil.createGrowFunctionNode(MAX_DEPTH - (tree.getDepth() - parent.getDepth()), rand.nextDouble() > 0.5 ? true : false);
		parent.setRandomChild(node);
		
//		System.out.println("NODE: " + node);
//		System.out.println("PARENT 2: " + parent);
//		
//		System.out.println("DRVO 2: " + tree);
		
		if (tree.getDepth() > MAX_DEPTH || tree.getNumberOfNodes() > MAX_NODES) {
			return null;
		}
		
		//System.out.println("KRAJ");
		
		return new TreeSolution(tree, 0, 0);
	}
}
