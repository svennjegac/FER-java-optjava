package hr.fer.zemris.optjava.dz13.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public class TerminalNode extends Node {

	private Action action;

	public TerminalNode(Action action, Node leftChild, Node rightChild) {
		super(leftChild, rightChild);
		this.action = action;
	}

	@Override
	public void evaluate(World world, List<Action> actions) {
		if (lastActionIsMoveAction(actions)) {
			return;
		}
		
		actions.add(action);
	}
	
	@Override
	public int getNumberOfNodes() {
		return 1;
	}
	
	@Override
	public int getDepth() {
		return 1;
	}
	
	@Override
	public Node copy() {
		return new TerminalNode(action, null, null);
	}
	
	@Override
	public String toString() {
		return action.toString();
	}
}