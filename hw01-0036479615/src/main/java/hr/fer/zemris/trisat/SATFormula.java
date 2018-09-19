package hr.fer.zemris.trisat;

public class SATFormula {

	private int numberOfVariables;
	private Clause[] clauses;
	
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}
	
	public int getNumberOfVariables() {
		return numberOfVariables;
	}
	
	public int getNumberOfClauses() {
		return clauses.length;
	}
	
	public Clause getClause(int index) {
		return clauses[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for (int i = 0; i < clauses.length; i++) {
			if (!clauses[i].isSatisfied(assignment)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
