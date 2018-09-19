package hr.fer.zemris.optjava.dz13.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public class IfFoodAheadNode extends Node {
	
	public IfFoodAheadNode(Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
	}
	
	@Override
	public void evaluate(World world, List<Action> actions) {
		if (lastActionIsMoveAction(actions)) {
			return;
		}
		
		if (world.foodAhead(actions)) {
			leftChild.evaluate(world, actions);
		} else {
			rightChild.evaluate(world, actions);
		}
	}
	
	@Override
	public Node copy() {
		return new IfFoodAheadNode(leftChild.copy(), rightChild.copy());
	}
	
	@Override
	public String toString() {
		return "IfFoodAhead(" + leftChild.toString() + ", " + rightChild.toString() + ")";
	}
}
