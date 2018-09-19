package hr.fer.zemris.optjava.dz13.nodes;

import java.security.cert.CRLReason;
import java.util.Random;

import hr.fer.zemris.optjava.dz13.game.Action;

public class NodeUtil {
	
	private static final double IF_NODE_PROB = 0.6;
	private static final double PROG_2_NODE_PROB = 0.3;
	
	private static final double GROW_STOP_PROB = 0.95;
	private static final double NEXT_NODE_PROB = 0.95;
	
	private static final double MOVE_PROB = 0.2;
	private static final double RIGHT = 0.6;
	
	private static final Action[] ACTIONS;
	private static Random rand = new Random(System.currentTimeMillis());
	
	static {
		ACTIONS = new Action[3];
		ACTIONS[0] = Action.LEFT;
		ACTIONS[1] = Action.RIGHT;
		ACTIONS[2] = Action.MOVE;
	}
	
	public static Tree createFullTree(int depth) {
		Node functionNode = createFullFunctionNode(depth);
		return new Tree(functionNode);
	}
	
	public static Tree createGrowTree(int depth) {
		Node functionNode = createGrowFunctionNode(depth, true);
		return new Tree(functionNode);
	}
	
	public static Node createTerminalNode() {
		double prob = rand.nextDouble();
		
		Action action = null;
		
		if (prob < MOVE_PROB) {
			action = ACTIONS[0];
		} else if (prob < MOVE_PROB + RIGHT) {
			action = ACTIONS[1];
		} else {
			action = ACTIONS[2];
		}
		
		return new TerminalNode(action, null, null);
	}
	
	public static Node createFullFunctionNode(int depth) {
		double prob = rand.nextDouble();
		
		if (depth == 0) {
			return createTerminalNode();
		}
		
		if (prob < IF_NODE_PROB) {
			return new IfFoodAheadNode(createFullFunctionNode(depth - 1), createFullFunctionNode(depth - 1));
		} else if (prob < IF_NODE_PROB + PROG_2_NODE_PROB) {
			return new Prog2Node(createFullFunctionNode(depth - 1), createFullFunctionNode(depth - 1));
		} else {
			return new Prog3Node(createFullFunctionNode(depth - 1), createFullFunctionNode(depth - 1), createFullFunctionNode(depth - 1));
		}
	}
	
	public static Node createGrowFunctionNode(int depth, boolean start) {
		double prob = rand.nextDouble();
		
		if (depth == 0) {
			return createTerminalNode();
		}
		
		if (prob < IF_NODE_PROB) {
			return new IfFoodAheadNode(growNode(depth, start), growNode(depth, start));
		} else if (prob < IF_NODE_PROB + PROG_2_NODE_PROB) {
			return new Prog2Node(growNode(depth, start), growNode(depth, start));
		} else {
			return new Prog3Node(growNode(depth, start), growNode(depth, start), growNode(depth, start));
		}
	}
	
	public static Node growNode(int depth, boolean start) {
		return stopGrow(start) ? createTerminalNode() : createGrowFunctionNode(depth - 1, false);
	}
	
	public static boolean stopGrow(boolean start) {
		if (start) {
			return false;
		}
		
		return rand.nextDouble() < GROW_STOP_PROB;
	}
	
	public static Node getRandomNode(Tree t1) {
		Node node = t1.getRootNode();
		
		while (rand.nextDouble() < NEXT_NODE_PROB) {
			Node child = node.getRandomChild();
			
			if (child == null) {
				return node;
			}
			
			node = child;
		}
		
		return node;
	}
}
