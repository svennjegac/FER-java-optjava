package hr.fer.zemris.optjava.dz13.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;

public class Prog3Node extends Node {

	private Node middleChild;
	
	public Prog3Node(Node leftChild, Node rightChild, Node middleChild) {
		super(leftChild, rightChild);
		this.middleChild = middleChild;
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
		
		middleChild.evaluate(world, actions);
		
		if (lastActionIsMoveAction(actions)) {
			return;
		}
		
		rightChild.evaluate(world, actions);	
	}
	
	@Override
	public void setRandomChild(Node child) {
		Random rand = new Random();
		double prob = rand.nextDouble();
		
		if (prob < 0.33) {
			leftChild = child;
		} else if (prob < 0.66) {
			middleChild = child;
		} else {
			rightChild = child;
		}
	}
	
	@Override
	public int getDepth() {
		int left = leftChild.getDepth();
		int middle = middleChild.getDepth();
		int right = rightChild.getDepth();
		
		int max;
		
		if (left >= right && left >= middle) {
			max = left;
		} else if (right >= left && right >= middle) {
			max = right;
		} else {
			max = middle;
		}
		
		return max + 1;
	}
	
	@Override
	public int getNumberOfNodes() {
		return leftChild.getNumberOfNodes() + middleChild.getNumberOfNodes() + rightChild.getNumberOfNodes() + 1;
	}
	
	@Override
	public Node getRandomChild() {
		Random rand = new Random();
		double prob = rand.nextDouble();
		
		if (prob < 0.33) {
			return leftChild;
		} else if (prob < 0.66) {
			return middleChild;
		} else {
			return rightChild;
		}
	}
	
	@Override
	public Node copy() {
		return new Prog3Node(leftChild.copy(), rightChild.copy(), middleChild.copy());
	}
	
	@Override
	public String toString() {
		return "P3(" +
				leftChild.toString() + ", " +
				middleChild.toString() + ", " +
				rightChild.toString() + ")";
	}
}
