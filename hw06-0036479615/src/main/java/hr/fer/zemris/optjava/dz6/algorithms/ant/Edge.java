package hr.fer.zemris.optjava.dz6.algorithms.ant;

public class Edge {

	private int id1;
	private int id2;
	
	public Edge(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public int getId1() {
		return id1;
	}
	
	public int getId2() {
		return id2;
	}
	
	public static Edge of(int id1, int id2) {
		return new Edge(id1, id2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id1 * id2);
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
		Edge other = (Edge) obj;
		
		if (equalsChecker(other.id1, other.id2)) {
			return true;
		}
		
		if (equalsChecker(other.id2, other.id1)) {
			return true;
		}
		
		return false;
	}
	
	private boolean equalsChecker(int id1, int id2) {
		if (this.id1 != id1)
			return false;
		if (this.id2 != id2)
			return false;
		return true;
	}
}
