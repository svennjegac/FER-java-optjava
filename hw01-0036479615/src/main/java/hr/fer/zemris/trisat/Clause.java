package hr.fer.zemris.trisat;

public class Clause {

	private int[] indexes;

	public Clause(int[] indexes) {
		this.indexes = indexes;
	}
	
	public int getSize() {
		return indexes.length;
	}
	
	public int getLiteral(int index) {
		return indexes[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (int i = 0; i < indexes.length; i++) {
			if (assignment.get(Math.abs(indexes[i]) - 1) == (indexes[i] > 0)) {
				return true;
			}
		}
		
		return false;
	}
}
