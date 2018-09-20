package hr.fer.zemris.optjava.dz13.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public class Prog2Node extends Node {
	
	public Prog2Node(Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
	}
	
	@Override
	public void evaluate(World world, List<Action> actions) {
		if (lastActionIsMoveAction(actions)) {
			return;
		}
		
		leftChild.evaluate(world, actions);
		
		if (lastActionIsMoveAction(actions)) {
			return;
		}
		
		rightChild.evaluate(world, actions);
	}

	@Override
	public Node copy() {
		return new Prog2Node(leftChild.copy(), rightChild.copy());
	}
	
	@Override
	public String toString() {
		return "P2(" + leftChild.toString() + ", " + rightChild.toString() + ")";
	}
}
