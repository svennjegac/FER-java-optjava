package hr.fer.zemris.optjava.dz13.nodes;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public abstract class Node {

	Node leftChild;
	Node rightChild;
	
	public Node(Node leftChild, Node rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	
	public Node getRandomChild() {
		Random rand = new Random();
		double prob = rand.nextDouble();
		
		if (prob < 0.5) {
			return getLeftChild();
		} else {
			return getRightChild();
		}
	}

	public abstract void evaluate(World world, List<Action> actions);
	
	public abstract Node copy();
	
	public boolean lastActionIsMoveAction(List<Action> actions) {
		if (!actions.isEmpty()) {
			Action lastAction = actions.get(actions.size() - 1);
			
			if (lastAction == Action.MOVE) {
				return true;
			}
		}
		
		return false;
	}

	public boolean hasChildren() {
		return leftChild != null;
	}
	
	public void setRandomChild(Node child) {
		Random rand = new Random();
		
		if (rand.nextDouble() < 0.5) {
			leftChild = child;
		} else {
			rightChild = child;
		}
	}
	
	public int getDepth() {
		int leftDepth = leftChild.getDepth();
		int rightDepth = rightChild.getDepth();
		
		return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
	}
	
	public int getNumberOfNodes() {
		return leftChild.getNumberOfNodes() + 1 + rightChild.getNumberOfNodes();
	}
}
