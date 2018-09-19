package hr.fer.zemris.optjava.dz6.algorithms.ant;

public class Point {

	private double x;
	private double y;
	private int id;

	public Point(double x, double y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
