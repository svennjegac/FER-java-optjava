package hr.fer.zemris.optjava.dz5.algorithms.genetic;

public interface IGenotipDuplicateChecker<T> {

	public boolean sameGenotips(ISolution<T> solution1, ISolution<T> solution2);
}
