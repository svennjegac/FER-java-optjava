package hr.fer.zemris.optjava.dz6.algorithms.ant;

public class EdgeData {

	private double length;
	private double pheromoneValue;
	
	public EdgeData(Point p1, Point p2, double pheromoneValue) {
		this.pheromoneValue = pheromoneValue;
		initLength(p1, p2);
	}

	private void initLength(Point p1, Point p2) {
		length = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}
	
	public double getLength() {
		return length;
	}
	
	public double getPheromoneValue() {
		return pheromoneValue;
	}
	
	public void setPheromoneValue(double pheromoneValue) {
		this.pheromoneValue = pheromoneValue;
	}
}
