package hr.fer.zemris.optjava.dz13.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public class Tree {

	private Node rootNode;
	
	public Tree(Node rootNode) {
		this.rootNode = rootNode;
	}

	public List<Action> evaluate(World world) {
		List<Action> actions = new ArrayList<>();
		rootNode.evaluate(world, actions);
		return actions;
	}
	
	public Node getRootNode() {
		return rootNode;
	}
	
	public int getDepth() {
		return rootNode.getDepth();
	}
	
	public int getNumberOfNodes() {
		return rootNode.getNumberOfNodes();
	}
	
	public Tree copy() {
		return new Tree(rootNode.copy());
	}
	
	@Override
	public String toString() {
		return rootNode.toString();
	}
}
