package hr.fer.zemris.optjava.dz6;

import java.util.Iterator;

import hr.fer.zemris.optjava.dz6.algorithms.ant.AbstractAnt;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Edge;
import hr.fer.zemris.optjava.dz6.algorithms.ant.IAnt;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;

public class Ant extends AbstractAnt<int[]> {
	
	public Ant(ISolution<int[]> solution, int startPoint) {
		super(solution, startPoint);
	}
	
	@Override
	public IAnt<int[]> copy() {
		return new Ant(getSolution().copy(), getStartPoint());
	}

	@Override
	public Iterator<Edge> iterator() {
		return new MyAntIterator();
	}
	
	private class MyAntIterator implements Iterator<Edge> {

		private int[] cities;
		private int index;
		
		public MyAntIterator() {
			cities = getSolution().getRepresentation();
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index <= cities.length;
		}

		@Override
		public Edge next() {
			if (index < cities.length - 1) {
				index++;
				return Edge.of(cities[index - 1], cities[index]);
			}
			
			index++;
			return Edge.of(cities[0], cities[cities.length - 1]);
		}
		
	}
}
